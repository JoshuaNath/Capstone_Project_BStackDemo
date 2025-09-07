package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // ✅ Initialize reports (equivalent to initReports)
    public static void initReports() {
        if (extent == null) {
            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("test-output/ExtentReport.html");
            htmlReporter.config().setDocumentTitle("Automation Test Report");
            htmlReporter.config().setReportName("Selenium Automation Report");
            htmlReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }
    }

    // ✅ Start a test (equivalent to startTest)
    public static ExtentTest startTest(String testName) {
        if (extent == null) {
            initReports();
        }
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    // ✅ Get the current test safely
    public static ExtentTest getTest() {
        if (test.get() == null) {
            return startTest("Default Test");
        }
        return test.get();
    }

    // ✅ Flush reports (equivalent to flushReports)
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
