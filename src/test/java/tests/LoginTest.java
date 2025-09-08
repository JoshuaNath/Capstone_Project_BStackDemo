package tests;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.DriverFactory;
import utils.ScreenshotUtil;
import utils.ExtentReportManager;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeSuite
    public void initReports() {
        ExtentReportManager.initReports();
    }

    @AfterSuite
    public void flushReports() {
        ExtentReportManager.flushReports();
    }

    @BeforeMethod
    public void setUp(Method method) {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        driver.get("https://bstackdemo.com/");
        loginPage = new LoginPage(driver);

        // ✅ Start ExtentTest for this method
        ExtentReportManager.startTest(method.getName());

        // Screenshot after opening app
        try {
            String path = ScreenshotUtil.takeScreenshot(driver, "Open_HomePage");
            ExtentReportManager.getTest().info("Opened application")
                               .addScreenCaptureFromPath(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    //  TC001-TC004: Login with valid credentials
    @Test(dataProvider = "validUsers")
    public void testValidLogin(String username, String password) throws Exception {
        loginPage.openLoginDropdown();
        try {
            ExtentReportManager.getTest().info("Opened login dropdown")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "LoginDropdown"));
        } catch (Exception e) { e.printStackTrace(); }

        loginPage.enterUsername(username);
        try {
            ExtentReportManager.getTest().info("Entered username: " + username)
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "EnteredUsername"));
        } catch (Exception e) { e.printStackTrace(); }

        loginPage.enterPassword(password);
        try {
            ExtentReportManager.getTest().info("Entered password")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "EnteredPassword"));
        } catch (Exception e) { e.printStackTrace(); }

        loginPage.clickLogin();
        try {
            ExtentReportManager.getTest().info("Clicked login button")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "ClickedLogin"));
        } catch (Exception e) { e.printStackTrace(); }

        Assert.assertTrue(
            loginPage.isUserLoggedIn(username),
            "User " + username + " was not logged in successfully."
        );

        try {
            ExtentReportManager.getTest().pass("User logged in successfully")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "LoginSuccess"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    //  TC005: Locked user
    @Test
    public void testLockedUser() throws Exception {
        loginPage.openLoginDropdown();
        try {
            ExtentReportManager.getTest().info("Opened login dropdown")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "LoginDropdown"));
        } catch (Exception e) { e.printStackTrace(); }

        loginPage.enterUsername("locked_user");
        try {
            ExtentReportManager.getTest().info("Entered username: locked_user")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "EnteredUsername"));
        } catch (Exception e) { e.printStackTrace(); }

        loginPage.enterPassword("testingisfun99");
        try {
            ExtentReportManager.getTest().info("Entered password")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "EnteredPassword"));
        } catch (Exception e) { e.printStackTrace(); }

        loginPage.clickLogin();
        try {
            ExtentReportManager.getTest().info("Clicked login button")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "ClickedLogin"));
        } catch (Exception e) { e.printStackTrace(); }

        String errorMsg = loginPage.getErrorMessage();
        Assert.assertFalse(errorMsg.isEmpty(), "Expected error message for locked user but found none.");
        Assert.assertTrue(errorMsg.toLowerCase().contains("locked"),
            "Unexpected error message for locked user: " + errorMsg);

        try {
            ExtentReportManager.getTest().pass("Locked user validation successful")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "LockedUserError"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    // TC006: Empty username
    @Test
    public void testEmptyUsername() throws Exception {
        loginPage.openLoginDropdown();
        try {
            ExtentReportManager.getTest().info("Opened login dropdown")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "LoginDropdown"));
        } catch (Exception e) { e.printStackTrace(); }

        loginPage.enterPassword("testingisfun99");
        try {
            ExtentReportManager.getTest().info("Entered password")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "EnteredPassword"));
        } catch (Exception e) { e.printStackTrace(); }

        loginPage.clickLogin();
        try {
            ExtentReportManager.getTest().info("Clicked login button")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "ClickedLogin"));
        } catch (Exception e) { e.printStackTrace(); }

        String errorMsg = loginPage.getErrorMessage();
        Assert.assertFalse(errorMsg.isEmpty(), "Expected error message for empty username but found none.");

        try {
            ExtentReportManager.getTest().pass("Empty username validation successful")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "EmptyUsernameError"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    //  TC007: Empty password
    @Test
    public void testEmptyPassword() throws Exception {
        loginPage.openLoginDropdown();
        try {
            ExtentReportManager.getTest().info("Opened login dropdown")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "LoginDropdown"));
        } catch (Exception e) { e.printStackTrace(); }

        loginPage.enterUsername("demouser");
        try {
            ExtentReportManager.getTest().info("Entered username: demouser")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "EnteredUsername"));
        } catch (Exception e) { e.printStackTrace(); }

        loginPage.clickLogin();
        try {
            ExtentReportManager.getTest().info("Clicked login button")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "ClickedLogin"));
        } catch (Exception e) { e.printStackTrace(); }

        String errorMsg = loginPage.getErrorMessage();
        Assert.assertFalse(errorMsg.isEmpty(), "Expected error message for empty password but found none.");

        try {
            ExtentReportManager.getTest().pass("Empty password validation successful")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "EmptyPasswordError"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    @DataProvider(name = "validUsers")
    public Object[][] getUsers() {
        return new Object[][]{
                {"demouser", "testingisfun99"},
                {"fav_user", "testingisfun99"},
                {"image_not_loading_user", "testingisfun99"},
                {"existing_orders_user", "testingisfun99"}
        };
    }
}
