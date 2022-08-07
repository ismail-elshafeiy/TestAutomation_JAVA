package testCases.gui.findElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import java.util.List;

public class RelativeLocators {

    private WebDriver driver;

    /*
    * Notes:
    * - RelativeBy is a class that extends the ByClass
    * - You can use Relative Locators to get a list of elements
    * - User "getBoundingClientRect()" to return a DOMRect object
    * */



    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    @Test
    public void testRelativeLocator() {
        driver.get("https://opensource-demo.orangehrmlive.com/");
        WebElement loginPanel = driver.findElement(By.id("logInPanelHeading"));
        WebElement credentials = driver.findElement(RelativeLocator.with(
                        By.tagName("span"))
                .above(loginPanel));
        System.out.println(credentials.getText());
    }

    @Test
    public void testListOfElements() {
        List<WebElement> allSocialMedia = driver.findElements(with(
                By.tagName("img"))
                .near(By.id("footer")));

        for (WebElement socialMedia : allSocialMedia) {
            System.out.println(socialMedia.getAttribute("alt"));
        }
    }
}