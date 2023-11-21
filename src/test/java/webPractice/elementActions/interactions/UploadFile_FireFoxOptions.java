package webPractice.elementActions.interactions;

import com.engine.actions.BrowserActions;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Test;
import webPractice.BaseTests;

public class UploadFile_FireFoxOptions extends BaseTests {

    static String downloadPath = "src/test/resources/Downloads";

    public static FirefoxOptions firefoxOption() {
        FirefoxOptions option = new FirefoxOptions();
        option.addPreference("browser.download.folderList", 2);
        option.addPreference("browser.download.dir", downloadPath);
        option.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
        option.addPreference("browser.download.manager.showWhenStarting", false);
        return option;
    }
    @Test
    public void TestDownloadFile() throws InterruptedException {
        BrowserActions.navigateToUrl(driver, "http://the-internet.herokuapp.com/download");
        driver.findElement(By.linkText("some-file.txt")).click();
        Thread.sleep(3000);
    }
}
