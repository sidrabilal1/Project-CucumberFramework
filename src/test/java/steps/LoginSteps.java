package steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import org.openqa.selenium.Point;
import utils.CommonMethods;
import utils.ConfigReader;

public class LoginSteps extends CommonMethods {

    @Given("user is able to access HRMS portal")
    public void user_is_able_to_access_hrms_portal() {
        openBrowserAndLaunchApplication();
    }

    @When("user attempts to login with an empty username field")
    public void user_attempts_to_login_with_an_empty_username_field() {
        sendText("", loginPage.userNameField);
        sendText(ConfigReader.read("password"), loginPage.passwordField);
    }

    @When("user clicks on login button")
    public void user_clicks_on_login_button() {
        click(loginPage.loginButton);
    }

    @Then("error message is clearly visible near the {string} field")
    public void error_message_is_clearly_visible_near_the_field(String field) {
        Assert.assertTrue(loginPage.errorMessage.isDisplayed());
        if(field.equals("userName")) {
            Assert.assertTrue(elementLocation(loginPage.errorMessage).getY()<elementLocation(loginPage.passwordField).getY());
        }else if(field.equals("password")){
            Assert.assertTrue(elementLocation(loginPage.errorMessage).getY()<elementLocation(loginPage.loginButton).getY());
        }else{
            Assert.assertTrue(elementLocation(loginPage.errorMessage).getY()>elementLocation(loginPage.loginButton).getY());
        }
    }

    @When("user attempts to login with an empty password field")
    public void user_attempts_to_login_with_an_empty_password_field() {
        sendText(ConfigReader.read("userName"), loginPage.userNameField);
        sendText("", loginPage.passwordField);
    }

    @When("user attempts to login with an incorrect {string} or {string}")
    public void user_attempts_to_login_with_an_incorrect_or(String userName, String password) {
        sendText(userName, loginPage.userNameField);
        sendText(password, loginPage.passwordField);
    }

    @Then("user sees error message {string}")
    public void user_sees_error_message(String errorMessage) {
        Assert.assertEquals(errorMessage, loginPage.errorMessage.getText());
    }

    @When("user attempts to login with an invalid credentials")
    public void user_attempts_to_login_with_an_invalid_credentials() {
        sendText("adm", loginPage.userNameField);
        sendText("password", loginPage.passwordField);
    }

    @When("user enters valid credentials")
    public void user_enters_valid_credentials() {
        sendText(ConfigReader.read("userName"), loginPage.userNameField);
        sendText(ConfigReader.read("password"), loginPage.passwordField);
    }

    @Then("user is navigated to dashboard page")
    public void user_is_navigated_to_dashboard_page() {
        Assert.assertEquals("Welcome Admin",dashboardPage.welcomeText.getText());
    }

}
