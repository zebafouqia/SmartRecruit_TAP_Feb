package com.backend.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.utils.ElementUtils;
import org.junit.Assert;

public class Sheet1Page {
    private WebDriver driver;
    private ElementUtils elementUtils;

    public Sheet1Page(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new ElementUtils(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "getStartedButton")
    private WebElement getStartedButton;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    @FindBy(id = "emailField")
    private WebElement emailField;

    @FindBy(id = "passwordField")
    private WebElement passwordField;

    @FindBy(id = "showHidePasswordOption")
    private WebElement showHidePasswordOption;

    @FindBy(id = "emailErrorMessage")
    private WebElement emailErrorMessage;

    @FindBy(id = "passwordErrorMessage")
    private WebElement passwordErrorMessage;

    @FindBy(id = "dashboard")
    private WebElement dashboard;

    public void openApplicationURL() {
        try {
            driver.get("http://localhost");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitForWelcomePageToLoad() {
        // Implementation for waiting can be added here
    }

    public void clicksGetStartedButton() {
        try {
            elementUtils.clickElement(getStartedButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void isLoginPageLoaded() {
        // Implement logic to verify if the login page is loaded
    }

    public void isURLReflectingLoginPage() {
        // Implement logic to verify the URL
    }

    public void enterEmailInField(String email) {
        try {
            elementUtils.clearAndSendKeys(emailField, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterPasswordInField(String password) {
        try {
            elementUtils.clearAndSendKeys(passwordField, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void isLoginButtonEnabled() {
        // Implement logic to verify if the login button is enabled
    }

    public void clicksLoginButton() {
        try {
            elementUtils.clickElement(loginButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void isDashboardDisplayed() {
        Assert.assertTrue("Dashboard is not displayed!", elementUtils.isElementDisplayed(dashboard));
    }

    public String getDisplayedPassword() {
        return passwordField.getAttribute("value");
    }

    public void isPasswordMasked() {
        String type = passwordField.getAttribute("type");
        Assert.assertEquals("Password is not masked!", "password", type);
    }

    public void isEmailIDFieldDisplayed() {
        Assert.assertTrue("Email ID field is not displayed!", elementUtils.isElementDisplayed(emailField));
    }

    public void isPasswordFieldDisplayed() {
        Assert.assertTrue("Password field is not displayed!", elementUtils.isElementDisplayed(passwordField));
    }

    public void isLoginButtonDisplayed() {
        Assert.assertTrue("Login button is not displayed!", elementUtils.isElementDisplayed(loginButton));
    }

    public void isShowHidePasswordOptionDisplayed() {
        Assert.assertTrue("Show/Hide Password option is not displayed!", elementUtils.isElementDisplayed(showHidePasswordOption));
    }

    public void isLayoutUserFriendly() {
        // Implement logic to verify if the layout is user-friendly
    }

    public void enterWhitespaceInEmailField() {
        enterEmailInField("   ");
    }

    public void enterWhitespaceInPasswordField() {
        enterPasswordInField("   ");
    }

    public String getEmailErrorMessage() {
        return emailErrorMessage.getText();
    }

    public String getPasswordErrorMessage() {
        return passwordErrorMessage.getText();
    }

    public void openLoginPage() {
        openApplicationURL();
    }

    public void clicksHidePasswordOption() {
        try {
            elementUtils.clickElement(showHidePasswordOption);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void isRedirectedToDashboard() {
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "http://localhost/dashboard"; // Replace with actual dashboard URL
        Assert.assertEquals("Redirection to Dashboard failed!", actualUrl, expectedUrl);
    }

    public void clicksShowPasswordOption() {
        try {
            elementUtils.clickElement(showHidePasswordOption);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void isPasswordVisible() {
        String type = passwordField.getAttribute("type");
        Assert.assertEquals("Password is not visible!", "text", type);
    }

    public void isValidPasswordAccepted(String validPassword) {
        try {
            elementUtils.clearAndSendKeys(passwordField, validPassword);
            clicksLoginButton();
            isRedirectedToDashboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void locateGetStartedButton() {
        Assert.assertTrue("Get Started button is not displayed!", elementUtils.isElementDisplayed(getStartedButton));
    }

    public void clicksPasswordField() {
        try {
            elementUtils.clickElement(passwordField);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}