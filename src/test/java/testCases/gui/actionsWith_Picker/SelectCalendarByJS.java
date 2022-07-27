package testCases.gui.actionsWith_Picker;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

public class SelectCalendarByJS {
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
    public void selectDateByJS() {
        driver.get("http://spicejet.com/"); // enter URL
        WebElement date = driver.findElement(By.xpath("//div[@class='css-76zvg2 css-bfa6kz r-homxoj r-ubezar']"));
        String dateVal = "30-12-2017";
        selectDateByJS(driver, date, dateVal);
    }

    public static void selectDateByJS(WebDriver driver, WebElement element, String dateVal) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].setAttribute('value','" + dateVal + "');", element);
    }
}
