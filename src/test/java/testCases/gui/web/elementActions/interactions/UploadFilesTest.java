package testCases.gui.web.elementActions.interactions;

import com.practice.gui.pages.homePage.HomePage;
import com.practice.gui.pages.uploadFilePage.FileUploadPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import testCases.gui.web.BaseTests;

import java.awt.*;

import static com.practice.gui.pages.uploadFilePage.FileUploadPage.fileUploader_dragDrop;
import static com.practice.gui.pages.uploadFilePage.FileUploadPage.uploadedFiles_text;

public class UploadFilesTest extends BaseTests {


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













