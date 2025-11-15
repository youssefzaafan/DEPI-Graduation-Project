package tests;

import Pages.AdminPage;
import Pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AdminTest extends BaseTest {
    private AdminPage adminPage;

    @BeforeMethod
    public void initPages() {
        adminPage = new AdminPage(driver);
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.naviagteToAdminPage();
       Assert.assertTrue(adminPage.isAtAdminPage(), "Not Admin Page");
    }

    @Test(description = "TC-001: Search with valid data" /*, dependsOnMethods = "addUser"*/)
    public void searchForUnExistsUser() {

        adminPage.enterUsername("Zzzzxzyzx");
        adminPage.enterEmployeename(adminPage.getCurrentUsername());
        adminPage.selectOption(adminPage.getUserRoleDropdown(), "Admin");
        adminPage.selectOption(adminPage.getStatusDropdown(), "Enabled");
        adminPage.clickSearchButton();
        Assert.assertFalse(adminPage.isSearchResultDisplayed());
    }

    @Test(description = "TC-002: add new user with valid data")
    public void addUser(){
        adminPage.clickAddButton();
        adminPage.addEmployeename(adminPage.getCurrentUsername());
        adminPage.selectOption(adminPage.getUserRoleDropdown(), "Admin");
        adminPage.selectOption(adminPage.getStatusDropdown(), "Enabled");
        adminPage.addUsername("Zzzzxzyzx");
        adminPage.enterPassword("Z1234z1234");
        adminPage.confirmPassword("Z1234z1234");
        adminPage.clickSaveButton();

        Assert.assertTrue(adminPage.verifyUserAdded("Zzzzxzyzx"));
    }
    @Test(description = "TC-003: Search with valid data" , dependsOnMethods = "addUser")
    public void searchForExistsUser() {

        adminPage.enterUsername("Zzzzxzyzx");
        adminPage.enterEmployeename(adminPage.getCurrentUsername());
        adminPage.selectOption(adminPage.getUserRoleDropdown(), "Admin");
        adminPage.selectOption(adminPage.getStatusDropdown(), "Enabled");
        adminPage.clickSearchButton();
        Assert.assertTrue(adminPage.isSearchResultDisplayed());
    }
    @Test(description = "TC-004: Delete All Exsit Users" )
    public void deleteAllUsers() {
        adminPage.checkAllUsers();
        adminPage.clickDeleteSelectedButton();
        Assert.assertTrue(adminPage.isAdminUserOnlyDisplayed());
    }

    @Test(description = "TC-005: Delete An Exsit Users", dependsOnMethods = "addUser")
    public void deleteExistUser() {
        adminPage.enterUsername("Zzzzxzyzx");
        adminPage.clickSearchButton();
        adminPage.checkAllUsers();
        adminPage.clickDeleteSelectedButton();
        Assert.assertTrue(adminPage.verifyUserDeleted("Zzzzxzyzx"));
    }

}




