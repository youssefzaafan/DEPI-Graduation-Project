package org.orangehrm2.pages;

import org.orangehrm.pages.DashboardPage;
import org.orangehrm.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;
    private WaitHelper waitHelper;

    // ===== Locators =====
    private final By USERNAME_INPUT = By.xpath("//input[@placeholder='Username']");
    private final By PASSWORD_INPUT = By.xpath("//input[@placeholder='Password']");
    private final By LOGIN_BUTTON = By.xpath("//button[@type='submit']");
    private final By LOGIN_PANEL = By.xpath("//h5[text()='Login']");

    // ===== Constructor =====
    public LoginPage(WebDriver driver, WaitHelper waitHelper) {
        this.driver = driver;
        this.waitHelper = waitHelper;
        // Optionally add a check here to ensure the page is loaded
        verifyLoginPageLoaded();
    }

    // ===== Actions =====

    /**
     * Waits for the login panel to confirm the page has loaded.
     */
    public void verifyLoginPageLoaded() {
        waitHelper.waitForElementToBeVisible(LOGIN_PANEL);
        System.out.println("[LoginPage] Login page loaded successfully.");
    }

    /**
     * Logs in using provided credentials and returns the next page (Dashboard).
     * @return DashboardPage instance
     */
    public DashboardPage login(String username, String password) {
        System.out.println("[LoginPage] Attempting to log in as: " + username);
        waitHelper.waitForElementToBeVisible(USERNAME_INPUT).sendKeys(username);
        waitHelper.waitForElementToBeVisible(PASSWORD_INPUT).sendKeys(password);
        waitHelper.waitForElementToBeClickable(LOGIN_BUTTON).click();

        // Return the DashboardPage instance which handles the success verification
        return new DashboardPage(driver, waitHelper);
    }
}