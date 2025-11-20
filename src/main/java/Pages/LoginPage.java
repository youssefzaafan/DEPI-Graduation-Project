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
    private DashboardPage dashboardPage;

    private By profileMenu = By.cssSelector("p.oxd-userdropdown-name");
    private By usernameField = By.cssSelector("input[placeholder='Username']");
    private By passwordField = By.cssSelector("input[placeholder='Password']");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.cssSelector(".oxd-text.oxd-text--span.oxd-input-field-error-message.oxd-input-group__message");
    private By errorMessage2 = By.cssSelector(".oxd-text.oxd-text--p.oxd-alert-content-text");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.dashboardPage = new DashboardPage(driver);
    }

    private void ensureLoginPage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        } catch (Exception e) {
            if (dashboardPage.isAtDashboard()) {
                dashboardPage.logout();
                wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            }
        }
    }


    public void enterUsername(String username) {
        ensureLoginPage();
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        wait.until(ExpectedConditions.elementToBeClickable(usernameField));
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

        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("dashboard"),
                    ExpectedConditions.visibilityOfElementLocated(errorMessage)
            ));
        } catch (Exception ignored) {}
    }


    public void loginWithEnter(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        driver.findElement(passwordField).sendKeys(Keys.ENTER);

        if (!username.isEmpty() && !password.isEmpty()) {
            try {
                wait.until(ExpectedConditions.urlContains("dashboard"));
            } catch (Exception ignored) {}
        }
    }

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

    public boolean isPasswordMasked() {
        try {
            return driver.findElement(passwordField).getAttribute("type").equals("password");
        } catch (Exception e) { return false; }
    }

    public boolean isUsernameFieldDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isPasswordFieldDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isLoginButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).isDisplayed();
        } catch (Exception e) { return false; }
    }
}
