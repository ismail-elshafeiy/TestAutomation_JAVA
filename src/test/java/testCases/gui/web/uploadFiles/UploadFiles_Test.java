package testCases.gui.web.uploadFiles;

import com.practice.gui.pages.homePage.HomePage;
import com.practice.gui.pages.uploadFilePage.FileUploadPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import java.awt.*;

import static com.practice.gui.pages.uploadFilePage.FileUploadPage.fileUploader_dragDrop;
import static com.practice.gui.pages.uploadFilePage.FileUploadPage.uploadedFiles_text;

public class UploadFiles_Test {

    private WebDriver driver;


    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
    }

    @AfterMethod(enabled = false)
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    @Test
    public void setUploadedFile_inputField() throws InterruptedException {
        String imageName = "uploadPic.jpg";
        String imagePath = System.getProperty("user.dir") + "/src/test/resources/Uploads/" + imageName;
        new HomePage(driver).navigateToHomePage("https://the-internet.herokuapp.com/upload");
        new FileUploadPage(driver)
                .uploadFileBy_inputFile(imagePath)
                .clickUploadButton();
        Assert.assertEquals(imageName, FileUploadPage.getUploadedFiles_text(), "The Validation Message is incorrect");
        Assert.assertTrue(FileUploadPage.getUploadedFiles_text().contains(imageName));
        Assert.assertTrue((driver.findElement(uploadedFiles_text).isDisplayed()));
    }


    @Test
    public void FileUploadWithRobot() throws InterruptedException, AWTException {
        String imageName = "uploadPic.jpg";
        String imagePath = "C:\\Users\\ismail\\Automation Projects\\Projects - Ismail_Elshafeiy\\Practice_TestAutomation_SeleniumWebDriver\\src\\test\\resources\\Uploads\\" + imageName;
        new HomePage(driver).navigateToHomePage("https://the-internet.herokuapp.com/upload");
        new FileUploadPage(driver).clickUpload_dragDropArea()
                .uploadFileBy_robot(imagePath);

        Assert.assertTrue(driver.findElement(fileUploader_dragDrop).getText().contains(imageName));
        Assert.assertTrue((driver.findElement(fileUploader_dragDrop).isDisplayed()));
    }



}













