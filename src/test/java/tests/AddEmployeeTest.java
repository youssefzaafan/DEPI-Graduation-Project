package tests;

import Pages.AddEmployeePage;
import Pages.EmployeeListPage;
// import Pages.LoginPage; // No longer needed
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AddEmployeeTest extends BaseTest {

    @Test(description = "Verify successful addition of a valid employee")
    public void testAddValidEmployee(){
        // REMOVED: Redundant login calls
        // LoginPage login = new LoginPage(driver);
        // login.loginAs("Admin","admin123");

        EmployeeListPage empList = new EmployeeListPage(driver);
        empList.navigateToPIM(); // Now should be stable

        empList.clickAddEmployee();

        AddEmployeePage addEmp = new AddEmployeePage(driver);
        addEmp.addEmployee("Ahmed", "Sayed", "Mohamed");

        Assert.assertTrue(addEmp.isToastDisplayed(), "Employee was not added! Success toast was not displayed.");
    }

    @Test(description = "Verify validation errors when submitting with empty mandatory fields")
    public void testAddEmployeeWithEmptyFields(){
        SoftAssert soft = new SoftAssert();


        EmployeeListPage empList = new EmployeeListPage(driver);
        empList.navigateToPIM(); // Now should be stable

        empList.clickAddEmployee();

        AddEmployeePage addEmp = new AddEmployeePage(driver);
        addEmp.addEmployee("", "", "");  // EMPTY FIELDS

        soft.assertFalse(addEmp.isToastDisplayed(),
                "Toast should NOT appear when attempting to add an employee with empty mandatory fields!");

        soft.assertTrue(addEmp.isRequiredFieldErrorDisplayed(),
                "Required field error message was NOT displayed for mandatory fields.");

        soft.assertAll();
    }
}