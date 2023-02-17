package examples.gui.web.waits;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.DriverFactory;

import static org.testng.AssertJUnit.assertTrue;

public class ExplicitWait {
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
    public void testExplicitWait() {
        WebElement queryTxt = driver.findElement(By.name("q"));
        queryTxt.sendKeys("Selenium WebDriver");
        queryTxt.submit();

//        WebDriverWait wait = new WebDriverWait(driver, 20);
//        wait.until(ExpectedConditions.titleContains("Selenium"));

        assertTrue(driver.getTitle().toLowerCase().startsWith("selenium"));

    }

    @Test
    public void testExplicitWait2() {
        driver.findElement(By.linkText("Example 1: Element on page that is hidden")).click();
        driver.findElement(By.cssSelector("#start button")).click();
//
//	WebDriverWait wait = new WebDriverWait(driver, 5);
//
//	wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("loading"))));

        Assert.assertEquals(driver.findElement(By.id("finish")).getText(), "Hello World!");
    }

}
