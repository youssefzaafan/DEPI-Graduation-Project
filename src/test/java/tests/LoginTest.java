package tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import TestData.LoginDataProvider;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

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

    @Test(priority = 1, description = "TC-001: Verify Login Page UI elements")
    @Description("Verify that all UI elements on the login page are displayed")
    public void verifyLoginPageUIElements() {
        LoginPage loginPage = openLoginPage();
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed());
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed());
        Assert.assertTrue(loginPage.isLoginButtonDisplayed());
    }

    @Test(priority = 2, dataProvider = "validLoginData", dataProviderClass = LoginDataProvider.class, description = "TC-002: Login with valid credentials")
    @Description("Login using valid credentials")
    public void loginWithValidCredentials(String username, String password) {
        DashboardPage dashboard = loginAs(username, password);
        Assert.assertTrue(dashboard.isAtDashboard());
    }

    @Test(priority = 3, dataProvider = "invalidLoginData", dataProviderClass = LoginDataProvider.class, description = "TC-003: Login with invalid username")
    @Description("Attempt login with an invalid username or password")
    public void loginWithInvalidCredentials(String username, String password) {
        attemptLogin(username, password);
        LoginPage loginPage = openLoginPage();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Invalid credentials") || error.contains("Required"));
    }

    @Test(priority = 6, description = "TC-006: Verify password masking")
    @Description("Verify that the password field masks the input")
    public void verifyPasswordMasking() {
        LoginPage loginPage = openLoginPage();
        loginPage.enterPassword("mypassword");
        Assert.assertTrue(loginPage.isPasswordMasked());
    }

    @Test(priority = 7, description = "TC-007: Press Enter key to login")
    @Description("Login using the Enter key")
    public void loginWithEnterKey() {
        LoginPage loginPage = openLoginPage();
        loginPage.loginWithEnter("Admin", "admin123");
        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isAtDashboard());
    }

    @Test(priority = 4, description = "TC-004: Attempt login with empty fields")
    @Description("Attempt login without entering username and password")
    public void attemptLoginWithEmptyFields() {
        attemptLogin("", "");
        LoginPage loginPage = openLoginPage();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Required") || error.contains("Invalid credentials"));
    }

    @Test(priority = 5, description = "TC-005: Case sensitivity of username (Admin)")
    @Description("Verify case sensitivity of the username 'admin'")
    public void caseSensitivityAdmin() {
        attemptLogin("admin", "admin123");
        LoginPage loginPage = openLoginPage();
        Assert.assertEquals(loginPage.getErrorMessage2(), "Invalid credentials");
    }

    @Test(priority = 8, description = "TC-008: Case sensitivity of username (Employee)")
    @Description("Verify case sensitivity of the username 'employee'")
    public void caseSensitivityEmployee() {
        attemptLogin("mariana", "m123");
        LoginPage loginPage = openLoginPage();
        Assert.assertEquals(loginPage.getErrorMessage2(), "Invalid credentials");
    }

    @Test(priority = 9, description = "TC-009: Verify session persistence after login")
    @Description("Verify that the session persists after logging in")
    public void sessionPersistenceAfterLogin() {
        DashboardPage dashboard = loginAs("Admin", "admin123");
        driver.navigate().refresh();
        Assert.assertTrue(dashboard.isAtDashboard());
    }

    @Test(priority = 10, description = "TC-010: Verify logout functionality")
    @Description("Verify that logout works correctly")
    public void verifyLogoutFunctionality() {
        DashboardPage dashboard = loginAs("Admin", "admin123");
        dashboard.logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("auth/login"));
    }
}
