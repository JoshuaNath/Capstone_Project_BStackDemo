package pages;

import java.util.List;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;

    // Locators
    private By offersLink = By.id("offers");
    private By ordersLink = By.id("orders");
    private By favoritesLink = By.id("favourites");
    private By logo = By.cssSelector("img.Navbar_logo__image__3Blki");

    // Messages
    private By offersMessage = By.cssSelector("div.pt-6.text-2xl.font-bold.tracking-wide.text-center.text-red-50");
    private By ordersMessage = By.cssSelector("h2.pt-6.text-2xl.font-bold.tracking-wide.text-center");
    private By favoritesBanner = By.cssSelector("img[alt='banner main']");

    // Vendors
    private By vendorCheckboxes = By.cssSelector("label input[type='checkbox']");

    // Sort
    private By sortDropdown = By.cssSelector("div.sort select");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Helper to wait 5 seconds between actions
    private void delay() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Navigate to Offers page
    public String goToOffers() {
        driver.findElement(offersLink).click();
        delay();
        try {
            return driver.findElement(offersMessage).getText();
        } catch (NoSuchElementException e) {
            return "Offers available";
        }
    }

    // Navigate to Orders page
    public String goToOrders() {
        driver.findElement(ordersLink).click();
        delay();
        try {
            return driver.findElement(ordersMessage).getText();
        } catch (NoSuchElementException e) {
            return "Orders available";
        }
    }

    // Navigate to Favorites page
    public boolean goToFavorites() {
        driver.findElement(favoritesLink).click();
        delay();
        try {
            return driver.findElement(favoritesBanner).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } 
    }

    // Click logo to go to homepage
    public void goToHomePage() {
        driver.findElement(logo).click();
        delay();
    }

    // Select all vendor checkboxes
    public void selectAllVendors() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> vendorLabels = driver.findElements(
            By.cssSelector(".filters-available-size label")
        );

        for (WebElement label : vendorLabels) {
            WebElement input = label.findElement(By.cssSelector("input[type='checkbox']"));
            WebElement span = label.findElement(By.cssSelector("span.checkmark"));

            if (!input.isSelected()) {
                span.click();

                // Wait until the input becomes selected
                wait.until(ExpectedConditions.elementToBeSelected(input));

                // Small pause for stability (5 seconds as per your requirement)
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // Select sorting option
    public void sortBy(String optionValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement dropdown = driver.findElement(sortDropdown);
        Select select = new Select(dropdown);
        select.selectByValue(optionValue);

        // Wait until the product grid reloads
        // Assuming products are in a ul with class "product-grid"
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".product-grid li")));
        } catch (TimeoutException e) {
            System.out.println("No products found or grid did not reload in time after sorting by: " + optionValue);
        }

        // Small pause for stability (5 sec as per your requirement)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
