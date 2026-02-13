
Feature: Sheet1
  @feature_user_login
  Background: 
    Given the user is on the Welcome Page

  @navigate-to-login
  Scenario Outline: Login Screen for Recruiters
    When I open the application URL
    And I wait for the Welcome page to load
    And I locate the 'Get Started' button
    And I click on the 'Get Started' button
    Then the Login page should load successfully
    And the URL should reflect the Login page

  Examples:
    | username | password |
    |          |          |

  @valid-login
  Scenario Outline: Login Screen for Recruiters
    Given the recruiter enters "<email>" in the Email ID field
    And the recruiter enters "<password>" in the Password field
    And the Login button is enabled
    When the recruiter clicks the Login button
    Then the recruiter should be redirected to the dashboard
    And the dashboard should be displayed

  Examples:
    | email                                 | password     |
    | fouqia.zeba+recruiter@walkingtree.tech | Password@123 |

  @toggle_password_visibility
  Scenario Outline: Verify 'Show/Hide Password' Functionality
    Given the user enters <password> in the Password field
    When the user locates the 'Show/Hide Password' option
    And the user clicks on the 'Show Password' option
    Then the user should see the password as <password>
    When the user clicks on the 'Hide Password' option
    Then the user should see the password masked

    Examples:
      | password      |
      | Password@123  |

@valid-password-input
Scenario Outline: Login Screen for Recruiters
  When the user clicks on the Password field
  And the user enters a valid password "<password>"
  Then the Password field accepts the valid password
  When the user clicks the 'Show Password' option
  Then the password is visible
  When the user clicks the 'Hide Password' option
  Then the password is masked again

Examples:
  | password      |
  | Password@123  |

  @ui-elements-verification
  Scenario Outline: Verify UI Elements on the Login Page
    When the user opens the Login page
    Then the Email ID field is displayed
    And the Password field is displayed
    And the Login button is displayed
    And the 'Show/Hide Password' option is displayed
    And the layout is user-friendly and intuitive

  Examples:
    |   |
    |   |

  @login_button_enabled
  Scenario Outline: Login Screen for Recruiters
    When the user enters whitespace in the Email ID field
    And the user enters whitespace in the Password field
    Then the Login button should be enabled
    And the error message below the Email field will be displayed 'Email is required!'
    And the error message below the Password field will be displayed 'Password must be 8 character length'

    Examples:
      | field_email | field_password |
      | " "         | " "            |
