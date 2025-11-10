package org.orangehrm.pages;

import org.orangehrm.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {

    private final WebDriver driver;
    private final WaitHelper waitHelper;

    // ===== Locators =====
    private final By DASHBOARD_HEADER = By.xpath("//span[text()='Dashboard']");
    private final By PIM_LINK = By.xpath("//span[text()='PIM']");
    private final By EMPLOYEE_LIST_LINK = By.xpath("//a[contains(@href, '/pim/viewEmployeeList')]");
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

    // ===== Primary Action Methods (Page Chaining) =====

    /**
     * Navigates to Employee List page and returns the EmployeeListPage.
     */
    public EmployeeListPage clickEmployeeList() {
        System.out.println("[DashboardPage] Navigating to Employee List...");

        // 1. Click PIM to expand the menu
        waitHelper.waitForElementToBeClickable(PIM_LINK).click();

        // 2. Wait for the target link to be visible (should stabilize the menu)
        waitHelper.waitForElementToBeVisible(EMPLOYEE_LIST_LINK);

        // 3. Wait for the link to be clickable and click it
        waitHelper.waitForElementToBeClickable(EMPLOYEE_LIST_LINK).click();

        return new EmployeeListPage(driver, waitHelper);
    }

    /**
     * Navigates to Add Employee page.
     */
    public AddEmployeePage clickAddEmployee() {
        System.out.println("[DashboardPage] Navigating to Add Employee...");
        waitHelper.waitForElementToBeClickable(PIM_LINK).click();

        waitHelper.waitForElementToBeVisible(ADD_EMPLOYEE_LINK);
        waitHelper.waitForElementToBeClickable(ADD_EMPLOYEE_LINK).click();

        return new AddEmployeePage(driver, waitHelper);
    }
    public boolean isDashboardHeaderDisplayed() {
        try {
            // We use a short wait time here (default to what WaitHelper uses)
            // to check for visibility without relying solely on try-catch.
            return waitHelper.waitForElementToBeVisible(DASHBOARD_HEADER).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}