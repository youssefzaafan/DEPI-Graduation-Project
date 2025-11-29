package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private String currentUsername;

    //Locators
//private By adminPageLocator = By.xpath("//a[contains(@href,'/web/index.php/admin/viewAdminModule");
    private By adminUsernameLocator = By.cssSelector(".oxd-input.oxd-input--active");
    private By dropDownlistsLocator = By.cssSelector(".oxd-icon.bi-caret-down-fill.oxd-select-text--arrow");
    public By adminOptionLocator =By.xpath("//div[@role='listbox']//span[text()='Admin']");
    public By essOptionLocator =By.xpath("//div[@role='listbox']//span[text()='ESS']");
    public By enableOptionLocator =By.xpath("//div[@role='listbox']//span[text()='Enabled']");
    public By disableOptionLocator =By.xpath("//div[@role='listbox']//span[text()='Disabled']");
    private By displayedNameLocator =By.cssSelector(".oxd-userdropdown-name");
    private By employeeNameLocator =By.xpath("//input[@placeholder=\"Type for hints...\"]");
    private By searchButtonLocator =By.xpath("//button[@type=\"submit\"]");
    public By userRoleDropDownListOptionsLocator =By.xpath("//div[@role='listbox']//span[text()='Admin' or contains(text(),'ESS')]");
    public By statusDropDownListOptionsLocator =By.xpath("//div[@role='listbox']//span[text()='Enabled' or contains(text(),'Disabled')]");
    private By suggestEmployeeNameLocator =By.xpath("//div[@role='listbox']//span");
    private By searchResultRowLocator = By.xpath("//div[@class='oxd-table-body']//div[@role='row']");
    private By addButtonLocator = By.cssSelector(".oxd-button.oxd-button--medium.oxd-button--secondary");
    private By addEmployeeNameLocator = By.xpath("//input[@placeholder='Type for hints...']");
    private By addEmployeeNameSuggestLocator = By.xpath("//div[@role='listbox']//span");
    private By saveButtonLocator = By.xpath("//button[@type=\"submit\"]");
    private By addBoxesAdd = By.xpath("//input[@class=\"oxd-input oxd-input--active\"]");
    private By addPassword = By.xpath("//input[@type=\"password\"]");
    private By successMessageTextLocator = By.xpath("//*[contains(text(),'Success')]");
    private By actionButton = By.xpath("//*[@class=\"oxd-icon-button oxd-table-cell-action-space\"]");
    private By recordsFoundLocator =By.className("oxd-checkbox-wrapper");
    private By deleteSelectedButtonLocator = By.cssSelector("button.oxd-button--label-danger");


    public     WebElement employeeName;
    private    WebElement searchButton;
    private    WebElement addButton;
    private    WebElement deleteButton;
    private    WebElement checkBoxUserName;

    //Constructor
    public AdminPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

/*
***********************************************
************Methods of System Users************
***********************************************
 */
    public WebElement getAdminOption() {
        return driver.findElement(adminOptionLocator);
    }

    public WebElement getEssOption() {
        return driver.findElement(essOptionLocator);
    }

    public WebElement getEnableOption() {
        return driver.findElement(enableOptionLocator);
    }

    public WebElement getStatusDropdown() {
        return driver.findElements(dropDownlistsLocator).get(1);
    }

    public WebElement getUserRoleDropdown() {
        return driver.findElements(dropDownlistsLocator).get(0);
    }
    public String getCurrentUsername() {
        return driver.findElement(displayedNameLocator).getText();
    }

    //Enter username
    public void enterUsername(String username){
        driver.findElement(adminUsernameLocator).clear();
        driver.findElements(adminUsernameLocator).get(1).sendKeys(username);
    }
    //Select option From dropdown list
    public void selectOption(WebElement dropdown, String optionText) {
        dropdown.click();
        By optionLocator = By.xpath("//div[@role='listbox']//span[normalize-space()='" + optionText + "']");
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        driver.findElement(optionLocator).click();
    }


    //Enter employee name
    public void enterEmployeename(String username){
        WebElement employeeName = driver.findElement(employeeNameLocator);
        employeeName.clear();
        employeeName.click();
        employeeName.sendKeys(username);
        wait.until(ExpectedConditions.presenceOfElementLocated(suggestEmployeeNameLocator));
        employeeName.sendKeys(Keys.ARROW_DOWN);
        employeeName.sendKeys(Keys.ENTER);
    }
    //Click on Search Button
    public void clickSearchButton(){
        searchButton = driver.findElement(searchButtonLocator);
        searchButton.click();
    }

    /*
     ***********************************************
     ************Methods of Add Users***************
     ***********************************************
     */
    //Click on Add Button
    public void clickAddButton(){
        List<WebElement> buttons = driver.findElements(addButtonLocator);
        addButton = buttons.get(1);
        addButton.click();
    }

    //Enter employee name for Add page
    public void addEmployeename(String username){
        WebElement employeeName = driver.findElement(addEmployeeNameLocator);
        employeeName.clear();
        employeeName.click();
        employeeName.sendKeys(username);
        wait.until(ExpectedConditions.presenceOfElementLocated(addEmployeeNameSuggestLocator));
        employeeName.sendKeys(Keys.ARROW_DOWN);
        employeeName.sendKeys(Keys.ENTER);
    }

    //Enter addusername
    public void addUsername(String username){
        driver.findElements(addBoxesAdd).get(1).clear();
        driver.findElements(addBoxesAdd).get(1).sendKeys(username);
    }
    //Enter password
    public void enterPassword(String password){
        //wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(addBoxesAdd, 2));
        driver.findElements(addPassword).get(0).clear();
        driver.findElements(addPassword).get(0).sendKeys(password);
    }
    //Enter confirmpassword
    public void confirmPassword(String password){
        driver.findElements(addPassword).get(1).clear();
        driver.findElements(addPassword).get(1).sendKeys(password);
    }

    //Sava Button
    public void clickSaveButton(){
        searchButton = driver.findElement(saveButtonLocator);
        searchButton.click();
    }


//Validating AdminPage
    public boolean isAtAdminPage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(adminUsernameLocator)).isDisplayed();
    }
//Validating SearchFunctionality
    public boolean isSearchResultDisplayed() {
        List<WebElement> results = driver.findElements(searchResultRowLocator);
        if (!results.isEmpty()){
            return true;
        } else {
            return false;
        }

    }
//Validating SearchFunctionality when delete all users
    public boolean isAdminUserOnlyDisplayed() {
        List<WebElement> results = driver.findElements(searchResultRowLocator);
        if (results.isEmpty()){
            return false;
        } else {
            return true;
        }
    }
    // Check All Users / Searched User
    public void checkAllUsers() {
        List <WebElement> checkBoxes = driver.findElements(recordsFoundLocator);
        checkBoxUserName = checkBoxes.get(0);
        if (!checkBoxUserName.isSelected()) {
            checkBoxUserName.click();
        }
    }
    //Delete Selected Button
    public void clickDeleteSelectedButton(){
        //List<WebElement> actions = driver.findElements(actionButton);
       WebElement deleteSelectedButton =  driver.findElement(deleteSelectedButtonLocator);
        deleteSelectedButton.click();
        WebElement handlePopup = driver.findElement(By.xpath("//i[@class=\"oxd-icon bi-trash oxd-button-icon\"]"));
        handlePopup.click();
    }
//Validating the user is deleted
    public boolean verifyUserDeleted(String username) {
        // Update table rows
        List<WebElement> rows = driver.findElements(By.cssSelector("div.oxd-table-body > div.oxd-table-card"));
        if (rows.isEmpty()) {
            return true;
        }
        //Search for deleted user
        for (WebElement row : rows) {
            String userText = row.getText();
            if (userText.contains(username)) {
                return false; // Still Exist
            }
        }
        return true; // User Deleted
    }
    //Validating the user is added
    public boolean verifyUserAdded(String username) {
        // Update table rows
        List<WebElement> rows = driver.findElements(By.cssSelector("div.oxd-table-body > div.oxd-table-card"));
        if (rows.isEmpty()) {
            return false;
        }
        //Search for deleted user
        for (WebElement row : rows) {
            String userText = row.getText();
            if (userText.contains(username)) {
                return true; // Added
            }
        }
        return false; // User Not Added
    }

}




/*
  public void  getRecordsFound(){
      List<WebElement> checkBoxes = driver.findElements(recordsFoundLocator);
      checkBoxUserName = checkBoxes.get(0);
      checkBoxUserName.click();

  }*/
//Delete Button
/*public void clickDeleteButton(){
    List<WebElement> actions = driver.findElements(actionButton);
    deleteButton = actions.get(4);
    deleteButton.click();
    WebElement handlePopup = driver.findElement(By.xpath("//i[@class=\"oxd-icon bi-trash oxd-button-icon\"]"));
    handlePopup.click();
}*/

/*public int sizeOfBoxes(){
    List<WebElement> boxes = driver.findElements(addBoxesAdd);
    return boxes.size();
}*/

//Get the current username
//    public String getCurrentUsername() {
//        return currentUsername = displayedName.getText();
//    }


//    public void validateNumOfRecordsFound(){
//        WebElement title = driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']")).get(0);
//        System.out.println(title);
//    }

//public boolean isSuccessMessageVisible() {
//    try {
//        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageTextLocator));
//        return message.isDisplayed();
//    } catch (Exception e) {
//        return false;
//    }
//}
//
//public String getSuccessMessageText() {
//    WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageTextLocator));
//    return message.getText();
//}
//
//
//
//public boolean isUserPresentInTable(String username) {
//    List<WebElement> rows = driver.findElements(By.xpath("//div[@class='orangehrm-container']"));
//    for (WebElement row : rows) {
//        if (row.getText().contains(username)) {
//            return true;
//        }
//    }
//    return false;
//}


// public   WebElement adminOption = driver.findElement(adminOptionLocator);
// public   WebElement enableOption = driver.findElement(enableOptionLocator);
//public   WebElement essOption = driver.findElement(essOptionLocator);
//public   WebElement displayedName =

//List<WebElement> dropDownlists;
//  dropDownlists = driver.findElements(dropDownlistsLocator);
//userRole = dropDownlists.get(0);
//  status = dropDownlists.get(1);