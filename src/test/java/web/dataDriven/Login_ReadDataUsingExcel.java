package web.dataDriven;

import com.practice.gui.pages.homePage.HomePage;
import com.practice.gui.pages.inputs.SecureAreaPage;
import engine.broswer.BrowserHelper;
import engine.dataDriven.ExcelFileManager;;
import engine.dataDriven.DataProvider;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.DriverFactory;

import static org.testng.Assert.assertTrue;

public class Login_ReadDataUsingExcel extends DataProvider {

	@Test (groups = "approach1")
	public void login_readDataFromExcelFile () {
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

	@Test (groups = "approach1")
	public void login_readDataFromExcelFile1 () {
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

	@Test (groups = "approach1")
	public void login_readDataFromExcelFile2 () {
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

	@Test (dataProvider = "getExcelData")
	public void login_readDataFromExcelFile_approach3 (String userName, String password, String expectedResult_successMessage) {
		new HomePage(driver).navigateToHomePage()
				.clickFormAuthentication()
				.setUsername(userName)
				.setPassword(password)
				.clickLoginButton();
		assertTrue(SecureAreaPage.getAlertText()
						.contains(expectedResult_successMessage),
				"Check Alert Message");
	}

	private WebDriver driver;
	String filePath = "src/test/resources/TestData/LoginData.xlsx";
	//private ExcelFileManager1 excelFileTestDataReader1;
	private ExcelFileManager excelFileTestDataReader;

	@BeforeClass
	public void setTestEnvironment () {
		excelFileTestDataReader = new ExcelFileManager("src/main/resources/config.xlsx");
		ExcelFileManager.switchToSheet("setup");

	}

	@BeforeMethod
	public void setup () {
		driver = DriverFactory.getBrowser();
		excelFileTestDataReader = new ExcelFileManager(filePath);
	}

	@AfterMethod (dependsOnGroups = "approach1" + "approach2" + "approach3")
	public void closeBrowser () {
		driver.quit();
	}

}
