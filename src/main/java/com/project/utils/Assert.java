package com.project.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Centralized assertion class for UI, functional, and data validations
 * Integrates with Waits utility for robust element validation
 * Provides reusable assertion methods aligned with business behavior
 */
public class Assert {

    // Soft Assert instance for collecting multiple failures
    private static final ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);

    /**
     * Flush all soft assertions and throw if any failures
     */
    public static void assertAll() {
        softAssert.get().assertAll();
    }

    /**
     * Reset soft assertion for new test
     */
    public static void resetSoftAssert() {
        softAssert.remove();
    }

    /* ==================== BASIC ASSERTIONS ==================== */

    public static void assertTrue(boolean condition, String message) {
        try {
            org.testng.Assert.assertTrue(condition, message);
            logPass(message);
        } catch (AssertionError e) {
            logFail(message, e.getMessage());
            throw e;
        }
    }

    public static void assertTrueSoft(boolean condition, String message) {
        softAssert.get().assertTrue(condition, message);
        logAssertion("Soft Assert", condition, message);
    }

    public static void assertFalse(boolean condition, String message) {
        try {
            org.testng.Assert.assertFalse(condition, message);
            logPass(message);
        } catch (AssertionError e) {
            logFail(message, e.getMessage());
            throw e;
        }
    }

    public static void assertFalseSoft(boolean condition, String message) {
        softAssert.get().assertFalse(condition, message);
        logAssertion("Soft Assert", !condition, message);
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        try {
            org.testng.Assert.assertEquals(actual, expected, message);
            logPass(message + " [Expected: " + expected + ", Actual: " + actual + "]");
        } catch (AssertionError e) {
            logFail(message + " [Expected: " + expected + ", Actual: " + actual + "]", e.getMessage());
            throw e;
        }
    }

    public static void assertEqualsSoft(Object actual, Object expected, String message) {
        softAssert.get().assertEquals(actual, expected, message);
        logAssertion("Soft Assert", actual.equals(expected),
                message + " [Expected: " + expected + ", Actual: " + actual + "]");
    }

    public static void assertNotEquals(Object actual, Object expected, String message) {
        try {
            org.testng.Assert.assertNotEquals(actual, expected, message);
            logPass(message);
        } catch (AssertionError e) {
            logFail(message, e.getMessage());
            throw e;
        }
    }

    public static void assertNotNull(Object object, String message) {
        try {
            org.testng.Assert.assertNotNull(object, message);
            logPass(message);
        } catch (AssertionError e) {
            logFail(message, e.getMessage());
            throw e;
        }
    }

    public static void assertNull(Object object, String message) {
        try {
            org.testng.Assert.assertNull(object, message);
            logPass(message);
        } catch (AssertionError e) {
            logFail(message, e.getMessage());
            throw e;
        }
    }

    public static void assertContains(String actual, String expectedSubstring, String message) {
        try {
            org.testng.Assert.assertTrue(actual.contains(expectedSubstring), message);
            logPass(message + " [Contains: " + expectedSubstring + "]");
        } catch (AssertionError e) {
            logFail(message + " [Does not contain: " + expectedSubstring + "]", e.getMessage());
            throw e;
        }
    }

    public static void assertNotContains(String actual, String unexpectedSubstring, String message) {
        try {
            org.testng.Assert.assertFalse(actual.contains(unexpectedSubstring), message);
            logPass(message + " [Does not contain: " + unexpectedSubstring + "]");
        } catch (AssertionError e) {
            logFail(message + " [Contains: " + unexpectedSubstring + "]", e.getMessage());
            throw e;
        }
    }

    /* ==================== UI ELEMENT ASSERTIONS ==================== */

    public static void assertElementVisible(WebDriver driver, WebElement element, String elementName) {
        try {
            boolean isVisible = WaitUtils.waitForVisibility(driver, element, 10) != null;
            assertTrue(isVisible, elementName + " should be visible");
        } catch (Exception e) {
            logFail(elementName + " should be visible", e.getMessage());
            throw new AssertionError(elementName + " is not visible", e);
        }
    }

    public static void assertElementVisible(WebDriver driver, By locator, String elementName) {
        try {
            boolean isVisible = WaitUtils.waitForVisibility(driver, locator, 10) != null;
            assertTrue(isVisible, elementName + " should be visible");
        } catch (Exception e) {
            logFail(elementName + " should be visible", e.getMessage());
            throw new AssertionError(elementName + " is not visible", e);
        }
    }

    public static void assertElementNotVisible(WebDriver driver, WebElement element, String elementName) {
        try {
            boolean isNotVisible = WaitUtils.waitForInvisibility(driver, element, 5);
            assertTrue(isNotVisible, elementName + " should not be visible");
        } catch (Exception e) {
            logFail(elementName + " should not be visible", e.getMessage());
            throw new AssertionError(elementName + " is still visible", e);
        }
    }

    public static void assertElementNotVisible(WebDriver driver, By locator, String elementName) {
        try {
            boolean isNotVisible = WaitUtils.waitForInvisibility(driver, locator, 5);
            assertTrue(isNotVisible, elementName + " should not be visible");
        } catch (Exception e) {
            logFail(elementName + " should not be visible", e.getMessage());
            throw new AssertionError(elementName + " is still visible", e);
        }
    }

    public static void assertElementEnabled(WebDriver driver, WebElement element, String elementName) {
        try {
            boolean isEnabled = element.isEnabled();
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(d -> element.isEnabled() == isEnabled);
            assertTrue(isEnabled, elementName + " should be enabled");
        } catch (Exception e) {
            logFail(elementName + " should be enabled", e.getMessage());
            throw new AssertionError(elementName + " is disabled", e);
        }
    }

    public static void assertElementDisabled(WebDriver driver, WebElement element, String elementName) {
        try {
            boolean isDisabled = !element.isEnabled();
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(d -> !element.isEnabled() == isDisabled);
            assertTrue(isDisabled, elementName + " should be disabled");
        } catch (Exception e) {
            logFail(elementName + " should be disabled", e.getMessage());
            throw new AssertionError(elementName + " is enabled", e);
        }
    }

    public static void assertElementClickable(WebDriver driver, WebElement element, String elementName) {
        try {
            WebElement clickableElement = WaitUtils.waitForClickable(driver, element, 10);
            assertNotNull(clickableElement, elementName + " should be clickable");
        } catch (Exception e) {
            logFail(elementName + " should be clickable", e.getMessage());
            throw new AssertionError(elementName + " is not clickable", e);
        }
    }

    public static void assertElementClickable(WebDriver driver, By locator, String elementName) {
        try {
            WebElement clickableElement = WaitUtils.waitForClickable(driver, locator, 10);
            assertNotNull(clickableElement, elementName + " should be clickable");
        } catch (Exception e) {
            logFail(elementName + " should be clickable", e.getMessage());
            throw new AssertionError(elementName + " is not clickable", e);
        }
    }

    public static void assertElementText(WebDriver driver, WebElement element, String expectedText,
            String elementName) {
        try {
            WebElement visibleElement = WaitUtils.waitForVisibility(driver, element, 10);
            String actualText = visibleElement.getText().trim();
            assertEquals(actualText, expectedText, elementName + " text verification");
        } catch (Exception e) {
            logFail("Text verification failed for " + elementName, e.getMessage());
            throw new AssertionError("Text mismatch for " + elementName, e);
        }
    }

    public static void assertElementText(WebDriver driver, By locator, String expectedText, String elementName) {
        try {
            WebElement visibleElement = WaitUtils.waitForVisibility(driver, locator, 10);
            String actualText = visibleElement.getText().trim();
            assertEquals(actualText, expectedText, elementName + " text verification");
        } catch (Exception e) {
            logFail("Text verification failed for " + elementName, e.getMessage());
            throw new AssertionError("Text mismatch for " + elementName, e);
        }
    }

    public static void assertElementTextContains(WebDriver driver, WebElement element, String expectedPartialText,
            String elementName) {
        try {
            WebElement visibleElement = WaitUtils.waitForVisibility(driver, element, 10);
            String actualText = visibleElement.getText().trim();
            assertTrue(actualText.contains(expectedPartialText),
                    elementName + " should contain text: " + expectedPartialText);
        } catch (Exception e) {
            logFail("Text contains check failed for " + elementName, e.getMessage());
            throw new AssertionError("Text does not contain expected partial text for " + elementName, e);
        }
    }

    public static void assertElementTextContains(WebDriver driver, By locator, String expectedPartialText,
            String elementName) {
        try {
            WebElement visibleElement = WaitUtils.waitForVisibility(driver, locator, 10);
            String actualText = visibleElement.getText().trim();
            assertTrue(actualText.contains(expectedPartialText),
                    elementName + " should contain text: " + expectedPartialText);
        } catch (Exception e) {
            logFail("Text contains check failed for " + elementName, e.getMessage());
            throw new AssertionError("Text does not contain expected partial text for " + elementName, e);
        }
    }

    public static void assertElementTextNotContains(WebDriver driver, WebElement element, String unexpectedPartialText,
            String elementName) {
        try {
            WebElement visibleElement = WaitUtils.waitForVisibility(driver, element, 10);
            String actualText = visibleElement.getText().trim();
            assertFalse(actualText.contains(unexpectedPartialText),
                    elementName + " should not contain text: " + unexpectedPartialText);
        } catch (Exception e) {
            logFail("Text not contains check failed for " + elementName, e.getMessage());
            throw new AssertionError("Text contains unexpected partial text for " + elementName, e);
        }
    }

    public static void assertElementAttribute(WebDriver driver, WebElement element, String attribute,
            String expectedValue, String elementName) {
        try {
            WaitUtils.waitForVisibility(driver, element, 10);
            String actualValue = element.getAttribute(attribute);
            assertEquals(actualValue, expectedValue,
                    elementName + " attribute '" + attribute + "' verification");
        } catch (Exception e) {
            logFail("Attribute verification failed for " + elementName, e.getMessage());
            throw new AssertionError("Attribute mismatch for " + elementName, e);
        }
    }

    public static void assertElementAttributeContains(WebDriver driver, WebElement element, String attribute,
            String expectedPartialValue, String elementName) {
        try {
            WaitUtils.waitForVisibility(driver, element, 10);
            String actualValue = element.getAttribute(attribute);
            assertTrue(actualValue.contains(expectedPartialValue),
                    elementName + " attribute should contain: " + expectedPartialValue);
        } catch (Exception e) {
            logFail("Attribute contains check failed for " + elementName, e.getMessage());
            throw new AssertionError("Attribute does not contain expected value", e);
        }
    }

    public static void assertElementCssProperty(WebDriver driver, WebElement element, String property,
            String expectedValue, String elementName) {
        try {
            WaitUtils.waitForVisibility(driver, element, 10);
            String actualValue = element.getCssValue(property);
            assertEquals(actualValue, expectedValue,
                    elementName + " CSS property '" + property + "' verification");
        } catch (Exception e) {
            logFail("CSS property verification failed for " + elementName, e.getMessage());
            throw new AssertionError("CSS property mismatch for " + elementName, e);
        }
    }

    public static void assertElementPresent(WebDriver driver, By locator, String elementName) {
        try {
            WebElement element = WaitUtils.waitForPresence(driver, locator, 10);
            assertNotNull(element, elementName + " should be present in DOM");
        } catch (Exception e) {
            logFail(elementName + " should be present in DOM", e.getMessage());
            throw new AssertionError(elementName + " is not present in DOM", e);
        }
    }

    public static void assertElementNotPresent(WebDriver driver, By locator, String elementName) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            assertTrue(elements.isEmpty(), elementName + " should not be present in DOM");
        } catch (Exception e) {
            logFail(elementName + " should not be present in DOM", e.getMessage());
            throw new AssertionError(elementName + " is present in DOM", e);
        }
    }

    public static void assertElementSelected(WebDriver driver, WebElement element, String elementName) {
        try {
            boolean isSelected = element.isSelected();
            assertTrue(isSelected, elementName + " should be selected");
        } catch (Exception e) {
            logFail(elementName + " should be selected", e.getMessage());
            throw new AssertionError(elementName + " is not selected", e);
        }
    }

    public static void assertElementNotSelected(WebDriver driver, WebElement element, String elementName) {
        try {
            boolean isNotSelected = !element.isSelected();
            assertTrue(isNotSelected, elementName + " should not be selected");
        } catch (Exception e) {
            logFail(elementName + " should not be selected", e.getMessage());
            throw new AssertionError(elementName + " is selected", e);
        }
    }

    public static void assertElementHasClass(WebDriver driver, WebElement element, String className,
            String elementName) {
        try {
            WaitUtils.waitForVisibility(driver, element, 10);
            String classAttribute = element.getAttribute("class");
            assertTrue(classAttribute.contains(className),
                    elementName + " should have CSS class: " + className);
        } catch (Exception e) {
            logFail("CSS class verification failed for " + elementName, e.getMessage());
            throw new AssertionError("Element does not have expected CSS class", e);
        }
    }

    public static void assertElementValue(WebDriver driver, WebElement element, String expectedValue,
            String elementName) {
        try {
            WaitUtils.waitForVisibility(driver, element, 10);
            String actualValue = element.getAttribute("value");
            assertEquals(actualValue, expectedValue, elementName + " value verification");
        } catch (Exception e) {
            logFail("Element value verification failed for " + elementName, e.getMessage());
            throw new AssertionError("Element value mismatch for " + elementName, e);
        }
    }

    /* ==================== PAGE & NAVIGATION ASSERTIONS ==================== */

    public static void assertPageTitle(WebDriver driver, String expectedTitle) {
        try {
            WaitUtils.waitForPageLoad(driver);
            String actualTitle = driver.getTitle().trim();
            assertEquals(actualTitle, expectedTitle, "Page title verification");
        } catch (Exception e) {
            logFail("Page title verification failed", e.getMessage());
            throw new AssertionError("Page title mismatch", e);
        }
    }

    public static void assertPageTitleContains(WebDriver driver, String expectedPartialTitle) {
        try {
            WaitUtils.waitForPageLoad(driver);
            String actualTitle = driver.getTitle().trim();
            assertTrue(actualTitle.contains(expectedPartialTitle),
                    "Page title should contain: " + expectedPartialTitle);
        } catch (Exception e) {
            logFail("Page title contains check failed", e.getMessage());
            throw new AssertionError("Page title does not contain expected text", e);
        }
    }

    public static void assertCurrentUrl(WebDriver driver, String expectedUrl) {
        try {
            WaitUtils.waitForPageLoad(driver);
            String actualUrl = driver.getCurrentUrl();
            assertEquals(actualUrl, expectedUrl, "Current URL verification");
        } catch (Exception e) {
            logFail("URL verification failed", e.getMessage());
            throw new AssertionError("URL mismatch", e);
        }
    }

    public static void assertCurrentUrlContains(WebDriver driver, String expectedPartialUrl) {
        try {
            WaitUtils.waitForPageLoad(driver);
            String actualUrl = driver.getCurrentUrl();
            assertTrue(actualUrl.contains(expectedPartialUrl),
                    "URL should contain: " + expectedPartialUrl);
        } catch (Exception e) {
            logFail("URL contains check failed", e.getMessage());
            throw new AssertionError("URL does not contain expected text", e);
        }
    }

    public static void assertCurrentUrlPattern(WebDriver driver, String regexPattern) {
        try {
            WaitUtils.waitForPageLoad(driver);
            String actualUrl = driver.getCurrentUrl();
            Pattern pattern = Pattern.compile(regexPattern);
            assertTrue(pattern.matcher(actualUrl).matches(),
                    "URL should match pattern: " + regexPattern);
        } catch (Exception e) {
            logFail("URL pattern match failed", e.getMessage());
            throw new AssertionError("URL does not match expected pattern", e);
        }
    }

    public static void assertPageLoaded(WebDriver driver) {
        try {
            WaitUtils.waitForPageLoadComplete(driver);
            assertTrue(true, "Page should be fully loaded");
        } catch (Exception e) {
            logFail("Page load failed", e.getMessage());
            throw new AssertionError("Page did not load completely", e);
        }
    }

    /* ==================== LIST & COLLECTION ASSERTIONS ==================== */

    public static void assertListSize(List<?> list, int expectedSize, String listName) {
        try {
            assertEquals(list.size(), expectedSize, listName + " size verification");
        } catch (Exception e) {
            logFail(listName + " size verification failed", e.getMessage());
            throw new AssertionError("List size mismatch for " + listName, e);
        }
    }

    public static void assertListNotEmpty(List<?> list, String listName) {
        try {
            assertTrue(!list.isEmpty(), listName + " should not be empty");
        } catch (Exception e) {
            logFail(listName + " should not be empty", e.getMessage());
            throw new AssertionError("List is empty for " + listName, e);
        }
    }

    public static void assertListEmpty(List<?> list, String listName) {
        try {
            assertTrue(list.isEmpty(), listName + " should be empty");
        } catch (Exception e) {
            logFail(listName + " should be empty", e.getMessage());
            throw new AssertionError("List is not empty for " + listName, e);
        }
    }

    public static void assertListContains(List<?> list, Object item, String listName) {
        try {
            assertTrue(list.contains(item), listName + " should contain: " + item);
        } catch (Exception e) {
            logFail(listName + " should contain item", e.getMessage());
            throw new AssertionError("List does not contain expected item", e);
        }
    }

    public static void assertElementsCount(WebDriver driver, By locator, int expectedCount, String elementName) {
        try {
            boolean countMatches = WaitUtils.waitForNumberOfElements(driver, locator, expectedCount, 10);
            assertTrue(countMatches, elementName + " count should be " + expectedCount);
        } catch (Exception e) {
            logFail("Element count verification failed for " + elementName, e.getMessage());
            throw new AssertionError("Element count mismatch for " + elementName, e);
        }
    }

    public static void assertAllElementsVisible(WebDriver driver, By locator, String elementName) {
        try {
            List<WebElement> elements = WaitUtils.waitForAllVisible(driver, locator, 10);
            assertTrue(!elements.isEmpty(), elementName + " should have visible elements");
            for (int i = 0; i < elements.size(); i++) {
                assertTrue(elements.get(i).isDisplayed(),
                        elementName + " #" + (i + 1) + " should be visible");
            }
        } catch (Exception e) {
            logFail("All elements should be visible for " + elementName, e.getMessage());
            throw new AssertionError("Not all elements are visible for " + elementName, e);
        }
    }

    /* ==================== BUSINESS FLOW ASSERTIONS ==================== */

    public static void assertSuccessfulOperation(String operationName, boolean successCondition) {
        assertTrue(successCondition, operationName + " should complete successfully");
    }

    public static void assertErrorMessage(String actualError, String expectedError, String context) {
        assertEquals(actualError, expectedError, "Error message verification for " + context);
    }

    public static void assertErrorMessageContains(String actualError, String expectedPartialError, String context) {
        assertTrue(actualError.contains(expectedPartialError),
                "Error message should contain '" + expectedPartialError + "' for " + context);
    }

    public static void assertConfirmationMessage(WebDriver driver, WebElement messageElement, String expectedMessage) {
        assertElementText(driver, messageElement, expectedMessage, "Confirmation message");
    }

    public static void assertConfirmationMessage(WebDriver driver, By locator, String expectedMessage) {
        assertElementText(driver, locator, expectedMessage, "Confirmation message");
    }

    public static void assertToastMessage(WebDriver driver, WebElement toastElement, String expectedMessage) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(toastElement));
            assertElementTextContains(driver, toastElement, expectedMessage, "Toast message");
        } catch (Exception e) {
            logFail("Toast message verification failed", e.getMessage());
            throw new AssertionError("Toast message not found or incorrect", e);
        }
    }

    public static void assertSuccessStatus(String action, boolean actualStatus) {
        assertTrue(actualStatus, action + " should be successful");
    }

    public static void assertFailureStatus(String action, boolean actualStatus) {
        assertFalse(actualStatus, action + " should fail");
    }

    public static void assertOperationCompleted(String operationName, long startTime, long timeoutInSeconds) {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        assertTrue(elapsedTime <= timeoutInSeconds,
                operationName + " should complete within " + timeoutInSeconds + " seconds");
    }

    /* ==================== FORM & VALIDATION ASSERTIONS ==================== */

    public static void assertFieldValidationError(WebDriver driver, WebElement errorElement, String expectedError) {
        assertElementText(driver, errorElement, expectedError, "Field validation error");
    }

    public static void assertFieldValidationError(WebDriver driver, By locator, String expectedError) {
        assertElementText(driver, locator, expectedError, "Field validation error");
    }

    public static void assertRequiredField(WebDriver driver, WebElement field, String fieldName) {
        String requiredAttr = field.getAttribute("required");
        assertTrue("true".equals(requiredAttr) || "required".equals(requiredAttr),
                fieldName + " should be a required field");
    }

    public static void assertFieldValue(WebDriver driver, WebElement field, String expectedValue, String fieldName) {
        String actualValue = field.getAttribute("value");
        assertEquals(actualValue, expectedValue, fieldName + " value verification");
    }

    public static void assertFieldPlaceholder(WebDriver driver, WebElement field, String expectedPlaceholder,
            String fieldName) {
        String actualPlaceholder = field.getAttribute("placeholder");
        assertEquals(actualPlaceholder, expectedPlaceholder, fieldName + " placeholder verification");
    }

    public static void assertCheckboxChecked(WebDriver driver, WebElement checkbox, String checkboxName) {
        assertTrue(checkbox.isSelected(), checkboxName + " should be checked");
    }

    public static void assertCheckboxUnchecked(WebDriver driver, WebElement checkbox, String checkboxName) {
        assertFalse(checkbox.isSelected(), checkboxName + " should be unchecked");
    }

    public static void assertRadioSelected(WebDriver driver, WebElement radio, String radioName) {
        assertTrue(radio.isSelected(), radioName + " should be selected");
    }

    public static void assertRadioNotSelected(WebDriver driver, WebElement radio, String radioName) {
        assertFalse(radio.isSelected(), radioName + " should not be selected");
    }

    public static void assertDropdownOptionSelected(WebDriver driver, WebElement dropdown, String expectedOption,
            String dropdownName) {
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(dropdown);
        WebElement selectedOption = select.getFirstSelectedOption();
        assertEquals(selectedOption.getText(), expectedOption,
                dropdownName + " should have option selected: " + expectedOption);
    }

    public static void assertDropdownContainsOption(WebDriver driver, WebElement dropdown, String expectedOption,
            String dropdownName) {
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(dropdown);
        List<WebElement> options = select.getOptions();
        boolean found = false;
        for (WebElement option : options) {
            if (option.getText().equals(expectedOption)) {
                found = true;
                break;
            }
        }
        assertTrue(found, dropdownName + " should contain option: " + expectedOption);
    }

    /* ==================== DATA VALIDATION ASSERTIONS ==================== */

    public static void assertDataEquals(Object actualData, Object expectedData, String dataName) {
        assertEquals(actualData, expectedData, "Data verification for " + dataName);
    }

    public static void assertDataContains(String actualData, String expectedSubstring, String dataName) {
        assertTrue(actualData.contains(expectedSubstring),
                dataName + " should contain: " + expectedSubstring);
    }

    public static void assertDataMatches(String actualData, String regexPattern, String dataName) {
        Pattern pattern = Pattern.compile(regexPattern);
        assertTrue(pattern.matcher(actualData).matches(),
                dataName + " should match pattern: " + regexPattern);
    }

    public static void assertNumericRange(int actualValue, int min, int max, String valueName) {
        assertTrue(actualValue >= min && actualValue <= max,
                valueName + " should be between " + min + " and " + max);
    }

    public static void assertNumericRange(double actualValue, double min, double max, String valueName) {
        assertTrue(actualValue >= min && actualValue <= max,
                valueName + " should be between " + min + " and " + max);
    }

    public static void assertStringLength(String text, int minLength, int maxLength, String fieldName) {
        assertTrue(text.length() >= minLength && text.length() <= maxLength,
                fieldName + " length should be between " + minLength + " and " + maxLength);
    }

    public static void assertStringNotEmpty(String text, String fieldName) {
        assertTrue(text != null && !text.trim().isEmpty(),
                fieldName + " should not be empty");
    }

    public static void assertStringEmpty(String text, String fieldName) {
        assertTrue(text == null || text.trim().isEmpty(),
                fieldName + " should be empty");
    }

    public static void assertValidEmail(String email, String fieldName) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        assertTrue(pattern.matcher(email).matches(),
                fieldName + " should be a valid email address");
    }

    public static void assertValidPhoneNumber(String phoneNumber, String fieldName) {
        // Basic phone number validation
        String phoneRegex = "^[+]?[0-9]{10,15}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        assertTrue(pattern.matcher(phoneNumber.replaceAll("[\\s-()]", "")).matches(),
                fieldName + " should be a valid phone number");
    }

    /* ==================== STATE & CONDITION ASSERTIONS ==================== */

    public static void assertElementFocused(WebDriver driver, WebElement element, String elementName) {
        WebElement activeElement = driver.switchTo().activeElement();
        assertEquals(activeElement, element, elementName + " should be focused");
    }

    public static void assertElementNotStale(WebDriver driver, WebElement element, String elementName) {
        try {
            element.isDisplayed();
            assertTrue(true, elementName + " should not be stale");
        } catch (Exception e) {
            logFail(elementName + " should not be stale", e.getMessage());
            throw new AssertionError(elementName + " is stale", e);
        }
    }

    public static void assertElementHasStyle(WebDriver driver, WebElement element, String cssProperty,
            String expectedValue, String elementName) {
        String actualValue = element.getCssValue(cssProperty);
        assertEquals(actualValue, expectedValue,
                elementName + " should have " + cssProperty + ": " + expectedValue);
    }

    /* ==================== UTILITY METHODS ==================== */

    public static void assertAlertPresent(WebDriver driver) {
        try {
            Alert alert = WaitUtils.waitForAlert(driver, 5);
            assertNotNull(alert, "Alert should be present");
        } catch (Exception e) {
            logFail("Alert should be present", e.getMessage());
            throw new AssertionError("No alert present", e);
        }
    }

    public static void assertAlertText(WebDriver driver, String expectedText) {
        try {
            Alert alert = WaitUtils.waitForAlert(driver, 5);
            String actualText = alert.getText();
            assertEquals(actualText, expectedText, "Alert text verification");
        } catch (Exception e) {
            logFail("Alert text verification failed", e.getMessage());
            throw new AssertionError("Alert text mismatch", e);
        }
    }

    public static void assertNewWindowOpens(WebDriver driver, Runnable actionThatOpensWindow) {
        int windowsBefore = driver.getWindowHandles().size();
        actionThatOpensWindow.run();

        try {
            boolean newWindowOpened = WaitUtils.waitForNumberOfWindows(driver, windowsBefore + 1, 10);
            assertTrue(newWindowOpened, "New window should open");
        } catch (Exception e) {
            logFail("New window should open", e.getMessage());
            throw new AssertionError("No new window opened", e);
        }
    }

    public static void assertFileDownloaded(String filePath, String fileName) {
        java.io.File file = new java.io.File(filePath, fileName);
        assertTrue(file.exists(), "File should be downloaded: " + fileName);
        assertTrue(file.length() > 0, "Downloaded file should not be empty: " + fileName);
    }

    public static void assertElementInViewport(WebDriver driver, WebElement element, String elementName) {
        try {
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            Boolean isInViewport = (Boolean) js.executeScript(
                    "var rect = arguments[0].getBoundingClientRect();" +
                            "return (" +
                            "rect.top >= 0 &&" +
                            "rect.left >= 0 &&" +
                            "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&" +
                            "rect.right <= (window.innerWidth || document.documentElement.clientWidth)" +
                            ");",
                    element);
            assertTrue(isInViewport, elementName + " should be in viewport");
        } catch (Exception e) {
            logFail("Viewport check failed for " + elementName, e.getMessage());
            throw new AssertionError(elementName + " is not in viewport", e);
        }
    }

    public static void assertWithScreenshot(WebDriver driver, boolean condition, String message) {
        try {
            assertTrue(condition, message);
        } catch (AssertionError e) {
            // Add screenshot logic here if needed
            // String screenshotPath = takeScreenshot(driver, "assert_failure_" +
            // System.currentTimeMillis());
            // logFail(message + " - Screenshot saved at: " + screenshotPath,
            // e.getMessage());
            throw e;
        }
    }

    /* ==================== LOGGING UTILITIES ==================== */

    private static void logPass(String message) {
        System.out.println("[PASS] " + message);
    }

    private static void logFail(String message, String details) {
        System.err.println("[FAIL] " + message);
        if (details != null && !details.isEmpty()) {
            System.err.println("       Details: " + details);
        }
    }

    private static void logAssertion(String type, boolean condition, String message) {
        if (condition) {
            System.out.println("[" + type + " - PASS] " + message);
        } else {
            System.err.println("[" + type + " - FAIL] " + message);
        }
    }

    /* ==================== CONVENIENCE METHODS ==================== */

    public static void assertLoginSuccessful(WebDriver driver, WebElement welcomeElement, String expectedUsername) {
        assertElementVisible(driver, welcomeElement, "Welcome message");
        assertElementTextContains(driver, welcomeElement, expectedUsername, "Welcome message");
    }

    public static void assertFormSubmittedSuccessfully(WebDriver driver, WebElement successMessage,
            String expectedMessage) {
        assertElementVisible(driver, successMessage, "Success message");
        assertElementTextContains(driver, successMessage, expectedMessage, "Success message");
    }

    public static void assertCartContainsItem(WebDriver driver, By cartItemsLocator, String itemName) {
        List<WebElement> cartItems = driver.findElements(cartItemsLocator);
        boolean itemFound = false;
        for (WebElement item : cartItems) {
            if (item.getText().contains(itemName)) {
                itemFound = true;
                break;
            }
        }
        assertTrue(itemFound, "Cart should contain item: " + itemName);
    }

    public static void assertPaymentSuccessful(WebDriver driver, WebElement confirmationElement, String orderNumber) {
        assertElementVisible(driver, confirmationElement, "Payment confirmation");
        assertElementTextContains(driver, confirmationElement, "success", "Payment confirmation");
        assertElementTextContains(driver, confirmationElement, orderNumber, "Order number");
    }

    public static void assertSearchResultsContain(WebDriver driver, By resultsLocator, String searchTerm) {
        List<WebElement> results = driver.findElements(resultsLocator);
        assertListNotEmpty(results, "Search results");
        boolean found = false;
        for (WebElement result : results) {
            if (result.getText().toLowerCase().contains(searchTerm.toLowerCase())) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Search results should contain: " + searchTerm);
    }
}