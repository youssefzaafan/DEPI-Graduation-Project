package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;


import java.time.Duration;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        try {
            WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            WebElement password = driver.findElement(By.name("password"));
            WebElement loginBtn = driver.findElement(By.cssSelector("button[type='submit']"));

            username.sendKeys("Admin");
            password.sendKeys("admin123");
            loginBtn.click();

            wait.until(ExpectedConditions.urlContains("dashboard"));

            System.out.println("✅ Login successful! User reached dashboard.");

            //Admin
            // Search for Exist User
            System.out.println("***********************************************************");
            System.out.println("********Search for current Epmloyee From Admin Page********");
            System.out.println("***********************************************************");
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            //Admin Page
            WebElement admin = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href,'/web/index.php/admin/viewAdminModule')]")));
            admin.click();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".oxd-input.oxd-input--active")));

            //Admin Username
            List<WebElement> adminUsername = driver.findElements(By.cssSelector(".oxd-input.oxd-input--active"));
            adminUsername.get(1).click();
            adminUsername.get(1).sendKeys("Admin");


            List<WebElement> dropDownlists = driver.findElements(By.cssSelector(".oxd-icon.bi-caret-down-fill.oxd-select-text--arrow"));
            // System.out.println(dropDownlists.size());
            //User Role DropDown list
            WebElement userRole = dropDownlists.getFirst();
            userRole.click();

            // Waiting until options to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='listbox']//span[text()='Admin']")));

            // Select "Admin"
            WebElement adminOption = driver.findElement(By.xpath("//div[@role='listbox']//span[text()='Admin']"));
            adminOption.click();

            //Status DropDown list
            WebElement status = dropDownlists.get(1);
            status.click();
            // Select "Enalbed"
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='listbox']//span[text()='Enabled']")));
            WebElement enableOption = driver.findElement(By.xpath("//div[@role='listbox']//span[text()='Enabled']"));
            enableOption.click();


            WebElement displayedName = driver.findElement(By.cssSelector(".oxd-userdropdown-name"));
            //Get the current user nae of the current user
            String usernameText = displayedName.getText();
            //System.out.println(usernameText);
            WebElement employeeName = driver.findElement(By.xpath("//input[@placeholder=\"Type for hints...\"]"));
            employeeName.click();
            employeeName.sendKeys(usernameText);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='listbox']//span")));
            employeeName.sendKeys(Keys.ARROW_DOWN);
            employeeName.sendKeys(Keys.ENTER);

            //Search Button
            WebElement searchButton = driver.findElement(By.xpath("//button[@type=\"submit\"]"));
            searchButton.click();
            System.out.println("✅ SearchFunctionality successful! Admin Page");
            System.out.println("***********************************************************");
            System.out.println("**************Add New Epmloyee From Admin Page*************");
            System.out.println("***********************************************************");

            // Add Button
            List<WebElement> buttons = driver.findElements(By.cssSelector(".oxd-button.oxd-button--medium.oxd-button--secondary"));
            WebElement addButton = buttons.get(1);
            addButton.click();

            //Employee name
            WebElement addEmployeeName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Type for hints...']")
            ));
            addEmployeeName.click();
            addEmployeeName.sendKeys(usernameText);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='listbox']//span")));
            addEmployeeName.sendKeys(Keys.ARROW_DOWN);
            addEmployeeName.sendKeys(Keys.ENTER);


            // User Role
            List<WebElement> addDropDownList = driver.findElements(By.xpath("//div[@class='oxd-select-text-input']"));
            System.out.println(addDropDownList.size());
            WebElement addUserRole = addDropDownList.get(0);
            addUserRole.click();

            // Waiting until options to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='listbox']//span[text()='Admin']")));

            // Select "Admin"
            WebElement addAdminOption = driver.findElement(By.xpath("//div[@role='listbox']//span[text()='Admin']"));
            addAdminOption.click();

            //Status
            WebElement addStatus = addDropDownList.get(1);
            addStatus.click();

            // Waiting until options to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@role='listbox']//span[text()='Enabled']")));

            // Select "Admin"
            WebElement addEnabled= driver.findElement(By.xpath("//div[@role='listbox']//span[text()='Enabled']"));
            addEnabled.click();

            List<WebElement> boxes = driver.findElements(By.xpath("//input[@class=\"oxd-input oxd-input--active\"]"));

            WebElement userName = boxes.get(1);
            userName.sendKeys("YoussefZaafan");



            WebElement addPassword = boxes.get(2);
            addPassword.sendKeys("Z1234z1234");

            WebElement addConfirmPassword = boxes.get(3);
            addConfirmPassword.sendKeys("Z1234z1234");

            //Save button
            WebElement saveButton = driver.findElement(By.xpath("//button[@type=\"submit\"]"));
            saveButton.click();

            System.out.println("✅ AddFunctionality successful! Admin Page");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[@class='oxd-text oxd-text--h6 oxd-topbar-header-breadcrumb-level']")));

//Must make assertion hereeeeeeeeeeeeeeeeee


            WebElement profileMenu = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("p.oxd-userdropdown-name")));
            profileMenu.click();

            WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Logout']")));
            logoutBtn.click();

            wait.until(ExpectedConditions.urlContains("auth/login"));
            System.out.println("✅ Logout successful! User returned to login page.");


        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            driver.quit();
        }

    }
}