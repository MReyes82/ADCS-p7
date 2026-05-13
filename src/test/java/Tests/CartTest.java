package Tests;

import Pages.AuthPage;
import Pages.CartPage;
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

public class CartTest {
    WebDriver driver;
    CartPage cartPage;
    AuthPage authPage;


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
        driver.get("https://www.saucedemo.com");
        authPage = new AuthPage(driver, 10);
        cartPage = new CartPage(driver, 10);
    }

    @Test // P-CART-01P
    public void addItemToCart(){
        loginAccess("standard_user");
        cartPage.addToCart();
        cartPage.waitForCart();
        Assert.assertEquals(cartPage.getNumberBadgeCart(), "1");
        Assert.assertTrue(cartPage.isElementDisplayed("removeBtn"));
    }

    @Test // P-CART-02P
    public void removeItemFromCart(){
        loginAccess("standard_user");
        cartPage.addToCart();
        cartPage.waitForCart();
        cartPage.removeFromCart();
        cartPage.waitForBadge();
        Assert.assertTrue(cartPage.isElementDisplayed("addToCartBtn"));
    }

    @Test // P-CART-04P
    public void validateRemoveSpecificProductOnCart(){
        loginAccess("error_user");
        cartPage.addToCart();
        cartPage.removeFromCart();
        Assert.assertTrue(cartPage.isBadgeCartDisplayed(), "El badge del carrito sigue visible");
        Assert.assertFalse(cartPage.isElementDisplayed("removeBtn"), "No cambió a Add to cart");

    }

    @Test // P-CART-06P
    public void validateRemoveSpecificProductOnCartII(){
        loginAccess("error_user");
        cartPage.addToCart();
        cartPage.goToCart();
        cartPage.removeFromCart();
        Assert.assertTrue(cartPage.isCardItemDisplayed(), "El item no se eliminó correctamente");
    }

    @AfterMethod
    public void tearDown(){
        if(driver != null)
            driver.quit();

    }

    public void loginAccess(String username){
        authPage.enterUsername(username);
        authPage.enterPassword("secret_sauce");
        authPage.clickLoginButton();
    }

}
