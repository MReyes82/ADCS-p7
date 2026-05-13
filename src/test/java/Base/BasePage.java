package Base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage
{
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver, int seconds)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }
    // Encontrar WebElement con wait explicito (ToBeClickable)
    protected WebElement findElement(By locator)
    {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    // Mandar señal de click al elemento (usando wait explicito)
    protected void click(By locator)
    {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // Mandar valor de string al elemento
    protected void type(By locator, String text)
    {
        findElement(locator).sendKeys(text);
    }
    // Obtener el valor de texto de un WebElement
    protected String getText(By locator)
    {
        return findElement(locator).getText();
    }
    // Usar wait hasta que el elemento sea visible
    protected void waitForElementVisibility(By locator)
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementInvisibility(By locator)
    {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    // Revisa si el WebElement esta visible
    protected boolean isDisplayed(By locator)
    {
        try
        {
            return findElement(locator).isDisplayed();
        } catch (Exception e)
        {
            return false;
        }
    }
    // Usar espera explicita para el cambio de url
    protected void waitForCurrentUrl(String urlFragment)
    {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }
}
