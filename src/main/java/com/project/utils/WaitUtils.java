package com.project.utils;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

    // Private constructor to prevent instantiation
    private WaitUtils() {
        throw new IllegalStateException("Utility class");
    }

    // Default timeout in seconds
    private static final int DEFAULT_TIMEOUT = 10;

    // Long timeout for slow operations
    private static final int LONG_TIMEOUT = 30;

    // Short timeout for quick checks
    private static final int SHORT_TIMEOUT = 5;

    // Polling interval in milliseconds
    private static final int DEFAULT_POLLING = 500;

    // Short polling for faster checks
    private static final int FAST_POLLING = 250;

    /* -------------------- WAIT BUILDER METHODS -------------------- */

    private static WebDriverWait getWait(WebDriver driver, int timeoutInSeconds) {
        return (WebDriverWait) new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(DEFAULT_POLLING))
                .ignoring(Exception.class);
    }

    private static WebDriverWait getWait(WebDriver driver, int timeoutInSeconds, int pollingInMillis) {
        return (WebDriverWait) new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(pollingInMillis))
                .ignoring(Exception.class);
    }

    /* -------------------- VISIBILITY WAITS -------------------- */

    public static WebElement waitForVisibility(WebDriver driver, WebElement element) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForVisibility(WebDriver driver, WebElement element, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(WebDriver driver, By locator, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForVisibility(WebDriver driver, By locator, int timeoutInSeconds,
            int pollingInMillis) {
        return getWait(driver, timeoutInSeconds, pollingInMillis)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /* -------------------- CLICKABLE WAITS -------------------- */

    public static WebElement waitForClickable(WebDriver driver, WebElement element) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, WebElement element, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    /* -------------------- PRESENCE WAITS -------------------- */

    public static WebElement waitForPresence(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitForPresence(WebDriver driver, By locator, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitForPresence(WebDriver driver, By locator, int timeoutInSeconds, int pollingInMillis) {
        return getWait(driver, timeoutInSeconds, pollingInMillis)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /* -------------------- MULTIPLE ELEMENTS WAITS -------------------- */

    public static List<WebElement> waitForAllVisible(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static List<WebElement> waitForAllVisible(WebDriver driver, By locator, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static List<WebElement> waitForPresenceOfAll(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static List<WebElement> waitForPresenceOfAll(WebDriver driver, By locator, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static boolean waitForNumberOfElements(WebDriver driver, By locator, int expectedCount) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(driver1 -> driver1.findElements(locator).size() == expectedCount);
    }

    public static boolean waitForNumberOfElements(WebDriver driver, By locator, int expectedCount,
            int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(driver1 -> driver1.findElements(locator).size() == expectedCount);
    }

    /* -------------------- INVISIBILITY WAITS -------------------- */

    public static boolean waitForInvisibility(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean waitForInvisibility(WebDriver driver, WebElement element) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public static boolean waitForInvisibility(WebDriver driver, By locator, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean waitForInvisibility(WebDriver driver, WebElement element, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.invisibilityOf(element));
    }

    /* -------------------- STALENESS WAITS -------------------- */

    public static boolean waitForStaleness(WebDriver driver, WebElement element) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.stalenessOf(element));
    }

    public static boolean waitForStaleness(WebDriver driver, WebElement element, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.stalenessOf(element));
    }

    /* -------------------- TEXT WAITS -------------------- */

    public static boolean waitForTextToBe(WebDriver driver, WebElement element, String expectedText) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.textToBePresentInElement(element, expectedText));
    }

    public static boolean waitForTextToBe(WebDriver driver, By locator, String expectedText) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    }

    public static boolean waitForTextToBe(WebDriver driver, WebElement element, String expectedText,
            int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.textToBePresentInElement(element, expectedText));
    }

    public static boolean waitForTextToBe(WebDriver driver, By locator, String expectedText, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    }

    public static boolean waitForTextNotEmpty(WebDriver driver, WebElement element) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(d -> !element.getText().trim().isEmpty());
    }

    public static boolean waitForTextNotEmpty(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(d -> !d.findElement(locator).getText().trim().isEmpty());
    }

    public static boolean waitForTextNotEmpty(WebDriver driver, WebElement element, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(d -> !element.getText().trim().isEmpty());
    }

    public static boolean waitForTextContains(WebDriver driver, WebElement element, String partialText) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.textToBePresentInElement(element, partialText));
    }

    public static boolean waitForTextContains(WebDriver driver, By locator, String partialText) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, partialText));
    }

    public static boolean waitForTextContains(WebDriver driver, WebElement element, String partialText,
            int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.textToBePresentInElement(element, partialText));
    }

    /* -------------------- ATTRIBUTE WAITS -------------------- */

    public static boolean waitForTextInAttribute(WebDriver driver, WebElement element,
            String attribute, String value) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.attributeContains(element, attribute, value));
    }

    public static boolean waitForAttributeToBe(WebDriver driver, WebElement element,
            String attribute, String value) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    public static boolean waitForAttributeToBe(WebDriver driver, WebElement element,
            String attribute, String value, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    public static boolean waitForAttributeNotEmpty(WebDriver driver, WebElement element, String attribute) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(d -> {
                    String attrValue = element.getAttribute(attribute);
                    return attrValue != null && !attrValue.trim().isEmpty();
                });
    }

    /* -------------------- ELEMENT STATE WAITS -------------------- */

    public static boolean waitForElementSelected(WebDriver driver, WebElement element) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeSelected(element));
    }

    public static boolean waitForElementSelected(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeSelected(locator));
    }

    public static boolean waitForElementNotSelected(WebDriver driver, WebElement element) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementSelectionStateToBe(element, false));
    }

    public static boolean waitForElementEnabled(WebDriver driver, WebElement element) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(d -> element.isEnabled());
    }

    public static boolean waitForElementDisabled(WebDriver driver, WebElement element) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(d -> !element.isEnabled());
    }

    /* -------------------- ALERT WAITS -------------------- */

    public static Alert waitForAlert(WebDriver driver) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.alertIsPresent());
    }

    public static Alert waitForAlert(WebDriver driver, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.alertIsPresent());
    }

    /* -------------------- FRAME WAITS -------------------- */

    public static WebDriver waitForFrameAndSwitch(WebDriver driver, By frameLocator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public static WebDriver waitForFrameAndSwitch(WebDriver driver, WebElement frameElement) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }

    public static WebDriver waitForFrameAndSwitch(WebDriver driver, String frameNameOrId) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));
    }

    public static WebDriver waitForFrameAndSwitch(WebDriver driver, By frameLocator, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    /* -------------------- PAGE LOAD WAITS -------------------- */

    public static void waitForPageLoad(WebDriver driver) {
        getWait(driver, DEFAULT_TIMEOUT).until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete"));
    }

    public static void waitForPageLoad(WebDriver driver, int timeoutInSeconds) {
        getWait(driver, timeoutInSeconds).until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete"));
    }

    public static void waitForPageLoadComplete(WebDriver driver) {
        getWait(driver, LONG_TIMEOUT).until(webDriver -> {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;

            // Check document ready state
            boolean isDocumentComplete = js.executeScript("return document.readyState")
                    .equals("complete");

            // Check jQuery (if present)
            boolean isJQueryComplete = true;
            try {
                isJQueryComplete = (Boolean) js.executeScript(
                        "if (typeof jQuery !== 'undefined') { " +
                                "    return jQuery.active === 0; " +
                                "} else { " +
                                "    return true; " +
                                "}");
            } catch (Exception e) {
                // jQuery not present or error
            }

            return isDocumentComplete && isJQueryComplete;
        });
    }

    /* -------------------- URL WAITS -------------------- */

    public static boolean waitForUrlContains(WebDriver driver, String text) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.urlContains(text));
    }

    public static boolean waitForUrlContains(WebDriver driver, String text, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.urlContains(text));
    }

    public static boolean waitForUrlToBe(WebDriver driver, String url) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.urlToBe(url));
    }

    public static boolean waitForUrlToBe(WebDriver driver, String url, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.urlToBe(url));
    }

    public static boolean waitForUrlMatches(WebDriver driver, String regex) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.urlMatches(regex));
    }

    public static boolean waitForUrlMatches(WebDriver driver, String regex, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.urlMatches(regex));
    }

    /* -------------------- WINDOW WAITS -------------------- */

    public static boolean waitForNumberOfWindows(WebDriver driver, int numberOfWindows) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
    }

    public static boolean waitForNumberOfWindows(WebDriver driver, int numberOfWindows, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
    }

    public static void waitForWindowWithTitle(WebDriver driver, String windowTitle) {
        getWait(driver, DEFAULT_TIMEOUT).until(driver1 -> {
            for (String windowHandle : driver1.getWindowHandles()) {
                driver1.switchTo().window(windowHandle);
                if (driver1.getTitle().contains(windowTitle)) {
                    return true;
                }
            }
            return false;
        });
    }

    /* -------------------- JAVASCRIPT WAITS -------------------- */

    public static Object waitForJavaScriptCondition(WebDriver driver, String javascriptCondition) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return " + javascriptCondition));
    }

    public static Object waitForJavaScriptCondition(WebDriver driver, String javascriptCondition,
            int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds)
                .until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return " + javascriptCondition));
    }

    /* -------------------- CUSTOM CONDITION WAITS -------------------- */

    public static <T> T waitForCondition(WebDriver driver, Function<WebDriver, T> condition) {
        return getWait(driver, DEFAULT_TIMEOUT).until(condition);
    }

    public static <T> T waitForCondition(WebDriver driver, Function<WebDriver, T> condition, int timeoutInSeconds) {
        return getWait(driver, timeoutInSeconds).until(condition);
    }

    public static <T> T waitForCondition(WebDriver driver, Function<WebDriver, T> condition,
            int timeoutInSeconds, int pollingInMillis) {
        return getWait(driver, timeoutInSeconds, pollingInMillis).until(condition);
    }

    /* -------------------- QUICK WAITS (SHORT TIMEOUT) -------------------- */

    public static WebElement quickWaitForVisibility(WebDriver driver, By locator) {
        return getWait(driver, SHORT_TIMEOUT, FAST_POLLING)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement quickWaitForClickable(WebDriver driver, WebElement element) {
        return getWait(driver, SHORT_TIMEOUT, FAST_POLLING)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean quickWaitForInvisibility(WebDriver driver, By locator) {
        return getWait(driver, SHORT_TIMEOUT, FAST_POLLING)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /* -------------------- LONG WAITS (FOR SLOW OPERATIONS) -------------------- */

    public static WebElement longWaitForVisibility(WebDriver driver, By locator) {
        return getWait(driver, LONG_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement longWaitForClickable(WebDriver driver, WebElement element) {
        return getWait(driver, LONG_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean longWaitForPageLoad(WebDriver driver) {
        return getWait(driver, LONG_TIMEOUT).until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete"));
    }

    /* -------------------- UTILITY METHODS -------------------- */

    /**
     * NEVER use Thread.sleep()! This is provided only as a last resort.
     * Prefer using explicit waits like waitForVisibility() instead.
     * 
     * @param milliseconds Time to sleep in milliseconds
     * @deprecated Use explicit waits instead. This is only for exceptional cases.
     */
    @Deprecated
    public static void hardWait(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Hard wait interrupted", e);
        }
    }

    /**
     * Wait for element with retry logic (useful for flaky elements)
     */
    public static WebElement waitWithRetry(WebDriver driver, By locator, int maxRetries) {
        int retryCount = 0;
        while (retryCount < maxRetries) {
            try {
                return waitForVisibility(driver, locator, SHORT_TIMEOUT);
            } catch (Exception e) {
                retryCount++;
                hardWait(1000); // Wait 1 second between retries
                if (retryCount == maxRetries) {
                    throw e;
                }
            }
        }
        throw new RuntimeException("Element not found after " + maxRetries + " retries");
    }

    /**
     * Wait for element to be refreshed and stale (useful for dynamic updates)
     */
    public static WebElement waitForElementRefresh(WebDriver driver, WebElement oldElement) {
        // Wait for the old element to become stale
        waitForStaleness(driver, oldElement);

        // Re-locate the element using its original locator strategy
        // Note: This requires the Page Object to store the locator
        throw new UnsupportedOperationException(
                "This method requires the original locator. " +
                        "Use waitForStaleness() followed by waitForVisibility() instead.");
    }

    /*
     * -------------------- CONVENIENCE METHODS FOR COMMON SCENARIOS
     * --------------------
     */

    /**
     * Wait for element to be visible AND clickable (common pattern)
     */
    public static WebElement waitForVisibleAndClickable(WebDriver driver, By locator) {
        WebElement element = waitForVisibility(driver, locator);
        return waitForClickable(driver, element);
    }

    public static WebElement waitForVisibleAndClickable(WebDriver driver, By locator, int timeoutInSeconds) {
        WebElement element = waitForVisibility(driver, locator, timeoutInSeconds);
        return waitForClickable(driver, element, timeoutInSeconds);
    }

    /**
     * Wait for element to disappear and reappear (useful for loading spinners)
     */
    public static WebElement waitForElementToReappear(WebDriver driver, By locator) {
        // Wait for element to disappear (if present)
        try {
            waitForInvisibility(driver, locator, SHORT_TIMEOUT);
        } catch (Exception e) {
            // Element might not have been present initially
        }

        // Wait for element to reappear
        return waitForVisibility(driver, locator);
    }

    /**
     * Wait for loading spinner to disappear
     */
    public static void waitForLoadingToComplete(WebDriver driver, By spinnerLocator) {
        // First wait for spinner to appear (if it does)
        try {
            waitForVisibility(driver, spinnerLocator, SHORT_TIMEOUT);
        } catch (Exception e) {
            // Spinner might not appear
        }

        // Then wait for spinner to disappear
        waitForInvisibility(driver, spinnerLocator, LONG_TIMEOUT);

        // Optional: wait a bit more for content to settle
        hardWait(500);
    }

    /**
     * Wait for AJAX calls to complete (jQuery specific)
     */
    public static void waitForAjaxComplete(WebDriver driver) {
        getWait(driver, DEFAULT_TIMEOUT).until(webDriver -> {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            try {
                return (Boolean) js.executeScript(
                        "return (typeof jQuery !== 'undefined') ? jQuery.active === 0 : true");
            } catch (Exception e) {
                return true; // jQuery not present
            }
        });
    }
}