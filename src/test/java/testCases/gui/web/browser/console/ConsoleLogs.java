package testCases.gui.web.browser.console;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v101.log.Log;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;

public class ConsoleLogs {
    //    EdgeDriver driver;
    ChromeDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    @Test
    public void viewBrowserConsoleLogs() {
        // Get The DevTools & Create A Session
        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        // Enable The Console Logs
        devTools.send(Log.enable());

        // Add A Listener For The Logs
        devTools.addListener(Log.entryAdded(), logEntry -> {
            System.out.println("----------");
            System.out.println("Level: " + logEntry.getLevel());
            System.out.println("Text: " + logEntry.getText());
            System.out.println("Broken URL: " + logEntry.getUrl());
        });

        // Load The AUT
        driver.get("http://the-internet.herokuapp.com/broken_images");
    }

}
