package testCases.gui.web.actions;

import com.practice.gui.pages.homePage.HomePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;
@Feature("web")
@Epic("Element Actions")
public class DragAndDrop {

    private WebDriver driver;

    @Test
    public void DragDrop() {
        new HomePage(driver).navigateToHomePage("https://jqueryui.com/resources/demos/droppable/default.html");
        WebElement source = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("droppable"));
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).build().perform();
        Assert.assertEquals("Dropped!", target.getText());
    }
    @BeforeMethod
    public void setUp_BeforeMethod () {
        driver = BrowserFactory.getBrowser();
    }

    @AfterMethod(enabled = false)
    public void closeBrowser () {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }
}
