package Tests;

import Pages.AuthPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AuthTest
{
    private WebDriver driver;
    private AuthPage authPage;

    @BeforeMethod
    public void setUp()
    {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
        authPage = new AuthPage(driver, 10);
    }

    @Test(priority = 1) // P-AUTH-01P
    public void testLoginPositive()
    {
        authPage.enterUsername("standard_user");
        authPage.enterPassword("secret_sauce");
        authPage.clickLoginButton();
        authPage.waitForRedirection();
        Assert.assertEquals(authPage.getUrlUtil(), "https://www.saucedemo.com/inventory.html");
    }

    @Test(priority = 2) //P-AUTH-01N

    public void testLoginNegative()
    {
        authPage.enterUsername("user");
        authPage.enterPassword("password");
        authPage.clickLoginButton();
        authPage.waitForErrorButton();
        // Assert para verificar que el login fallo y sigue en la misma url
        Assert.assertEquals(authPage.getUrlUtil(), "https://www.saucedemo.com/");
    }

    @Test(priority = 3) //P-AUTH-02N
    public void testLockedUser()
    {
        authPage.enterUsername("locked_out_user");
        authPage.enterPassword("secret_sauce");
        authPage.clickLoginButton();
        authPage.waitForErrorH3();
        Assert.assertTrue(authPage.isH3ErrorDisplayed());
    }

    @AfterMethod
    public void tearDown()
    {
        if (driver != null)
            driver.quit();
    }
}
