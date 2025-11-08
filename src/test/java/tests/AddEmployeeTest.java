package tests;

import org.orangehrm.base.Base;
import org.orangehrm.pages.AddEmployeePage;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddEmployeeTest extends Base {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private AddEmployeePage addEmployeePage;

    @BeforeClass
    public void setUpTest() {
        // Calls Base.setUp() to launch browser and initialize driver/waitHelper
        setUp();

        // Initialize LoginPage
        loginPage = new LoginPage(driver, waitHelper);
    }

    @Test(priority = 1, description = "Verify that user can log in successfully.")
    public void testLogin() {
        System.out.println("\n[Test] Starting login test...");

        loginPage.verifyLoginPageLoaded();

        // Use Page Chaining: Login returns the DashboardPage instance.
        dashboardPage = loginPage.login("Admin", "admin123");

        // Verify login success via DashboardPage method
        dashboardPage.verifyLoginSuccessful();

        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"),
                "User should be redirected to the dashboard after login.");
    }

    @Test(priority = 2, description = "Verify navigation to Add Employee page works.")
    public void testNavigateToAddEmployee() {
        System.out.println("\n[Test] Starting Add Employee navigation test...");

        // Ensure we are on the dashboard before navigating (good habit)
        dashboardPage.verifyLoginSuccessful();

        // Perform navigation and save the AddEmployeePage instance for ALL subsequent tests.
        addEmployeePage = dashboardPage.clickAddEmployee();
        addEmployeePage.verifyPageLoaded();

        Assert.assertTrue(driver.getCurrentUrl().contains("addEmployee"),
                "User should be on the Add Employee page.");
    }

    // --- CORRECTED TEST CASES ---

    // NOTE: These tests now rely on the browser being left on the 'Add Employee' page
    // by the previous test (testNavigateToAddEmployee).

    @Test(priority = 3, description = "Verify form submission fails when required fields are empty.")
    public void testRequiredFieldValidation() {
        System.out.println("\n[Test] Starting required field validation test...");

        // FIX: DO NOT navigate again. Use the existing 'addEmployeePage' instance.
        // We ensure the form is empty by resetting it or clearing fields before submission.
        // Since we don't have a dedicated reset/clear method, we'll rely on submitEmployee
        // which attempts to submit the current state (empty in this scenario).

        // Ensure fields are clear (good practice)
        addEmployeePage.fillRequiredFields("", "");

        // Submit the empty form
        addEmployeePage.submitEmployee();

        String firstNameError = addEmployeePage.getRequiredFieldError("First Name");
        String lastNameError = addEmployeePage.getRequiredFieldError("Last Name");

        Assert.assertEquals(firstNameError, "Required",
                "Expected 'Required' validation error for First Name.");
        Assert.assertEquals(lastNameError, "Required",
                "Expected 'Required' validation error for Last Name.");
    }

    @Test(priority = 4, description = "Verify new employee creation with only required fields.")
    public void testSuccessfulEmployeeCreation() {
        System.out.println("\n[Test] Starting successful employee creation test...");

        // FIX: DO NOT navigate again. Use the existing 'addEmployeePage' instance.

        String firstName = "Ahmed" + System.currentTimeMillis();
        String lastName = "Sayed";

        addEmployeePage.fillRequiredFields(firstName, lastName);
        addEmployeePage.submitEmployee();

        Assert.assertTrue(addEmployeePage.verifySuccessToast(),
                "Employee creation failed; success toast not displayed.");

        // IMPORTANT: This test leaves the browser on the 'Personal Details' page.
        Assert.assertTrue(driver.getCurrentUrl().contains("viewPersonalDetails/empNumber/"),
                "After saving, the page should navigate to the employee's Personal Details.");
    }

    @Test(priority = 5, description = "Verify Cancel button returns to Employee List.")
    public void testCancelButton() {
        System.out.println("\n[Test] Starting cancel button functionality test...");

        // Since test 4 left us on the 'Personal Details' page, we must navigate back
        // to the 'Add Employee' page or the 'Employee List' page to perform the Cancel test.

        // Option 1 (Better for flow): Navigate back to Add Employee Page for the test.
        // For simplicity with current Page Objects: navigate to Employee List first,
        // and then back to Add Employee.

        // 1. Navigate back to Dashboard (needed after Personal Details page)
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
        dashboardPage.verifyLoginSuccessful();

        // 2. Navigate back to Add Employee Page
        addEmployeePage = dashboardPage.clickAddEmployee();

        addEmployeePage.fillRequiredFields("CancelTest", "User");

        // 3. Click Cancel (This will return EmployeeListPage)
        addEmployeePage.clickCancelButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("viewEmployeeList"),
                "Clicking Cancel should navigate back to the Employee List page.");
    }

    @AfterClass
    public void tearDownTest() {
        tearDown(); // Calls Base.tearDown() to close the browser
    }
}