package web.practice.elementActions.interactions;

import com.engine.actions.BrowserActions;
import com.engine.actions.ElementActions;
import com.engine.constants.FrameworkConstants;
import com.engine.dataDriven.FakerData;
import com.engine.reports.ExtentReport;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import web.practice.base.BaseTests;
import web.practice.homePage.HomePage;
import web.practice.keys.KeyPressesPage;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

@Epic("ElementActions")
@Feature("Actions")
public class ElementActionBySelenium extends BaseTests {

    @Test
    @Description("Performing KeyBoard actions Test Case")
    public void sendingKeysAndCLick() {
        BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/login");
        new ElementActions(driver)
                .clickAndSendKeys(By.id("username"), "tomsmith")
                .typeEncrypt(By.id("password"), "SuperSecretPassword!")
                .hoverAndClick(By.cssSelector("button.radius"));
    }

    @Test
    public void RightClick() {
        BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/context_menu");
        ElementActions.rightClick(driver, By.id("hot-spot"));
        BrowserActions.alertAction(driver, BrowserActions.AlertAction.ACCEPT);
    }

    @Test
    public void testContextMenu2() throws Exception {
        String[][] data = {{"span.context-menu-one.btn.btn-neutral",
                "li.context-menu-item.context-menu-icon.context-menu-icon-edit",
                "Right Click on Menu Button and then click on Edit"},
        };
        Object data2 = new Object();
        data2 = data;
        BrowserActions.navigateToUrl(driver, "https://swisnl.github.io/jQuery-contextMenu/demo/accesskeys.html");
        By clickMenuBtn = By.cssSelector("span.context-menu-one.btn.btn-neutral");
        By contextMenu = By.cssSelector("li.context-menu-item.context-menu-icon.context-menu-icon-edit");
        ExtentReport.logCodeBlock("Right Click on Menu Button and then click on Edit" + ElementActions.class);
        ExtentReport.logJsonCodeBlock("Right Click on Menu Button and then click on Edit" + ElementActions.class);
        ExtentReport.logTable(data);
        ExtentReport.logOrderList(data2);
        ExtentReport.info(FrameworkConstants.ICON_Navigate_Right + "Test Navigate Right");
        ExtentReport.info(FrameworkConstants.ICON_BUG + "Test Bug");
        ExtentReport.info(FrameworkConstants.ICON_SMILEY_PASS + "Test Pass");
        ExtentReport.info(FrameworkConstants.ICON_SMILEY_FAIL + "Test Fail");
        ExtentReport.info(FrameworkConstants.ICON_SMILEY_SKIP + "Test Skip");
        ExtentReport.addSystemInfo("OS", FrameworkConstants.ICON_OS_WINDOWS + " Test OS");
        //ExtentReport.addTestRunnerOutput(FakerData.getStringLimit(5000));
        ArrayList<String> list = new ArrayList<>();
        list.add(FakerData.getFirstName());
        list.add(FakerData.getFirstName());
        list.add(FakerData.getFirstName());
        list.add(FakerData.getPhoneNumberLimit(122));
        ExtentReport.addTestRunnerOutput(list);
        ElementActions.rightClick(driver, clickMenuBtn);
        ElementActions.click(driver, contextMenu);
        BrowserActions.alertAction(driver, BrowserActions.AlertAction.ACCEPT);
    }

    @Test
    @io.qameta.allure.Description("""
            Handling Different KeyPresses Test Case
                      We can use Keys.chrod() if we want to press multiple keys simultaneously
                        for example clicking CTRL and then S
                        TextBox.sendKeys(Keys.chord(Keys.CONTROL),"S");
                        Or
                        TextBox.sendKeys("S",Keys.CONTROL)
            """)
    public void keyboardKey() {
        BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/key_presses");
        By TextBox = By.id("target");
        By TheUsedKeyPressLabel = By.id("result");
        ElementActions.keyPress(driver, TextBox, Keys.ARROW_DOWN);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: DOWN");
        ElementActions.keyPress(driver, TextBox, Keys.ARROW_UP);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: UP");
        ElementActions.keyPress(driver, TextBox, Keys.ARROW_LEFT);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: LEFT");
        ElementActions.keyPress(driver, TextBox, Keys.ARROW_RIGHT);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: RIGHT");
        ElementActions.keyPress(driver, TextBox, Keys.ENTER);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: ENTER");
        ElementActions.keyPress(driver, TextBox, Keys.ESCAPE);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: ESCAPE");
        ElementActions.keyPress(driver, TextBox, Keys.BACK_SPACE);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: BACK_SPACE");
        ElementActions.keyPress(driver, TextBox, Keys.DELETE);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: DELETE");
        ElementActions.keyPress(driver, TextBox, Keys.TAB);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: TAB");
        ElementActions.keyPress(driver, TextBox, Keys.SPACE);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: SPACE");
        ElementActions.keyPress(driver, TextBox, Keys.PAGE_DOWN);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: PAGE_DOWN");
        ElementActions.keyPress(driver, TextBox, Keys.PAGE_UP);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: PAGE_UP");
        ElementActions.keyPress(driver, TextBox, Keys.END);
        assertEquals(ElementActions.getText(driver, TheUsedKeyPressLabel), "You entered: END");
    }

    @Test
    public void Backspace() {
        BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/key_presses");
        String getResult = new KeyPressesPage(driver)
                .enterText("ismailaaaa")
                .getResult();
        assertEquals(getResult, "You entered: BACK_SPACE", "Input result incorrect");
    }

    @Test
    public void DoubleClick() {
        BrowserActions.navigateToUrl(driver, "http://cookbook.seleniumacademy.com/DoubleClickDemo.html");
        By box = By.id("message");
        assertEquals(ElementActions.getCssValue(driver, box, "background-color"), "rgba(0, 0, 255, 1)");
        ElementActions.doubleClick(driver, box);
        assertEquals(ElementActions.getCssValue(driver, box, "background-color"), "rgba(255, 255, 0, 1)");
    }

    @Test
    public void hoverAndHoverThenClick() {
        BrowserActions.navigateToUrl(driver, "https://demo.nopcommerce.com/");
        By computers = By.xpath("//ul[@class='top-menu notmobile']//a[contains(text(),'Computers')]");
        new ElementActions(driver)
                .hover(computers)
                .hoverAndClick(By.xpath("//ul[@class='top-menu notmobile']//a[contains(text(),'Desktops')]"));
    }


    @Test
    public void DragDropByLocator() {
        new HomePage(driver).navigateToHomePage("https://jqueryui.com/resources/demos/droppable/default.html");
        By source = By.id("draggable");
        By target = By.id("droppable");
        ElementActions.dragAndDrop(driver, source, target);
        Assert.assertEquals(driver.findElement(target).getText(), "Dropped!");
    }

    @Test
    public void dragAndDropByOffset() {
        BrowserActions.navigateToUrl(driver, "https://jqueryui.com/resources/demos/droppable/default.html");
        By sourceElement = By.id("draggable");    // Locator to the element you want to drag
        int xPos = 180;
        int yPos = 30;
        ElementActions.dragAndDrop(driver, sourceElement, xPos, yPos);
    }

    @Test
    @Description("""
             Using Actions Class to perform a Click on element and Hold it
             	then goes to specific location or and Release the Hold button
            	Mainly used in horizontal bars , Draw something in Canvas
            """)
    public void ClickAndHoldByLocator() {
        BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/horizontal_slider");
        ElementActions.clickAndHoldTo(driver, By.xpath("//input[@type='range']"), 10, 0);
    }

    @Test
    public void setDatePicker() {
        BrowserActions.navigateToUrl(driver, "https://formy-project.herokuapp.com/datepicker");
        By DatePicker = By.id("datepicker");
        new ElementActions(driver)
                .type(DatePicker, "01/01/2021")
                .keyPress(DatePicker, Keys.ENTER);
    }
}
