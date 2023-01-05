package examples.gui.web.dataDriven;

import com.practice.gui.pages.homePage.HomePage;
import com.practice.gui.pages.inputs.SecureAreaPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;
import engine.tools.io.ExcelFileManager;
import engine.tools.io.JSONFileManager;
import engine.tools.io.JSONFileManager_Approach2;

import java.io.FileNotFoundException;

import static org.testng.Assert.assertTrue;

public class Login_ByReadData {

    @Test
    public void testSuccessfulLogin() {
        String email = excelFileManager.getCellData("email", 2);
        String password = excelFileManager.getCellData("password", 2);
        String expectedResult_successMessage = excelFileManager.getCellData("expectedResult_successMessage", 2);

        new HomePage(driver).navigateToHomePage()
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

        new HomePage(driver).navigateToHomePage()
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

        new HomePage(driver).navigateToHomePage()
                .clickFormAuthentication()
                .setUsername(email)
                .setPassword(password)
                .clickLoginButton();
//        assertTrue(SecureAreaPage.getAlertText().contains(expectedResult_successMessage),
//                "Check Alert Message");
    }

    private WebDriver driver;
    private ExcelFileManager excelFileManager;
    private JSONFileManager jsonFileManager;

    private JSONFileManager_Approach2 jsonFileAppr2;

    @BeforeMethod
    public void setup_BeforeMethod() throws FileNotFoundException {
        driver = BrowserFactory.getBrowser();
//        excelFileManager = new ExcelFileManager(new File("src/test/resources/TestData/TestData.xlsx"));
        jsonFileManager = new JSONFileManager("src/test/resources/TestData/TestData.json");
    }

    @AfterMethod( enabled = false )
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }


}
