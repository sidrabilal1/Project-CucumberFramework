Feature: Add employee to HRMS

  Background:
     #Given user is able to access HRMS portal
    When user enters valid credentials
    And user clicks on login button
    Then user is navigated to dashboard page
    When user clicks on PIM option
    And user clicks on add employee option
    Then user is navigated to add employee page
    And user sees unique employee ID populated in the employee Id field

  @addEmp @withoutProvidingID
  Scenario: Add Employee without providing Employee Id
    When user enters firstname middlename and lastname in the name fields
    And user clicks on save button
    Then user added successfully

  @addEmp @provideUniqueEmpId
  Scenario: Add Employee by providing unique Employee Id
    When user enters firstname middlename and lastname in the name fields
    And user enters unique employee Id
    And user clicks on save button
    Then user added successfully

  @addEmp @incompleteDetails
  Scenario Outline: Add Employee with incomplete details
    When user enters incomplete "<firstName>" "<middleName>" "<lastName>"
    And user clicks on save button
    Then user sees "<errorMessage>" on the "<missingField>"
    Examples:
      | firstName | middleName | lastName | errorMessage |missingField|
      |           | MS         | data     | Required     |firstName   |
      | dummy     |            |          | Required     |lastName    |


  @addEmp @invalidDetails
  Scenario: Add Employee with invalid details
    When user enters firstname middlename and lastname in the name fields
    And user enters existing employee ID "15258329"
    And user clicks on save button
    Then user sees error message "Failed To Save: Employee Id Exists" on entering invalid employee ID