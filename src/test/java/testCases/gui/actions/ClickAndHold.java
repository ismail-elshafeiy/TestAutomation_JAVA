package testCases.gui.actions;

import com.practice.gui.pages.homePage.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

public class ClickAndHold {
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
     * Using Actions Class to perform a Click on element and Hold it
     * then goes to specific location or and Release the Hold button
     * Mainly used in horizontal bars , Draw something in Canvas
     *
     */

    Actions actions;

    @Test
    public void ClickAndHold() {
        actions = new Actions(driver);
        new HomePage(driver).navigateTo_homePage("https://the-internet.herokuapp.com/horizontal_slider");
        // clicks on a horizontal slider and hold it and then slide it to x = 10px and y = 0
        actions.clickAndHold(driver.findElement(By.xpath("//input[@type='range']")))
                .moveByOffset(10, 0)
                .release()
                .build().perform();

    }
}
