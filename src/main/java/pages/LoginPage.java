package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By signinBtn = By.id("signin");
    private By usernameDropdown = By.id("username");
    private By passwordDropdown = By.id("password");
    private By loginBtn = By.id("login-btn");
    private By loggedInUser = By.cssSelector("span.username");
    private By errorMessage = By.cssSelector("h3.api-error");  // ✅ Corrected

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
    }

    // Open login popup
    public void openLoginDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(signinBtn)).click();
    }

    // Select username from dropdown
    public void enterUsername(String username) {
        WebElement userDropdown = wait.until(ExpectedConditions.elementToBeClickable(usernameDropdown));
        userDropdown.click();

        WebElement userOption = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@id,'react-select') and text()='" + username + "']")
            )
        );
        userOption.click();
    }

    // Select password from dropdown
    public void enterPassword(String password) {
        WebElement passDropdown = wait.until(ExpectedConditions.elementToBeClickable(passwordDropdown));
        passDropdown.click();

        WebElement passOption = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@id,'react-select') and text()='" + password + "']")
            )
        );
        passOption.click();
    }

    // Click login
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }

    // Check if user logged in
    public boolean isUserLoggedIn(String expectedUsername) {
        try {
            WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInUser));
            return user.getText().equalsIgnoreCase(expectedUsername);
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ✅ Updated: safely fetch error message without failing test
    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        } catch (TimeoutException e) {
            return ""; // return empty string instead of null to avoid test failure
        }
    }
}
