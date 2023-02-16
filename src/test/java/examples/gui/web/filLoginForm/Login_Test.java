package examples.gui.web.filLoginForm;

import com.practice.gui.pages.homePage.HomePage;
import com.practice.gui.pages.inputs.SecureAreaPage;
import engine.dataDriven.ExcelFileManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Login_Test {
    private WebDriver driver;
    private ExcelFileManager testDataFile;


    @BeforeMethod
    public void setup_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
        testDataFile = new ExcelFileManager("src/test/resources/TestData/TestData.xlsx");
        testDataFile.switchToSheet("login");
    }

    @AfterMethod(enabled = false)
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    @Test
    public void testSuccessfulLogin() {
		String email = testDataFile.getCellData(2, "email");
		String password = testDataFile.getCellData(2, "password");
		String expectedResult_successMessage = testDataFile.getCellData(2, "expectedResult_successMessage");

		new HomePage(driver).navigateToHomePage()
				.clickFormAuthentication()
				.setUsername(email)
				.setPassword(password)
				.clickLoginButton();
		assertTrue(SecureAreaPage.getAlertText().contains(expectedResult_successMessage),
				"Check Alert Message");
	}


}
