package examples.gui.web.dataDriven;

import com.practice.gui.pages.homePage.HomePage;
import com.practice.gui.pages.inputs.SecureAreaPage;
import engine.dataDriven.ExcelFileManager;
import engine.dataDriven.ExcelFileManager1;
import engine.dataDriven.DataProvider;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserFactory;

import static org.testng.Assert.assertTrue;

public class Login_ReadDataUsingExcel extends DataProvider {

	@Test (groups = "approach1")
	public void login_readDataFromExcelFile () {
		new HomePage(driver).navigateToHomePage()
				.clickFormAuthentication()
				.setUsername(excelFileTestDataReader1.getCellData("login Data2", "email1", "email"))
				.setPassword(excelFileTestDataReader1.getCellData("login Data2", "email1", "password"))
				.clickLoginButton();
		assertTrue(SecureAreaPage.getAlertText()
						.contains(excelFileTestDataReader1.getCellData("login data2", "email1", "expected result")),
				"Check Alert Message");
	}

	@Test (groups = "approach1")
	public void login_readDataFromExcelFile1 () {
		new HomePage(driver).navigateToHomePage()
				.clickFormAuthentication()
				.setUsername(ExcelFileManager.getCellData(2, "email"))
				.setPassword(ExcelFileManager.getCellData(2, "password"))
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
	private ExcelFileManager1 excelFileTestDataReader1;
	private ExcelFileManager excelFileTestDataReader;

	@BeforeClass
	public void setTestData () {
		//new ExcelFileManager(filePath);
		//ExcelFileManager.switchToSheet("Login Data2");
		excelFileTestDataReader1 = new ExcelFileManager1(filePath);
		//excelFileTestDataReader1.switchToSheet("Login data");
	}

	@BeforeMethod
	public void setup () {
		excelFileTestDataReader = new ExcelFileManager("src/main/resources/config.xlsx");
		ExcelFileManager.switchToSheet("setup");
		driver = BrowserFactory.getBrowser();
	}

	@AfterMethod (dependsOnGroups = "approach1" + "approach2" + "approach3")
	public void closeBrowser () {
		//new ExcelFileManager(filePath);

		//ExcelFileManager.switchToSheet("Login data");
		//ExcelFileManager.setCellData("Pass", 3, 1);
		driver.quit();
	}

}
