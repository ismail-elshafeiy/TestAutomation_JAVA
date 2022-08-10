package testCases.gui.web.browser;


import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

public class JQueryMenus {
    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    Actions actions;
    WebDriverWait wait;

    /*
     *
     * Using Waits and sync also Actions class to handle JQuery dropdowns
     * Mainly used with Jquery dropdowns that takes time to load and also don't need to be clicked
     * to open the next dropdown
     *
     */

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Handling Jquery dropdown Test Case")
    @Epic("Selenium Actions on Elements")
    @Story("Jquery Tutorial")
    void JQueryMenu() {
        /*
         *  JQuery UI Menus are a nice UI element from a user perspective
         *  but poses an interesting automation challenge since it requires mouse operations and synchronization between them.
         *  Another 'fun' aspect is that the visibility of elements is actually not in the html itself,
         *  but done magically by JQuery
         */
        driver.get("https://the-internet.herokuapp.com/jqueryui/menu");
        actions = new Actions(driver);
//        wait = new WebDriverWait(driver, 10);
        // move the mouse cursor to Enabled
        actions.moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Enabled')]"))).perform();
        // waits for 5 seconds until the next JQuery menu appears and move the cursor to Downloads
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'Downloads')]"))));
        actions.moveToElement(driver.findElement(By.xpath("//a[contains(text(),'Downloads')]"))).perform();
        // waits for 5 seconds until the JQuery to generate the menu and moves to PDF option
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'PDF')]"))));
        actions.moveToElement(driver.findElement(By.xpath("//a[contains(text(),'PDF')]"))).perform();
    }
}
