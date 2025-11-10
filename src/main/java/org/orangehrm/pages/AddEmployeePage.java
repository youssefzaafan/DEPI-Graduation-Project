package org.orangehrm.pages;

import org.orangehrm.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the PIM 'Add Employee' screen.
 */
public class AddEmployeePage {

    private final WebDriver driver;
    private final WaitHelper waitHelper;

    // ===== Locators =====
    private final By PAGE_HEADER = By.xpath("//h6[text()='Add Employee']");
    private final By FIRST_NAME_INPUT = By.xpath("//input[@name='firstName']");
    private final By MIDDLE_NAME_INPUT = By.xpath("//input[@name='middleName']");
    private final By LAST_NAME_INPUT = By.xpath("//input[@name='lastName']");
    private final By EMPLOYEE_ID_INPUT = By.xpath("//label[text()='Employee Id']/following::input[1]");
    private final By SAVE_BUTTON = By.xpath("//button[@type='submit' and normalize-space()='Save']");
    private final By CANCEL_BUTTON = By.xpath("//button[@type='button' and normalize-space()='Cancel']");
    private final By SUCCESS_TOAST = By.xpath("//div[@id='oxd-toaster_1']//p[text()='Success']");
    private final By PERSONAL_DETAILS_HEADER = By.xpath("//h6[text()='Personal Details']");

    // Relative locator for validation error (finds the error text relative to the input's parent container)
    private final By REQUIRED_ERROR_RELATIVE = By.xpath("./following-sibling::span[text()='Required']");

    // ===== Constructor =====
    public AddEmployeePage(WebDriver driver, WaitHelper waitHelper) {
        this.driver = driver;
        this.waitHelper = waitHelper;
        verifyPageLoaded();
    }

    // ===== Verification Methods =====
    public void verifyPageLoaded() {
        waitHelper.waitForElementToBeVisible(PAGE_HEADER);
        System.out.println("[AddEmployeePage] Add Employee page loaded successfully.");
    }

    public boolean verifySuccessToast() {
        try {
            // Wait for the success toast to appear and then disappear (implicit synchronization)
            waitHelper.waitForElementToBeVisible(SUCCESS_TOAST);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ===== Helper Method (Strict Matching for Validation) =====

    /**
     * Maps a strict field name string to its By locator.
     */
    private By getLocatorForField(String fieldName) {
        String cleanFieldName = fieldName.trim();
        if (cleanFieldName.equalsIgnoreCase("First Name")) {
            return FIRST_NAME_INPUT;
        } else if (cleanFieldName.equalsIgnoreCase("Last Name")) {
            return LAST_NAME_INPUT;
        }
        System.err.println("[AddEmployeePage] Error: Field name '" + fieldName + "' not mapped in getLocatorForField.");
        return null;
    }

    // ===== Action Methods =====

    public void fillRequiredFields(String firstName, String lastName) {
        waitHelper.waitForElementToBeVisible(FIRST_NAME_INPUT).clear();
        waitHelper.waitForElementToBeVisible(FIRST_NAME_INPUT).sendKeys(firstName);

        waitHelper.waitForElementToBeVisible(LAST_NAME_INPUT).clear();
        waitHelper.waitForElementToBeVisible(LAST_NAME_INPUT).sendKeys(lastName);
    }

    public void submitEmployee() {
        System.out.println("[AddEmployeePage] Clicking Save button.");
        waitHelper.waitForElementToBeClickable(SAVE_BUTTON).click();
    }

    /**
     * Clicks Cancel button and uses Page Chaining to return to the Employee List Page.
     * @return The EmployeeListPage object.
     */
    public EmployeeListPage clickCancelButton() {
        System.out.println("[AddEmployeePage] Clicking Cancel button.");
        waitHelper.waitForElementToBeClickable(CANCEL_BUTTON).click();

        return new EmployeeListPage(driver, waitHelper);
    }

    /**
     * Retrieves the validation error message next to a field.
     */
    public String getRequiredFieldError(String fieldName) {
        By fieldLocator = getLocatorForField(fieldName);

        if (fieldLocator == null) return null;

        try {
            // 1. Locate the input field element
            WebElement inputElement = waitHelper.waitForElementToBeVisible(fieldLocator);
            // 2. Navigate up to the input's immediate parent div
            WebElement parentOfInput = inputElement.findElement(By.xpath("./.."));
            // 3. Find the sibling element containing the error text using the relative locator
            WebElement errorElement = parentOfInput.findElement(REQUIRED_ERROR_RELATIVE);
            return errorElement.getText();

        } catch (Exception e) {
            // Return null if the error text element is not found (i.e., validation passed or locator failed)
            return null;
        }
    }
}