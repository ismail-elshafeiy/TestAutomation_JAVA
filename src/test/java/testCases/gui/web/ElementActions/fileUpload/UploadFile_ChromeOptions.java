package testCases.gui.web.ElementActions.fileUpload;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

public class UploadFile_ChromeOptions {
    private WebDriver driver;


    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOption());
        driver.get("http://the-internet.herokuapp.com/download");
        driver.manage().window().maximize();
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }

    static String downloadPath = "src/test/resources/Downloads";

    public static ChromeOptions chromeOption() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default.content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadPath);
        options.setExperimentalOption("prefs", chromePrefs);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        return options;
    }


    @Test
    public void testDownloadFile() throws InterruptedException {
        driver.findElement(By.linkText("images.jpeg")).click();
        Thread.sleep(3000);
    }


}


