package testCases.gui.browser.navigations;

import gui.pages.navigations.NavigationPages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

public class NavigationTests {
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
    public void testNavigator() {
        new NavigationPages(driver).navigateTo_homePage("https://the-internet.herokuapp.com/")
                .getWindowManager()
                .goBack()
                .refreshPage()
                .goForward()
                .goTo("https://github.com/ismail-elshafeiy");

    }


    @Test
    public void testSwitchTab() {
        new NavigationPages(driver).navigateTo_homePage("https://the-internet.herokuapp.com/")
                .getWindowManager()
                .switchToTab("New Window");
    }

    @Test
    public void driverNavigationCommand() {
        // Go to the URL using driver.get
        driver.get("https://the-internet.herokuapp.com");
        // Go to the URL using driver.navigate.to
        driver.navigate().to("https://the-internet.herokuapp.com/login");
        // Perform Back button on the browser
        driver.navigate().back();
        // Perform Forward button on the browser
        driver.navigate().forward();
        // Perform Refresh (F5) Button on the browser
        driver.navigate().refresh();
        // Send value to a textbox
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        // Clear the value in the textbox
        driver.findElement(By.id("password")).clear();
        // Send value to a textbox
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        // Click on an element
        driver.findElement(By.cssSelector("button.radius")).click();
    }


}