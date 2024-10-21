Feature: Login Validation for HRMs Portal

  Background:
     #Given user is able to access HRMS portal

  @emptyUsernameLogin @invalidLogin
  Scenario: Login with an empty username field
    When user attempts to login with an empty username field
    And user clicks on login button
    Then user sees error message "Username cannot be empty"
    And error message is clearly visible near the "userName" field

  @emptyPasswordLogin @invalidLogin
  Scenario:  Login with an empty password field
    When user attempts to login with an empty password field
    And user clicks on login button
    Then user sees error message "Password is empty"
    And error message is clearly visible near the "password" field

  @invalidCredLogin @invalidLogin
  Scenario Outline: Login with incorrect credentials
    When user attempts to login with an incorrect "<userName>" or "<password>"
    And user clicks on login button
    Then user sees error message "Invalid credentials"
    And error message is clearly visible near the "loginButton" field
    Examples:
      | userName | password      |
      | admi     | Hum@nhrm123   |
      | admin    | Hum@nhrm12345 |


  @validlogin
  Scenario: Successful login after invalid attempt
    When user attempts to login with an invalid credentials
    And user clicks on login button
    Then user sees error message "Invalid credentials"
    When user enters valid credentials
    And user clicks on login button
    Then user is navigated to dashboard page