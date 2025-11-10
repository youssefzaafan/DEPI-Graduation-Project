package org.orangehrm.tests;

import org.orangehrm.base.Base;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Login test - verifies successful login into OrangeHRM.
 */
public class LoginTest extends Base {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private SoftAssert softAssert;

    @BeforeMethod
    public void setUpTest() {
        // Use Base class setup
        setUp();

        // Initialize page objects after driver is ready
        loginPage = new LoginPage(driver, waitHelper);
        dashboardPage = new DashboardPage(driver, waitHelper);
        softAssert = new SoftAssert();
    }

    @Test(description = "Verify successful login")
    public void testLoginSuccess() {
        System.out.println("[Test] Starting successful login test...");

        // Base already navigates to login page, so no need to open URL again
        loginPage.login("Admin", "admin123");

        // Verify that Dashboard is displayed after successful login
        boolean isDashboardDisplayed = dashboardPage.isDashboardHeaderDisplayed();
        softAssert.assertTrue(isDashboardDisplayed,
                "Dashboard header should be visible after successful login");

        softAssert.assertAll();
    }

    @AfterMethod
    public void tearDownTest() {
        tearDown();
    }
}
