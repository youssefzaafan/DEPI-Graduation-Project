package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.cssSelector(".oxd-text.oxd-text--span.oxd-input-field-error-message.oxd-input-group__message");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        try {
            wait.until(ExpectedConditions.urlContains("dashboard"));
        } catch (Exception e) {
            // Ignore if login fails
        }
    }

    public void loginWithEnter(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        driver.findElement(passwordField).sendKeys(Keys.ENTER);
        try {
            wait.until(ExpectedConditions.urlContains("dashboard"));
        } catch (Exception e) {
            // Ignore if login fails
        }
    }



    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }


    public boolean isPasswordMasked() {
        return driver.findElement(passwordField).getAttribute("type").equals("password");
    }

    public boolean isUsernameFieldDisplayed() {
        return driver.findElement(usernameField).isDisplayed();
    }

    public boolean isPasswordFieldDisplayed() {
        return driver.findElement(passwordField).isDisplayed();
    }

    public boolean isLoginButtonDisplayed() {
        return driver.findElement(loginButton).isDisplayed();
    }
}
