package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.WebPage;
import utils.DriverFactory;
import utils.ScreenshotUtil;
import utils.ExtentReportManager;

public class WebTest {

    private WebDriver driver;
    private WebPage webPage;

    @BeforeClass
    public void setUp() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        driver.get("https://bstackdemo.com/");
        webPage = new WebPage(driver);

        try {
            String path = ScreenshotUtil.takeScreenshot(driver, "Open_HomePage");
            ExtentReportManager.getTest().info("Opened application")
                               .addScreenCaptureFromPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ✅ Login with fav_user
        try {
			webPage.login("fav_user", "testingisfun99");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
            ExtentReportManager.getTest().pass("Logged in with fav_user")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "LoginFavUser"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1)
    public void testSamsungFavoriteFlow() throws Exception {
        webPage.selectVendor("Samsung");
        try {
            ExtentReportManager.getTest().info("Selected Samsung vendor")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "SamsungVendor"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        webPage.markFavoriteById("10");
        try {
            ExtentReportManager.getTest().info("Marked Galaxy S20 as favorite")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "GalaxyS20Fav"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        webPage.goToFavorites();
        try {
            ExtentReportManager.getTest().info("Opened Favorites page")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "FavoritesPage"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        webPage.addToCartFromFavorites("Galaxy S20");
        try {
            ExtentReportManager.getTest().pass("Added Galaxy S20 to cart from Favorites")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "GalaxyS20AddedToCart"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 2)
    public void testAppleFavoriteFlow() throws Exception {
        webPage.goToHomePage();

        webPage.selectVendor("Apple");
        try {
            ExtentReportManager.getTest().info("Selected Apple vendor")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "AppleVendor"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        webPage.markFavoriteById("6");
        try {
            ExtentReportManager.getTest().info("Marked iPhone 11 Pro as favorite")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "iPhone11Fav"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        webPage.goToFavorites();
        try {
            ExtentReportManager.getTest().info("Opened Favorites page")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "FavoritesPageApple"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        webPage.addToCartFromFavorites("iPhone 11 Pro");
        try {
            ExtentReportManager.getTest().pass("Added iPhone 11 Pro to cart from Favorites")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "iPhone11AddedToCart"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 3)
    public void testCartAndCheckoutFlow() throws Exception {
        webPage.increaseProductQuantity("Galaxy S20", 2);
        try {
            ExtentReportManager.getTest().info("Increased Galaxy S20 quantity by 2")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "IncreasedQuantity"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        webPage.clickCheckout();
        try {
            ExtentReportManager.getTest().info("Clicked Checkout")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "CheckoutClicked"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        webPage.fillShippingForm("Joshua", "Nath", "Bhopal", "MP", "011010");
        try {
            ExtentReportManager.getTest().info("Filled shipping form")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "ShippingFormFilled"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        webPage.downloadReceipt();
        try {
            ExtentReportManager.getTest().pass("Downloaded receipt")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "ReceiptDownloaded"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        webPage.continueShopping();
        try {
            ExtentReportManager.getTest().info("Continued shopping")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "ContinuedShopping"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        webPage.goToOrders();
        try {
            ExtentReportManager.getTest().info("Opened Orders page")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "OrdersPage"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ✅ Wait 10 seconds safely
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        webPage.logout();
        try {
            ExtentReportManager.getTest().pass("Logged out successfully")
                               .addScreenCaptureFromPath(ScreenshotUtil.takeScreenshot(driver, "LoggedOut"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
