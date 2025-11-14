package org.example.tests;

import org.example.Pages.DashboardPage;
import org.example.Pages.LoginPage;
import org.example.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {



    @Test(description = "TC-001: Verify Login Page UI elements")
    public void verifyLoginPageUIElements() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field not displayed");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field not displayed");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button not displayed");
    }

    @Test(description = "TC-002: Login with valid credentials")
    public void loginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("Admin", "admin123");
        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isAtDashboard(), "Dashboard not reached");
    }

    @Test(description = "TC-003: Login with invalid username")
    public void loginWithInvalidUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("Admin1", "admin123");
        Assert.assertEquals(loginPage.getErrorMessage2(), "Invalid credentials");
    }

    @Test(description = "TC-004: Login with invalid password")
    public void loginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("Admin", "12345");
        Assert.assertEquals(loginPage.getErrorMessage2(), "Invalid credentials");
    }

    @Test(description = "TC-005: Attempt login with empty fields")
    public void attemptLoginWithEmptyFields() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("", "");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Required") || error.contains("Invalid credentials"),
                "Validation message not displayed. Found: " + error);
    }



    @Test(description = "TC-006: Verify password masking")
    public void verifyPasswordMasking() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterPassword("mypassword");
        Assert.assertTrue(loginPage.isPasswordMasked(), "Password is not masked");
    }

    @Test(description = "TC-007: Press Enter key to login")
    public void loginWithEnterKey() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithEnter("Admin", "admin123");
        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isAtDashboard(), "Dashboard not reached");
    }

    @Test(description = "TC-008: Check case sensitivity of username(admin)")
    public void caseSensitivityAdmin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("admin", "admin123");
        Assert.assertEquals(loginPage.getErrorMessage2(), "Invalid credentials");
    }

    @Test(description = "TC-009: Verify session persistence after login")
    public void sessionPersistenceAfterLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("Admin", "admin123");
        driver.navigate().refresh();
        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isAtDashboard(), "Session not maintained");
    }



    @Test(description = "TC-010: Check case sensitivity of username(employee)")
    public void caseSensitivityEmployee() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("mariana", "m123");
        Assert.assertEquals(loginPage.getErrorMessage2(), "Invalid credentials");
    }

    @Test(description = "TC-11: Verify logout functionality")
    public void verifyLogoutFunctionality() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("Admin", "admin123");
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("auth/login"), "Logout failed");
    }

}
