package Pages;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthPage extends BasePage
{
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.xpath("//input[@class='submit-button btn_action']");
    private final By errorButton = By.xpath("//button[@class='error-button']");
    private final By errorH3 = By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3");

    public AuthPage(WebDriver driver, int seconds)
    {
        super(driver, seconds);
    }

    /**
     * Ingresa el nombre de usuario en el campo de texto.
     * @param username El nombre de usuario a ingresar
     */
    public void enterUsername(String username)
    {
        type(usernameField, username);
    }
    // Casi siempre es "secret_sauce" pero hay un TC donde se debe introducir algo diferente
    /**
     * Ingresa la contraseña en el campo de texto.
     * @param password La contraseña a ingresar
     */
    public void enterPassword(String password)
    {
        type(passwordField, password);
    }

    /**
     * Hace clic en el botón de inicio de sesión.
     */
    public void clickLoginButton()
    {
        click(loginButton);
    }

    /**
     * Espera a que la página se redirija a la página de inventario.
     */
    public void waitForRedirection()
    {
        waitForCurrentUrl("inventory");
    }

    /**
     * Espera a que el botón de error sea visible.
     */
    public void waitForErrorButton()
    {
        waitForElementVisibility(errorButton);
    }

    /**
     * Espera a que el encabezado H3 de error sea visible.
     */
    public void waitForErrorH3()
    {
        waitForElementVisibility(errorH3);
    }

    public String getUrlUtil()
    {
        return driver.getCurrentUrl();
    }

    public boolean isH3ErrorDisplayed()
    {
        return isDisplayed(errorH3);
    }
}
