package testCases.gui.actions;

import gui.pages.homePage.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

public class DragAndDrop_Test {

    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
    }

    @AfterMethod(enabled = false)
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }


    @Test
    public void DragDrop() {
        new HomePage(driver).navigateTo_homePage("https://jqueryui.com/resources/demos/droppable/default.html");
        WebElement source = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("droppable"));


        Actions actions = new Actions(driver);


        actions.dragAndDrop(source, target).build().perform();

        Assert.assertEquals("Dropped!", target.getText());

    }

}
