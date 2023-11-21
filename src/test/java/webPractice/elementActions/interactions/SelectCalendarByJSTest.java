package webPractice.elementActions.interactions;

import com.engine.actions.BrowserActions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import webPractice.BaseTests;

public class SelectCalendarByJSTest extends BaseTests {

    @Test
    public void selectDateByJS() {
        BrowserActions.navigateToUrl(driver,"http://spicejet.com/");
        WebElement date = driver.findElement(By.xpath("//div[@class='css-76zvg2 css-bfa6kz r-homxoj r-ubezar']"));
        String dateVal = "25-12-2022";
        selectDateByJS(driver, date, dateVal);
    }

    public static void selectDateByJS(WebDriver driver, WebElement element, String dateVal) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].setAttribute('value','" + dateVal + "');", element);
    }

}
