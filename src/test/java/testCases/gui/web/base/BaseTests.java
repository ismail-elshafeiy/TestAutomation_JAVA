package testCases.gui.web.base;


import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
public class BaseTests {

	public WebDriver driver;

	@BeforeClass
	public void setUp () {
		driver = BrowserFactory.getBrowser();
	}


	@AfterMethod
	public void tearDown (ITestResult result) {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}

}
