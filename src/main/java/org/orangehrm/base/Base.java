package org.orangehrm.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.orangehrm.utils.WaitHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class Base {

    protected static WebDriver driver;
    protected static WaitHelper waitHelper;
    String url = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    /**
     * Launches the browser in GUI mode and navigates to the OrangeHRM demo.
     */
    public void setUp() {
        if (driver == null) {
            System.out.println("[Base] Starting Chrome browser (GUI mode)...");

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-infobars");
            options.addArguments("--remote-allow-origins=*");
            // Headless intentionally NOT used

            driver = new ChromeDriver(options);
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); // Explicit waits only

            waitHelper = new WaitHelper(driver);

            driver.get(url);
            System.out.println("[Base] Navigated to OrangeHRM demo site.");
        }
    }

    /**
     * Quits the browser after tests.
     */
    public void tearDown() {
        if (driver != null) {
            System.out.println("[Base] Closing browser...");
            driver.quit();
            driver = null;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WaitHelper getWaitHelper() {
        return waitHelper;
    }
}
