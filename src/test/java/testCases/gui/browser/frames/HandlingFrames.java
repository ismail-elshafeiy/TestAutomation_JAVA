package Course.A3_workingWithSeleniumAPI;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class S5_HandlingFrames {

    WebDriver driver;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.navigate().to("http://cookbook.seleniumacademy.com/Frames.html");
        driver.manage().window().maximize();
    }

    @Test
    public void testHandlingFramesWithIdOrName() {
        driver.switchTo().frame("left");

        // Before find element should be switch to the frame first
        WebElement msgL = driver.findElement(By.tagName("p"));
        Assert.assertEquals(msgL.getText(), "This is Left Frame");
        System.out.println(msgL.getText());

        // to go back instead still in the same frame
        driver.switchTo().defaultContent();

        // go to another frame from default Content
        driver.switchTo().frame("right");
        WebElement msgR = driver.findElement(By.tagName("p"));
        Assert.assertEquals(msgR.getText(), "This is Right Frame");
        System.out.println(msgR.getText());
        driver.switchTo().defaultContent();

        // go to another frame from default Content
        driver.switchTo().frame(1);
        WebElement msgS = driver.findElement(By.tagName("p"));
        Assert.assertEquals(msgS.getText(), "This Frame doesn't have id or name]");
        System.out.println(msgS.getText());
        driver.switchTo().defaultContent();

    }
}
