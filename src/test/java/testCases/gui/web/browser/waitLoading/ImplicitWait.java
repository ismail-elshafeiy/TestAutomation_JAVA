package testCases.gui.web.browser.waitLoading;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertTrue;

public class ImplicitWait {
    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
        driver.navigate().to("http://cookbook.seleniumacademy.com/AjaxDemo.html");
        System.out.println("Title: " + driver.getTitle());
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    @Test
    public void testImplicitWait (){

        // set implicit wait time to 20 Seconds
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Page 4")).click();

        WebElement message = driver.findElement(By.id("page4"));
        assertTrue(message.getText().contains("Nunc nibh tortor"));

    }

}

