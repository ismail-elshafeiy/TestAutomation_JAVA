package testCases.gui.takeScreenShot;

import com.practice.gui.pages.homePage.HomePage;
import engine.broswer.BrowserFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;

import java.io.File;
import java.io.IOException;


// TODO refactor Code
public class TakeScreenShots {
    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        WebDriverManager.firefoxdriver().setup();
//        driver = new FirefoxDriver(UploadFile_FireFoxOptions.firefoxOption());
        driver = BrowserFactory.getBrowser();
        new HomePage(driver).navigateTo_homePage("https://applitools.com/");
    }

    String filePath = System.getProperty("user.dir") + "/src/test/resources/TestsScreenshots/";

    @Test
    public void takeWebElement_Screenshot() throws IOException {
        WebElement nextGenerationPlatform = driver.findElement(By.cssSelector("#post-8 h1"));
        File source = nextGenerationPlatform.getScreenshotAs(OutputType.FILE);
        File destination = new File(filePath + "Next Generation Platform.png");
        FileHandler.copy(source, destination);
    }

    @Test
    public void takeWebElementPageSection_Screenshot() throws IOException {
        WebElement applitoolsPageSection = driver.findElement(By.cssSelector("#post-8>header"));
        File source = applitoolsPageSection.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File(filePath + "Applitools Page Section.png"));

    }

    @Test
    public void takeFullPage_Screenshot() throws IOException {
        File source = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.FILE);
        FileHandler.copy(source, new File(filePath + "Applitools Full Page Screenshot.png"));
    }

    @AfterMethod
    public void takeScreenShot(ITestResult result) throws IOException {
        // checks if the TestResult is failure
        if ( ITestResult.FAILURE == result.getStatus() ) {
            // Create reference of takesScreenShot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(source, new File("./ScreenShot_TestFails/" + result.getName() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }
}
