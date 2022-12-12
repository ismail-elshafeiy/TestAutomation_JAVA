package testCases.gui.web.browserInteractions;

import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;
import engine.tools.Logger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.*;
import org.openqa.selenium.print.PrintOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import engine.broswer.BrowserFactory.BrowserType;
import engine.broswer.BrowserFactory.ExecutionType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Epic("Browser Interactions")
@Feature("Windows")
public class ReferenceTests {
	private WebDriver driver;

	public void setUp_BeforeMethod2 () {

	}

	@Story("Screen Shots")
	@Test(description = "Take Screen Shot Test")
	public void takeScreenShotTest () {
		driver = BrowserFactory.getBrowser();
		BrowserActions.navigateToUrl(driver, "https://www.google.com/");
		Logger.takeScreenShotToFile(driver);
	}

	@Story("Screen Shots")
	@Test(description = "Take Element Screen Shot Test")
	public void takeElementScreenShotTest () {
		driver = BrowserFactory.getBrowser();
		BrowserActions.navigateToUrl(driver, "https://www.google.com/");
		Logger.takeElementScreenShot(driver, By.xpath("//a[text()='Gmail']"));
	}

	@Test(description = "Take Full Page Screenshot")
	public void takeFullPage_Screenshot () throws IOException {
		driver = BrowserFactory.getBrowser(BrowserType.MOZILLA_FIREFOX, ExecutionType.LOCAL);
		BrowserActions.navigateToUrl(driver, "https://www.selenium.dev/");
		Logger.takeFullPageScreenshot(driver, "Selenium Full Page Screenshot");
	}

	@Story("Print Page")
	@Test(description = "")
	public void printWindow () throws IOException {
		driver = BrowserFactory.getBrowser(BrowserType.GOOGLE_CHROME, ExecutionType.LOCAL_HEADLESS);
		BrowserActions.navigateToUrl(driver, "https://www.google.com/");
		driver.get("https://www.selenium.dev");
		Logger.printPage(driver, 6);
	}
}
