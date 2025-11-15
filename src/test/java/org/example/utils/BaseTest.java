package org.example.utils;

import Pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected final String BASE_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(BASE_URL);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("Admin", "admin123");
    }

   /* @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }*/
}
