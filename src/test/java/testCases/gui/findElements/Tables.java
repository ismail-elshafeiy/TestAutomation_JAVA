package testCases.gui.findElements;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import java.util.List;

public class Tables {
    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    /*
     * Selecting an option or specific cell on the table can be performed
     * when selecting a specific date from a table calendar
     */
    @Test
    @Story("Tables Tutorial")
    public void webTableTest() {
        driver.get("https://the-internet.herokuapp.com/tables");

        WebElement webTable = driver.findElement(By.id("table1"));
        // Get all rows at table 1 ( rows = tr )
        List<WebElement> rows = webTable.findElements(By.tagName("tr"));
        Assert.assertEquals(5, rows.size(), "The Rows number is incorrect");
        // Get all Cells data (cols and rows) ( cols = td )
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            for (WebElement col : cols) {
                System.out.println(col.getText() + "\t");
            }
            // To Print Empty line between each row
            System.out.println();
        }
    }
}
