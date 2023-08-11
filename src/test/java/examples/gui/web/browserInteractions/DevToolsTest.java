package examples.gui.web.browserInteractions;

import engine.broswer.BrowserActions;
import engine.gui.actions.ElementActions;
import engine.listeners.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DevToolsTest {
	ChromeDriver driver;
	DevTools devTools;
	@Test
	public void testConsole () throws Throwable {
		BrowserActions.navigateToUrl(driver, "http://the-internet.herokuapp.com/broken_images");
		ElementActions.click(driver, By.ByXPath.xpath("//a[contains(text(),'Broken Images')]"));
	}
//	@Test
//	public void viewBrowserConsoleLogs() {
//		Logger.logConsoleLogs(driver,"http://the-internet.herokuapp.com/broken_images");
//	}
	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		devTools = driver.getDevTools();
	}


	@AfterMethod
	public void tearDown_AfterMethod (ITestResult result) throws Throwable {
		BrowserActions.closeAllOpenedBrowserWindows(driver, result);
	}
}
