package examples.gui.web.dataDriven;

import com.practice.gui.pages.homePage.HomePage;
import com.practice.gui.pages.inputs.SecureAreaPage;
import engine.tools.io.ExcelFileManager;
import engine.tools.io.DataProvider;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserFactory;

import java.io.File;
import java.util.Objects;

import static engine.tools.io.ExcelFileManager.*;
import static org.testng.Assert.assertTrue;

public class Login_ReadDataUsingExcel extends DataProvider {

	@Test (groups = "approach1")
	public void login_readDataFromExcelFile () {
		new HomePage(driver).navigateToHomePage()
				.clickFormAuthentication()
				.setUsername(getCellData("email", 1))
				.setPassword(getCellData("password", 2))
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

	@BeforeClass
	public void setup_BeforeMethod () {
		new ExcelFileManager(new File(filePath));
		ExcelFileManager.switchToSheet("Login data");
	}

	@BeforeMethod
	public void setup () {
		driver = BrowserFactory.getBrowser();
	}

	@AfterMethod (dependsOnGroups = "approach1" + "approach2" + "approach3")
	public void closeBrowser () {
		new ExcelFileManager(new File(filePath));
		switchToSheet("Login data");
		setCellData("Pass", 3, 1);
		driver.quit();
	}

}
