package testCases.gui.web.countList;

import com.practice.gui.pages.homePage.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import java.util.List;

public class MenuCount_Test {
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
        new HomePage(driver).navigateToHomePage()
                .getAll_links_homePage();
    }

    @Test
    public void printLinks_HomePage_1() {
        new HomePage(driver).navigateToHomePage()
                .printAll_Links_homePage_1();
    }

    @Test
    public void printLinks_HomePage_2() {
        new HomePage(driver).navigateToHomePage()
                .printAll_Links_homePage_2();
    }

    @Test
    public void clickOn_HomePage_2() {
        new HomePage(driver).navigateToHomePage()
                .clickOn_Link_homePage(5);
    }

    public void printLinkCount() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
        driver.findElement(By.linkText("Shifting Content")).click();
        driver.findElement(By.linkText("Example 1: Menu Element")).click();
        List<WebElement> menuItems = driver.findElements(By.tagName("li"));
        System.out.println("Number of menu elements: " + menuItems.size());
        driver.quit();
    }
}