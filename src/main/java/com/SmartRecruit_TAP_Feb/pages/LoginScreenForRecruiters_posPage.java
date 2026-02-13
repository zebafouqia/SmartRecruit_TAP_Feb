package com.backend.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.utils.ElementUtils;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import java.time.Duration;

public class LoginScreenForRecruiters_posPage {
    private WebDriver driver;
    private ElementUtils elementUtils;

    @FindBy(id = "getStartedButton")
    private WebElement getStartedButton;

    @FindBy(id = "emailField")
    private WebElement emailField;

    @FindBy(id = "passwordField")
    private WebElement passwordField;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    @FindBy(id = "showPasswordOption")
    private WebElement showPasswordOption;

    @FindBy(id = "hidePasswordOption")
    private WebElement hidePasswordOption;

    @FindBy(id = "loginPageIndicator")
    private WebElement loginPageIndicator;

    @FindBy(id = "dashboardIndicator")
    private WebElement dashboardIndicator;

    @FindBy(id = "emailValidationMessage")
    private WebElement emailValidationMessage;

    @FindBy(id = "passwordValidationMessage")
    private WebElement passwordValidationMessage;

    public LoginScreenForRecruiters_posPage(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new ElementUtils(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToWelcomePage() {
        driver.get("http://localhost/welcome");
    }

    public void locateGetStartedButton() {
        try {
            elementUtils.isElementDisplayed(getStartedButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clicksGetStartedButton() {
        try {
            elementUtils.clickElement(getStartedButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterEmail(String email) {
        try {
            elementUtils.clearAndSendKeys(emailField, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterPassword(String password) {
        try {
            elementUtils.clearAndSendKeys(passwordField, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clicksLoginButton() {
        try {
            elementUtils.clickElement(loginButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLoginPageDisplayed() {
        try {
            return elementUtils.isElementDisplayed(loginPageIndicator);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isDashboardDisplayed() {
        try {
            return elementUtils.isElementDisplayed(dashboardIndicator);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLoginButtonEnabled() {
        try {
            return loginButton.isEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clicksShowPassword() {
        try {
            elementUtils.clickElement(showPasswordOption);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clicksHidePassword() {
        try {
            elementUtils.clickElement(hidePasswordOption);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPasswordFieldValue() {
        try {
            return passwordField.getAttribute("value");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isPasswordMasked() {
        try {
            return passwordField.getAttribute("type").equals("password");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getEmailValidationMessage() {
        try {
            return elementUtils.getElementText(emailValidationMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPasswordValidationMessage() {
        try {
            return elementUtils.getElementText(passwordValidationMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void leaveFieldsEmpty() {
        try {
            elementUtils.clearElement(emailField);
            elementUtils.clearElement(passwordField);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRecruiterInformationDisplayed() {
        // Implement logic to check if recruiter information is displayed on the dashboard.
        return true; // Placeholder return value.
    }

    public void waitForPageToLoad() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(webDriver -> 
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void locateShowHidePasswordOption() {
        WebElement showHidePasswordElement = null;
        try {
            showHidePasswordElement = driver.findElement(By.id("show-hide-password")); // Assuming the locator is by ID
            if (elementUtils.isElementDisplayed(showHidePasswordElement)) {
                System.out.println("Show/Hide Password option is displayed.");
            } else {
                System.out.println("Show/Hide Password option is NOT displayed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}