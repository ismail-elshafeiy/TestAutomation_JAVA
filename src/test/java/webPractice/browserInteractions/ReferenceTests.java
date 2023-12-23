package webPractice.browserInteractions;

import com.engine.driver.DriverHelper;
import com.engine.reports.Attachments;
import com.engine.actions.BrowserActions;
import com.engine.driver.DriverFactory;
import com.engine.evidence.ScreenShot;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.*;
import org.testng.annotations.Test;

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
		driver = DriverFactory.getBrowser();
		BrowserActions.navigateToUrl(driver, "https://www.google.com/");
		ScreenShot.takeScreenShotToFile(driver);
	}

	@Story("Screen Shots")
	@Test(description = "Take Element Screen Shot Test")
	@Description("""
				Used to capture screenshot of an element for current browsing context. 
				The WebDriver endpoint screenshot returns screenshot which is encoded in Base64 format		
			""")
	public void takeElementScreenShotTest () {
		driver = DriverFactory.getBrowser();
		BrowserActions.navigateToUrl(driver, "https://www.google.com/");
		ScreenShot.takeElementScreenShot(driver, By.xpath("//a[text()='Gmail']"));
	}

	@Story("Full Page Screen Shot")
	@Test(description = "Take Full Page Screenshot")
	@Description("""
	
			""")
	public void takeFullPage_Screenshot () throws IOException {
		driver = DriverFactory.getBrowser(DriverHelper.BrowserType.MOZILLA_FIREFOX, DriverHelper.ExecutionType.LOCAL);
		BrowserActions.navigateToUrl(driver, "https://www.selenium.dev/");
		ScreenShot.takeFullPageScreenshot(driver, "Selenium Full Page Screenshot");
	}

	@Story("Print Page")
	@Test(description = "Print Page Test with headless Mode Only")
	@Description("""
			Prints the current page within the browser.
			Note: This requires Chromium Browsers to be in headless mode
			""")
	public void printWindow () throws IOException {
		driver = DriverFactory.getBrowser(DriverHelper.BrowserType.GOOGLE_CHROME, DriverHelper.ExecutionType.LOCAL_HEADLESS);
		BrowserActions.navigateToUrl(driver, "https://www.selenium.dev");
		Attachments.printPage(driver, 6);
	}
}
