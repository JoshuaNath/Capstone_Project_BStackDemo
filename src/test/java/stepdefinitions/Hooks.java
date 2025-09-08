package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.DriverFactory;

public class Hooks {

    @Before
    public void setup(Scenario scenario) {
        try {
            DriverFactory.initDriver();
            DriverFactory.getDriver().get("https://bstackdemo.com/");
            System.out.println("Browser launched and navigated to bstackdemo.com for scenario: " + scenario.getName());
        } catch (Exception e) {
            System.err.println("Error during setup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        try {          
            if (scenario.isFailed() && DriverFactory.getDriver() != null) {
                byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
            }
        } catch (Exception e) {
            System.err.println("Error while taking screenshot: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                DriverFactory.quitDriver();
                System.out.println("Browser closed for scenario: " + scenario.getName());
            } catch (Exception e) {
                System.err.println("Error while quitting driver: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
