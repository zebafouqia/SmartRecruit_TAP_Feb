package com.project.utils;

import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * ElementUtils - A comprehensive utility class for handling Selenium WebDriver operations
 * 
 * This class provides reusable methods for interacting with web elements including:
 * - Element clicking (regular, JavaScript, double-click)
 * - Text input operations
 * - Dropdown selections (standard and ng-select)
 * - Alert handling
 * - Scroll operations
 * - Element visibility checks
 * - Drag and drop
 * - File download verification
 * - And many more utility operations
 * 
 * @author FracProPlus Team
 * @version 1.0
 */
public class ElementUtils {

    // Logger instance for logging operations and debugging
    private static Logger logger = LogManager.getLogger(ElementUtils.class);
    
    // WebDriver instance to interact with the browser
    WebDriver driver;
    
    // Default timeout duration for explicit waits
    long durationInSeconds = CommonUtils.EXPLICIT_WAIT_BASIC_TIME;

    /**
     * Constructor to initialize ElementUtils with WebDriver
     * 
     * @param driver WebDriver instance for browser interaction
     */
    public ElementUtils(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Clicks an element using JavaScript executor
     * Useful when standard click doesn't work due to overlays or positioning issues
     * 
     * @param driver WebDriver instance
     * @param element WebElement to be clicked
     */
    public static void jsClick(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Scroll element into view first
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        // Perform JavaScript click
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Generates a random time (hours and minutes)
     * Useful for test data generation
     * 
     * @return LocalTime object with random hour (0-23) and minute (0-59)
     */
    public static LocalTime getRandomTime() {
        // Generate random hours (0-23) and minutes (0-59)
        int randomHour = ThreadLocalRandom.current().nextInt(0, 24);
        int randomMinute = ThreadLocalRandom.current().nextInt(0, 60);

        // Return the random time
        return LocalTime.of(randomHour, randomMinute);
    }
    
    /**
     * Checks if an element is read-only by examining disabled and readonly attributes
     * 
     * @param driver WebDriver instance
     * @param Element WebElement to check
     * @return boolean indicating if element is read-only
     */
    public boolean isElementReadOnly(WebDriver driver, WebElement Element) {
        try {
            // Check if element is not disabled and not readonly
            boolean isFieldReadOnly = !Element.getAttribute("disabled").equals("true") 
                                    && !Element.getAttribute("readonly").equals("true");
            // Assert that the field should be read-only
            Assert.assertFalse(isFieldReadOnly, "Display Temperature at is enabled");
        } catch (TimeoutException e) {
            // Handle timeout exception silently
        }
        return false;
    }
    
    /**
     * Scrolls to a specific element using Actions class
     * 
     * @param driver WebDriver instance
     * @param element WebElement to scroll to
     */
    public static void scrollToElement(WebDriver driver, WebElement element) {
        try {
            // Use Actions class to move to element
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a field is read-only by examining the 'readonly' attribute
     * Asserts that the field should be read-only
     * 
     * @param driver WebDriver instance
     * @param locator By locator to find the element
     */
    public static void checkFieldReadOnly(WebDriver driver, By locator) {
        try {
            // Find the element using the locator
            WebElement element = driver.findElement(locator);
            
            // Check if the 'readonly' attribute is present
            String readOnlyAttr = element.getAttribute("readonly");
            boolean isReadOnly = readOnlyAttr != null;

            // Assert based on the read-only status
            Assert.assertTrue(isReadOnly, "Field is not read-only!");
            System.out.println("  Assertion Passed: Field is read-only.");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("  Exception occurred while checking the field.");
        }
    }
    
    /**
     * Checks if a field is NOT read-only
     * Asserts that the field should be editable
     * 
     * @param driver WebDriver instance
     * @param locator By locator to find the element
     */
    public static void checkFieldNotReadOnly(WebDriver driver, By locator) {
        try {
            // Find the element using the locator
            WebElement element = driver.findElement(locator);
            
            // Check if the 'readonly' attribute is present
            String readOnlyAttr = element.getAttribute("readonly");
            boolean isReadOnly = readOnlyAttr != null;

            // Assert that field should NOT be read-only
            Assert.assertTrue(!isReadOnly, "Field is not read-only!");
            System.out.println("  Assertion Passed: Field is not read-only.");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("  Exception occurred while checking the field.");
        }
    }

    /**
     * Clicks on a WebElement with fallback to JavaScript click if standard click fails
     * Waits for element to be clickable before attempting click
     * 
     * @param element WebElement to be clicked
     */
    public void clickElement(WebElement element) {
        try {
            // Wait for element to be clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));
            // Perform standard click
            clickableElement.click();
        } catch (Exception e) {
            try {
                // Fallback to JavaScript click if standard click fails
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", element);
                js.executeScript("arguments[0].click();", element);
            } catch (Exception jsException) {
                throw new RuntimeException("Failed to click the element using both Selenium and JavaScript click.", jsException);
            }
        }
    }

    /**
     * Checks if an element is clickable within a timeout period
     * 
     * @param driver WebDriver instance
     * @param locator By locator to find the element
     * @return true if element is NOT enabled (counterintuitive - may be a bug), false if timeout occurs
     */
    public static boolean isElementClickable(WebDriver driver, By locator) {
        try {
            // Set timeout to 10 seconds
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            // Returns the opposite of isEnabled (may need review)
            return !element.isEnabled();
        } catch (TimeoutException e) {
            // Element was not clickable within the timeout
            System.out.println("Element is not clickable: " + locator);
            return false;
        }
    }

    /**
     * Clears an element and sends keys (types text)
     * Waits for element visibility before interaction
     * 
     * @param element WebElement to type into
     * @param textToBeTyped String text to enter
     */
    public void clearAndSendKeys(WebElement element, String textToBeTyped) {
        // Initialize wait with 10 seconds timeout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the element to be visible
        WebElement welement = wait.until(ExpectedConditions.visibilityOf(element));
        
        // Clear existing text
        element.clear();
        
        try {
            // Small delay for stability
            Thread.sleep(200);
        } catch (Exception e) {
            // Handle exception silently
        }
        
        // Type the new text
        element.sendKeys(textToBeTyped);
    }

    /**
     * Clears text from a WebElement
     * Waits for element to be available before clearing
     * 
     * @param element WebElement to clear
     */
    public void clearElement(WebElement element) {
        // Wait for element to be available
        WebElement webElement = waitForElement(element, durationInSeconds);
        // Clear the element
        webElement.clear();
        logger.info("clearElement() in" + element);
    }

    /**
     * Waits for an element to be clickable
     * 
     * @param element WebElement to wait for
     * @param durationInSeconds Maximum wait time in seconds
     * @return WebElement if clickable, null if timeout occurs
     */
    public WebElement waitForElement(WebElement element, long durationInSeconds) {
        WebElement webElement = null;

        try {
            // Create explicit wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(durationInSeconds));
            // Wait until element is clickable
            webElement = wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        logger.info(element + " element is clickable");
        return webElement;
    }
    
    /**
     * Fetches and verifies values of multiple WebElements against expected values
     * Useful for validating multiple fields at once
     * 
     * @param xpaths List of XPath strings to locate elements
     * @param expectedValues List of expected values corresponding to each XPath
     */
    public void fetchAndVerifyElementValues(List<String> xpaths, List<String> expectedValues) {
        // Iterate through all XPaths
        for (int i = 0; i < xpaths.size(); i++) {
            try {
                // Find the element using XPath
                WebElement element = driver.findElement(By.xpath(xpaths.get(i)));

                // Get the actual value from the element
                String actualValue = element.getText();  // or element.getAttribute("value") if it's an input field

                // Compare the actual value with the expected value
                Assert.assertEquals(actualValue, expectedValues.get(i), "Value mismatch for element " + (i + 1));
                System.out.println("Value of element " + (i + 1) + " is correct: " + actualValue);
            } catch (Exception e) {
                System.out.println("Error occurred while fetching value for element " + (i + 1) + ": " + e.getMessage());
            }
        }
    }

    /**
     * Selects an option from a standard HTML dropdown by visible text
     * 
     * @param element Dropdown WebElement
     * @param dropDownOption Text of the option to select
     */
    public void selectOptionInDropdown(WebElement element, String dropDownOption) {
        // Wait for dropdown to be available
        WebElement webElement = waitForElement(element, durationInSeconds);
        // Create Select object
        Select select = new Select(webElement);
        // Select by visible text
        select.selectByVisibleText(dropDownOption);
        logger.info("selectOptionInDropdown(), invoked , value selected from dropdown " + dropDownOption);
    }
    
    /**
     * Selects an option from an ng-select dropdown (Angular component)
     * Different from standard HTML dropdowns
     * 
     * @param ngSelectElement The ng-select dropdown element
     * @param optionText Text of the option to select
     */
    public void selectOptionInNgSelect(WebElement ngSelectElement, String optionText) {
        // Click to open the ng-select dropdown
        WebElement webElement = waitForElement(ngSelectElement, durationInSeconds);
        webElement.click();

        // Wait for the options to appear and then select the matching one
        // XPath to locate the option in ng-select dropdown
        By optionLocator = By.xpath("//*[contains(@class,'ng-select-searchable')]//following::span[contains(text(),'" + optionText + "')]");
        WebElement optionElement = waitForElement(driver.findElement(optionLocator), durationInSeconds);
        optionElement.click();

        logger.info("selectOptionInNgSelect(), invoked, value selected from ng-select: " + optionText);
    }

    /**
     * Accepts (clicks OK on) a JavaScript alert
     */
    public void acceptAlert() {
        // Wait for alert to be present
        Alert alert = waitForAlert();
        // Accept the alert
        alert.accept();
        logger.info("alert accepted successfully");
    }

    /**
     * Dismisses (clicks Cancel on) a JavaScript alert
     * 
     * @param durationInSeconds Maximum wait time for alert
     */
    public void dismissAlert(long durationInSeconds) {
        // Wait for alert to be present
        Alert alert = waitForAlert();
        // Dismiss the alert
        alert.dismiss();
        logger.info("alert dismiss successfully");
    }

    /**
     * Waits for a JavaScript alert to be present
     * 
     * @return Alert object if present, null if timeout occurs
     */
    public Alert waitForAlert() {
        Alert alert = null;

        try {
            // Create explicit wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(durationInSeconds));
            // Wait for alert to be present
            alert = wait.until(ExpectedConditions.alertIsPresent());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        logger.info("waiting for the alert");
        return alert;
    }

    /**
     * Performs mouse hover on an element and then clicks it
     * Useful for dropdown menus that appear on hover
     * 
     * @param element WebElement to hover and click
     */
    public void mouseHoverAndClick(WebElement element) {
        // Wait for element visibility
        WebElement webElement = waitForVisibilityOfElement(element, durationInSeconds);
        // Create Actions object
        Actions actions = new Actions(driver);
        // Move to element and click
        actions.moveToElement(webElement).click().build().perform();
        logger.info("mousehover on element " + element + " and clicked");
    }

    /**
     * Waits for an element to be visible on the page
     * 
     * @param element WebElement to wait for
     * @param durationInSeconds Maximum wait time in seconds
     * @return WebElement if visible, null if timeout occurs
     */
    public WebElement waitForVisibilityOfElement(WebElement element, long durationInSeconds) {
        WebElement webElement = null;

        try {
            // Create explicit wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(durationInSeconds));
            // Wait until element is visible
            webElement = wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        logger.info("waiting for the element visibility");
        return webElement;
    }

    /**
     * Clicks an element using JavaScript executor
     * Alternative to standard click for stubborn elements
     * 
     * @param element WebElement to click
     */
    public void javaScriptClick(WebElement element) {
        // Wait for element visibility
        WebElement webElement = waitForVisibilityOfElement(element, durationInSeconds);
        // Execute JavaScript click
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("arguments[0].click();", webElement);
        logger.info("clicked on element using javascriptexecutor");
    }

    /**
     * Types text into an element using JavaScript executor
     * Alternative to sendKeys() for problematic input fields
     * 
     * @param element WebElement to type into
     * @param textToBeTyped Text to enter
     */
    public void javaScriptType(WebElement element, String textToBeTyped) {
        // Wait for element visibility
        WebElement webElement = waitForVisibilityOfElement(element, durationInSeconds);
        // Execute JavaScript to set value
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("arguments[0].value='" + textToBeTyped + "'", webElement);
        logger.info("enter text in textbox using  javascriptexecutor");
    }

    /**
     * Gets the visible text of an element
     * 
     * @param element WebElement to get text from
     * @return String containing the element's text
     */
    public String getElementText(WebElement element) {
        // Wait for element to be available
        WebElement webElement = waitForElement(element, durationInSeconds);
        logger.info("getting text from webpage " + webElement.getText());
        return webElement.getText();
    }

    /**
     * Checks if pagination is present on the current page
     * 
     * @return true if pagination exists, false otherwise
     */
    public boolean isPaginationPresent() {
        // Check if any elements with paginator class exist
        return !driver.findElements(By.xpath("(//*[contains(@class,'paginator')])[1]")).isEmpty();
    }

    /**
     * Navigates to the last page if pagination is present
     * Otherwise stays on current page
     */
    public void navigateToLastPageIfPresent() {
        if (isPaginationPresent()) {
            // Find and click the last page button
            WebElement lastPage = driver.findElement(By.xpath("//*[@aria-label='Last page']"));
            lastPage.click();
        } else {
            System.out.println("Pagination not present. Checking the current page.");
        }
    }
    
    /**
     * Checks if an element is displayed on the page
     * 
     * @param element WebElement to check
     * @return true if displayed, false if not displayed or timeout occurs
     */
    public boolean isElementDisplayed(WebElement element) {
        try {
            // Wait for element visibility
            WebElement webElement = waitForVisibilityOfElement(element, durationInSeconds);
            return webElement.isDisplayed();
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * Scrolls the page to bring an element into view
     * 
     * @param element WebElement to scroll to
     */
    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Scroll the page to bring the specified element into view
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Asserts that a specific number/value is NOT present in dropdown options
     * Useful for validation that certain options are restricted
     * 
     * @param dropdownOptions List of dropdown option elements
     * @param restrictedNumber The number/value that should not be present
     */
    public void assertNumberNotInDropdown(List<WebElement> dropdownOptions, String restrictedNumber) {
        // Check if the restricted number is present in any option
        boolean isNumberPresent = dropdownOptions.stream()
                .anyMatch(option -> option.getText().equals(String.valueOf(restrictedNumber)));

        // Assert that the number should NOT be present
        Assert.assertFalse(isNumberPresent, "Error: Number '" + restrictedNumber + "' should not be visible in the dropdown.");
    }

    /**
     * Selects an option from a dropdown by index position
     * 
     * @param element Dropdown WebElement
     * @param index Index of the option to select (0-based)
     */
    public void selectOptionInDropdownByIndex(WebElement element, int index) {
        // Wait for dropdown to be available
        WebElement webElement = waitForElement(element, durationInSeconds);
        // Create Select object
        Select select = new Select(webElement);
        // Select by index
        select.selectByIndex(index);
        logger.info("selectOptionInDropdownByIndex(), invoked, index selected from dropdown: " + index);
    }

    /**
     * Double-clicks on an element with fallback to JavaScript
     * Useful for elements that require double-click to activate
     * 
     * @param element WebElement to double-click
     */
    public void doubleClickElement(WebElement element) {
        try {
            // Wait for element to be clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));
            // Perform double-click using Actions
            Actions actions = new Actions(driver);
            actions.doubleClick(clickableElement).perform();
        } catch (Exception e) {
            try {
                // Fallback to JavaScript double-click
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", element);
                // Create and dispatch double-click event
                js.executeScript("var event = document.createEvent('MouseEvents');"
                        + "event.initMouseEvent('dblclick', true, true, window, 1, 0, 0, 0, 0, false, false, false, false, 0, null);"
                        + "arguments[0].dispatchEvent(event);", element);
            } catch (Exception jsException) {
                throw new RuntimeException("Failed to double-click the element using both Selenium and JavaScript.", jsException);
            }
        }
    }

    // Email verification constants (for future implementation)
    private static final String HOST = "imap.gmail.com";  // IMAP server (e.g., for Gmail)
    private static final String USERNAME = "your-email@gmail.com";  // Your test email
    private static final String PASSWORD = "your-app-password";  // Use App Password if needed

    /**
     * Gets verification code from email (placeholder method)
     * Note: Implementation is commented out - needs completion
     * 
     * @param subjectKeyword Keyword to search in email subject
     * @return Verification code string, or null if not found
     */
    public static String getVerificationCode(String subjectKeyword) {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");

            // Email retrieval implementation commented out
            // Would connect to email, search for subject, and extract code
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Verifies that a cell in a Handsontable is read-only
     * Checks the aria-readonly attribute
     * 
     * @param cell WebElement representing the cell
     */
    public void verifyCellIsReadOnly(WebElement cell) {
        // Get aria-readonly attribute
        String ariaReadOnly = cell.getAttribute("aria-readonly");
        // Assert that it equals "true"
        Assert.assertEquals(ariaReadOnly, "true", "The cell is not read-only");
    }
 
    /**
     * Verifies that a cell in a Handsontable is editable (not read-only)
     * 
     * @param cell WebElement representing the cell
     */
    public void verifyCellIsEditable(WebElement cell) {
        // Get aria-readonly attribute
        String ariaReadOnly = cell.getAttribute("aria-readonly");
        // Check if cell is editable (attribute is null or not "true")
        boolean isEditable = (ariaReadOnly == null || !ariaReadOnly.equals("true"));
        Assert.assertTrue(isEditable, "The cell is not editable");
    }
 
    /**
     * Checks if a checkbox is checked/selected
     * 
     * @param checkbox Checkbox WebElement
     * @return true if checked, false otherwise
     */
    public boolean isCheckboxChecked(WebElement checkbox) {
        return checkbox.isSelected();  // Returns true if checkbox is checked, otherwise false
    }
  
    /**
     * Checks if a specific option is selected in a dropdown
     * 
     * @param dropdownElement Dropdown WebElement
     * @param expectedOption Expected selected option text
     * @return true if the expected option is selected, false otherwise
     */
    public boolean isOptionSelected(WebElement dropdownElement, String expectedOption) {
        // Create Select object
        Select dropdown = new Select(dropdownElement);
        // Get currently selected option text
        String selectedOption = dropdown.getFirstSelectedOption().getText();
        // Compare with expected option
        return selectedOption.equals(expectedOption);
    }
 
    /**
     * Verifies dropdown options against expected values
     * Checks both the count and the content of options
     * 
     * @param dropdownElement Dropdown WebElement
     * @param expectedOptions List of expected option texts
     * @param dropDownOptionCountMessage Error message for count mismatch
     */
    public void verifyDropdownOptions(WebElement dropdownElement, List<String> expectedOptions, String dropDownOptionCountMessage) {
        // Create Select object
        Select dropdown = new Select(dropdownElement);
        // Get all options
        List<WebElement> options = dropdown.getOptions();

        // Extract text from all options
        List<String> actualOptions = new ArrayList<>();
        for (WebElement option : options) {
            actualOptions.add(option.getText().trim());
        }

        // Assert the size first
        Assert.assertEquals(actualOptions.size(), expectedOptions.size(), dropDownOptionCountMessage);

        // Assert each option matches expected
        for (int i = 0; i < expectedOptions.size(); i++) {
            Assert.assertEquals(expectedOptions.get(i), actualOptions.get(i), "Mismatch at index " + i);
        }
    }
 
    /**
     * Performs drag and drop operation from source to target element
     * 
     * @param source WebElement to drag
     * @param target WebElement to drop onto
     */
    public void dragAndDrop(WebElement source, WebElement target) {
        try {
            // Create Actions object and perform drag-drop
            Actions actions = new Actions(driver);
            actions.dragAndDrop(source, target).build().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if all elements matching the XPath are in an expanded state
     * Uses aria-expanded attribute or "expanded" class
     * 
     * @param xpath XPath to locate elements
     * @return true if all elements are expanded, false otherwise
     */
    public boolean areElementsExpanded(String xpath) {
        try {
            // Find all elements matching xpath
            List<WebElement> elements = driver.findElements(By.xpath(xpath));
            for (WebElement element : elements) {
                String ariaExpanded = element.getAttribute("aria-expanded");
                if (ariaExpanded != null) {
                    // Check aria-expanded attribute
                    if (!ariaExpanded.equalsIgnoreCase("true")) return false;
                } else if (!element.getAttribute("class").contains("expanded")) {
                    // Check for "expanded" class
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if all elements matching the XPath are in a collapsed state
     * Uses aria-expanded attribute or absence of "expanded" class
     * 
     * @param xpath XPath to locate elements
     * @return true if all elements are collapsed, false otherwise
     */
    public boolean areElementsCollapsed(String xpath) {
        try {
            // Find all elements matching xpath
            List<WebElement> elements = driver.findElements(By.xpath(xpath));
            for (WebElement element : elements) {
                String ariaExpanded = element.getAttribute("aria-expanded");
                if (ariaExpanded != null) {
                    // Check aria-expanded attribute
                    if (!ariaExpanded.equalsIgnoreCase("false")) return false;
                } else if (element.getAttribute("class").contains("expanded")) {
                    // Check that "expanded" class is NOT present
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if an element is selected (useful for radio buttons, checkboxes, dropdown options)
     * 
     * @param element WebElement to check
     * @return true if selected, false otherwise
     */
    public boolean isElementSelected(WebElement element) {
        try {
            return element.isSelected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Selects an option from an ng-select dropdown (Angular component)
     * Opens the dropdown and clicks the specified option
     * 
     * @param dropdown The ng-select dropdown element
     * @param option The option element to select
     */
    public void selectFromNgSelect(WebElement dropdown, WebElement option) {
        // Wait for dropdown to be clickable and click it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
        // Wait for option to be clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    /**
     * Waits for page title to contain specified text
     * 
     * @param title Text that should be in the page title
     * @return true if title contains the text within timeout, false otherwise
     */
    public boolean waitForTitleContains(String title) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.titleContains(title));
    }

    /**
     * Waits for URL to contain specified text
     * Useful for verifying navigation to correct pages
     * 
     * @param urlPart Text that should be in the URL
     * @return true if URL contains the text within timeout, false otherwise
     */
    public boolean waitForUrlContains(String urlPart) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.urlContains(urlPart));
    }

    /**
     * Checks if an element is present in the DOM
     * Does not check visibility, only presence
     * 
     * @param locator By locator to find the element
     * @return true if element exists, false otherwise
     */
    public boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    /**
     * Gets the value of a specified attribute from an element
     * 
     * @param element WebElement to get attribute from
     * @param attribute Name of the attribute
     * @return String value of the attribute
     */
    public String getAttributeValue(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    /**
     * Performs a hard refresh of the page (clears cache)
     * Uses JavaScript to force reload from server
     */
    public void hardRefresh() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // true parameter forces reload from server, not cache
        js.executeScript("location.reload(true);");
    }

    /**
     * Switches driver context to a specific iframe
     * 
     * @param locator By locator to find the iframe
     */
    public void switchToFrame(By locator) {
        driver.switchTo().frame(driver.findElement(locator));
    }

    /**
     * Switches driver context back to default content (out of iframes)
     */
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    /**
     * Scrolls the page by specified pixels
     * 
     * @param x Horizontal scroll distance (negative for left, positive for right)
     * @param y Vertical scroll distance (negative for up, positive for down)
     */
    public void scrollBy(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }

    /**
     * Attempts to click an element with retry mechanism
     * Useful for flaky elements
     * 
     * @param element WebElement to click
     * @param retries Number of retry attempts
     * @throws RuntimeException if all retries fail
     */
    public void safeClick(WebElement element, int retries) {
        // Attempt to click with specified retries
        for (int i = 0; i < retries; i++) {
            try {
                clickElement(element);
                return; // Success, exit method
            } catch (Exception e) {
                sleep(1); // Wait 1 second before retry
            }
        }
        // All retries failed
        throw new RuntimeException("Unable to click element after retries");
    }

    /**
     * Waits for a file to be downloaded to the Downloads folder
     * 
     * @param fileName Name (or partial name) of the file to wait for
     * @param timeoutSeconds Maximum time to wait in seconds
     * @return true if file is found, false if timeout occurs
     */
    public boolean waitForFileDownload(String fileName, int timeoutSeconds) {
        // Get Downloads directory
        File dir = new File(System.getProperty("user.home") + "/Downloads");
        int waited = 0;
        
        // Keep checking until timeout
        while (waited < timeoutSeconds) {
            // Check all files in Downloads folder
            for (File file : dir.listFiles()) {
                if (file.getName().contains(fileName)) return true;
            }
            sleep(1); // Wait 1 second
            waited++;
        }
        return false; // Timeout reached, file not found
    }

    /**
     * Highlights an element with a red border
     * Useful for debugging and visual verification
     * 
     * @param element WebElement to highlight
     */
    public void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Add red border to element
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    /**
     * Helper method to pause execution for specified seconds
     * 
     * @param seconds Number of seconds to sleep
     */
    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}