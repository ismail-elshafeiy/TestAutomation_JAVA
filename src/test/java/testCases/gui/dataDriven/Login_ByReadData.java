package testCases.gui.dataDriven;

import gui.pages.homePage.HomePage;
import gui.pages.inputs.SecureAreaPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;
import utilities.dataDriven.ExcelFileManager;
import utilities.dataDriven.ExcelFileManager_approach2;
import utilities.dataDriven.JSONFileManager;
import utilities.dataDriven.JSONFile_appr2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class Login_ByReadData {
    private WebDriver driver;
    private ExcelFileManager excelFileManager;
    private JSONFileManager jsonFileManager;

    private JSONFile_appr2 jsonFileAppr2;


    @BeforeMethod
    public void setup_BeforeMethod() throws FileNotFoundException {
        driver = BrowserFactory.getBrowser();
//        excelFileManager = new ExcelFileManager(new File("src/test/resources/TestData/TestData.xlsx"));

        jsonFileManager = new JSONFileManager("src/test/resources/TestData/TestData.json");

    }

    @AfterMethod(enabled = false)
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }


    // ExcelFileManager

    @Test
    public void testSuccessfulLogin() {
        String email = excelFileManager.getCellData("email", 2);
        String password = excelFileManager.getCellData("password", 2);
        String expectedResult_successMessage = excelFileManager.getCellData("expectedResult_successMessage", 2);

        new HomePage(driver).navigateTo_homePage()
                .clickFormAuthentication()
                .setUsername(email)
                .setPassword(password)
                .clickLoginButton();
        assertTrue(SecureAreaPage.getAlertText().contains(expectedResult_successMessage),
                "Check Alert Message");
    }

    // JSON filer Manager

    @Test
    public void testSuccessfulLogin2() {
        String email = jsonFileManager.getTestData("email");
        String password = jsonFileManager.getTestData("password");
        String expectedResult_successMessage = jsonFileManager.getTestData("expectedResult_successMessage");

        new HomePage(driver).navigateTo_homePage()
                .clickFormAuthentication()
                .setUsername(email)
                .setPassword(password)
                .clickLoginButton();
        assertTrue(SecureAreaPage.getAlertText().contains(expectedResult_successMessage),
                "Check Alert Message");
    }


    @Test
    public void testSuccessfulLogin3() {
        String email = String.valueOf(jsonFileAppr2.getDataFile("email"));
        String password = String.valueOf(jsonFileAppr2.getDataFile("password"));
//        String expectedResult_successMessage = jsonFileManager.getTestData("expectedResult_successMessage");

        new HomePage(driver).navigateTo_homePage()
                .clickFormAuthentication()
                .setUsername(email)
                .setPassword(password)
                .clickLoginButton();
//        assertTrue(SecureAreaPage.getAlertText().contains(expectedResult_successMessage),
//                "Check Alert Message");
    }


}
