
package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By profileMenu = By.cssSelector("p.oxd-userdropdown-name");
    private By logoutBtn = By.xpath("//a[text()='Logout']");
    private By adminPageLocator = By.xpath("//a[contains(@href,'/web/index.php/admin/viewAdminModule')]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void naviagteToAdminPage() {
        wait.until(ExpectedConditions.elementToBeClickable(adminPageLocator)).click();
    }

    public String getLoggedInUsername() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(profileMenu)).getText();
    }

    public void logout() {
        try {
            WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(profileMenu));
            profile.click();
            WebElement logoutElement = wait.until(ExpectedConditions.visibilityOfElementLocated(logoutBtn));
            wait.until(ExpectedConditions.elementToBeClickable(logoutElement));
            logoutElement.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Username']")));
        } catch (Exception e) {
            System.out.println("Logout failed: " + e.getMessage());
        }
    }

    public boolean isAtDashboard() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(profileMenu));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
