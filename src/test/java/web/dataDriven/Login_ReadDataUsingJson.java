package web.dataDriven;

import com.practice.gui.pages.homePage.HomePage;
import com.practice.gui.pages.inputs.SecureAreaPage;
import engine.broswer.BrowserActions;
import engine.broswer.DriverFactory;
import engine.dataDriven.ExcelFileManager;
import engine.dataDriven.JSONFileManager;
import engine.dataDriven.JSONFileManager_Approach2;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class Login_ReadDataUsingJson {

	@Test
	public void testSuccessfulLogin_readDataFromJsonFile () {
		new HomePage(driver).navigateToHomePage()
				.clickFormAuthentication().setUsername(jsonFileManager.getTestData("email")).setPassword(jsonFileManager.getTestData("password")).clickLoginButton();
		assertTrue(SecureAreaPage.getAlertText().contains(jsonFileManager.getTestData("expectedResult_successMessage")), "Check Alert Message");
	}


	@Test
	public void testSuccessfulLogin3 () {
		String email = String.valueOf(jsonFileAppr2.getDataFile("email"));
		String password = String.valueOf(jsonFileAppr2.getDataFile("password"));
//        String expectedResult_successMessage = jsonFileManager.getTestData("expectedResult_successMessage");

		new HomePage(driver)
				.navigateToHomePage()
				.clickFormAuthentication()
				.setUsername(email)
				.setPassword(password).clickLoginButton();
//        assertTrue(SecureAreaPage.getAlertText().contains(expectedResult_successMessage),
//                "Check Alert Message");
	}

	private WebDriver driver;
	private JSONFileManager jsonFileManager;

	private JSONFileManager_Approach2 jsonFileAppr2;
	private ExcelFileManager excelFileTestDataReader;


	@BeforeClass
	public void setup_BeforeClass () {
		new ExcelFileManager("src/main/resources/config.xlsx");
		excelFileTestDataReader.switchToSheet("setup");
		jsonFileManager = new JSONFileManager("src/test/resources/TestData/TestData.json");
	}

	@BeforeMethod
	public void setup_BeforeMethod () {
		driver = DriverFactory.getBrowser();
	}

	@AfterMethod
	public void closeBrowser () {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}
}
