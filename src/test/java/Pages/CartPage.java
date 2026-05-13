package Pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

    private final By addToCartBtn = By.id("add-to-cart-sauce-labs-backpack");
    private final By removeCartBtn = By.id("remove-sauce-labs-backpack");
    private final By goToCartLink = By.xpath("//a[@class='shopping_cart_link']");
    private final By badgeCart = By.xpath("//span[@class='shopping_cart_badge']");
    private final By cardItem = By.xpath("//div[@class='cart_item_label']");

    public CartPage(WebDriver driver, int seconds)
    {
        super(driver, seconds);
    }

    public void addToCart(){
        click(addToCartBtn);
    }

    public void waitForCart(){
        wait.until(ExpectedConditions.and(
                ExpectedConditions.textToBePresentInElementLocated(badgeCart, "1"),
                ExpectedConditions.visibilityOfElementLocated(removeCartBtn)));

    }

    public String getNumberBadgeCart(){
        return driver.findElement(badgeCart).getText();
    }

    public boolean isElementDisplayed(String elementName){
        By element = switch(elementName) {
            case "removeBtn" -> removeCartBtn;
            case "addToCartBtn" -> addToCartBtn;
            case "cardItem" -> cardItem;
            default -> throw new IllegalArgumentException("Unknown element: " + elementName);
        };
        return driver.findElement(element).isDisplayed();
    }

    public void removeFromCart(){
        click(removeCartBtn);
    }

    public void goToCart(){
        click(goToCartLink);
    }

    public void waitForBadge(){
        waitForElementInvisibility(badgeCart);
    }

    public boolean isBadgeCartDisplayed(){
        return driver.findElements(badgeCart).isEmpty();
    }

    public boolean isCardItemDisplayed(){
        return driver.findElements(cardItem).isEmpty();
    }

}
