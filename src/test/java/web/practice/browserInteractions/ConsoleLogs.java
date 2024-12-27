package web.practice.browserInteractions;

import com.engine.actions.BrowserActions;
import com.engine.actions.ElementActions;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Browser Interactions")
@Feature("Console Logs")
public class ConsoleLogs{
	ChromeDriver driver;
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
        //WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}


	@AfterMethod
	public void tearDown_AfterMethod (ITestResult result) throws Throwable {
		BrowserActions.closeAllOpenedBrowserWindows(driver, result);
	}
}
