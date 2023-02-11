package engine.broswer;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import engine.listeners.Logger;

import java.net.MalformedURLException;
import java.net.URL;

import static engine.broswer.BrowserFactoryHelper.*;
import static org.testng.Assert.fail;

public class BrowserFactory {

	/**
	 * This method will return the browser instance based on the browser type and execution type from the property file
	 *
	 * @param browserType   - Browser Type from the property file (chrome, firefox, edge.)
	 * @param executionType - Execution Type from the property file (local, remote, headless)
	 *
	 * @return - Browser instance based on the browser type and execution type from the property file (chrome, firefox, edge.)
	 */
	@Step ("Initializing a new Web GUI Browser!.....")
	public static synchronized WebDriver getBrowser (BrowserType browserType, ExecutionType executionType) {
		//eyesManager = new EyesManager(driver.get(), appName);
		Logger.logStep("Initialize [" + browserType.getValue() + "] Browser and the Execution Type is [" + executionType.getValue() + "]");
		boolean googleChrome = browserType == BrowserType.GOOGLE_CHROME || (browserType == BrowserType.FROM_EXCEL && BrowserFactoryHelper.browserType.equalsIgnoreCase("google chrome"));
		boolean mozillaFirefox = browserType == BrowserType.MOZILLA_FIREFOX || (browserType == BrowserType.FROM_EXCEL && BrowserFactoryHelper.browserType.equalsIgnoreCase("mozilla firefox"));
		boolean microsoftEdge = browserType == BrowserType.MICROSOFT_EDGE || (browserType == BrowserType.FROM_EXCEL && BrowserFactoryHelper.browserType.equalsIgnoreCase("microsoft edge"));
		String browserTypeWarningMsg = "The driver is null! because the browser type [" + BrowserFactoryHelper.browserType + "] is not valid/supported; Please choose a valid browser type from the given choices in the properties file";
		// Remote execution condition
		if ( executionType == ExecutionType.REMOTE || (executionType == ExecutionType.FROM_EXCEL && BrowserFactoryHelper.executionType.equalsIgnoreCase("remote")) ) {
			if ( googleChrome ) {
				try {
					driver.set(new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), getChromeOptions()));
					setITestContext();
				} catch ( MalformedURLException e ) {
					e.printStackTrace();
				}
			} else if ( mozillaFirefox ) {
				try {
					driver.set(new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), getFirefoxOptions()));
					setITestContext();
				} catch ( MalformedURLException e ) {
					e.printStackTrace();
				}
			} else if ( microsoftEdge ) {
				try {
					driver.set(new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), getEdgeOptions()));
					setITestContext();
				} catch ( MalformedURLException e ) {
					e.printStackTrace();
				}
			} else {
				Logger.logMessage(browserTypeWarningMsg);
				fail(browserTypeWarningMsg);
//		throw new NullPointerException(warningMsg);
			}
		}
		// Local execution condition
		else if ( executionType == ExecutionType.LOCAL || (executionType == ExecutionType.FROM_EXCEL && BrowserFactoryHelper.executionType.equalsIgnoreCase("local")) ) {
			if ( googleChrome ) {
				WebDriverManager.chromedriver().setup();
				driver.set(new ChromeDriver());
				setITestContext();
				checkMaximizeOptionFromProperty();
			} else if ( mozillaFirefox ) {
				WebDriverManager.firefoxdriver().setup();
				driver.set(new FirefoxDriver());
				setITestContext();
				checkMaximizeOptionFromProperty();
			} else if ( microsoftEdge ) {
				WebDriverManager.edgedriver().setup();
				driver.set(new EdgeDriver());
				checkMaximizeOptionFromProperty();
				setITestContext();
			} else {
				Logger.logMessage(browserTypeWarningMsg);
				fail(browserTypeWarningMsg);
//		throw new NullPointerException(warningMsg);
			}
		}
		// Local Headless execution condition
		else if ( executionType == ExecutionType.LOCAL_HEADLESS || (executionType == ExecutionType.FROM_EXCEL && BrowserFactoryHelper.executionType.equalsIgnoreCase("local headless")) ) {
			if ( googleChrome ) {
				WebDriverManager.chromedriver().setup();
				driver.set(new ChromeDriver(getChromeOptions()));
				setITestContext();
			} else if ( mozillaFirefox ) {
				WebDriverManager.firefoxdriver().setup();
				driver.set(new FirefoxDriver(getFirefoxOptions()));
				setITestContext();
			} else if ( microsoftEdge ) {
				WebDriverManager.edgedriver().setup();
				driver.set(new EdgeDriver(getEdgeOptions()));
				setITestContext();
			} else {
				Logger.logMessage(browserTypeWarningMsg);
				fail(browserTypeWarningMsg);
			}
		} else {
			String warningMsg = "The driver is null! because the execution type [" + BrowserFactoryHelper.executionType + "] is not valid/supported; Please choose a valid execution type from the given choices in the properties file";
			Logger.logMessage(warningMsg);
			fail(warningMsg);
		}
		return driver.get();
	}

	public static WebDriver getBrowser () {
		return getBrowser(BrowserType.FROM_EXCEL, ExecutionType.FROM_EXCEL);
	}


}
