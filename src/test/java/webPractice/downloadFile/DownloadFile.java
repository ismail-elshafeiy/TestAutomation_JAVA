package webPractice.downloadFile;

import com.engine.Waits;
import com.engine.actions.BrowserActions;
import com.engine.actions.ElementActions;

import com.engine.actions.FileActions;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import practice.gui.pages.homePage.HomePage;
import practice.gui.pages.uploadFilePage.FileUploadPage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.engine.constants.FrameworkConstants.TIMEOUT_EXPLICIT;
import static com.engine.dataDriven.CSVFileManager.compareTwoCSVFiles2;


public class DownloadFile {
    private WebDriver driver;
    By downloadLink = By.linkText("CSVFile.csv");
    String downloadFilepath = System.getProperty("user.dir") + "/src/test/resources/downloadFiles/";

    @Test
    public void test1() {
        String imageName = "CSVFile.csv";
        String imagePath = System.getProperty("user.dir") + "/src/test/resources/TestData/" + imageName;
        new HomePage(driver).navigateToHomePage("https://the-internet.herokuapp.com/upload");
        new FileUploadPage(driver)
                .uploadFileBy_inputFile(imagePath)
                .clickUploadButton();
        Assert.assertEquals(imageName, FileUploadPage.getUploadedFiles_text(), "The Validation Message is incorrect");
    }

    @Test
    public void test2() throws InterruptedException {
        BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/download");
        ElementActions.click(driver, downloadLink);
        Thread.sleep(10000);
    }

    @Test
    public void test3() throws IOException {
        String imageName = "CSVFile.csv";
        String imagePath = downloadFilepath + imageName;
        new HomePage(driver).navigateToHomePage("https://the-internet.herokuapp.com/upload");
        new FileUploadPage(driver)
                .uploadFileBy_inputFile(imagePath)
                .clickUploadButton();
        Assert.assertEquals(imageName, FileUploadPage.getUploadedFiles_text(), "The Validation Message is incorrect");
        compareTwoCSVFiles2(imagePath, imagePath);
    }

    @NotNull
    private static Map<String, Object> getStringObjectMap(String downloadFilepath) {
        Map<String, Object> prefs = new HashMap<>();
        //This will set the path of the download folder
        prefs.put("download.default_directory", downloadFilepath);
        //To disable download popup
        prefs.put("download.prompt_for_download", false);
        prefs.put("--start-maximized", true);
        return prefs;
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Chrome Download path set to: " + downloadFilepath);
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = getStringObjectMap(downloadFilepath);
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        //BrowserActions.maximizeWindow(driver);
        Waits.implicitWait(driver, 30);
    }


    @AfterMethod
    public void afterMethod() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

}
