package testCases.gui.findElements;

import gui.pages.homePage.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

import java.util.List;

public class FindElements {
    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
        new HomePage(driver).navigateTo_homePage("http://the-internet.herokuapp.com/dropdown");
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }


    @Test
    public void list() {

        // get all Links displayed onm page
        List<WebElement> links = driver.findElements(By.tagName("a"));


        //verify there are 20 Links displayed on the page

        Assert.assertEquals(links.size(), 46);
        System.out.println("The number of elements = " + links.size());

        // Print each link value
        for (WebElement link : links) {
            System.out.println(link.getAttribute("href"));
        }
    }


    @AfterTest
    public void closeDriver() {
        driver.quit();
    }

}

