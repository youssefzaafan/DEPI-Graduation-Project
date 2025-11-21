package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EmployeeListPage {

    private WebDriver driver;
    private WebDriverWait wait;


    private By employeeListHeader = By.xpath("//h5[contains(text(),'Employee List')]");
    private By pimMenuLink = By.xpath("//a[normalize-space()='PIM']");
    private By addEmployeeBtn = By.xpath("//button[@type='button'][normalize-space()='Add']");
    private By empNameField = By.xpath("(//input[@placeholder='Type for hints...'])[1]");
    private By searchBtn = By.xpath("//button[normalize-space()='Search']");
    private By resetBtn  = By.xpath("//button[normalize-space()='Reset']");
    private By resultRows = By.xpath("//div[@class='oxd-table-body']/div[@role='row']");
    private By noRecordsFound = By.xpath("//span[normalize-space(text())='No Records Found']");
    private By autocompleteResult = By.xpath("//div[@role='option']//span");


    // --------------------------- Methods ----------------------------------

    public void navigateToPIM() {
        wait.until(ExpectedConditions.elementToBeClickable(pimMenuLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(empNameField));
    }
    public EmployeeListPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public boolean isEmployeeListPageLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(employeeListHeader)).isDisplayed();
    }

    public void clickAddEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeBtn)).click();
    }

    public void searchEmployeeByName(String name) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(empNameField));
        nameField.clear();
        nameField.sendKeys(name);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(autocompleteResult));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].dispatchEvent(new Event('change'));", nameField);

            wait.until(ExpectedConditions.attributeContains(empNameField, "value", name));

        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("No autocomplete result found for: " + name + ". Proceeding to search.");
        }

        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
    }

    public void searchEmployee(String name) {
        searchEmployeeByName(name);
    }

    public boolean isResultVisible() {
        return driver.findElements(resultRows).size() > 0;
    }

    public boolean isNoRecordMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(noRecordsFound)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void resetSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(resetBtn)).click();
    }
}