package web.practice.elementActions.interactions;

import com.engine.actions.BrowserActions;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import web.practice.base.BaseTests;

import java.util.HashMap;

public class UploadFileChromeOptions extends BaseTests {
    static String downloadPath = "src/test/resources/Downloads";

    public static ChromeOptions chromeOption() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default.content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadPath);
        options.setExperimentalOption("prefs", chromePrefs);
//        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        return options;
    }


    @Test
    public void testDownloadFile() throws InterruptedException {
        BrowserActions.navigateToUrl(driver, "http://the-internet.herokuapp.com/download");
        driver.findElement(By.linkText("images.png")).click();
        Thread.sleep(3000);
    }


}


