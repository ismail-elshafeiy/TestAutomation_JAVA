package testCases.gui.findElements;

import com.practice.gui.pages.homePage.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

public class FindLinksByTest {
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
    public void textLinkText() {
        WebElement seleniumLink = driver.findElement(By.linkText("Elemental Selenium"));
        System.out.println("The Link = " + seleniumLink.getAttribute("href"));
    }

    @Test
    public void textPartialText() {
        WebElement seleniumLink = driver.findElement(By.partialLinkText("Elemental"));
        System.out.println("The Link 2 = " + seleniumLink.getAttribute("href"));
    }

    @AfterTest
    public void closeDriver() {
        driver.quit();
    }


}
