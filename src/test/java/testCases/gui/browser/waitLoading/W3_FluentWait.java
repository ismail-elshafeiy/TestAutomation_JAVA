package Course.A4_wait;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.testng.AssertJUnit.assertTrue;

public class W3_FluentWait {
    ChromeDriver driver;


    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.navigate().to("http://cookbook.seleniumacademy.com/AjaxDemo.html");
        driver.manage().window().maximize();
    }

    @Test
    public void testFluentWait() {
        driver.findElement(By.linkText("Page 4")).click();

        // Create Fluent Wait
        Wait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(2000))
                .withTimeout(Duration.ofSeconds(2000))
                .pollingEvery(Duration.ofSeconds(2000))
                .ignoring(NoSuchElementException.class);

        // Find Element by locator = locator
        WebElement message = fWait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.id("page4"));
            }
        });

        // asset about it when implemeted
        assertTrue(message.getText().contains("Nunc nibh tortor"));

    }

}
