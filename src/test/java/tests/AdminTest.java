package org.example.tests;

import org.example.Pages.AdminPage;
import org.example.Pages.DashboardPage;
import org.example.Pages.LoginPage;
import org.example.utils.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.TestRunner.PriorityWeight.dependsOnMethods;
public class AdminTest extends BaseTest {
    private AdminPage adminPage;

    @BeforeMethod
    public void initPages() {
        adminPage = new AdminPage(driver);
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.naviagteToAdminPage();
        adminPage.isAtAdminPage();
    }

    @Test(description = "TC-001: Search with valid data")
    public void searchUser() {
        adminPage.enterUsername("Zzzzxzyzx");
        adminPage.enterEmployeename(adminPage.getCurrentUsername());
        adminPage.selectOption(adminPage.getUserRoleDropdown(), "Admin");
        adminPage.selectOption(adminPage.getStatusDropdown(), "Enabled");
        adminPage.clickSearchButton();
        Assert.assertTrue(adminPage.isSearchResultDisplayed(), "Search result not displayed");
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

//       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(155));
//       wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-toast-content-text")));
//        boolean isUserAdded = adminPage.isUserPresentInTable("Zzzzxzyzx");
//        Assert.assertTrue(isUserAdded, "User Zzzzxzyzx should be present in the user table after saving.");
    }

    @Test(description = "TC-003: delete exsit user" )
    public void deleteAllUsers() {
      //  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        adminPage.getRecordsFound();
        adminPage.clickDeleteButton();

    }

//       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(155));
//       wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-toast-content-text")));
//        boolean isUserAdded = adminPage.isUserPresentInTable("Zzzzxzyzx");
//        Assert.assertTrue(isUserAdded, "User Zzzzxzyzx should be present in the user table after saving.");
//  }


}

