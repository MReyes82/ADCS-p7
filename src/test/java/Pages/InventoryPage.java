package Pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class InventoryPage extends BasePage
{
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.xpath("//input[@class='submit-button btn_action']");
    private final By filterDropdown = By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select");
    // Definir el locator para las opciones del dropdown
    private final By dropdownAtoZ = By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[1]");
    private final By dropdownZtoA = By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[2]");
    private final By dropdownLtoH = By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[3]");
    private final By dropdownHtoL = By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[4]");
    private final By inventoryList = By.xpath("//div[@class='inventory_list']");
    private final By inventoryListElement = By.cssSelector(".inventory_item");

    public InventoryPage(WebDriver driver, int seconds)
    {
        super(driver, seconds);
    }

    public void waitForFilterDropdown()
    {
        waitForElementVisibility(filterDropdown);
    }

    public void clickDropdown()
    {
        click(filterDropdown);
    }
    public void clickDropdownOption(String option)
    {
        switch(option)
        {
            case "AtoZ":
                click(dropdownAtoZ);
                break;

            case "ZtoA":
                click(dropdownZtoA);
                break;

            case "LtoH":
                click(dropdownLtoH);
                break;

            case "HtoL":
                click(dropdownHtoL);
                break;
            default:
                System.err.println("[InventoryPage]: Opcion de dropdown no valida" + option);
                break;
        }
    }

    public void waitForInventory()
    {
        waitForElementVisibility(inventoryList);
    }

    public String getItemName(WebElement inventoryItem)
    {
        return inventoryItem.findElement(By.cssSelector(".inventory_item_name ")).getText();
    }
    // Esperar
    public void waitForAlert()
    {
        wait.until(ExpectedConditions.alertIsPresent());
    }
    // dismiss alert
    public void dismissAlert()
    {
        driver.switchTo().alert().accept();
    }

    // Util para obtener un N elemento de la lista de Inventory Items
    public WebElement getInventoryListElement(int index)
    {
        if (index < 0)
        {
            System.err.println("[getInventoryListElement] ERROR: index no puede ser negativo");
            return null;
        }

        List<WebElement> items = findElement(inventoryList).findElements(inventoryListElement);
        if (items.isEmpty()) // guardrail
        {
            System.err.println("[getInventoryListElement] ERROR al obtener elementos del dropdown");
            return null;
        }

        if (index >= items.size())
        {
            System.err.println("[getInventoryListElement] ERROR: index fuera de rango. Max index: " + (items.size() - 1));
            return null;
        }

        return items.get(index);
    }
}
