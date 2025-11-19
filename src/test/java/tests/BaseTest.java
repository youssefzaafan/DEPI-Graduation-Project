package tests;

import Pages.LoginPage;
import utils.AllureTestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected final String BASE_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    private By dashboardMenuLink = By.xpath("//a[normalize-space()='Dashboard']");
    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        AllureTestListener.setDriver(driver);
        driver.manage().window().maximize();

        //driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(BASE_URL);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("Admin", "admin123");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(dashboardMenuLink));
    }



    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}