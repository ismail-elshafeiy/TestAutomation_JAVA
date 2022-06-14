package testCases.gui.findElements;

import gui.pages.homePage.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

import java.util.List;

public class FindElementByTagName {

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
    public void Register() {
        new HomePage(driver).navigateTo_yourHomePage("http://the-internet.herokuapp.com/tables");
        WebElement table = driver.findElement(By.id("table"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        System.out.println(rows.size());
        System.out.println(rows.get(3).getText());

    }


}