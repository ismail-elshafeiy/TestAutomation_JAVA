package testCases.gui.javaScript;


import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

public class JavaScriptExecutor_SendKeysAndClickButtons  {
    JavaScriptExecutor js;
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

    /*
     * to type text in Selenium WebDriver without using sendKeys() method
     */
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Performing JavaScript Executor commands to send keys and click instead of selenium commands Test Case")
    @Epic("Selenium Actions on Elements")
    @Story("JavaScript Executor Tutorial")
    void SendKeysUsingJse() {
        driver.get("https://the-internet.herokuapp.com/login");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('username').value='tomsmith';");
        js.executeScript("document.getElementById('password').value='SuperSecretPassword!';");

        /*
         * to click a button in Selenium WebDriver using JavaScript
         */
        WebElement loginButton = driver.findElement(By.cssSelector("button.radius"));
        js.executeScript("arguments[0].click();", loginButton);
//       js.executeScript("document.getElementById('enter your element id').click();");
    }
}
