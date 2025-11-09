package org.orangehrm.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {

    private final WebDriver driver;
    // Set a default explicit wait timeout
    private static final long DEFAULT_WAIT_SECONDS = 20;

    public WaitHelper(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Creates a WebDriverWait instance with the default timeout.
     */
    private WebDriverWait getWebDriverWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS));
    }

    /**
     * Waits for an element to be visible on the page.
     * @param by The locator of the element.
     * @return The WebElement once it is visible.
     */
    public WebElement waitForElementToBeVisible(By by) {
        System.out.println("[WaitHelper] Waiting for visibility of: " + by.toString());
        return getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Waits for an element to be clickable on the page.
     * @param by The locator of the element.
     * @return The WebElement once it is clickable.
     */
    public WebElement waitForElementToBeClickable(By by) {
        System.out.println("[WaitHelper] Waiting for clickability of: " + by.toString());
        return getWebDriverWait().until(ExpectedConditions.elementToBeClickable(by));
    }

    /**
     * Waits for an element to be present in the DOM (but not necessarily visible).
     * @param by The locator of the element.
     * @return The WebElement once it is present.
     */
    public WebElement waitForElementToExist(By by) {
        System.out.println("[WaitHelper] Waiting for existence of: " + by.toString());
        return getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Waits for the URL to contain a specific fraction.
     * @param fraction The expected fraction of the URL.
     * @return True if the URL contains the fraction within the timeout.
     */
    public boolean waitForUrlContains(String fraction) {
        System.out.println("[WaitHelper] Waiting for URL to contain: " + fraction);
        return getWebDriverWait().until(ExpectedConditions.urlContains(fraction));
    }

    /**
     * Waits for an element to become invisible.
     * Useful for waiting for loading spinners to disappear.
     * @param by The locator of the element.
     * @return True if the element becomes invisible.
     */
    public boolean waitForElementToBecomeInvisible(By by) {
        System.out.println("[WaitHelper] Waiting for invisibility of: " + by.toString());
        return getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
}