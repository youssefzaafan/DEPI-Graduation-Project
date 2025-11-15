package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import Pages.DashboardPage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    // ---------- Steps ----------

    @Step("Open Login Page")
    public LoginPage openLoginPage() {
        return new LoginPage(driver);
    }

    @Step("Login with Username: {0}, Password: {1}")
    public DashboardPage loginAs(String username, String password) {
        LoginPage loginPage = openLoginPage();
        loginPage.loginAs(username, password);
        return new DashboardPage(driver);
    }

    @Step("Attempt login with Username: {0}, Password: {1}")
    public void attemptLogin(String username, String password) {
        LoginPage loginPage = openLoginPage();
        loginPage.loginAs(username, password);
    }

    // ---------- Tests ----------

    @Test(description = "TC-001: Verify Login Page UI elements")
    @Description("Verify that all UI elements on the login page are displayed")
    public void verifyLoginPageUIElements() {
        LoginPage loginPage = openLoginPage();
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field not displayed");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field not displayed");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button not displayed");
    }

    @Test(description = "TC-002: Login with valid credentials")
    @Description("Login using valid credentials")
    public void loginWithValidCredentials() {
        DashboardPage dashboard = loginAs("Admin", "admin123");
        Assert.assertTrue(dashboard.isAtDashboard(), "Dashboard not reached");
    }

    @Test(description = "TC-003: Login with invalid username")
    @Description("Attempt login with an invalid username")
    public void loginWithInvalidUsername() {
        attemptLogin("Admin1", "admin123");
        LoginPage loginPage = openLoginPage();
        Assert.assertEquals(loginPage.getErrorMessage2(), "Invalid credentials");
    }

    @Test(description = "TC-004: Login with invalid password")
    @Description("Attempt login with an invalid password")
    public void loginWithInvalidPassword() {
        attemptLogin("Admin", "12345");
        LoginPage loginPage = openLoginPage();
        Assert.assertEquals(loginPage.getErrorMessage2(), "Invalid credentials");
    }

    @Test(description = "TC-005: Attempt login with empty fields")
    @Description("Attempt login without entering username and password")
    public void attemptLoginWithEmptyFields() {
        attemptLogin("", "");
        LoginPage loginPage = openLoginPage();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Required") || error.contains("Invalid credentials"),
                "Validation message not displayed. Found: " + error);
    }

    @Test(description = "TC-006: Verify password masking")
    @Description("Verify that the password field masks the input")
    public void verifyPasswordMasking() {
        LoginPage loginPage = openLoginPage();
        loginPage.enterPassword("mypassword");
        Assert.assertTrue(loginPage.isPasswordMasked(), "Password is not masked");
    }

    @Test(description = "TC-007: Press Enter key to login")
    @Description("Login using the Enter key")
    public void loginWithEnterKey() {
        LoginPage loginPage = openLoginPage();
        loginPage.loginWithEnter("Admin", "admin123");
        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isAtDashboard(), "Dashboard not reached");
    }

    @Test(description = "TC-008: Check case sensitivity of username(admin)")
    @Description("Verify case sensitivity of the username 'admin'")
    public void caseSensitivityAdmin() {
        attemptLogin("admin", "admin123");
        LoginPage loginPage = openLoginPage();
        Assert.assertEquals(loginPage.getErrorMessage2(), "Invalid credentials");
    }

    @Test(description = "TC-009: Verify session persistence after login")
    @Description("Verify that the session persists after logging in")
    public void sessionPersistenceAfterLogin() {
        DashboardPage dashboard = loginAs("Admin", "admin123");
        driver.navigate().refresh();
        Assert.assertTrue(dashboard.isAtDashboard(), "Session not maintained");
    }

    @Test(description = "TC-010: Check case sensitivity of username(employee)")
    @Description("Verify case sensitivity of the username 'employee'")
    public void caseSensitivityEmployee() {
        attemptLogin("mariana", "m123");
        LoginPage loginPage = openLoginPage();
        Assert.assertEquals(loginPage.getErrorMessage2(), "Invalid credentials");
    }

    @Test(description = "TC-011: Verify logout functionality")
    @Description("Verify that logout works correctly")
    public void verifyLogoutFunctionality() {
        DashboardPage dashboard = loginAs("Admin", "admin123");
        dashboard.logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("auth/login"), "Logout failed");
    }
}
