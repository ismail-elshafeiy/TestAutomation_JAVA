package testCases.gui.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;


public class SliderTests {
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
    public void testSlideToWholeNumber() {
        driver.get("http://www.globalsqa.com/");

        int size = driver.findElements(By.xpath("//ul/li/img")).size();
        System.out.println(size);

        for ( int i = 1; i < size; i++ ) {

            driver.findElement(By.xpath("//ul/li[" + i + "]/img")).click();
            System.out.println(driver.findElements(By.id("gallery")).size());

        }
    }
}