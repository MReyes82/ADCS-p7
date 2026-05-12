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
    }

    @Test
    public void testFilterLtoH()
    {
        accessToInventory("standard_user");
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
    @Test
    public void testFilterLtoHNegative()
    {
        accessToInventory("problem_user");
        inventoryPage.waitForFilterDropdown();
        inventoryPage.clickDropdown();
        inventoryPage.clickDropdownOption("LtoH");
        inventoryPage.waitForInventory();
        var firstElement = inventoryPage.getInventoryListElement(0);
        if (firstElement == null)
        {
            System.err.println("[testFilterLtoHNegativeError] ERROR al obtener el primer elemento del inventario");
            return;
        }
        var itemName = inventoryPage.getItemName(firstElement);
        Assert.assertNotEquals(itemName, "Sauce Labs Onesie"); // invertimos el assert para verificar que el nombre del primer elemento no es "Sauce Labs Onesie"
    }
    @Test
    public void testFilterHtoLPositive()
    {
        accessToInventory("standard_user");
        inventoryPage.waitForFilterDropdown();
        inventoryPage.clickDropdown();
        inventoryPage.clickDropdownOption("HtoL");
        inventoryPage.waitForInventory();
        var firstElement = inventoryPage.getInventoryListElement(0);
        if (firstElement == null)
        {
            System.err.println("[testFilterHtoLPositiveError] ERROR al obtener el primer elemento del inventario");
            return;
        }
        var itemName = inventoryPage.getItemName(firstElement);
        Assert.assertEquals(itemName, "Sauce Labs Fleece Jacket");
    }

    @Test
    public void testFilterHtoLNegative()
    {
        accessToInventory("problem_user");
        inventoryPage.waitForFilterDropdown();
        inventoryPage.clickDropdown();
        inventoryPage.clickDropdownOption("HtoL");
        inventoryPage.waitForInventory();
        var firstElement = inventoryPage.getInventoryListElement(0);
        if (firstElement == null)
        {
            System.err.println("[testFilterHtoLNegativeError] ERROR al obtener el primer elemento del inventario");
            return;
        }
        var itemName = inventoryPage.getItemName(firstElement);
        Assert.assertNotEquals(itemName, "Sauce Labs Fleece Jacket");
    }
    @Test
    public void testFilterLtoHError()
    {
        accessToInventory("error_user");
        inventoryPage.waitForFilterDropdown();
        inventoryPage.clickDropdown();
        inventoryPage.clickDropdownOption("LtoH");
        inventoryPage.waitForAlert();
        inventoryPage.dismissAlert();
        var firstElement = inventoryPage.getInventoryListElement(0);
        if (firstElement == null)
        {
            System.err.println("[testFilterLtoHError] ERROR al obtener el primer elemento del inventario");
            return;
        }
        var itemName = inventoryPage.getItemName(firstElement);
        Assert.assertEquals(itemName, "Sauce Labs Backpack");
    }

    @AfterMethod
    public void tearDown()
    {
        driver.quit();
    }

    public void accessToInventory(String username)
    {
        authPage.enterUsername(username);
        authPage.enterPassword("secret_sauce");
        authPage.clickLoginButton();
        authPage.waitForRedirection();
    }
}
