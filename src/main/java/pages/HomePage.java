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

    // Sort
    private By sortDropdown = By.cssSelector("div.sort select");


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private void delay() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String goToOffers() {
        driver.findElement(offersLink).click();
        delay();
        try {
            return driver.findElement(offersMessage).getText();
        } catch (NoSuchElementException e) {
            return "Offers available";
        }
    }

    public String goToOrders() {
        driver.findElement(ordersLink).click();
        delay();
        try {
            return driver.findElement(ordersMessage).getText();
        } catch (NoSuchElementException e) {
            return "Orders available";
        }
    }

    public boolean goToFavorites() {
        driver.findElement(favoritesLink).click();
        delay();
        try {
            return driver.findElement(favoritesBanner).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } 
    }

    public void goToHomePage() {
        driver.findElement(logo).click();
        delay();
    }

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

                wait.until(ExpectedConditions.elementToBeSelected(input));

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void sortBy(String optionValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement dropdown = driver.findElement(sortDropdown);
        Select select = new Select(dropdown);
        select.selectByValue(optionValue);


        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".product-grid li")));
        } catch (TimeoutException e) {
            System.out.println("No products found or grid did not reload in time after sorting by: " + optionValue);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
