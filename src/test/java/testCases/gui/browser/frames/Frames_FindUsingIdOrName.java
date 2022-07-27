package testCases.gui.browser.frames;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.Helper;
import utilities.Logger;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

public class Frames_FindUsingIdOrName {
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
     * Using driver.switchTo().frame() to handle frames
     * mainly used with iframe's ID , name , index or as WebElement
     *
     */
    @Test
    public void NestedFrames() {
        driver.get("https://cookbook.seleniumacademy.com/Frames.html");
        // prints out the number of frames in the WebPage
        System.out.println("There are " + driver.findElements(By.tagName("frame")).size() +
                " frames inside the Web page");
        // // Switch to the frame with specific Id ( left )
        driver.switchTo().frame("left");
        System.out.println(driver.findElement(By.tagName("p")).getText());
        driver.switchTo().defaultContent();
        // Switch to the frame with specific name ( right )
        driver.switchTo().frame("right");
        System.out.println(driver.findElement(By.tagName("p")).getText());
        driver.switchTo().defaultContent();
    }

    @Test
    public void TextAreaInIFrame() throws Throwable {
        driver.navigate().to("https://the-internet.herokuapp.com/iframe");
        // Switch to the Frame with id
        driver.switchTo().frame("mce_0_ifr");
        WebElement tinymceTextArea = driver.findElement(By.xpath("//body[@id='tinymce']"));
        // Clear the text in the Text Area
        tinymceTextArea.clear();
        Actions actions = new Actions(driver);
        // Send keys using Actions class to perform KeyDown SHIFT button and type in the text with UpperCase
        actions.keyDown(Keys.SHIFT).sendKeys(tinymceTextArea, "hello from the Text Area in the Iframe")
                .build().perform();
        // Returns back the default content
        driver.switchTo().defaultContent();
        // prints out a text from outside the frame
        System.out.println(driver.findElement(By.xpath("//div[@class='example']/h3")).getText());
    }
}
