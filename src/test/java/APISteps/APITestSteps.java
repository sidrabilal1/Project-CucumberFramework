package APISteps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.CommonMethods;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class APITestSteps {

    String baseURI = RestAssured.baseURI = ConfigReader.read("apiBaseURI");
    RequestSpecification request;
    Response response;
    public static String employee_id;
    public static String token;
    public static String requestType;


    @Given("a JWT bearer token is generated")
    public void a_jwt_bearer_token_is_generated() {

        request = given().
                header("Content-Type", "application/json").
                body("{\n" +
                        "     \"email\": \"tharani4@gmail.com\",\n" +
                        "     \"password\": \"tharani4\"\n" +
                        "}");

        response = request.when().post(ConfigReader.read("apiGenerateToken"));
        token = "Bearer " + response.jsonPath().getString("token");

    }

    @Given("a request is prepared for creating the employee")
    public void a_request_is_prepared_for_creating_the_employee() {
        request = given().
                header("Content-Type", "application/json").
                header("Authorization", token).
                body("{\n" +
                        "  \"emp_firstname\": \"Asanth\",\n" +
                        "  \"emp_lastname\": \"Bilal\",\n" +
                        "  \"emp_middle_name\": \"ms\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"1993-11-07\",\n" +
                        "  \"emp_status\": \"Permanent\",\n" +
                        "  \"emp_job_title\": \"Trainee\"\n" +
                        "}");
    }

    @When("a {string} call is made to create the employee")
    public void a_call_is_made_to_create_the_employee(String requestType) {
        response = request.when().post(ConfigReader.read("apiCreateEmployee"));
        this.requestType = requestType;
    }

    @Then("the status code for this request is validated")
    public void the_status_code_for_this_request_is_validated() {
        response.then().assertThat().statusCode(CommonMethods.apiStatusCode(requestType));
        System.out.println(requestType);
    }

    @Then("the employee id {string} is stored and values are validated")
    public void the_employee_id_is_stored_and_values_are_validated(String empPath) {
        employee_id = response.jsonPath().getString(empPath);
        response.then().assertThat().body("Message", equalTo("Employee Created"));
        response.then().assertThat().body("Employee.emp_firstname", equalTo("Asanth"));
        response.then().assertThat().body("Employee.emp_middle_name", equalTo("ms"));
        response.then().assertThat().body("Employee.emp_lastname", equalTo("Bilal"));
        response.then().assertThat().body("Employee.emp_birthday", equalTo("1993-11-07"));
        response.then().assertThat().body("Employee.emp_job_title", equalTo("Trainee"));
        response.then().assertThat().body("Employee.emp_status", equalTo("Permanent"));
    }

    @Given("a request is prepared for retrieving the employee")
    public void a_request_is_prepared_for_retrieving_the_employee() {
        request = given().
                header("Content-Type", "application/json").
                header("Authorization", token).
                queryParam("employee_id", employee_id);
    }

    @When("a {string} call is made to retrieve the employee")
    public void a_call_is_made_to_retrieve_the_employee(String requestType) {
        response = request.when().get(ConfigReader.read("apiGetOneEmployee"));
        this.requestType = requestType;
    }

    @Then("the retrieved employee details are validated")
    public void the_retrieved_employee_details_are_validated() {
        response.then().assertThat().body("employee.employee_id", equalTo(employee_id));
        response.then().assertThat().body("employee.emp_firstname", equalTo("Asanth"));
        response.then().assertThat().body("employee.emp_middle_name", equalTo("ms"));
        response.then().assertThat().body("employee.emp_lastname", equalTo("Bilal"));
        response.then().assertThat().body("employee.emp_birthday", equalTo("1993-11-07"));
        response.then().assertThat().body("employee.emp_gender", equalTo("Male"));
        response.then().assertThat().body("employee.emp_job_title", equalTo("Trainee"));
        response.then().assertThat().body("employee.emp_status", equalTo("permanent"));
    }

    @Given("a request is prepared for retrieving all the employee")
    public void a_request_is_prepared_for_retrieving_all_the_employee() {
        request = given().
                header("Content-Type", "application/json").
                header("Authorization", token);
    }

    @When("a {string} call is made to retrieve all the employee")
    public void a_call_is_made_to_retrieve_all_the_employee(String requestType) {
        response = request.when().get(ConfigReader.read("apiGetAllEmployee"));
        this.requestType = requestType;
    }

    @Then("all the employee details are validated")
    public void all_the_employee_details_are_validated() {
        response.then().assertThat().body("$", hasKey("Total Employees"));
    }

    @Given("a request is prepared for retrieving job title")
    public void a_request_is_prepared_for_retrieving_job_title() {
        request = given().
                header("Content-Type", "application/json").
                header("Authorization", token);
    }

    @When("a {string} call is made to retrieve job title")
    public void a_call_is_made_to_retrieve_job_title(String requestType) {
        response = request.when().get(ConfigReader.read("apiGetJobTitle"));
        this.requestType = requestType;
    }

    @Then("the job titles are validated")
    public void the_job_titles_are_validated() {
        response.then().assertThat().body("$", hasKey("Jobs"));
    }

    @Given("a request is prepared for retrieving employment status")
    public void a_request_is_prepared_for_retrieving_employment_status() {
        request = given().
                header("Content-Type", "application/json").
                header("Authorization", token);
    }

    @When("a {string} call is made to retrieve employment status")
    public void a_call_is_made_to_retrieve_employment_status(String requestType) {
        response = request.when().get(ConfigReader.read("apiGetEmploymentStatus"));
        this.requestType = requestType;
    }

    @Then("the employment status are validated")
    public void the_employment_status_are_validated() {
        response.then().assertThat().body("$", hasKey("Employeement Status"));
    }

    @Given("a request is prepared for updating the employee")
    public void a_request_is_prepared_for_updating_the_employee() {
        request = given().
                header("Content-Type", "application/json").
                header("Authorization", token).
                body("{\n" +
                        "  \"employee_id\": \"" + employee_id + "\",\n" +
                        "  \"emp_firstname\": \"Asanth\",\n" +
                        "  \"emp_lastname\": \"Bilal\",\n" +
                        "  \"emp_middle_name\": \"ms\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"1993-11-07\",\n" +
                        "  \"emp_status\": \"Permanent\",\n" +
                        "  \"emp_job_title\": \"QA Engineer\"\n" +
                        "}");
    }

    @When("a {string} call is made to update the employee")
    public void a_call_is_made_to_update_the_employee(String requestType) {
        response = request.when().put(ConfigReader.read("apiUpdateEmployee"));
        this.requestType = requestType;
    }

    @Then("the updated employee details are validated")
    public void the_updated_employee_details_are_validated() {
        response.then().assertThat().body("Message", equalTo("Employee record Updated"));
        response.then().assertThat().body("Employee.emp_firstname", equalTo("Asanth"));
        response.then().assertThat().body("Employee.emp_middle_name", equalTo("ms"));
        response.then().assertThat().body("Employee.emp_lastname", equalTo("Bilal"));
        response.then().assertThat().body("Employee.emp_birthday", equalTo("1993-11-07"));
        response.then().assertThat().body("Employee.emp_gender", equalTo("Male"));
        response.then().assertThat().body("Employee.emp_job_title", equalTo("QA Engineer"));
        response.then().assertThat().body("Employee.emp_status", equalTo("Permanent"));
    }
}

