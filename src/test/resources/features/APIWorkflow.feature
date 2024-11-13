Feature: API Workflow

  Background:
    Given a JWT bearer token is generated

  @api @createEmp
  Scenario: Create Employee
    Given a request is prepared for creating the employee
    When a "POST" call is made to create the employee
    Then the status code for this request is validated
    Then the employee id "Employee.employee_id" is stored and values are validated

  @api @getEmp
  Scenario: Get Created Employee
    Given a request is prepared for retrieving the employee
    When a "GET" call is made to retrieve the employee
    Then the status code for this request is validated
    Then the retrieved employee details are validated

  @api @updateEmployee
  Scenario: Update Employee
    Given a request is prepared for updating the employee
    When a "PUT" call is made to update the employee
    Then the status code for this request is validated
    Then the updated employee details are validated

  @api @getAllEmp
  Scenario: Get All Employee
    Given a request is prepared for retrieving all the employee
    When a "GET" call is made to retrieve all the employee
    Then the status code for this request is validated
    Then all the employee details are validated

  @api @getJobTitle
  Scenario: Get Job Title
    Given a request is prepared for retrieving job title
    When a "GET" call is made to retrieve job title
    Then the status code for this request is validated
    Then the job titles are validated

  @api @getEmploymentStatus
  Scenario: Get Employment Status
    Given a request is prepared for retrieving employment status
    When a "GET" call is made to retrieve employment status
    Then the status code for this request is validated
    Then the employment status are validated


