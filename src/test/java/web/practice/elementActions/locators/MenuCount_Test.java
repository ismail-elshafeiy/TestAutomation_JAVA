package web.practice.elementActions.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import web.practice.base.BaseTests;
import web.practice.homePage.HomePage;

import java.util.List;

public class MenuCount_Test extends BaseTests {
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