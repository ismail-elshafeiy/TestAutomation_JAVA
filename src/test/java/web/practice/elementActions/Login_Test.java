package web.practice.elementActions;

import com.engine.actions.BrowserActions;
import com.engine.dataDriven.ExcelFileManager;
import com.engine.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import web.practice.homePage.HomePage;
import web.practice.inputs.SecureAreaPage;

import static org.testng.Assert.assertTrue;

public class Login_Test {
    private WebDriver driver;
    private ExcelFileManager testDataFile;


    @BeforeMethod
    public void setup_BeforeMethod() {
		driver = DriverFactory.getBrowser();
        testDataFile = new ExcelFileManager("src/test/resources/TestData/TestData.xlsx", "login");
    }

    @AfterMethod(enabled = false)
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    @Test
    public void testSuccessfulLogin() {
        String email = ExcelFileManager.getCellData(2, "email");
        String password = ExcelFileManager.getCellData(2, "password");
        String expectedResult_successMessage = ExcelFileManager.getCellData(2, "expectedResult_successMessage");
		new HomePage(driver).navigateToHomePage()
				.clickFormAuthentication()
				.setUsername(email)
				.setPassword(password)
				.clickLoginButton();
		assertTrue(SecureAreaPage.getAlertText().contains(expectedResult_successMessage),
				"Check Alert Message");
	}


}
