package webPractice.downloadFile;

import com.engine.Waits;
import com.engine.actions.BrowserActions;
import com.engine.actions.ElementActions;

import com.engine.actions.FileActions;
import com.engine.driver.DriverFactory;
import com.engine.driver.DriverHelper;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class DownloadFile {
    private WebDriver driver;
    By downloadLink = By.linkText("CSVFile.csv");
    String downloadFilepath = System.getProperty("user.dir") + "/src/test/resources/downloadFiles/";
    String downloadFilepath2 = System.getProperty("user.dir") + "/src/test/resources/downloadFiles2/";

//    @Test
//    public void test1() {
//        String imageName = "CSVFile.csv";
//        String imagePath = System.getProperty("user.dir") + "/src/test/resources/TestData/" + imageName;
//        new HomePage(driver).navigateToHomePage("https://the-internet.herokuapp.com/upload");
//        new FileUploadPage(driver)
//                .uploadFileBy_inputFile(imagePath)
//                .clickUploadButton();
//        Assert.assertEquals(imageName, FileUploadPage.getUploadedFiles_text(), "The Validation Message is incorrect");
//    }

    @Test
    public void test2() throws InterruptedException {
        driver = DriverFactory.getBrowser(DriverHelper.BrowserType.CHROME);
        BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/download");
        ElementActions.click(driver, By.linkText("sample.pdf"));
        Thread.sleep(10000);
        FileActions.getInstance().doesFileExist(downloadFilepath + "sample.pdf");
    }
    @Test
    public void test3() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = getStringObjectMap(downloadFilepath2);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        Waits.implicitWait(driver, 30);
        BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/download");
        ElementActions.click(driver, By.linkText("text.txt"));
        Thread.sleep(10000);
    }

    @Test
    public void test4() throws InterruptedException {

    }

//    @Test
//    public void tes4() throws IOException {
//        String imageName = "CSVFile.csv";
//        String imagePath = downloadFilepath + imageName;
//        new HomePage(driver).navigateToHomePage("https://the-internet.herokuapp.com/upload");
//        new FileUploadPage(driver)
//                .uploadFileBy_inputFile(imagePath)
//                .clickUploadButton();
//        Assert.assertEquals(imageName, FileUploadPage.getUploadedFiles_text(), "The Validation Message is incorrect");
//        compareTwoCSVFiles2(imagePath, imagePath);
//    }

    @NotNull
    private static Map<String, Object> getStringObjectMap(String downloadFilepath) {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFilepath);
        prefs.put("download.prompt_for_download", false);
        return prefs;
    }

//    @BeforeMethod
//    public void beforeMethod() {
//        WebDriverManager.chromedriver().setup();
//        ChromeOptions options = new ChromeOptions();
//        Map<String, Object> prefs = getStringObjectMap(downloadFilepath);
//        options.setExperimentalOption("prefs", prefs);
//        driver = new ChromeDriver(options);
//        BrowserActions.maximizeWindow(driver);
//        Waits.implicitWait(driver, 30);
//    }


    @AfterMethod
    public void afterMethod() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

}
