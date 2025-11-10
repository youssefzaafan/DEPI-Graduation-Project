package org.orangehrm2.pages;

import org.orangehrm.pages.AddEmployeePage;
import org.orangehrm.pages.EmployeeListPage;
import org.orangehrm.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {

    private final WebDriver driver;
    private final WaitHelper waitHelper;

    // ===== Locators =====
    private final By DASHBOARD_HEADER = By.xpath("//span[text()='Dashboard']");
    private final By PIM_LINK = By.xpath("//span[text()='PIM']");
    private final By EMPLOYEE_LIST_LINK = By.xpath("//a[normalize-space()='Employee List']");
    private final By ADD_EMPLOYEE_LINK = By.xpath("//a[normalize-space()='Add Employee']");


    // ===== Constructor =====
    public DashboardPage(WebDriver driver, WaitHelper waitHelper) {
        this.driver = driver;
        this.waitHelper = waitHelper;
        verifyLoginSuccessful();
    }

    public void verifyLoginSuccessful() {
        waitHelper.waitForElementToBeVisible(DASHBOARD_HEADER);
        System.out.println("[DashboardPage] Login successful — Dashboard loaded.");
    }

    /**
     * RESOLVES ERROR: Checks if the Dashboard header is displayed.
     */
    public boolean isDashboardHeaderDisplayed() {
        try {
            return waitHelper.waitForElementToBeVisible(DASHBOARD_HEADER).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ===== Primary Action Methods (Page Chaining) =====

    /**
     * Navigates to Employee List page (uses robust synchronization).
     */
    public EmployeeListPage clickEmployeeList() {
        System.out.println("[DashboardPage] Navigating to Employee List...");
        waitHelper.waitForElementToBeClickable(PIM_LINK).click();
        waitHelper.waitForElementToBeVisible(EMPLOYEE_LIST_LINK);
        waitHelper.waitForElementToBeClickable(EMPLOYEE_LIST_LINK).click();
        return new EmployeeListPage(driver, waitHelper);
    }

    /**
     * RESOLVES ERROR: Navigates to Add Employee page.
     */
    public AddEmployeePage clickAddEmployee() {
        System.out.println("[DashboardPage] Navigating to Add Employee...");
        waitHelper.waitForElementToBeClickable(PIM_LINK).click();
        waitHelper.waitForElementToBeVisible(ADD_EMPLOYEE_LINK);
        waitHelper.waitForElementToBeClickable(ADD_EMPLOYEE_LINK).click();
        return new AddEmployeePage(driver, waitHelper);
    }
}