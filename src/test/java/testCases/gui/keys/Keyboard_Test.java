package testCases.gui.keys;

import gui.pages.homePage.HomePage;
import gui.pages.keys.KeyPressesPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

import static org.testng.Assert.assertEquals;

public class Keyboard_Test {

    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
        new HomePage(driver).navigateTo_homePage("https://the-internet.herokuapp.com/key_presses");
    }

    @AfterMethod(enabled = false)
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    @Test
    public void testBackspace() {
        String getResult = new KeyPressesPage(driver)
                .enterText("A")
                .getResult();
        assertEquals(getResult, "You entered: BACK_SPACE", "Input result incorrect");
    }
}
