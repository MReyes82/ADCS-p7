package Tests;

import Pages.AuthPage;
import Pages.InventoryPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InventoryTest
{
    WebDriver driver;
    InventoryPage inventoryPage;
    AuthPage authPage;

    @BeforeMethod
    public void setUp()
    {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
        authPage = new AuthPage(driver, 10);
        inventoryPage = new InventoryPage(driver, 10);
        accessToInventory();
    }

    @Test
    public void testFilterLtoH()
    {
        inventoryPage.waitForFilterDropdown();
        inventoryPage.clickDropdown();
        inventoryPage.clickDropdownOption("LtoH");
        inventoryPage.waitForInventory();
        var firstElement = inventoryPage.getInventoryListElement(0);
        if (firstElement == null)
        {
            System.err.println("[testFilterLtoHError] ERROR al obtener el primer elemento del inventario");
            return;
        }
        var itemName = inventoryPage.getItemName(firstElement);
        Assert.assertEquals(itemName, "Sauce Labs Onesie");
    }

    @AfterMethod
    public void tearDown()
    {
        driver.quit();
    }

    public void accessToInventory()
    {
        authPage.enterUsername("standard_user");
        authPage.enterPassword("secret_sauce");
        authPage.clickLoginButton();
        authPage.waitForRedirection();
    }
}
