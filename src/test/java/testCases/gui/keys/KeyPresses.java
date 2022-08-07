package testCases.gui.keys;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

public class KeyPresses {

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
     *
     * Using driver.SendKeys to Perform some keyboard special presses like Enter
     * ALT , CTRL or SHIFT
     * also can be used to perform series of keys for example A and CTRL key
     *
     */
    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Handling Different KeyPresses Test Case")
    @Epic("Selenium Actions on Elements")
    @Story("KeyPresses in selenium Tutorial")
    void KeyPress() {
        /*
            We can use Keys.chrod() if we want to press multiple keys simultaneously
            for example clicking CTRL and then S
            TextBox.sendKeys(Keys.chord(Keys.CONTROL),"S");
            Or
            TextBox.sendKeys("S",Keys.CONTROL)
         */
        driver.get("https://the-internet.herokuapp.com/key_presses");
        WebElement TextBox = driver.findElement(By.id("target"));
        WebElement TheUsedKeyPressLabel = driver.findElement(By.id("result"));

        TextBox.sendKeys(Keys.ARROW_DOWN);
        System.out.println("Pressing down");
        System.out.println(TheUsedKeyPressLabel.getText());

        TextBox.sendKeys(Keys.ARROW_LEFT);
        System.out.println("Pressing left");
        System.out.println(TheUsedKeyPressLabel.getText());

        TextBox.sendKeys(Keys.ARROW_RIGHT);
        System.out.println("Pressing right");
        System.out.println(TheUsedKeyPressLabel.getText());

        TextBox.sendKeys(Keys.ARROW_UP);
        System.out.println("Pressing up");
        System.out.println(TheUsedKeyPressLabel.getText());

        TextBox.sendKeys(Keys.BACK_SPACE);
        System.out.println("Pressing Backspace");
        System.out.println(TheUsedKeyPressLabel.getText());

        TextBox.sendKeys(Keys.CONTROL);
        System.out.println("Pressing CTRL");
        System.out.println(TheUsedKeyPressLabel.getText());

        TextBox.sendKeys(Keys.TAB);
        System.out.println("Pressing Tab");
        System.out.println(TheUsedKeyPressLabel.getText());

        TextBox.sendKeys(Keys.SPACE);
        System.out.println("Pressing space");
        System.out.println(TheUsedKeyPressLabel.getText());
    }
}
