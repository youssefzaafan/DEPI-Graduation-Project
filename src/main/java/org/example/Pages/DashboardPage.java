package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By profileMenu = By.cssSelector("p.oxd-userdropdown-name");
    private By logoutBtn = By.xpath("//a[text()='Logout']");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getLoggedInUsername() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(profileMenu)).getText();
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(profileMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
        wait.until(ExpectedConditions.urlContains("auth/login"));
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
