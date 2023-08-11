package web.waits;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.DriverFactory;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.testng.AssertJUnit.assertTrue;

public class FluentWait {
    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
		driver = DriverFactory.getBrowser();
		driver.navigate().to("https://www.google.com/");
        System.out.println("Title: " + driver.getTitle());
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    @Test
    public void testFluentWait() {
        driver.navigate().to("http://cookbook.seleniumacademy.com/AjaxDemo.html");
        driver.findElement(By.linkText("Page 4")).click();

        // Create Fluent Wait
        Wait<WebDriver> fWait = new org.openqa.selenium.support.ui.FluentWait<WebDriver>(driver)
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


    @Test
    public void testFluentWait2() {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading");
        driver.findElement(By.linkText("Example 1: Element on page that is hidden")).click();
        driver.findElement(By.cssSelector("#start button")).click();

        org.openqa.selenium.support.ui.FluentWait<WebDriver> wait = new org.openqa.selenium.support.ui.FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1)).ignoring(org.openqa.selenium.NoSuchElementException.class);

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("loading"))));

        Assert.assertEquals(driver.findElement(By.id("finish")).getText(), "Hello World!");

    }
}
