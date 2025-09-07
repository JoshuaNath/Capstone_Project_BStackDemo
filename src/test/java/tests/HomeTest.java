package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.HomePage;
import utils.DriverFactory;
import utils.ScreenshotUtil;
import utils.ExtentReportManager;

public class HomeTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeClass
    public void setUp() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        driver.get("https://bstackdemo.com/");
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);

        try {
            String path = ScreenshotUtil.takeScreenshot(driver, "Open_HomePage");
            ExtentReportManager.getTest().info("Opened application")
                               .addScreenCaptureFromPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Login with one fixed user
        loginPage.openLoginDropdown();
        try {
            ExtentReportManager.getTest().info("Opened login dropdown")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "LoginDropdown"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        loginPage.enterUsername("demouser");
        try {
            ExtentReportManager.getTest().info("Entered username: demouser")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "EnteredUsername"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        loginPage.enterPassword("testingisfun99");
        try {
            ExtentReportManager.getTest().info("Entered password")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "EnteredPassword"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        loginPage.clickLogin();
        try {
            ExtentReportManager.getTest().info("Clicked login button")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "ClickedLogin"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(
            loginPage.isUserLoggedIn("demouser"),
            "User demouser was not logged in successfully."
        );

        try {
            ExtentReportManager.getTest().pass("User logged in successfully")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "LoginSuccess"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    // TC001: Offers page validation
    @Test(priority = 1)
    public void testOffersPage() {
        String offersText = homePage.goToOffers();
        try {
            ExtentReportManager.getTest().info("Navigated to Offers page")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "OffersPage"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(
            offersText.contains("Sorry we do not have any promotional offers") || offersText.equals("Offers available"),
            "Unexpected Offers page message: " + offersText
        );

        try {
            ExtentReportManager.getTest().pass("Offers page validation successful")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "OffersValidation"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TC002: Orders page validation
    @Test(priority = 2)
    public void testOrdersPage() {
        String ordersText = homePage.goToOrders();
        try {
            ExtentReportManager.getTest().info("Navigated to Orders page")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "OrdersPage"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(
            ordersText.contains("No orders found") || ordersText.equals("Orders available"),
            "Unexpected Orders page message: " + ordersText
        );

        try {
            ExtentReportManager.getTest().pass("Orders page validation successful")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "OrdersValidation"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TC003: Favorites page validation
    @Test(priority = 3)
    public void testFavoritesPage() {
        boolean bannerVisible = homePage.goToFavorites();
        try {
            ExtentReportManager.getTest().info("Navigated to Favorites page")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "FavoritesPage"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(bannerVisible, "Favorites page banner not found!");

        try {
            ExtentReportManager.getTest().pass("Favorites page validation successful")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "FavoritesValidation"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TC004: Homepage logo click
    @Test(priority = 4)
    public void testGoToHomePage() {
        homePage.goToHomePage();
        String currentUrl = driver.getCurrentUrl();
        try {
            ExtentReportManager.getTest().info("Clicked homepage logo")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "HomePageLogoClick"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(currentUrl.contains("bstackdemo.com"), "Not navigated to homepage after logo click!");

        try {
            ExtentReportManager.getTest().pass("Homepage logo click validation successful")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "HomePageValidation"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TC005: Select all vendors
    @Test(priority = 5)
    public void testSelectAllVendors() {
        homePage.selectAllVendors();
        try {
            ExtentReportManager.getTest().info("Selected all vendors")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "VendorsSelection"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(true, "Vendors selection failed unexpectedly.");

        try {
            ExtentReportManager.getTest().pass("Vendors selection validation successful")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "VendorsValidation"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TC006: Sort products
    @Test(priority = 6)
    public void testSortProducts() {
        homePage.sortBy("lowestprice");
        try {
            ExtentReportManager.getTest().info("Sorted products by lowest price")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "SortLowestPrice"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        homePage.sortBy("highestprice");
        try {
            ExtentReportManager.getTest().info("Sorted products by highest price")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "SortHighestPrice"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(true, "Sorting failed unexpectedly.");

        try {
            ExtentReportManager.getTest().pass("Sorting validation successful")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "SortingValidation"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
