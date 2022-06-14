package testCases.gui.countList;

import gui.pages.homePage.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

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
        new HomePage(driver).navigateTo_homePage()
                .getAll_links_homePage();

    }

    @Test
    public void printLinks_HomePage_1() {
        new HomePage(driver).navigateTo_homePage()
                .printAll_Links_homePage_1();

    }

    @Test
    public void printLinks_HomePage_2() {
        new HomePage(driver).navigateTo_homePage()
                .printAll_Links_homePage_2();

    }
    @Test
    public void clickOn_HomePage_2() {
        new HomePage(driver).navigateTo_homePage()
                .clickOn_Link_homePage(5);

    }








}