package testCases.gui.findElements;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

public class ElementPosition {
    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
        driver.get("https://testautomationu.applitools.com/learningpaths.html");
        System.out.println("Title: " + driver.getTitle());
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    /*
     * Notes:
     * - getRect method. It returns a position and dimension of an element = 2 methods: getLocation and getSize.
     * - position (x,y) --> X = horizontally from the left to the right, Y = vertically from the top to the bottom.
     * - get position from DOM by  open Properties to see the location.
     * - dimension (width, height) --> measure the size of an element.
     * - get dimension from DOM by hover on element.
     *
     * */

    @Test
    public void getPositionDimension() {
        WebElement logoTAU = driver.findElement(By.xpath("//div[@id='app']//header/a/img"));
        Rectangle rectTAULogo = logoTAU.getRect();
        System.out.println("x: " + rectTAULogo.getX());
        System.out.println("y: " + rectTAULogo.getY());
        System.out.println("Width: " + rectTAULogo.getWidth());
        System.out.println("Height: " + rectTAULogo.getHeight());
    }

}
