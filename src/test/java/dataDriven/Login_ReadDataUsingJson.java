package dataDriven;

import com.engine.actions.BrowserActions;
import com.engine.dataDriven.ExcelFileManager;
import com.engine.dataDriven.JSONFileManager;
import com.engine.driver.DriverFactory;
import com.engine.reports.CustomReporter;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import web.practice.homePage.HomePage;
import web.practice.inputs.SecureAreaPage;

import java.io.IOException;

import static com.engine.dataDriven.CSVFileManager.compareTwoCSVFilesByValue;
import static com.engine.dataDriven.CSVFileManager.readDataLineByLine;
import static com.engine.dataDriven.TextFileManager.*;
import static org.testng.Assert.assertTrue;

public class Login_ReadDataUsingJson {

	@Test
	public void testSuccessfulLogin_readDataFromJsonFile () {
		new HomePage(driver).navigateToHomePage()
				.clickFormAuthentication()
				.setUsername(jsonFileManager.getTestData("user.email"))
				.setPassword(jsonFileManager.getTestData("user.password"))
				.clickLoginButton();
		new BrowserActions(driver).capturePageSnapshot();
		assertTrue(SecureAreaPage.getAlertText().contains(jsonFileManager.getTestData("expectedResult_successMessage")), "Check Alert Message");

	}


	@Test
    public void testSuccessfulLogin3() throws IOException {
//		new CSVFileManager("src/test/resources/TestData/CSVFile.csv");
//		String text = String.valueOf(excelFileTestDataReader.getCellData());
//		CustomReporter.logConsole("text: " + text);
        readDataLineByLine("src/test/resources/TestData/CSVFile.csv");

		String email = String.valueOf(jsonFileManager.getTestData("user.email"));
		String password = String.valueOf(jsonFileManager.getTestData("user.password"));
//        String expectedResult_successMessage = jsonFileManager.getTestData("expectedResult_successMessage");
		setTextFile("src/test/resources/TestData/TestData.txt", email + "\n" + password + "\n");
		String line1 = getTextFile("src/test/resources/TestData/TestData.txt", 0);
		CustomReporter.logConsole("line1: " + line1);
		String textFile = getAllTextFile("src/test/resources/TestData/TestData.txt");
		CustomReporter.logConsole("textFile: " + textFile);
		new HomePage(driver)
				.navigateToHomePage()
				.clickFormAuthentication()
				.setUsername(email)
				.setPassword(password).clickLoginButton();
//        assertTrue(SecureAreaPage.getAlertText().contains(expectedResult_successMessage),
//                "Check Alert Message");
	}

    @Test
    public void testSuccessfulLogin2() throws IOException {
        String filePath = "src/test/resources/TestData/CSVFile.csv";
        String filePath2 = "src/test/resources/TestData/CSVFile2.csv";
        //new CSVFileManager("src/test/resources/TestData/CSVFile.csv");
//		String text = String.valueOf(excelFileTestDataReader.getCellData());
//		CustomReporter.logConsole("text: " + text);
        //readDataLineByLine("src/test/resources/TestData/CSVFile.csv");
        //compareTwoCSVFiles(filePath, filePath2);
        compareTwoCSVFilesByValue(filePath, filePath2);
    }

	private WebDriver driver;
	private JSONFileManager jsonFileManager;

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
