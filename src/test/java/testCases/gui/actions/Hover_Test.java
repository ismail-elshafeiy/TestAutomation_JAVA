package testCases.gui.actions;

import gui.pages.homePage.HomePage;
import gui.pages.actions.HoverPage;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Hover_Test {
    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();

    }

    @AfterMethod(enabled = false)
    public void closeBrowser(ITestResult result) {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    // TODO:  refactors method Code
    @Test
    public void hoverOf_element() {
        new HomePage(driver).navigateTo_yourHomePage("https://the-internet.herokuapp.com/hovers");
        new HoverPage(driver).hoverOverFigure(1);
        assertTrue(HoverPage.FigureCaption.isCaptionDisplayed(), "Caption not displayed");
        assertEquals(HoverPage.FigureCaption.getTitle(), "name: user1", "Caption title incorrect");
        assertEquals(HoverPage.FigureCaption.getLinkText(), "View profile", "Caption link text incorrect");
        assertTrue(HoverPage.FigureCaption.getLinkText().endsWith("/users/1"), "Link incorrect");

    }

    @Test
    public void mouseHover() {
        new HomePage(driver).navigateTo_yourHomePage("http://automationpractice.com/index.php");
        new HoverPage(driver)
                .hoverOfElement();

    }

    @Test
    public void mousehover() {
        Actions a = new Actions(driver);
        WebElement element = driver.findElement(By.linkText("Printed Chiffon Dress"));
        a.moveToElement(element).perform();
        driver.findElement(By.xpath("(//a[@data-id-product='7'])[1]//span")).click();

        a.contextClick(element);
    }

    Actions actions;
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Hover over an element Test Case")
    @Epic("Selenium Actions on Elements")
    @Story("Action Class")
    void hoverOverElement(){
        new HomePage(driver).navigateTo_yourHomePage("https://the-internet.herokuapp.com/hovers");
        actions = new Actions(driver);
        // Hover the mouse to an element by moving the mouse cursor to that element
        actions.moveToElement(driver.findElement(By.xpath("(//img[@alt='User Avatar'])[1]")))
                .moveToElement(driver.findElement(By.xpath("(//img[@alt='User Avatar'])[2]")))
                .moveToElement(driver.findElement(By.xpath("(//img[@alt='User Avatar'])[3]")))
                .build().perform();
    }
}
