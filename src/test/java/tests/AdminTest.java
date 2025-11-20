package tests;

import Pages.AdminPage;
import Pages.DashboardPage;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AdminTest extends BaseTest {
    private AdminPage adminPage;

    @BeforeMethod
    public void initPages() {
        adminPage = new AdminPage(driver);
        DashboardPage dashboard = new DashboardPage(driver);
        Allure.step("Navigate to AdminPage");
        dashboard.naviagteToAdminPage();
       Assert.assertTrue(adminPage.isAtAdminPage(), "Not Admin Page");
    }

    @Test(description = "TC-001: Search with valid data", priority = 2 ,dataProvider = "adminData", dataProviderClass = TestData.AdminDataProvider.class)
    public void searchForUnExistsUser(String username, String roleType, String statusOtion) {
        Allure.step("Search for unexisting user");
        adminPage.enterUsername(username);
        adminPage.enterEmployeename(adminPage.getCurrentUsername());
        adminPage.selectOption(adminPage.getUserRoleDropdown(), roleType);
        adminPage.selectOption(adminPage.getStatusDropdown(), statusOtion);
        adminPage.clickSearchButton();
        Assert.assertFalse(adminPage.isSearchResultDisplayed());
    }

    @Test(description = "TC-002: add new user with valid data", priority = 5,dataProvider = "adminData", dataProviderClass = TestData.AdminDataProvider.class)
    public void addUser(String username, String roleType, String statusOtion, String password){
        Allure.step("Add new user with ESS role");

        adminPage.clickAddButton();
        adminPage.addEmployeename(adminPage.getCurrentUsername());
        adminPage.selectOption(adminPage.getUserRoleDropdown(), roleType);
        adminPage.selectOption(adminPage.getStatusDropdown(), statusOtion);
        adminPage.addUsername(username);
        adminPage.enterPassword(password);
        adminPage.confirmPassword(password);
        adminPage.clickSaveButton();

        Assert.assertTrue(adminPage.verifyUserAdded(username));
    }
    @Test(description = "TC-003: Search with valid data" , dependsOnMethods = "addUser", priority = 3,dataProvider = "adminData", dataProviderClass = TestData.AdminDataProvider.class)
    public void searchForExistsUser(String username, String roleType, String statusOtion) {
        Allure.step("Search for exist user");

        adminPage.enterUsername(username);
        adminPage.enterEmployeename(adminPage.getCurrentUsername());
        adminPage.selectOption(adminPage.getUserRoleDropdown(), roleType);
        adminPage.selectOption(adminPage.getStatusDropdown(), statusOtion);
        adminPage.clickSearchButton();
        Assert.assertTrue(adminPage.isSearchResultDisplayed());
    }
    @Test(description = "TC-004: Delete All Exsit Users", priority = 1)
    public void deleteAllUsers() {
        Allure.step("Delete All Users to prevent the conflict");

        adminPage.checkAllUsers();
        adminPage.clickDeleteSelectedButton();
        Assert.assertTrue(adminPage.isAdminUserOnlyDisplayed());
    }

    @Test(description = "TC-005: Delete An Exsit Users", dependsOnMethods = "addUser" , priority = 4, dataProvider = "adminData", dataProviderClass = TestData.AdminDataProvider.class)
    public void deleteExistUser(String username) {
        Allure.step("Delete Specific user");

        adminPage.enterUsername(username);
        adminPage.clickSearchButton();
        adminPage.checkAllUsers();
        adminPage.clickDeleteSelectedButton();
        Assert.assertTrue(adminPage.verifyUserDeleted(username));
    }

}




