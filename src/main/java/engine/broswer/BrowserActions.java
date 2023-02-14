package engine.broswer;

import engine.dataDriven.ExcelFileManager;
import engine.evidence.Attachments;
import engine.Waits;
import engine.gui.actions.ElementActions;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestResult;
import engine.listeners.Logger;
import engine.evidence.RecordManager;

import java.util.Set;

import static engine.broswer.BrowserFactoryHelper.eyesManager;
import static org.testng.Assert.fail;

public class BrowserActions {
	static WebDriver driver;

	public BrowserActions (WebDriver driver) {
		BrowserActions.driver = driver;
	}

	@Step ("Navigate to URL: [{url}]")
	public static void navigateToUrl (WebDriver driver, String url) {
		try {
			Logger.logStep("[Browser Action] Navigate to URL [" + url + "]");
			driver.get(url);
			((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
		} catch ( Exception e ) {
			Logger.logStep(e.getMessage());
			fail(e.getMessage());
		}
	}

	@Step ("Navigate to URL: [{url}]")
	public BrowserActions navigateToUrl (String url) {
		navigateToUrl(driver, url);
		return this;
	}

	@Step ("Navigate to URL using JavaScript: [{url}]")
	public static void navigateToUrlUsingJavaScript (WebDriver driver, String url) {
		try {
			Logger.logStep("[Browser Action] Navigate to URL using JavaScript [" + url + "]");
			((JavascriptExecutor) driver).executeScript("window.location = '" + url + "'");
		} catch ( Exception e ) {
			Logger.logStep(e.getMessage());
			fail(e.getMessage());
		}
	}

	@Step ("Navigate to URL using JavaScript: [{url}]")
	public BrowserActions navigateToUrlUsingJavaScript (String url) {
		navigateToUrlUsingJavaScript(driver, url);
		return this;
	}


	//************* Windows Methods ************//
	//*********************************************************************************************//

	//****** Window Positions and Size ******//
	@Step ("Maximize the Browser Window")
	public static void maximizeWindow (WebDriver driver) {
		try {
			Logger.logStep("[Browser Action] Maximize the Browser Window");
			driver.manage().window().maximize();
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step ("Minimize the Browser Window")
	public static void minimizeWindow (WebDriver driver) {
		try {
			Logger.logStep("[Browser Action] minimize the Browser Window");
			driver.manage().window().minimize();
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step ("Full Screen the Browser Window")
	public static void fullScreenWindow (WebDriver driver) {
		try {
			Logger.logStep("[Browser Action] Full Screen the Browser Window");
			driver.manage().window().fullscreen();
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step ("Set the Window Size [{width}], [{height}]")
	public static void setWindowSize (WebDriver driver, int width, int height) {
		try {
			Logger.logStep("[Browser Action] Set Window Resolution as Width [" + width + "] and Height [" + height + "]");
			Dimension dimension = new Dimension(width, height);
			driver.manage().window().setSize(dimension);
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step ("Set the Window Size [{width}], [{height}]")
	public static void setWindowSize (WebDriver driver) {
		//	String width = PropertiesReader.getProperty("project.properties", "width");
		//	String height = PropertiesReader.getProperty("project.properties", "height");
		String width = ExcelFileManager.getCellData(7, "values");
		String height = ExcelFileManager.getCellData(8, "values");
		try {
			Logger.logStep("[Browser Action] Set Window Resolution as Width [" + width + "] and Height [" + height + "]");
			Dimension dimension = new Dimension(Integer.parseInt(width), Integer.parseInt(height));
			driver.manage().window().setSize(dimension);
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step ("Get Window Size")
	public static void getWindowSize (WebDriver driver) {
		try {
			Dimension dimension = driver.manage().window().getSize();
			Logger.logStep("[Browser Action] Window Size : [ " + dimension + " ]");
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step ("Set Window Positions")
	public static void setWindowPositions (WebDriver driver, int x, int y) {
		try {
			Logger.logStep("[Browser Action] Set Window Positions");
			driver.manage().window().setPosition(new Point(x, y));
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step ("Get Window Positions")
	public static void getWindowPositions (WebDriver driver) {
		try {
			Point point = driver.manage().window().getPosition();
			Logger.logStep("[Browser Action] Window Positions : [ " + point + " ]");
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}


	//****** Switch Windows ******//
	public enum PageType {
		WINDOW("window"), TAB("tab");
		private final String windowType;

		PageType (String windowType) {
			this.windowType = windowType;
		}
	}

	@Step ("Switch to Window [{windowType}]")
	public static void switchToNewPage (WebDriver driver, PageType pageType, String url) {
		try {
			switch ( pageType ) {
				case WINDOW -> {
					Logger.logStep("[Browser Action] Switch to new Window Title: " + driver.getTitle() + " [" + url + "]");
					driver.switchTo().newWindow(WindowType.WINDOW).get(url);
				}
				case TAB -> {
					Logger.logStep("[Browser Action] Switch to new Tab Title: " + driver.getTitle() + " [" + url + "]");
					driver.switchTo().newWindow(WindowType.TAB).get(url);
				}
			}
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}

	@Step ("Switch to new window and focus on it")
	public static void switchToNewWindowByClickHyperLink (WebDriver driver, By elementLocator) {
		try {
			getPageTitle(driver);
			String parentWindow = driver.getWindowHandle();
			ElementActions.click(driver, elementLocator);
			// Return a set of all window handles that can be used
			Set<String> handles = driver.getWindowHandles();
			for ( String childWindow : handles ) {
				if ( ! parentWindow.equals(childWindow) ) {
					driver.switchTo().window(childWindow);
					Logger.logStep("[Browser Action] Switch to new Window Title: " + driver.getTitle());
				}
			}
			Logger.logMessage("All Windows= " + handles.size());
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}


	@Step ("Check the test result and Close All Opened Browser Windows.....")
	public static void closeAllOpenedBrowserWindows (WebDriver driver, ITestResult result) throws Throwable {
		if ( ITestResult.FAILURE == result.getStatus() ) {
			Attachments.attachScreenshotToAllureReport(driver);
			Attachments.attachScreenshotToExtentReport(driver);
			Logger.logConsoleLogs(driver, result);
		}
		RecordManager.attachVideoRecording();
		closeAllOpenedBrowserWindows(driver);
		eyesManager.abort();
	}

	@Step ("Close All Opened Browser Windows.....")
	public static void closeAllOpenedBrowserWindows (WebDriver driver) {
		Logger.logStep("[Browser Action] Close all Opened Browser Windows");
		if ( driver != null ) {
			try {
				driver.quit();
			} catch ( WebDriverException rootCauseException ) {
				Logger.logMessage(rootCauseException.getMessage());
			}
		} else {
			Logger.logMessage("Windows are already closed and the driver object is null.");
		}
	}

	// TODO: handle record Video
	public static synchronized void closeDriver (int hashCode) {
		if ( System.getProperty("videoParams_scope").trim().equals("DriverSession") ) {
			RecordManager.attachVideoRecording();
		}
	}

	//**************************************  Alerts Methods **************************************//
	//*********************************************************************************************//

	public enum AlertAction {
		ACCEPT, DISMISS, SET_TEXT, GET_TEXT
	}

	@Step ("Confirm the Alert")
	public static void alertAction (WebDriver driver, AlertAction confirmAlertType) {
		Waits.getExplicitWait(driver).until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		try {
			switch ( confirmAlertType ) {
				case ACCEPT -> {
					Logger.logStep("[Browser Action] Confirm the Alert");
					alert.accept();
				}
				case DISMISS -> {
					Logger.logStep("[Browser Action] Dismiss the Alert");
					alert.dismiss();
				}
				case GET_TEXT -> {
					Logger.logStep("[Browser Action] Get Text from the Alert");
					alert.getText();
				}
			}
		} catch ( Exception e ) {
			Logger.logMessage("Alert is not present" + e.getMessage());
		}

	}

	@Step ("Confirm the Alert")
	public static void alertAction (WebDriver driver, String text) {
		Waits.getExplicitWait(driver).until(ExpectedConditions.alertIsPresent());
		try {
			Logger.logStep("[Browser Action] Send Keys the Alert");
			driver.switchTo().alert().sendKeys(text);
		} catch ( Exception e ) {
			Logger.logMessage("Alert is not present" + e.getMessage());
		}
	}

	//************************************** Cookies Methods ************************************** //
	//*********************************************************************************************//

	public enum CookieBuilderType {
		ADD, DELETE
	}

	public static void cookieBuilder (WebDriver driver,
									  CookieBuilderType cookieBuilderType,
									  String name, String value, String domain) {
		Cookie cookie = new Cookie.Builder(name, value).domain(domain).build();
		switch ( cookieBuilderType ) {
			case ADD -> driver.manage().addCookie(cookie);
			case DELETE -> driver.manage().deleteCookie(cookie);
		}
	}

	public static void getAllCookies (WebDriver driver) {
		try {
			Set<Cookie> cookieSet = driver.manage().getCookies();
			Logger.logStep("Get All Cookies = " + cookieSet.size());
			for ( Cookie cookie : cookieSet ) {
				Logger.logStep("Cookie Domain: " + cookie.getDomain());
				Logger.logStep("Cookie Name: " + cookie.getName());
				Logger.logStep("Cookie Value: " + cookie.getValue());
				Logger.logStep("Cookie Path: " + cookie.getPath());
				Logger.logStep("Cookie Expiry: " + cookie.getExpiry());
			}
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());

		}
	}

	public boolean isCookiePresent (WebDriver driver, Cookie cookie) {
		return driver.manage().getCookieNamed(cookie.getName()) != null;
	}

	//**************************************  Frames Methods ************************************** //
	//*********************************************************************************************//

	/**
	 * Using driver.switchTo().frame() to handle frames
	 * using with iframe's ID
	 **/
	public static void switchToFrame (WebDriver driver, By elementLocator) {
		try {
			Logger.logStep("[Browser Action] Switch to Frame by Locator: " + elementLocator.toString());
			driver.switchTo().frame(driver.findElement(elementLocator));
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}

	public BrowserActions switchToFrame (By elementLocator) {
		switchToFrame(driver, elementLocator);
		return this;
	}

	/**
	 * Using driver.switchTo().frame() to handle frames
	 * using with iframe's ID Or name
	 **/
	public static void switchToFrame (WebDriver driver, String nameOrId) {
		try {
			Logger.logStep("[Browser Action] Switch to Frame by name Or id: " + nameOrId.toString());
			driver.switchTo().frame(nameOrId);
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}

	public BrowserActions switchToFrame (String nameOrId) {
		switchToFrame(driver, nameOrId);
		return this;
	}

	/**
	 * Using driver.switchTo().frame() to handle frames
	 * using with iframe's index
	 **/
	public static void switchToFrame (WebDriver driver, int index) {
		try {
			Logger.logStep("[Browser Action] Switch to Frame by index : " + index);
			driver.switchTo().frame(index);
		} catch ( Exception e ) {
			Logger.logMessage(e.getMessage());
		}
	}

	public BrowserActions switchToFrame (int index) {
		switchToFrame(driver, index);
		return this;
	}

	//**************************************  Navigation Methods ************************************** //
	//*********************************************************************************************//


	public static void goBack (WebDriver driver) {
		try {
			Logger.logStep("[Browser Action] Navigate Back from [" + getCurrentUrl(driver) + "]");
			driver.navigate().back();
		} catch ( Exception e ) {
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
		} catch ( Exception e ) {
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
		} catch ( Exception e ) {
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
		} catch ( Exception e ) {
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
				Exception e ) {
			Logger.logStep(e.getMessage());
			fail(e.getMessage());
		}
		return url;
	}

	public static String getCurrentPageUsingJavaScript (WebDriver driver) {
		String page = "";
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			page = (String) js.executeScript("return document.title");
			Logger.logStep("[Browser Action] Get current page using JavaScript [" + page + "]");
		} catch ( Exception e ) {
			Logger.logStep(e.getMessage());
			fail(e.getMessage());
		}
		return page;
	}

	public BrowserActions getCurrentPageUsingJavaScript () {
		getCurrentUrlUsingJavaScript(driver);
		return this;
	}

	public BrowserActions getCurrentUrl () {
		getCurrentUrl(driver);
		return this;
	}

	public static String getCurrentUrlUsingJavaScript (WebDriver driver) {
		String page = "";
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			page = (String) js.executeScript("return document.domain;");
			Logger.logStep("[Browser Action] Get current url using JavaScript [" + page + "]");
		} catch ( Exception e ) {
			Logger.logStep(e.getMessage());
			fail(e.getMessage());
		}
		return page;
	}

	public BrowserActions getCurrentUrlUsingJavaScript () {
		getCurrentUrlUsingJavaScript(driver);
		return this;
	}


}
