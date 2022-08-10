package testCases.gui.web.browser.waitLoading;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

public class Waits {


    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Handling Waits and sync Test Case")
    @Epic("Selenium Actions on Elements")
    @Story("Waits Tutorial")
    void WaitTest() {
        // Thread sleep: Stop all the execution for a specific amount of time in mil Seconds

//        Thread.sleep(3000);

        /*
         * The implicit wait will tell to the web driver to wait for certain amount of time
         * before it throws a "No Such Element Exception".
         * The default setting is 0. Once we set the time, web driver will wait for that time
         * before throwing an exception.
         * In the below example we have declared an implicit wait with the time frame of 10 seconds.
         * It means that if the element is not located on the web page within that time frame,
         * it will throw an exception.
         */

//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;


        /*
         * The explicit wait is used to tell the Web Driver to wait for certain conditions (Expected Conditions)
         * or the maximum time exceeded before throwing an "ElementNotVisibleException" exception.
         * The explicit wait is an intelligent kind of wait, but it can be applied only for specified elements.
         */

//        WebDriverWait wait=new WebDriverWait(driver, 20);
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "Xpath")));
//
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath( "Xpath")));
//
//        wait.until(ExpectedConditions.elementToBeSelected(By.xpath( "Xpath")));
//
//        wait.until(ExpectedConditions.urlToBe(""));
//
//        WebElement element = driver.findElement(By.xpath(""));
//        wait.until(ExpectedConditions.textToBePresentInElementValue(element, "Click me"));


        driver.get("http://the-internet.herokuapp.com/dynamic_loading");
        driver.findElement(By.linkText("Example 1: Element on page that is hidden")).click();
        driver.findElement(By.cssSelector("#start button")).click();

//        WebDriverWait wait = new WebDriverWait(driver, 5);
//         Waits 5 seconds for the element to be visible and then returns back its Text
//        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("loading"))));
        Assert.assertEquals(driver.findElement(By.id("finish")).getText(), "Hello World!");


    }
}
