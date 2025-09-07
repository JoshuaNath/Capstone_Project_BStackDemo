package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;
import utils.DriverFactory;

public class LoginSteps {

    private LoginPage loginPage;

    @Given("the user is on the login page")
    public void userIsOnLoginPage() {
        DriverFactory.getDriver().get("https://bstackdemo.com/");
        loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.openLoginDropdown();
    }

    @When("the user logs in with username {string} and password {string}")
    public void userLogsIn(String username, String password) {
        if (!username.isEmpty()) {
            loginPage.enterUsername(username);
        }
        if (!password.isEmpty()) {
            loginPage.enterPassword(password);
        }
        loginPage.clickLogin();
    }

    // ✅ Valid user
    @Then("the username {string} should be visible")
    public void usernameShouldBeVisible(String username) {
        Assert.assertTrue(
            loginPage.isUserLoggedIn(username),
            "Expected username not visible after login: " + username
        );
    }

    // ✅ Locked user
    @Then("a locked user message should be shown")
    public void lockedUserMessageShouldBeShown() {
        String msg = loginPage.getErrorMessage();
        Assert.assertNotNull(msg, "Locked user message not found!");
        Assert.assertTrue(msg.toLowerCase().contains("locked"),
            "Unexpected locked user message: " + msg);
    }

    // ✅ Invalid user
    @Then("an invalid user message should be shown")
    public void invalidUserMessageShouldBeShown() {
        String msg = loginPage.getErrorMessage();
        Assert.assertNotNull(msg, "Invalid user message not found!");
        Assert.assertTrue(msg.toLowerCase().contains("invalid"),
            "Unexpected invalid user message: " + msg);
    }

    // ✅ Empty username
    @Then("an error message for empty username should be shown")
    public void emptyUsernameMessageShouldBeShown() {
        String msg = loginPage.getErrorMessage();
        Assert.assertNotNull(msg, "Empty username message not found!");
    }

    // ✅ Empty password
    @Then("an error message for empty password should be shown")
    public void emptyPasswordMessageShouldBeShown() {
        String msg = loginPage.getErrorMessage();
        Assert.assertNotNull(msg, "Empty password message not found!");
    }
}
