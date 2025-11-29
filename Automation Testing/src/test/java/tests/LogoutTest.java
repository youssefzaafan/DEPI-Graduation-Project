package tests;

import Pages.DashboardPage;
import Pages.LoginPage;
import TestData.LoginData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogoutTest extends BaseTest {

    @Test(dataProvider = "logoutData", dataProviderClass = LoginData.class,
            description = "TC-010: Verify logout functionality works")
    public void verifyLogoutFunctionality(String username, String password) {

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.logout();

        Assert.assertTrue(loginPage.isAtLoginPage(), "Logout failed — not redirected to login page");

    }
}
