package Tests;

import Pages.AuthPage;
import Pages.CartPage;
import Pages.CheckoutPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CheckoutTest {
    WebDriver driver;
    AuthPage authPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    @BeforeMethod
    public void setUp(){
        final Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        chromePrefs.put("profile.password_manager_leak_detection", false);
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.saucedemo.com/");
        
        authPage = new AuthPage(driver, 10);
        cartPage = new CartPage(driver, 10);
        checkoutPage = new CheckoutPage(driver, 10);
    }

    @Test // P-CHOUT-01P
    public void successfullyCheckout(){
        loginAccess("standard_user");
        cartPage.addToCart();

        cartPage.goToCart();
        checkoutPage.clickCheckout();
        checkoutPage.fillCheckoutInfo("Juan", "Perez", "12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        checkoutPage.waitForOrderConfirmation();
        Assert.assertTrue(checkoutPage.isElementDisplayed("orderConfirmation"));
    }

    @Test // P-CHOUT-01N
    public void emptyFieldCheckout(){
        loginAccess("standard_user");
        cartPage.addToCart();

        cartPage.goToCart();
        checkoutPage.clickCheckout();
        checkoutPage.clickContinue();

        checkoutPage.waitForErrorMessage();
        Assert.assertTrue(checkoutPage.isElementDisplayed("errorMessage"));
    }

    @Test // P-CHOUT-02P
    public void changeFieldCheckout(){
        loginAccess("problem_user");
        cartPage.addToCart();

        cartPage.goToCart();
        checkoutPage.clickCheckout();
        checkoutPage.fillCheckoutInfo("Juan", "Perez", "12345");
        checkoutPage.clickContinue();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html");
    }

    @Test // P-CHOUT-03P
    public void errorCheckout(){
        loginAccess("error_user");
        cartPage.addToCart();

        cartPage.goToCart();
        checkoutPage.clickCheckout();
        checkoutPage.fillCheckoutInfo("Juan", "Perez", "12345");
        checkoutPage.clickContinue();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");
    }

    @Test // P-CHOUT-02N
    public void failCheckout(){
        loginAccess("error_user");
        cartPage.addToCart();

        cartPage.goToCart();
        checkoutPage.clickCheckout();
        checkoutPage.fillCheckoutInfo("Juan", "", "12345");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isElementDisplayed("errorMessage"));
    }

    @AfterMethod
    public void tearDown()
    {
        if (driver != null)
            driver.quit();
    }

    public void loginAccess(String username){
        authPage.enterUsername(username);
        authPage.enterPassword("secret_sauce");
        authPage.clickLoginButton();
    }
}
