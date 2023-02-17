package examples.gui.web;

import engine.broswer.BrowserActions;
import engine.broswer.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTests {

	protected WebDriver driver;

	@BeforeMethod
	public void setUp () {
		driver = DriverFactory.getBrowser();
	}

	@AfterMethod
	public void tearDown (ITestResult result) {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}

}
