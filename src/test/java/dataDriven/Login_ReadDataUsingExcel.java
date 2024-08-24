package dataDriven;

import com.engine.dataDriven.DataProvider;
import com.engine.dataDriven.ExcelFileManager;
import com.engine.driver.DriverFactory;
import io.qameta.allure.Allure;
import practice.gui.pages.homePage.HomePage;
import practice.gui.pages.inputs.SecureAreaPage;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.testng.Assert.assertTrue;

public class Login_ReadDataUsingExcel {
    String epicName = "Login";

    private WebDriver driver;
    String filePath = "src/test/resources/TestData/LoginData.xlsx";
    //private ExcelFileManager1 excelFileTestDataReader1;
    private ExcelFileManager excelFileTestDataReader;

    @Test(groups = "approach1")
    public void login_readDataFromExcelFile() {
        Allure.epic(epicName);
        ExcelFileManager.switchToSheet("Login Data2");
        new HomePage(driver).navigateToHomePage()
                .clickFormAuthentication()
                .setUsername(ExcelFileManager.getCellData("email1", "email"))
                .setPassword(ExcelFileManager.getCellData("email1", "password"))
                .clickLoginButton();
        assertTrue(SecureAreaPage.getAlertText()
                        .contains(ExcelFileManager.getCellData("email1", "expected result")),
                "Check Alert Message");
    }

    @Test(groups = "approach1")
    public void login_readDataFromExcelFile1() {
        ExcelFileManager.switchToSheet("Login Data2");
        new HomePage(driver).navigateToHomePage()
                .clickFormAuthentication()
                .setUsername(ExcelFileManager.getCellData(2, "email"))
                .setPassword(ExcelFileManager.getCellData(2, "password"))
                .clickLoginButton();
        assertTrue(SecureAreaPage.getAlertText()
                        .contains(ExcelFileManager.getCellData(2, "expected result")),
                "Check Alert Message");
    }

    @Test(groups = "approach1")
    public void login_readDataFromExcelFile2() {
        ExcelFileManager.switchToSheet("Login Data2");
        new HomePage(driver).navigateToHomePage()
                .clickFormAuthentication()
                .setUsername(ExcelFileManager.getCellData(2, 2))
                .setPassword(ExcelFileManager.getCellData(2, 3))
                .clickLoginButton();
        assertTrue(SecureAreaPage.getAlertText()
                        .contains(ExcelFileManager.getCellData(2, "expected result")),
                "Check Alert Message");
    }


    @BeforeClass
    public void setTestEnvironment() {
        excelFileTestDataReader = new ExcelFileManager("src/main/resources/config.xlsx");
        ExcelFileManager.switchToSheet("setup");

    }

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getBrowser();
        excelFileTestDataReader = new ExcelFileManager(filePath);
    }

    @AfterMethod(dependsOnGroups = "approach1" + "approach2" + "approach3")
    public void closeBrowser() {
        driver.quit();
    }

}
