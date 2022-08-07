package testCases.gui.browser.frames;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

public class Frames_FindUsingIndex {

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
     * Using driver.switchTo().frame() to handle frames
     * mainly used with iframe's ID , name , index or as WebElement
     *
     */

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Handling Frame using index Test Case")
    @Epic("Selenium Actions on Elements")
    @Story("Frames Tutorial")
    void NestedFrames() {
        driver.get("https://cookbook.seleniumacademy.com/Frames.html");
        // Switch to the frame with index 1
        driver.switchTo().frame(1);
        System.out.println(driver.findElement(By.tagName("p")).getText());
        driver.switchTo().defaultContent();
    }
}
