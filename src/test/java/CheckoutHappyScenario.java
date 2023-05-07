import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class CheckoutHappyScenario {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver(); //can use WebDriverManager here but no need for it in the scope of the code
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.saucedemo.com/");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='login_logo']")).getText(), "Swag Labs");
        String username = getUsernameOrPassword("standard", driver.findElement(By.id("login_credentials")).getText());
        String password = getUsernameOrPassword("secret", driver.findElement(By.cssSelector("div[class='login_password']")).getText());
        driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys(username);
        driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys(password);
        driver.findElement(By.cssSelector("input[data-test='login-button']")).click();
        Thread.sleep(2000L); //must be updated to fluent wait
        //List of webElements to have the capablity of adding them all in the cart
        List<WebElement> cartButtons = driver.findElements(By.cssSelector("div.pricebar"));
        cartButtons.get(0).click(); //choosing the first product
        driver.findElement(By.cssSelector("a[class='shopping_cart_link']")).click();
        driver.findElement(By.cssSelector("button[data-test='checkout']")).click();
        driver.findElement(By.cssSelector("input[data-test='firstName']")).sendKeys("Omar");
        driver.findElement(By.cssSelector("input[data-test='lastName']")).sendKeys("Hegazy");
        driver.findElement(By.cssSelector("input[data-test='postalCode']")).sendKeys("11865");
        driver.findElement(By.cssSelector("input[data-test='continue']")).click();
        //Assertion values must be checked against excel or json file not hard coded
        Assert.assertEquals(driver.findElement(By.cssSelector("div[class='summary_info_label summary_total_label']")).getText(),"Total: $32.39");
        driver.findElement(By.cssSelector("button[data-test='finish']")).click();
        //Assertion values must be checked against excel or json file not hard coded
        Assert.assertEquals(driver.findElement(By.cssSelector("h2.complete-header")).getText(), "Thank you for your order!");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.complete-text")).getText(),"Your order has been dispatched, and will arrive just as fast as the pony can get there!");
    }


    public static String getUsernameOrPassword(String keyword, String text) {
        String[] textarray = text.split("\n");
        for (String textobj : textarray) {
            if (textobj.contains(keyword)) {
                text = textobj;
                break;
            }
        }
        return text;
    }
}
