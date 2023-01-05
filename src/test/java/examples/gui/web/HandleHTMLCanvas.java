package examples.gui.web;


import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import java.io.File;
import java.io.IOException;


public class HandleHTMLCanvas {
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
     * Using Actions Class to perform click and hold to draw something in canvas Area
     *
     */
    @Test
    @Severity( SeverityLevel.CRITICAL )
    @Description( "draw in canvas Test Case" )
    @Epic( "Selenium Actions on Elements" )
    @Story( "Actions Tutorial" )
    public void TestHTMLCanvas() throws IOException {
        driver.get("https://cookbook.seleniumacademy.com/html5canvasdraw.html");
        WebElement drawList = driver.findElement(By.id("dtool"));
        WebElement canvas = driver.findElement(By.id("imageTemp"));
        Select drawTool = new Select(drawList);
        drawTool.selectByValue("pencil");
        Actions builder = new Actions(driver);
        builder.clickAndHold(canvas).moveByOffset(5, 60).moveByOffset(60, 5)
                .moveByOffset(- 5, - 60).moveByOffset(- 60, - 5).release().perform();
        // Takes ScreenShot
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File("\\src\\ScreenShots\\canvas.png"));
    }
}
