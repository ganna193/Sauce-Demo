import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class Login {
    WebDriver driver = new ChromeDriver();
    @BeforeClass
    public void SetUp(){
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/v1/index.html");
//        LoginWithInvalidDate();
//        LoginWithValidDate();
//        AddToCart();
//        CheckCartPage();
//        Removeitems();
//        Checkout();
//        LogOut();

    }

    @Test(priority = 1)
    public void LoginWithValidDate(){
        By username = By.id("user-name");
        By password = By.id("password");
        By loginButton = By.id("login-button");
        driver.findElement(username).sendKeys("standard_user");
        driver.findElement(password).sendKeys("secret_sauce");
        driver.findElement(loginButton).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("https://www.saucedemo.com/v1/inventory.html"));
    }
    @Test(priority = 0)
    public void LoginWithInvalidDate(){
        By username = By.id("user-name");
        By password = By.id("password");
        By loginButton = By.id("login-button");
        driver.findElement(username).sendKeys("standard_userrr");
        driver.findElement(password).sendKeys("secret_saucee");
        driver.findElement(loginButton).click();
        Assert.assertTrue(driver.findElement(By.className("error-button")).isDisplayed(), "Epic sadface: Username and password do not match any user in this service");
        driver.navigate().refresh();

    }
    @Test(priority = 2)
    public void AddToCart(){
        By Item0 = By.cssSelector(".inventory_item:nth-child(1) .btn_inventory");
        By Item1 = By.cssSelector(".inventory_item:nth-child(2) .btn_inventory");
        driver.findElement(Item0).click();
        driver.findElement(Item1).click();
    }

    @Test(priority = 3)
    public void CheckCartPage(){
        By CartIcon = By.className("shopping_cart_link");
        driver.findElement(CartIcon).click();

        List<WebElement> items = driver.findElements(By.className("cart_item"));
        Assert.assertEquals(items.get(0).findElement(By.className("inventory_item_name")).getText(),"Sauce Labs Backpack");
        Assert.assertEquals(items.get(1).findElement(By.className("inventory_item_name")).getText(),"Sauce Labs Bike Light");

        for (WebElement item : items) {
            Assert.assertTrue(item.findElement(By.className("inventory_item_name")).isDisplayed());
            Assert.assertTrue(item.findElement(By.className("inventory_item_price")).isDisplayed());
            Assert.assertTrue(item.findElement(By.className("cart_quantity")).isDisplayed());
        }

    }

    @Test(priority = 4)
    public void Removeitems(){
        WebElement firstCartItem = driver.findElement(By.className("cart_item"));
        firstCartItem.findElement(By.className("cart_button")).click();
        List<WebElement> items = driver.findElements(By.className("cart_item"));
        Assert.assertEquals(items.size(), 1);

    }

    @Test(priority = 5)
    public void Checkout(){
        By checkoutButton = By.className("btn_action");
        driver.findElement(checkoutButton).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("https://www.saucedemo.com/v1/checkout-step-one.html"));
        By FirstName = By.id("first-name");
        By LastName = By.id("last-name");
        By PostalCode = By.id("postal-code");
        By ContinueButton = By.className("btn_primary");
        driver.findElement(FirstName).sendKeys("min");
        driver.findElement(LastName).sendKeys("gogo");
        driver.findElement(PostalCode).sendKeys("12345");
        driver.findElement(ContinueButton).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("https://www.saucedemo.com/v1/checkout-step-two.html"));
        By Finish = By.className("btn_action");
        driver.findElement(Finish).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("https://www.saucedemo.com/v1/checkout-complete.html"));
    }

    @Test(priority = 6)
    public void LogOut(){
        By menu = By.className("bm-burger-button");
        driver.findElement(menu).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("https://www.saucedemo.com/v1/index.html"));
    }

    @AfterClass
    public void quit() {
        driver.quit();
    }

}
