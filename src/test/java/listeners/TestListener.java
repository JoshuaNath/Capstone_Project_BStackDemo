package listeners;

import com.aventstack.extentreports.Status;
import utils.ExtentReportManager;
import utils.ScreenshotUtil;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportManager.startTest(result.getMethod().getMethodName());  // ✅ changed
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = null;

        // Try to get WebDriver instance from test class
        try {
            Object testInstance = result.getInstance();
            driver = (WebDriver) testInstance.getClass().getMethod("getDriver").invoke(testInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Log failure and attach screenshot
        ExtentReportManager.getTest().fail(result.getThrowable());

        if (driver != null) {
            String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getName());
            try {
                ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReports();  // ✅ changed
    }
}
