package org.orangehrm.tests;

import org.orangehrm.base.Base;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.EmployeeListPage; // Import the correct Page Object
import org.orangehrm.pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PIMSearchTest extends Base {

    private LoginPage loginPage;
    private SoftAssert softAssert;

    @BeforeMethod
    public void setUpTest() {
        // Calls Base.setUp() to launch browser and initialize driver/waitHelper
        super.setUp();

        // Initialize LoginPage.
        loginPage = new LoginPage(driver, waitHelper);
        softAssert = new SoftAssert();
    }

    /**
     * Verify search by invalid employee name returns zero records.
     */
    @Test(description = "Verify search by invalid employee name returns zero records")
    public void testSearchByInvalidName() {
        System.out.println("\n[Test] Starting invalid PIM search test...");

        // 1. Log in and get the Dashboard Page object
        DashboardPage dashboardPage = loginPage.login("Admin", "admin123");

        // 2. Use Page Chaining: Navigate from Dashboard to Employee List
        EmployeeListPage employeeListPage = dashboardPage.clickEmployeeList();

        // Ensure we are on the correct page (Verification is already in the constructor, but explicit call is good)
        employeeListPage.verifyPageLoaded();

        // Define the non-existent employee name
        String invalidName = "NonExistentName12345";

        // 3. Perform the search using the EmployeeListPage object
        employeeListPage.searchEmployeeByName(invalidName);

        // 4. Get the record count text from the EmployeeListPage
        String recordCountText = employeeListPage.getRecordCountText();

        // 5. Assert that no records were found
        softAssert.assertTrue(
                recordCountText.contains("No Records Found"),
                "Expected 'No Records Found' message, but found: " + recordCountText
        );

        softAssert.assertAll();
    }

    @AfterMethod
    public void tearDownTest() {
        // Ensures the browser is closed after each test
        super.tearDown();
    }
}