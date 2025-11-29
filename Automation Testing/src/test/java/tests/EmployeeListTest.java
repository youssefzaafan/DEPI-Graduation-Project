package tests;


import Pages.AddEmployeePage;
import Pages.EmployeeListPage;
// import Pages.LoginPage; // No longer needed
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class EmployeeListTest extends BaseTest {

    @Test(priority = 2, description = "Verify searching for an existing employee returns results")
    public void testSearchValidEmployee(){

        EmployeeListPage emp = new EmployeeListPage(driver);
        emp.navigateToPIM(); // Navigate to PIM module (Now should be stable)

        emp.searchEmployee("Paul");

        Assert.assertTrue(emp.isResultVisible(), "Employee result should appear for a valid search!");
    }

    @Test(priority = 1, description = "Verify successful navigation from Employee List to Add Employee page")
    public void testNavigateToAddEmployee(){


        EmployeeListPage emp = new EmployeeListPage(driver);
        emp.navigateToPIM(); // Navigate to PIM module (Now should be stable)

        emp.clickAddEmployee();

        AddEmployeePage addEmp = new AddEmployeePage(driver);
        Assert.assertTrue(addEmp.isAddEmployeePageLoaded(),
                "Add Employee page did not load!");
    }

    @Test(priority = 3, description = "Verify searching for a non-existent employee shows no results")
    public void testInvalidEmployeeSearch(){
        SoftAssert soft = new SoftAssert();


        EmployeeListPage emp = new EmployeeListPage(driver);
        emp.navigateToPIM();

        emp.searchEmployee("Ahmed Sayed Mohamed");


        soft.assertFalse(emp.isResultVisible(),
                "Result should NOT appear for an invalid employee search!");

        soft.assertTrue(emp.isNoRecordMessageDisplayed(),
                "The 'No Records Found' message should be displayed.");

        soft.assertAll();
    }
}