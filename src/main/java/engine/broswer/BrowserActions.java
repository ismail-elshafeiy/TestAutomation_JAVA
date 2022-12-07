package engine.broswer;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestResult;
import engine.tools.Logger;
import engine.PropertiesReader;
import engine.RecordManager;

import java.util.Iterator;
import java.util.Set;

import static org.testng.Assert.fail;

public class BrowserActions {
	static WebDriver driver;

	public BrowserActions (WebDriver driver) {
		this.driver = driver;
	}

	@Step("Navigate to URL: [{url}]")
	public static void navigateToUrl (WebDriver driver, String url) {
		try {
			Logger.logStep("[Browser Action] Navigate to URL [" + url + "]");
			driver.get(url);
			((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
		} catch (Exception e) {
			Logger.logStep(e.getMessage());
			fail(e.getMessage());
		}
	}

	@Step("Navigate to URL: [{url}]")
	public BrowserActions navigateToUrl (String url) {
		navigateToUrl(driver, url);
		return this;
	}

	//************* Windows Methods ************//
	//*********************************************************************************************//

	//****** Window Positions ******//
	@Step("Maximize the Browser Window")
	public static void maximizeWindow (WebDriver driver) {
		try {
			Logger.logStep("[Browser Action] Maximize the Browser Window");
			driver.manage().window().maximize();
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step("Minimize the Browser Window")
	public static void minimizeWindow (WebDriver driver) {
		try {
			Logger.logStep("[Browser Action] minimize the Browser Window");
			driver.manage().window().minimize();
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step("Full Screen the Browser Window")
	public static void fullScreenWindow (WebDriver driver) {
		try {
			Logger.logStep("[Browser Action] Full Screen the Browser Window");
			driver.manage().window().fullscreen();
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step("Set Window Positions")
	public static void setWindowPositions (WebDriver driver, int x, int y) {
		try {
			Logger.logStep("[Browser Action] Set Window Positions");
			driver.manage().window().setPosition(new Point(x, y));
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step("Get Window Positions")
	public static Point getWindowPositions (WebDriver driver) {
		Point point = null;
		try {
			point = driver.manage().window().getPosition();
			Logger.logStep("[Browser Action] Window Positions : [ " + point + " ]");
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
		}
		return point;
	}

	@Step("Set the WindowResolution [{width}], [{height}]")
	public static void setWindowResolution (WebDriver driver, int width, int height) {
		try {
			Logger.logStep("[Browser Action] Set Window Resolution as Width [" + width + "] and Height [" + height + "]");
			Dimension dimension = new Dimension(width, width);
			driver.manage().window().setSize(dimension);
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step("Set the WindowResolution [{width}], [{height}]")
	public static void setWindowResolution (WebDriver driver) {
		String width = PropertiesReader.getProperty("project.properties", "width");
		String height = PropertiesReader.getProperty("project.properties", "height");
		try {
			Logger.logStep("[Browser Action] Set Window Resolution as Width [" + width + "] and Height [" + height + "]");
			Dimension dimension = new Dimension(Integer.parseInt(width), Integer.parseInt(height));
			driver.manage().window().setSize(dimension);
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
		}
	}


	public void switchToTab (String tabTitle) {
		var windows = driver.getWindowHandles();
		Logger.logMessage("Number of tabs: " + windows.size());
		System.out.println("Window handles:");
		windows.forEach(System.out::println);
		for (String window : windows) {
			Logger.logStep("Switching to window: " + window);
			driver.switchTo().window(window);
			Logger.logMessage("Current window title: " + driver.getTitle());
			if (tabTitle.equals(driver.getTitle())) {
				break;
			}
		}
	}

	public void switchToNewTab () {
		var windows = driver.getWindowHandles();
		try {
			windows.forEach(driver.switchTo()::window);
		} catch (Exception e) {
			Logger.logMessage("" + e.getMessage());
		}
	}

	public void printAllWindowsTitle () {
		Set<String> urls = driver.getWindowHandles();
		Iterator<String> it = urls.iterator();
		while (it.hasNext()) {
			driver.switchTo().window(it.next());
			System.out.println(driver.getCurrentUrl());
		}
	}

	public void switchWindow () {
		Set<String> urls = driver.getWindowHandles();
		Iterator<String> it = urls.iterator();
		String parentWindowID = it.next();
		String childWindowID = it.next();
		//To switch to child window
		driver.switchTo().window(childWindowID);
		// To switch back to parent ID after complete the test
		driver.switchTo().window(parentWindowID);

	}

	@Step("Check the test result and Close All Opened Browser Windows.....")
	public static void closeAllOpenedBrowserWindows (WebDriver driver, ITestResult result) throws Throwable {
		if (ITestResult.FAILURE == result.getStatus()) {
			Logger.attachScreenshotToAllureReport(driver);
			Logger.attachScreenshotToExtentReport(driver);
			Logger.logConsoleLogs(driver, result);
		}
		RecordManager.attachVideoRecording();
		closeAllOpenedBrowserWindows(driver);
	}

	@Step("Close All Opened Browser Windows.....")
	public static void closeAllOpenedBrowserWindows (WebDriver driver) {
		Logger.logStep("[Browser Action] Close all Opened Browser Windows");
		if (driver != null) {
			try {
				driver.quit();
			} catch (WebDriverException rootCauseException) {
				Logger.logMessage(rootCauseException.getMessage());
			}
		} else {
			Logger.logMessage("Windows are already closed and the driver object is null.");
		}
	}

	public static synchronized void closeDriver (int hashCode) {
		if (System.getProperty("videoParams_scope").trim().equals("DriverSession")) {
			RecordManager.attachVideoRecording();
		}
	}
	//**************************************  Alerts Methods **************************************//
	//*********************************************************************************************//

	public enum ConfirmAlertType {
		ACCEPT, DISMISS, SET_TEXT, GET_TEXT
	}

	@Step("Confirm the Alert")
	public static void alertAction (WebDriver driver, ConfirmAlertType confirmAlertType) {
		Waits.getExplicitWait(driver).until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		try {
			switch (confirmAlertType) {
				case ACCEPT:
					Logger.logStep("[Browser Action] Confirm the Alert");
					alert.accept();
					break;
				case DISMISS:
					Logger.logStep("[Browser Action] Dismiss the Alert");
					alert.dismiss();
					break;
				case SET_TEXT:
					Logger.logStep("[Browser Action] Get Text from the Alert");
					alert.getText();
					break;
			}
		} catch (Exception e) {
			Logger.logMessage("Alert is not present" + e.getMessage());
		}

	}

	@Step("Confirm the Alert")
	public static void alertAction (WebDriver driver, ConfirmAlertType confirmAlertType, String text) {
		Waits.getExplicitWait(driver).until(ExpectedConditions.alertIsPresent());
		try {
			Logger.logStep("[Browser Action] Send Keys the Alert");
			driver.switchTo().alert().sendKeys(text);
		} catch (Exception e) {
			Logger.logMessage("Alert is not present" + e.getMessage());
		}
	}


	//************************************** Cookies Methods ************************************** //
	//*********************************************************************************************//

	public enum CookieBuilderType {
		ADD, DELETE
	}

	public static void cookieBuilder (WebDriver driver, CookieBuilderType cookieBuilderType, String name, String value,
									  String domain) {
		Cookie cookie = new Cookie.Builder(name, value).domain(domain).build();

		switch (cookieBuilderType) {
			case ADD -> driver.manage().addCookie(cookie);
			case DELETE -> driver.manage().deleteCookie(cookie);
		}
	}

	public boolean isCookiePresent (WebDriver driver, Cookie cookie) {
		return driver.manage().getCookieNamed(cookie.getName()) != null;
	}

	public Cookie buildCookie (String name, String value) {
		return new Cookie.Builder(name, value)
				.domain("the-internet.herokuapp.com")
				.build();

	}

	//**************************************  Frames Methods ************************************** //
	//*********************************************************************************************//


	//**************************************  Navigation Methods ************************************** //
	//*********************************************************************************************//
	public static void goBack (WebDriver driver) {
		try {
			Logger.logStep("[Browser Action] Navigate Back from [" + getCurrentUrl(driver) + "]");
			driver.navigate().back();
		} catch (Exception e) {
			Logger.logStep(e.getMessage());
			fail(e.getMessage());
		}
	}

	public BrowserActions goBack () {
		goBack(driver);
		return this;
	}

	public static void goForward (WebDriver driver) {
		try {
			driver.navigate().forward();
			Logger.logStep("[Browser Action] Navigate Forward [" + getCurrentUrl(driver) + "]");
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
			fail(e.getMessage());
		}
	}

	public BrowserActions goForward () {
		goForward(driver);
		return this;
	}

	public static void refreshPage (WebDriver driver) {
		try {
			Logger.logStep("[Browser Action] Refresh current page [" + getCurrentUrl(driver) + "]");
			driver.navigate().refresh();
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
			fail(e.getMessage());
		}
		driver.navigate().forward();
	}

	public BrowserActions refreshPage () {
		refreshPage(driver);
		return this;
	}


	//**************************************  Getters Methods **************************************//
	//*********************************************************************************************//
	public static String getPageTitle (WebDriver driver) {
		String title = "";
		try {
			title = driver.getTitle();
			Logger.logStep("[Browser Action] Get page Title [" + title + "]");
		} catch (Exception e) {
			Logger.logStep(e.getMessage());
			fail(e.getMessage());
		}
		return title;
	}

	public BrowserActions getPageTitle () {
		getPageTitle(driver);
		return this;
	}

	public static String getCurrentUrl (WebDriver driver) {
		String url = "";
		try {
			url = driver.getCurrentUrl();
			Logger.logStep("[Browser Action] Get current URL [" + url + "]");
		} catch (
				Exception e) {
			Logger.logStep(e.getMessage());
			fail(e.getMessage());
		}
		return url;
	}

	public BrowserActions getCurrentUrl () {
		getCurrentUrl(driver);
		return this;
	}


}
