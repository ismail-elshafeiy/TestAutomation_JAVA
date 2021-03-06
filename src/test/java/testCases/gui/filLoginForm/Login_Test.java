package testCases.gui.filLoginForm;

import gui.pages.homePage.HomePage;
import gui.pages.inputs.SecureAreaPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;
import utilities.dataDriven.ExcelFileManager;

import java.io.File;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Login_Test {
    private WebDriver driver;
    private ExcelFileManager testDataFile;


    @BeforeMethod
    public void setup_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
        testDataFile = new ExcelFileManager(new File ("src/test/resources/TestData/TestData.xlsx"));
        testDataFile.switchToSheet("login");
    }

    @AfterMethod(enabled = false)
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    @Test
    public void testSuccessfulLogin() {
        String email= testDataFile.getCellData("email",2);
        String password = testDataFile.getCellData("password",2);
        String expectedResult_successMessage = testDataFile.getCellData("expectedResult_successMessage",2);

        new HomePage(driver).navigateTo_homePage()
                .clickFormAuthentication()
                .setUsername(email)
                .setPassword(password)
                .clickLoginButton();
        assertTrue(SecureAreaPage.getAlertText().contains(expectedResult_successMessage),
                "Check Alert Message");
    }


}
