package org.orangehrm2.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.orangehrm.pages.AddEmployeePage;
import org.orangehrm.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EmployeeListPage {

    private final WebDriver driver;
    private final WaitHelper waitHelper;

    // ===== Locators =====
    private final By PAGE_HEADER = By.xpath("//h5[text()='Employee Information']");
    private final By EMPLOYEE_NAME_INPUT = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private final By EMPLOYEE_ID_INPUT = By.xpath("//label[text()='Employee Id']/following::input[1]");
    private final By EMPLOYMENT_STATUS_DROPDOWN = By.xpath("(//div[@class='oxd-select-text-input'])[1]");
    private final By INCLUDE_DROPDOWN = By.xpath("(//div[@class='oxd-select-text-input'])[2]");
    private final By SEARCH_BUTTON = By.xpath("//button[@type='submit' and normalize-space()='Search']");
    private final By RESET_BUTTON = By.xpath("//button[@type='button' and normalize-space()='Reset']");    private final By ADD_BUTTON = By.xpath("//button[normalize-space()='Add']");
    private final By RECORD_COUNT_TEXT = By.xpath("//div[@class='orangehrm-horizontal-padding orangehrm-vertical-padding']//span");

    // ===== Constructor =====
    public EmployeeListPage(WebDriver driver, WaitHelper waitHelper) {
        this.driver = driver;
        this.waitHelper = waitHelper;
        verifyPageLoaded();
    }

    // ===== Verification =====
    public void verifyPageLoaded() {
        waitHelper.waitForElementToBeVisible(PAGE_HEADER);
        System.out.println("[EmployeeListPage] Employee Information page loaded successfully.");
    }

    // ===== Actions =====

    public void searchEmployeeByName(String name) {
        System.out.println("[EmployeeListPage] Searching for employee: " + name);
        waitHelper.waitForElementToBeVisible(EMPLOYEE_NAME_INPUT).sendKeys(name);

        // Click search and wait for results text to confirm load
        waitHelper.waitForElementToBeClickable(SEARCH_BUTTON).click();
        waitHelper.waitForElementToBeVisible(RECORD_COUNT_TEXT);
    }

    public void selectDropdownOption(By dropdownLocator, String optionText) {
        waitHelper.waitForElementToBeClickable(dropdownLocator).click();

        // Locator for any dropdown option using exact text match
        By optionLocator = By.xpath("//div[@role='option']//span[normalize-space()='" + optionText + "']");
        waitHelper.waitForElementToBeClickable(optionLocator).click();
    }

    // ===== Getters =====

    public String getRecordCountText() {
        return waitHelper.waitForElementToBeVisible(RECORD_COUNT_TEXT).getText();
    }

    public org.orangehrm.pages.AddEmployeePage clickAddButton() {
        waitHelper.waitForElementToBeClickable(ADD_BUTTON).click();
        return new AddEmployeePage(driver, waitHelper);
    }
    // Inside EmployeeListPage.java



// ...

    // New method to check if the specific page header is displayed
    public boolean isEmployeeListPageHeaderDisplayed() {
        try {
            return waitHelper.waitForElementToBeVisible(PAGE_HEADER).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}