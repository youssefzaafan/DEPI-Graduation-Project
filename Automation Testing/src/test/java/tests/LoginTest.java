package tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import TestData.LoginData;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    // ---------------- Before & After ----------------

    @BeforeMethod
    public void beforeEachTest() {

        String currentUrl = driver.getCurrentUrl();

        if (!currentUrl.contains("auth/login")) {
            try {
                DashboardPage dashboard = new DashboardPage(driver);
                dashboard.logout();
            } catch (Exception ignored) {}
        }

        driver.get(BASE_URL);
    }

    @AfterMethod
    public void afterEachTest() {
        String currentUrl = driver.getCurrentUrl();

        if (!currentUrl.contains("auth/login")) {
            try {
                DashboardPage dashboard = new DashboardPage(driver);
                dashboard.logout();
            } catch (Exception e) {
                driver.get(BASE_URL);
            }
        }
    }

    // ---------------- TC-001: Verify UI ----------------

    @Test(priority = 1, description = "TC-001: Verify Login Page UI elements")
    public void verifyLoginPageUIElements() {

        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field is NOT displayed!");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field is NOT displayed!");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button is NOT displayed!");
    }

    // ---------------- TC-002: Valid Login ----------------

    @Test(priority = 2,
            dataProvider = "validLogin",
            dataProviderClass = LoginData.class,
            description = "TC-002: Login with valid credentials")
    public void loginWithValidCredentials(String username, String password) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(username, password);

        DashboardPage dashboard = new DashboardPage(driver);

        Assert.assertTrue(dashboard.isAtDashboard(), "Valid login failed!");

        dashboard.logout();
    }

    // ---------------- TC-003: Invalid Login ----------------

    @Test(priority = 3,
            dataProvider = "invalidLogin",
            dataProviderClass = LoginData.class,
            description = "TC-003: Login with invalid credentials")
    public void loginWithInvalidCredentials(String username, String password) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(username, password);

        String err1 = loginPage.getErrorMessage();
        String err2 = loginPage.getErrorMessage2();

        Assert.assertTrue(
                !err1.isEmpty() || !err2.isEmpty(),
                "Expected an error message, but none appeared!"
        );
    }

    // ---------------- TC-004: Empty Fields ----------------

    @Test(priority = 4,
            dataProvider = "emptyFields",
            dataProviderClass = LoginData.class,
            description = "TC-004: Login with empty fields")
    public void attemptLoginWithEmptyFields(String username, String password) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(username, password);

        Assert.assertFalse(
                loginPage.getErrorMessage().isEmpty(),
                "Error must appear when fields are empty!"
        );
    }

    // ---------------- TC-005: Case Sensitivity (Admin) ----------------

    @Test(priority = 5,
            dataProvider = "invalidLogin",
            dataProviderClass = LoginData.class,
            description = "TC-005: Case sensitivity - Admin")
    public void caseSensitivityAdmin(String username, String password) {

        if (!username.equals("admin")) return; // نفلتر الحالة فقط

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(username, password);

        Assert.assertEquals(
                loginPage.getErrorMessage2(),
                "Invalid credentials",
                "Admin case sensitivity check failed!"
        );
    }

    // ---------------- TC-006: Password Masking ----------------

    @Test(priority = 6,
            dataProvider = "maskingData",
            dataProviderClass = LoginData.class,
            description = "TC-006: Verify password masking")
    public void verifyPasswordMasking(String password) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterPassword(password);

        Assert.assertTrue(loginPage.isPasswordMasked(), "Password is NOT masked!");
    }

    // ---------------- TC-007: Login with Enter Key ----------------

    @Test(priority = 7,
            dataProvider = "enterKeyLogin",
            dataProviderClass = LoginData.class,
            description = "TC-007: Login using Enter key")
    public void loginWithEnterKey(String username, String password) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithEnter(username, password);

        DashboardPage dashboard = new DashboardPage(driver);

        Assert.assertTrue(dashboard.isAtDashboard(), "Login using Enter key failed!");

        dashboard.logout();
    }

    // ---------------- TC-008: Case Sensitivity (Employee) ----------------

    @Test(priority = 8,
            dataProvider = "invalidLogin",
            dataProviderClass = LoginData.class,
            description = "TC-008: Case sensitivity - Employee")
    public void caseSensitivityEmployee(String username, String password) {

        if (!username.equals("mariana")) return;

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(username, password);

        Assert.assertEquals(
                loginPage.getErrorMessage2(),
                "Invalid credentials",
                "Employee case sensitivity check failed!"
        );
    }

    // ---------------- TC-009: Session Persistence ----------------

    @Test(priority = 9,
            dataProvider = "sessionPersistence",
            dataProviderClass = LoginData.class,
            description = "TC-009: Session persistence after login")
    public void sessionPersistenceAfterLogin(String username, String password) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(username, password);

        DashboardPage dashboard = new DashboardPage(driver);

        driver.navigate().refresh();

        Assert.assertTrue(dashboard.isAtDashboard(),
                "Session did NOT persist after refreshing the page!");

        dashboard.logout();
    }
}
