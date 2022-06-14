package testCases.gui.actions;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

public class KeyboardEvents_test {

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
     * Using Actions class to perform Keyboard and mouse actions such as insert value or text
     * or clicking on Element
     * Mainly used with TextBoxes and clickable elements
     *
     */
    Actions actions;

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Performing KeyBoard actions Test Case")
    @Epic("Selenium Actions on Elements")
    @Story("Action Class")
    void SendingKeysAndCLick() {
        driver.get("https://the-internet.herokuapp.com/login");
        actions = new Actions(driver);
        // send value to textbox using sendKeys of actions class
        actions.sendKeys(driver.findElement(By.id("username")), "tomsmith")
                .perform();
        // some textbox needs the user to click on them first before sending text to them to make them enabled
        // when we have more than one action like clicking and sending text we should build before perform
        actions.click(driver.findElement(By.id("password")))
                .sendKeys("SuperSecretPassword!")
                .build().perform();
        // moveToElement moves the cursor to the center of the element and then clicks it by using click
        actions.moveToElement(driver.findElement(By.cssSelector("button.radius"))).click()
                .build().perform();
    }
}
