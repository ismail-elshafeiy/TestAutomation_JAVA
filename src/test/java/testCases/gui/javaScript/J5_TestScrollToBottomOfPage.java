package testCases.gui.javaScript;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class J5_TestScrollToBottomOfPage {
    WebDriver driver;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.amazon.com/");
    }

    @Test
    public void testScrollWithJs() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("scrollBy(0,4300)");
        Thread.sleep(3000);
        WebElement amazonLogo = driver.findElement(By.cssSelector("div.nav-logo-base.nav-sprite"));
        Assert.assertTrue(amazonLogo.isDisplayed());

    }
}
