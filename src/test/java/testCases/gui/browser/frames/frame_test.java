package testCases.gui.frames;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

public class frame_test {
    private WebDriver driver;

    @BeforeMethod
    public void setup_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }



}
