package tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    // ----------------- Setup & Teardown -----------------

    @BeforeMethod
    public void beforeEachTest() {
        // Logout if already logged in (from BaseTest setup)
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("auth/login")) {
            DashboardPage dashboard = new DashboardPage(driver);
            dashboard.logout();
        }

        // Navigate to login page
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void afterEachTest() {
        // Ensure we're logged out after each test
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("auth/login")) {
            try {
                DashboardPage dashboard = new DashboardPage(driver);
                dashboard.logout();
            } catch (Exception e) {
                // If logout fails, navigate directly to login page
                driver.get(BASE_URL);
            }
        }
    }

    // ----------------- Utility Methods -----------------

    @Step("Login as {0} / {1} then logout")
    public void loginAndLogout(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(username, password);

        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isAtDashboard(), "Login failed — cannot access dashboard");

        dashboard.logout();

        Assert.assertTrue(driver.getCurrentUrl().contains("auth/login"),
                "Logout failed — still not redirected to login page");
    }

    @Step("Attempt login with username={0}, password={1}")
    public LoginPage attemptLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(username, password);
        return loginPage;
    }

    // ----------------- Test Cases -----------------

    @Test(priority = 1, description = "TC-001: Verify Login Page UI elements")
    @Description("Validate that username, password and login button are displayed")
    public void verifyLoginPageUIElements() {
        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field should be visible");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field should be visible");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be visible");
    }

    @Test(priority = 2, description = "TC-002: Login with valid credentials")
    public void loginWithValidCredentials() {
        loginAndLogout("Admin", "admin123");
    }

    @Test(priority = 3, description = "TC-003: Login with invalid credentials")
    public void loginWithInvalidCredentials() {
        LoginPage loginPage = attemptLogin("wrongUser", "wrongPass");

        String error = loginPage.getErrorMessage();
        String secondError = loginPage.getErrorMessage2();

        Assert.assertTrue(
                !error.isEmpty() || !secondError.isEmpty(),
                "Error message must appear for invalid credentials"
        );
    }

    @Test(priority = 4, description = "TC-004: Attempt login with empty fields")
    public void attemptLoginWithEmptyFields() {
        LoginPage loginPage = attemptLogin("", "");

        String error = loginPage.getErrorMessage();
        Assert.assertFalse(error.isEmpty(), "Error must appear when fields are empty");
        Assert.assertTrue(error.contains("Required"), "Error should contain 'Required'");
    }

    @Test(priority = 5, description = "TC-005: Case sensitivity username Admin")
    public void caseSensitivityAdmin() {
        LoginPage loginPage = attemptLogin("admin", "admin123");

        String error = loginPage.getErrorMessage2();
        Assert.assertFalse(error.isEmpty(), "Error must appear");
        Assert.assertEquals(error, "Invalid credentials", "Case sensitivity failed!");
    }

    @Test(priority = 6, description = "TC-006: Verify password masking")
    public void verifyPasswordMasking() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterPassword("mypassword");

        Assert.assertTrue(loginPage.isPasswordMasked(), "Password should be masked");
    }

    @Test(priority = 7, description = "TC-007: Login using Enter key")
    public void loginWithEnterKey() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithEnter("Admin", "admin123");

        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isAtDashboard(), "Login with Enter key failed");

        dashboard.logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("auth/login"),
                "Logout failed after Enter-login");
    }

    @Test(priority = 8, description = "TC-008: Case sensitivity username Employee")
    public void caseSensitivityEmployee() {
        LoginPage loginPage = attemptLogin("mariana", "m123");

        String error = loginPage.getErrorMessage2();
        Assert.assertFalse(error.isEmpty(), "Error must appear");
        Assert.assertEquals(error, "Invalid credentials");
    }

    @Test(priority = 9, description = "TC-009: Verify session persistence after login")
    public void sessionPersistenceAfterLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("Admin", "admin123");

        DashboardPage dashboard = new DashboardPage(driver);

        driver.navigate().refresh();

        Assert.assertTrue(dashboard.isAtDashboard(),
                "Session should persist after refresh");

        dashboard.logout();
    }

    @Test(priority = 10, description = "TC-010: Verify logout functionality works")
    public void verifyLogoutFunctionality() {
        loginAndLogout("Admin", "admin123");
    }
}