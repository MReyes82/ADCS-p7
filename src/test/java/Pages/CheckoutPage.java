package Pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    private final By checkoutBtn = By.id("checkout");
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueBtn = By.id("continue");
    private final By finishBtn = By.id("finish");
    private final By orderConfirmation = By.id("checkout_complete_container");
    private final By errorMessage = By.xpath("//div[@class='error-message-container error']");

    public CheckoutPage(WebDriver driver, int seconds) {
        super(driver, seconds);
    }

    public void clickCheckout() {
        click(checkoutBtn);
    }

    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(postalCodeField, postalCode);
    }

    public void clickContinue() {
        click(continueBtn);
    }

    public void clickFinish() {
        click(finishBtn);
    }

    public void waitForOrderConfirmation() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmation));
    }

    public void waitForErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
    }

    public boolean isElementDisplayed(String elementName) {
        By element = switch(elementName) {
            case "orderConfirmation" -> orderConfirmation;
            case "errorMessage" -> errorMessage;
            default -> throw new IllegalArgumentException("Unknown element: " + elementName);
        };
        return driver.findElement(element).isDisplayed();
    }
}
