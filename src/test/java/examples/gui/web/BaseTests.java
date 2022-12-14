package examples.gui.web;

import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTests {

	protected WebDriver driver;

	@BeforeMethod
	public void setUp () {
		driver = BrowserFactory.getBrowser();
	}

	@AfterMethod(enabled = false)
	public void tearDown (ITestResult result) {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}

}
