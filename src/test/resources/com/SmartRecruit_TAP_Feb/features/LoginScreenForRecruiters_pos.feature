
Feature: LoginScreenForRecruiters_pos @feature_user_login

  Background: 
    Given the user is on the Welcome page

  @verify_get_started_button
  Scenario Outline: Verify clicking 'Get Started' navigates to Login page
    When I locate the 'Get Started' button
    And I click on the 'Get Started' button
    And I wait for the page to load
    Then the user should be redirected to the Login page
    And the URL should reflect the Login page

  Examples:
    | action                |
    | Follow the step actions |

@valid-login
Scenario Outline: Login Screen for Recruiters
  Given the user enters a valid email address <email> in the Email ID field
  And the user enters a valid password <password> in the Password field
  And the user ensures the Login button is enabled
  When the user clicks the Login button
  Then the user should be redirected to the dashboard
  And the dashboard should display relevant recruiter information

  Examples:
    | email                   | password            |
    | recruiter@example.com   | SecurePassword123    |

  @toggle_password_visibility
  Scenario Outline: Login Screen for Recruiters
    Given the user has entered "<password>" in the Password field
    When the user locates the 'Show/Hide Password' option
    And the user clicks the 'Show' option
    Then the Password field should display the password as "<password>"
    When the user clicks the 'Hide' option
    Then the Password field should mask the password again

    Examples:
      | password      |
      | P@ssw0rd123   |

@empty_fields_validation
Scenario Outline: Verify Login button behavior with empty Email ID and Password
  When the user leaves both Email ID and Password fields empty
  And the user clicks on the Login Button
  Then the login button will be enabled but the fields display the validation message
  And the Email field validation displays "<email_validation_message>"
  And the Password field validation displays "<password_validation_message>"

  Examples:
    | email_validation_message     | password_validation_message     |
    | Email is required!          | Password is required!          |
