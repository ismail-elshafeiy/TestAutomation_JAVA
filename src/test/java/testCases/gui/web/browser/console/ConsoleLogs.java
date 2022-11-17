package testCases.gui.web.browser.console;

import com.practice.gui.pages.homePage.HomePage;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;
import engine.gui.actions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintWriter;

public class ConsoleLogs {
	private WebDriver driver;
	Logs logs;
	LogEntries logEntries;
	PrintWriter writer;

	@Test
	public void testConsole () throws Throwable {
		BrowserActions.navigateToUrl(driver, "http://the-internet.herokuapp.com/broken_images");
		ElementActions.click(driver, By.ByXPath.xpath("//a[contains(text(),'Broken Images')]"));
	}

	public void getConsoleLogs (WebDriver driver) throws Throwable {
		logs = driver.manage().logs();
		logEntries = logs.get(LogType.BROWSER);
		writer = new PrintWriter(System.getProperty("user.dir") + "/src/test/resources/consoleLog/console.txt", "UTF-8");
		for (LogEntry logEntry : logEntries) {
			writer.println("Console log found in Test- ");
			writer.println("__________________________________________________________");
			if (logEntry.getMessage().toLowerCase().contains("error")) {
				writer.println("Error Message in Console:" + logEntry.getMessage());

			} else if (logEntry.getMessage().toLowerCase().contains("warning")) {
				writer.println("Warning Message in Console:" + logEntry.getMessage());

			} else {
				writer.println("Information Message in Console:" + logEntry.getMessage());
			}
		}
		writer.close();
	}

	@BeforeMethod
	public void setUp_BeforeMethod () {
		driver = BrowserFactory.getBrowser();
		new HomePage(driver).navigateToHomePage("http://the-internet.herokuapp.com/broken_images");
	}

	@AfterMethod
	public void tearDown_AfterMethod (ITestResult result) throws Throwable {
		BrowserActions.closeAllOpenedBrowserWindows(driver, result);
	}
}
