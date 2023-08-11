package visualTest;

import com.practice.gui.pages.visualPage.SearchPage;
import junit.framework.Assert;
import org.openqa.selenium.By;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchTests extends BaseTests {

    private SearchPage page = new SearchPage(driver);

    @BeforeClass
    public static void launchApp(){
        driver.get(System.getProperty("site.bookstore.url"));
    }

    @Test
    public void testSearchByFullTitle(){
        String title = "Agile Testing";
        page.search(title);
        eyesManager.validateWindow();
    }

    @Test
    public void testSearchByFullTitle_Element(){
        String title = "Agile Testing";
        page.search(title);
        eyesManager.validateElement(By.id("pid3"));
        Assert.assertEquals("Number of books returned",
                1, page.getNumberOfVisibleBooks());
    }
}