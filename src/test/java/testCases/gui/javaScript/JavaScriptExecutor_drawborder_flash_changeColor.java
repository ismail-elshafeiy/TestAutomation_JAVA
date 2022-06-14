package testCases.gui.javaScript;


import io.qameta.allure.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;


public class JavaScriptExecutor_drawborder_flash_changeColor  {
    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }


    /*
     *
     * Using JavaScriptExecutor to perform some actions such as
     * input text , click , scrollDown to element or to x and y
     * refresh the page or draw a border to an element
     * since selenium doesnt always work in all the cases so we need to use the JavaScriptExecutor
     * to perform these actions
     *
     */


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Performing JavaScript Executor commands to Change color of an element and draw border Test Case")
    @Epic("Selenium Actions on Elements")
    @Story("JavaScript Executor Tutorial")
    void NavigationUsingJse() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("https://the-internet.herokuapp.com/");
        /*
         * draw a border over the selected element
         */
        WebElement element;
//        element = DriverContext.driver.findElement(By.xpath("//a[contains(text(),'Form Authentication')]"));
//        js.executeScript("arguments[0].style.border='3px solid red'", element);
//		js.executeScript("window.scrollBy(0,300)");
    }
}
