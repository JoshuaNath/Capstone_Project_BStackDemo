package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;

    public WebPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.loginPage = new LoginPage(driver); 
    }

    private void pause() throws InterruptedException {
        Thread.sleep(5000);
    }


    public void login(String username, String password) throws InterruptedException {
        loginPage.openLoginDropdown();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        pause();
        loginPage.clickLogin();
        pause();
    }


    public void selectVendor(String vendor) throws InterruptedException {
        WebElement vendorCheckbox = driver.findElement(By.cssSelector("input[value='" + vendor + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", vendorCheckbox);
        pause();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", vendorCheckbox);
        pause();
    }


    public void markFavoriteById(String productId) throws InterruptedException {
        WebElement favBtn = driver.findElement(By.cssSelector("div.shelf-item[id='" + productId + "'] .MuiButtonBase-root"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", favBtn);
        pause();
    }


    public void goToFavorites() throws InterruptedException {
        driver.findElement(By.id("favourites")).click();
        pause();
    }


    public void addToCartFromFavorites(String productTitle) throws InterruptedException {
        WebElement product = driver.findElement(By.xpath("//p[@class='shelf-item__title' and text()='" + productTitle + "']/..//div[@class='shelf-item__buy-btn']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", product);
        pause();
    }


    public void goToHomePage() throws InterruptedException {
        driver.findElement(By.cssSelector("img[alt='logo']")).click();
        pause();
    }


    public void increaseProductQuantity(String productName, int times) throws InterruptedException {
        for (int i = 0; i < times; i++) {
            WebElement plusBtn = driver.findElement(By.xpath("//p[@class='title' and text()='" + productName + "']/../..//button[text()='+']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", plusBtn);
            pause();
        }
    }


    public void clickCheckout() throws InterruptedException {
        driver.findElement(By.cssSelector("div.buy-btn")).click();
        pause();
    }


    public void fillShippingForm(String fname, String lname, String address, String state, String postal) throws InterruptedException {
        driver.findElement(By.id("firstNameInput")).sendKeys(fname);
        pause();
        driver.findElement(By.id("lastNameInput")).sendKeys(lname);
        pause();
        driver.findElement(By.id("addressLine1Input")).sendKeys(address);
        pause();
        driver.findElement(By.id("provinceInput")).sendKeys(state);
        pause();
        driver.findElement(By.id("postCodeInput")).sendKeys(postal);
        pause();
        driver.findElement(By.id("checkout-shipping-continue")).click();
        pause();
    }


    public void downloadReceipt() throws InterruptedException {
        driver.findElement(By.id("downloadpdf")).click();
        pause();
    }


    public void continueShopping() throws InterruptedException {
        driver.findElement(By.cssSelector("button.optimizedCheckout-buttonSecondary")).click();
        pause();
    }


    public void goToOrders() throws InterruptedException {
        driver.findElement(By.linkText("Orders")).click();
        pause();
    }


    public void logout() throws InterruptedException {
        driver.findElement(By.cssSelector("span.logout-link")).click(); // ✅ fixed logout locator
        pause();
    }
}
