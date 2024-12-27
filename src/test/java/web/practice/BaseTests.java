package web;

import com.engine.actions.BrowserActions;
import com.engine.driver.DriverHelper;
import com.engine.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTests {

	protected WebDriver driver;

	@BeforeMethod
	public void setUp () {
        driver = DriverFactory.getBrowser(DriverHelper.BrowserType.CHROME);
	}

	@AfterMethod
    public void tearDown() {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}

}
