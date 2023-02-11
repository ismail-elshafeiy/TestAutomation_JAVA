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

import java.io.File;
import java.util.Objects;

import static engine.dataDriven.ExcelFileManager.getCellData;
import static engine.dataDriven.ExcelFileManager1.*;
import static org.testng.Assert.assertTrue;

public class Login_ReadDataUsingExcel extends DataProvider {

	@Test (groups = "approach1")
	public void login_readDataFromExcelFile () {
		new HomePage(driver).navigateToHomePage()
				.clickFormAuthentication()
				.setUsername(excelFileTestDataReader.getCellData1("tomsmith", "password"))
				.setPassword(excelFileTestDataReader.getCellData1("tomsmith2", "password"))
				.clickLoginButton();
		assertTrue(SecureAreaPage.getAlertText()
						.contains(Objects.requireNonNull(getCellData("expectedResult_successMessage", 2))),
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
	String filePath = "src/test/resources/TestData/loginData.xlsx";
	private ExcelFileManager1 excelFileTestDataReader;

	@BeforeClass
	public void setup_BeforeMethod () {
		new ExcelFileManager("src/main/resources/config.xlsx");
		ExcelFileManager.switchToSheet("setup");
		excelFileTestDataReader = new ExcelFileManager1(filePath);
		excelFileTestDataReader.switchToSheet("Login data");
	}

	@BeforeMethod
	public void setup () {
		driver = BrowserFactory.getBrowser();
	}

	@AfterMethod (dependsOnGroups = "approach1" + "approach2" + "approach3")
	public void closeBrowser () {
		new ExcelFileManager(filePath);

		ExcelFileManager.switchToSheet("Login data");
		ExcelFileManager.setCellData("Pass", 3, 1);
		driver.quit();
	}

}
