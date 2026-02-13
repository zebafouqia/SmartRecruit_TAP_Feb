package com.SmartRecruit_TAP_Feb.stepdefinitions;

import com..utils.ElementUtils;
import org.junit.Assert;
import com..driverfactory.DriverFactory;
import com.SmartRecruit_TAP_Feb.pages.LoginScreenForRecruiters_posPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class LoginScreenForRecruiters_pos extends DriverFactory {
LoginScreenForRecruiters_posPage loginPage = new LoginScreenForRecruiters_posPage(driver);
@Given("the user is on the Welcome page")
public void the_user_is_on_the_welcome_page() {
driver = DriverFactory.getDriver();
loginPage.navigateToWelcomePage();
}
@When("I locate the 'Get Started' button")
public void i_locate_the_get_started_button() {
loginPage.locateGetStartedButton();
}
// DUPLICATE: @And("I click on the 'Get Started' button")
public void i_click_on_the_get_started_button() {
loginPage.clicksGetStartedButton();
}
@And("I wait for the page to load")
public void i_wait_for_the_page_to_load() {
loginPage.waitForPageToLoad();
}
@Then("the user should be redirected to the Login page")
public void the_user_should_be_redirected_to_the_login_page() {
loginPage.isLoginPageDisplayed();
}
// DUPLICATE: @And("the URL should reflect the Login page")
public void the_url_should_reflect_the_login_page() {
Assert.assertEquals("expected_login_page_url", driver.getCurrentUrl());
}
@Given("the user enters a valid email address {string} in the Email ID field")
public void the_user_enters_a_valid_email_address_in_the_email_id_field(String email) {
loginPage.enterEmail(email);
}
@And("the user enters a valid password {string} in the Password field")
public void the_user_enters_a_valid_password_in_the_password_field(String password) {
loginPage.enterPassword(password);
}
@And("the user ensures the Login button is enabled")
public void the_user_ensures_the_login_button_is_enabled() {
loginPage.isLoginButtonEnabled();
}
@When("the user clicks the Login button")
public void the_user_clicks_the_login_button() {
loginPage.clicksLoginButton();
}
@Then("the user should be redirected to the dashboard")
public void the_user_should_be_redirected_to_the_dashboard() {
loginPage.isDashboardDisplayed();
}
@And("the dashboard should display relevant recruiter information")
public void the_dashboard_should_display_relevant_recruiter_information() {
loginPage.isRecruiterInformationDisplayed();
}
@Given("the user has entered {string} in the Password field")
public void the_user_has_entered_in_the_password_field(String password) {
loginPage.enterPassword(password);
}
// DUPLICATE: @When("the user locates the 'Show/Hide Password' option")
public void the_user_locates_the_show_hide_password_option() {
loginPage.locateShowHidePasswordOption();
}
@And("the user clicks the 'Show' option")
public void the_user_clicks_the_show_option() {
loginPage.clicksShowPassword();
}
@Then("the Password field should display the password as {string}")
public void the_password_field_should_display_the_password_as(String password) {
Assert.assertEquals(password, loginPage.getPasswordFieldValue());
}
@When("the user clicks the 'Hide' option")
public void the_user_clicks_the_hide_option() {
loginPage.clicksHidePassword();
}
@Then("the Password field should mask the password again")
public void the_password_field_should_mask_the_password_again() {
loginPage.isPasswordMasked();
}
@When("the user leaves both Email ID and Password fields empty")
public void the_user_leaves_both_email_id_and_password_fields_empty() {
loginPage.leaveFieldsEmpty();
}
@And("the user clicks on the Login Button")
public void the_user_clicks_on_the_login_button() {
loginPage.clicksLoginButton();
}
@Then("the login button will be enabled but the fields display the validation message")
public void the_login_button_will_be_enabled_but_the_fields_display_the_validation_message() {
loginPage.isLoginButtonEnabled();
}
@And("the Email field validation displays {string}")
public void the_email_field_validation_displays(String emailValidationMessage) {
Assert.assertEquals(emailValidationMessage, loginPage.getEmailValidationMessage());
}
@And("the Password field validation displays {string}")
public void the_password_field_validation_displays(String passwordValidationMessage) {
Assert.assertEquals(passwordValidationMessage, loginPage.getPasswordValidationMessage());
}