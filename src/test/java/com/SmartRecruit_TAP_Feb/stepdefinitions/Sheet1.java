package com.SmartRecruit_TAP_Feb.stepdefinitions;

import com..utils.ElementUtils;
import org.junit.Assert;
import com..driverfactory.DriverFactory;
import com.SmartRecruit_TAP_Feb.pages.Sheet1Page;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class Sheet1 extends DriverFactory {
Sheet1Page sheet1Page = new Sheet1Page(driver);
@Given("the user is on the Welcome Page")
public void the_user_is_on_the_welcome_page() {
driver = DriverFactory.getDriver();
}
@When("I open the application URL")
public void i_open_the_application_url() {
sheet1Page.openApplicationURL();
}
@And("I wait for the Welcome page to load")
public void i_wait_for_the_welcome_page_to_load() {
sheet1Page.waitForWelcomePageToLoad();
}
@And("I locate the 'Get Started' button")
public void i_locate_the_get_started_button() {
sheet1Page.locateGetStartedButton();
}
@And("I click on the 'Get Started' button")
public void i_click_on_the_get_started_button() {
sheet1Page.clicksGetStartedButton();
}
@Then("the Login page should load successfully")
public void the_login_page_should_load_successfully() {
sheet1Page.isLoginPageLoaded();
}
@And("the URL should reflect the Login page")
public void the_url_should_reflect_the_login_page() {
sheet1Page.isURLReflectingLoginPage();
}
@Given("the recruiter enters {string} in the Email ID field")
public void the_recruiter_enters_email_in_the_email_id_field(String email) {
sheet1Page.enterEmailInField(email);
}
@And("the recruiter enters {string} in the Password field")
public void the_recruiter_enters_password_in_the_password_field(String password) {
sheet1Page.enterPasswordInField(password);
}
@And("the Login button is enabled")
public void the_login_button_is_enabled() {
sheet1Page.isLoginButtonEnabled();
}
@When("the recruiter clicks the Login button")
public void the_recruiter_clicks_the_login_button() {
sheet1Page.clicksLoginButton();
}
@Then("the recruiter should be redirected to the dashboard")
public void the_recruiter_should_be_redirected_to_the_dashboard() {
sheet1Page.isRedirectedToDashboard();
}
@And("the dashboard should be displayed")
public void the_dashboard_should_be_displayed() {
sheet1Page.isDashboardDisplayed();
}
@Given("the user enters {string} in the Password field")
public void the_user_enters_password_in_the_password_field(String password) {
sheet1Page.enterPasswordInField(password);
}
@When("the user locates the 'Show/Hide Password' option")
public void the_user_locates_the_show_hide_password_option() {
sheet1Page.locateShowHidePasswordOption();
}
@And("the user clicks on the 'Show Password' option")
public void the_user_clicks_on_the_show_password_option() {
sheet1Page.clicksShowPasswordOption();
}
@Then("the user should see the password as {string}")
public void the_user_should_see_the_password_as(String password) {
Assert.assertEquals(password, sheet1Page.getDisplayedPassword());
}
@When("the user clicks on the 'Hide Password' option")
public void the_user_clicks_on_the_hide_password_option() {
sheet1Page.clicksHidePasswordOption();
}
@Then("the user should see the password masked")
public void the_user_should_see_the_password_masked() {
sheet1Page.isPasswordMasked();
}
@When("the user clicks on the Password field")
public void the_user_clicks_on_the_password_field() {
sheet1Page.clicksPasswordField();
}
@And("the user enters a valid password {string}")
public void the_user_enters_a_valid_password(String password) {
sheet1Page.enterPasswordInField(password);
}
@Then("the Password field accepts the valid password")
public void the_password_field_accepts_the_valid_password() {
sheet1Page.isValidPasswordAccepted();
}
@When("the user clicks the 'Show Password' option")
public void the_user_clicks_the_show_password_option() {
sheet1Page.clicksShowPasswordOption();
}
@Then("the password is visible")
public void the_password_is_visible() {
sheet1Page.isPasswordVisible();
}
@When("the user clicks the 'Hide Password' option")
public void the_user_clicks_the_hide_password_option() {
sheet1Page.clicksHidePasswordOption();
}
@Then("the password is masked again")
public void the_password_is_masked_again() {
sheet1Page.isPasswordMasked();
}
@When("the user opens the Login page")
public void the_user_opens_the_login_page() {
sheet1Page.openLoginPage();
}
@Then("the Email ID field is displayed")
public void the_email_id_field_is_displayed() {
sheet1Page.isEmailIDFieldDisplayed();
}
@And("the Password field is displayed")
public void the_password_field_is_displayed() {
sheet1Page.isPasswordFieldDisplayed();
}
@And("the Login button is displayed")
public void the_login_button_is_displayed() {
sheet1Page.isLoginButtonDisplayed();
}
@And("the 'Show/Hide Password' option is displayed")
public void the_show_hide_password_option_is_displayed() {
sheet1Page.isShowHidePasswordOptionDisplayed();
}
@And("the layout is user-friendly and intuitive")
public void the_layout_is_user_friendly_and_intuitive() {
sheet1Page.isLayoutUserFriendly();
}
@When("the user enters whitespace in the Email ID field")
public void the_user_enters_whitespace_in_the_email_id_field() {
sheet1Page.enterWhitespaceInEmailField();
}
@And("the user enters whitespace in the Password field")
public void the_user_enters_whitespace_in_the_password_field() {
sheet1Page.enterWhitespaceInPasswordField();
}
@Then("the Login button should be enabled")
public void the_login_button_should_be_enabled() {
sheet1Page.isLoginButtonEnabled();
}
@And("the error message below the Email field will be displayed 'Email is required!'")
public void the_error_message_below_the_email_field_will_be_displayed() {
Assert.assertEquals("Email is required!", sheet1Page.getEmailErrorMessage());
}
@And("the error message below the Password field will be displayed 'Password must be 8 character length'")
public void the_error_message_below_the_password_field_will_be_displayed() {
Assert.assertEquals("Password must be 8 character length", sheet1Page.getPasswordErrorMessage());
}