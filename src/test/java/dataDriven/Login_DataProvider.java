package dataDriven;

import com.engine.dataDriven.DataProvider;
import com.engine.dataDriven.ExcelFileManager;
import com.engine.driver.DriverFactory;
import com.engine.reports.ExtentReport;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import practice.gui.pages.homePage.HomePage;
import practice.gui.pages.inputs.SecureAreaPage;

import java.util.Hashtable;

import static org.testng.Assert.assertTrue;

public class Login_DataProvider {
    private WebDriver driver;
    private ExcelFileManager excelFileTestDataReader;
    String filePath = "src/test/resources/TestData/LoginData.xlsx";

    @Test(dataProvider = "getExcelData", dataProviderClass = DataProvider.class)
    public void login_readDataFromExcelFile_approach3(String userName, String password, String expectedResult_successMessage) {
        ExtentReport.createTest("Login Test");
        new HomePage(driver).navigateToHomePage()
                .clickFormAuthentication()
                .setUsername(userName)
                .setPassword(password)
                .clickLoginButton();
        assertTrue(SecureAreaPage.getAlertText().contains(expectedResult_successMessage),
                "Check Alert Message");
    }

    @Test(dataProvider = "getExcelData2", dataProviderClass = DataProvider.class)
    public void login_readDataFromExcelFile_approach4(Hashtable<String, String> data) {
        ExtentReport.createTest("Login Test - 4");
        new HomePage(driver).navigateToHomePage()
                .clickFormAuthentication()
                .setUsername(data.get("ema"))
                .setPassword(data.get("password"))
                .clickLoginButton();
        assertTrue(SecureAreaPage.getAlertText().contains(data.get("expectedResult_successMessage")),
                "Check Alert Message");
    }


    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getBrowser();
        excelFileTestDataReader = new ExcelFileManager(filePath);
    }

}
