package SeleniumActions_tests;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class Tables extends TestBase {


    /*
     * Selecting an option or specific cell on the table can be performed
     * when selecting a specific date from a table calendar
     */
    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Handling Tables Test Case")
    @Epic("Selenium Actions on Elements")
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
