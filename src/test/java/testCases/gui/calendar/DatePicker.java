package testCases.gui.calendar;

import com.practice.gui.pages.homePage.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

public class DatePicker {
    /*
     * to handle a Date Picker if it has a tag name as input we can send the date with the right format "mm/dd/yyyy"
     * DatePicker.sendKeys("01/01/2021");
     */

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
    public void countLinks_HomePage() {
        new HomePage(driver).navigateToHomePage("https://formy-project.herokuapp.com/datepicker");
        WebElement DatePicker = driver.findElement(By.id("datepicker"));
        DatePicker.sendKeys("18/12/2021", Keys.ENTER);
    }
}
