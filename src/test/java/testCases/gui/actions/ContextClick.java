package testCases.gui.actions;

import com.practice.gui.pages.homePage.HomePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

public class ContextClick {

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
     * Using Actions Class to perform context Click ( Right Mouse Click ) on Elements
     *
     */

    Actions actions;
    Alert alert;
    @Test
   public void contextClick() {
        actions = new Actions(driver);
        new HomePage(driver).navigateToHomePage("https://the-internet.herokuapp.com/context_menu");
        // perform context click ( right click ) on element
        actions.contextClick(driver.findElement(By.id("hot-spot")))
                .perform();
        // performing context click generates Alert
        // switching to the generated alert
        alert = driver.switchTo().alert();
        // accepts the generated alert
        alert.accept();
    }
}
