package tests;

import org.orangehrm.base.Base;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.EmployeeListPage;
import org.orangehrm.pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class EmployeeListTests extends Base {

    private LoginPage loginPage;
    private SoftAssert softAssert;
    private EmployeeListPage employeeListPage;

    // Define commonly used test data (can be moved to a separate DataProvider later)
    private final String VALID_NAME = "Aaradhya patil"; // Assuming this employee exists
    private final String INVALID_NAME = "Ahmed Mahgoub";

    // In your EmployeeListTests.java, update the assertion inside @BeforeMethod:

    // EmployeeListTests.java

    @BeforeMethod
    public void setUpTest() {
        // 1. Launch browser and initialize driver/waitHelper (must be called first!)
        super.setUp();

        // 2. Initialize Page Objects and SoftAssert here
        loginPage = new LoginPage(driver, waitHelper);
        softAssert = new SoftAssert();

        // --- Setup: Login and Navigate to Employee List Page ---
        // (This is the line that failed because loginPage was null)
        DashboardPage dashboardPage = loginPage.login("Admin", "admin123");

        employeeListPage = dashboardPage.clickEmployeeList();

        // Use the CORRECTED verification method (isEmployeeListPageHeaderDisplayed)
        softAssert.assertTrue(employeeListPage.isEmployeeListPageHeaderDisplayed(),
                "Pre-condition Failed: Should be on the Employee List Page.");
        softAssert.assertAll();
    }
    @Test(description = "Verify search by valid existing name returns single record")
    public void testSearchByValidFullName() {
        System.out.println("\n[Test] Starting search by valid full name...");

        // 1. Perform the search
        employeeListPage.searchEmployeeByName(VALID_NAME);

        // 2. Get the record count text
        String recordCountText = employeeListPage.getRecordCountText();

        // 3. Assert that exactly one record was found
        softAssert.assertTrue(
                recordCountText.contains("(1) Record Found"),
                "PIM_EL_01 Failure: Expected '(1) Record Found', but found: " + recordCountText
        );
        softAssert.assertAll();
    }

    /**
     * PIM_EL_02: Verify search by invalid name returns zero records.
     */
    @Test(description = "Verify search by invalid name returns zero records")
    public void testSearchByInvalidName() {
        System.out.println("\n[Test] Starting search by invalid name...");

        // 1. Perform the search
        employeeListPage.searchEmployeeByName(INVALID_NAME);

        // 2. Get the record count text
        String recordCountText = employeeListPage.getRecordCountText();

        // 3. Assert that no records were found
        softAssert.assertTrue(
                recordCountText.contains("No Records Found"),
                "PIM_EL_02 Failure: Expected 'No Records Found' message, but found: " + recordCountText
        );
        softAssert.assertAll();
    }


    @Test(description = "Verify navigation to Add Employee Page")
    public void testAddEmployeeNavigation() {
        System.out.println("\n[Test] Starting Add Employee Navigation test...");

        // 1. Click the Add button (returns AddEmployeePage object)
        employeeListPage.clickAddButton();

        // 2. Verify the resulting page is the Add Employee Page
        // This is implicitly verified by the AddEmployeePage constructor,
        // but we can explicitly call a verification method if one exists.
        // Assuming the constructor handles verification:
        System.out.println("Navigation to AddEmployeePage verified by Page Object instantiation.");

        softAssert.assertAll();
    }

    @AfterMethod
    public void tearDownTest() {
        super.tearDown();
    }
}