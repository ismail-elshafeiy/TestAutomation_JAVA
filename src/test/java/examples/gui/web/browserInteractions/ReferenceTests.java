package examples.gui.web.browserInteractions;

import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;
import engine.tools.Logger;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import engine.broswer.BrowserFactory.BrowserType;
import engine.broswer.BrowserFactory.ExecutionType;
import java.io.IOException;

@Epic("Browser Interactions")
@Feature("Windows")
public class ReferenceTests {
	private WebDriver driver;

	@Story("Screen Shots")
	@Test(description = "Take Screen Shot Test")
	@Description("""
			Used to capture screenshot for current browsing context. 
			The WebDriver endpoint screenshot returns screenshot which is encoded in Base64 format.	
				""")
	public void takeScreenShotTest () {
		driver = BrowserFactory.getBrowser();
		BrowserActions.navigateToUrl(driver, "https://www.google.com/");
		Logger.takeScreenShotToFile(driver);
	}

	@Story("Screen Shots")
	@Test(description = "Take Element Screen Shot Test")
	@Description("""
				Used to capture screenshot of an element for current browsing context. 
				The WebDriver endpoint screenshot returns screenshot which is encoded in Base64 format		
			""")
	public void takeElementScreenShotTest () {
		driver = BrowserFactory.getBrowser();
		BrowserActions.navigateToUrl(driver, "https://www.google.com/");
		Logger.takeElementScreenShot(driver, By.xpath("//a[text()='Gmail']"));
	}

	@Story("Full Page Screen Shot")
	@Test(description = "Take Full Page Screenshot")
	@Description("""
	
			""")
	public void takeFullPage_Screenshot () throws IOException {
		driver = BrowserFactory.getBrowser(BrowserType.MOZILLA_FIREFOX, ExecutionType.LOCAL);
		BrowserActions.navigateToUrl(driver, "https://www.selenium.dev/");
		Logger.takeFullPageScreenshot(driver, "Selenium Full Page Screenshot");
	}

	@Story("Print Page")
	@Test(description = "Print Page Test with headless Mode Only")
	@Description("""
			Prints the current page within the browser.
			Note: This requires Chromium Browsers to be in headless mode
			""")
	public void printWindow () throws IOException {
		driver = BrowserFactory.getBrowser(BrowserType.GOOGLE_CHROME, ExecutionType.LOCAL_HEADLESS);
		BrowserActions.navigateToUrl(driver, "https://www.google.com/");
		driver.get("https://www.selenium.dev");
		Logger.printPage(driver, 6);
	}
}