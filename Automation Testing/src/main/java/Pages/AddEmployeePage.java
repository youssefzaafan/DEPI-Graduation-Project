package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddEmployeePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public AddEmployeePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private By addEmployeeHeader = By.xpath("//h6[normalize-space()='Add Employee']");

    private By firstName = By.name("firstName");
    private By middleName = By.name("middleName");
    private By lastName = By.name("lastName");

    private By saveBtn = By.xpath("//button[normalize-space()='Save']");

    private By requiredFieldError = By.xpath("//span[contains(@class,'oxd-input-field-error-message')]");

    private By toastMessage = By.xpath("//div[@id='oxd-toaster_1']//p[text()='Success']");


    // --------------------------- Actions ----------------------------------

    public boolean isAddEmployeePageLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(addEmployeeHeader)).isDisplayed();
    }

    public void enterFirstName(String fName) {
        WebElement fn = wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        fn.clear();
        fn.sendKeys(fName);
    }

    public void enterMiddleName(String mName) {
        WebElement mn = wait.until(ExpectedConditions.visibilityOfElementLocated(middleName));
        mn.clear();
        mn.sendKeys(mName);
    }

    public void enterLastName(String lName) {
        WebElement ln = wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
        ln.clear();
        ln.sendKeys(lName);
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
    }

    public boolean isRequiredFieldErrorDisplayed() {
        return driver.findElements(requiredFieldError).size() > 0;
    }

    public void addEmployee(String fName, String mName, String lName) {
        enterFirstName(fName);
        enterMiddleName(mName);
        enterLastName(lName);
        clickSave();
    }

    public boolean isToastDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}