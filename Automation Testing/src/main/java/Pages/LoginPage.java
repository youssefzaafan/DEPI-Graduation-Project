package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    //  Locators
    private By usernameField = By.cssSelector("input[placeholder='Username']");
    private By passwordField = By.cssSelector("input[placeholder='Password']");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.cssSelector(".oxd-text.oxd-text--span.oxd-input-field-error-message.oxd-input-group__message");
    private By errorMessage2 = By.cssSelector(".oxd-text.oxd-text--p.oxd-alert-content-text");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }




    // Actions
    public void enterUsername(String username) {
        WebElement usernameInput = wait.until(ExpectedConditions.elementToBeClickable(usernameField));
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }



    public void enterPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        btn.click();
    }

    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();

        WebDriverWait flexibleWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        flexibleWait.pollingEvery(Duration.ofMillis(500));
        flexibleWait.ignoring(org.openqa.selenium.NoSuchElementException.class);

        flexibleWait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("dashboard"),
                ExpectedConditions.presenceOfElementLocated(errorMessage),
                ExpectedConditions.presenceOfElementLocated(errorMessage2)
        ));
    }

    public boolean isAtLoginPage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).isDisplayed();
    }

    public void loginWithEnter(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        driver.findElement(passwordField).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("dashboard"),
                ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ));
    }

    //  Getters
    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        } catch (Exception e) { return ""; }
    }

    public String getErrorMessage2() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage2)).getText();
        } catch (Exception e) { return ""; }
    }

    // Checks
    public boolean isPasswordMasked() {
        return driver.findElement(passwordField).getAttribute("type").equals("password");
    }

    public boolean isUsernameFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).isDisplayed();
    }

    public boolean isPasswordFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).isDisplayed();
    }

    public boolean isLoginButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).isDisplayed();
    }
}
