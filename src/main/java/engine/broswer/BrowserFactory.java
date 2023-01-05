package engine.broswer;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import engine.tools.Logger;
import engine.PropertiesReader;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.fail;

public class BrowserFactory {
	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static final String propertiesFileName = "project.properties";
	private static final String browserTypeProperty = PropertiesReader.getProperty(propertiesFileName, "browser.type");
	private static final String executionTypeProperty = PropertiesReader.getProperty(propertiesFileName, "execution.type");
	private static final String host = PropertiesReader.getProperty(propertiesFileName, "remote.execution.host");
	private static final String port = PropertiesReader.getProperty(propertiesFileName, "remote.execution.port");


	// Check the Browser and Execution from property file
	@Step("Initializing a new Web GUI Browser!.....")
	public static synchronized WebDriver getBrowser (BrowserType browserType, ExecutionType executionType) {
		ITestResult result = Reporter.getCurrentTestResult();
		ITestContext context = result.getTestContext();
		Logger.logStep("Initialize [" + browserType.getValue() + "] Browser and the Execution Type is [" + executionType.getValue() + "]");

		// Remote execution condition
		if (executionType == ExecutionType.REMOTE
				|| (executionType == ExecutionType.FROM_PROPERTIES && executionTypeProperty.equalsIgnoreCase("remote"))) {
			if (browserType == BrowserType.GOOGLE_CHROME
					|| (browserType == BrowserType.FROM_PROPERTIES && browserTypeProperty.equalsIgnoreCase("google chrome"))) {
				try {
					driver.set(new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), getChromeOptions()));
					context.setAttribute("driver", driver.get());
					Waits.implicitWait(driver.get());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

			} else if (browserType == BrowserType.MOZILLA_FIREFOX
					|| (browserType == BrowserType.FROM_PROPERTIES && browserTypeProperty.equalsIgnoreCase("mozilla firefox"))) {
				try {
					driver.set(new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"),
							getFirefoxOptions()));
					context.setAttribute("driver", driver.get());
					Waits.implicitWait(driver.get());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			} else {
				String warningMsg = "The driver is null! because the browser type [" + browserTypeProperty + "] is not valid/supported; Please choose a valid browser type from the given choices in the properties file";
				Logger.logMessage(warningMsg);
				fail(warningMsg);
//		throw new NullPointerException(warningMsg);
			}
		}
		// Local execution condition
		else if (executionType == ExecutionType.LOCAL
				|| (executionType == ExecutionType.FROM_PROPERTIES && executionTypeProperty.equalsIgnoreCase("local"))) {

			if (browserType == BrowserType.GOOGLE_CHROME
					|| (browserType == BrowserType.FROM_PROPERTIES && browserTypeProperty.equalsIgnoreCase("google chrome"))) {
				// start session recording
				WebDriverManager.chromedriver().setup();
				driver.set(new ChromeDriver());
				context.setAttribute("driver", driver.get());
				Waits.implicitWait(driver.get());
				if (PropertiesReader.getProperty(propertiesFileName, "maximize").equalsIgnoreCase("true")) {
					BrowserActions.maximizeWindow(driver.get());
				} else {
					BrowserActions.setWindowSize(driver.get());
				}
			} else if (browserType == BrowserType.MOZILLA_FIREFOX
					|| (browserType == BrowserType.FROM_PROPERTIES && browserTypeProperty.equalsIgnoreCase("mozilla firefox"))) {
				WebDriverManager.firefoxdriver().setup();
				driver.set(new FirefoxDriver());
				context.setAttribute("driver", driver.get());
				Waits.implicitWait(driver.get());
				if (PropertiesReader.getProperty(propertiesFileName, "maximize").equalsIgnoreCase("true")) {
					BrowserActions.maximizeWindow(driver.get());
				} else {
					BrowserActions.setWindowSize(driver.get());
				}
			} else {
				String warningMsg = "The driver is null! because the browser type [" + browserTypeProperty + "] is not valid/supported; Please choose a valid browser type from the given choices in the properties file";
				Logger.logMessage(warningMsg);
				fail(warningMsg);
//		throw new NullPointerException(warningMsg);
			}
		}
		// Local Headless execution condition
		else if (executionType == ExecutionType.LOCAL_HEADLESS
				|| (executionType == ExecutionType.FROM_PROPERTIES && executionTypeProperty.equalsIgnoreCase("local headless"))) {
			if (browserType == BrowserType.GOOGLE_CHROME
					|| (browserType == BrowserType.FROM_PROPERTIES && browserTypeProperty.equalsIgnoreCase("google chrome"))) {
				WebDriverManager.chromedriver().setup();
				driver.set(new ChromeDriver(getChromeOptions()));
				context.setAttribute("driver", driver.get());
				Waits.implicitWait(driver.get());
			} else if (browserType == BrowserType.MOZILLA_FIREFOX
					|| (browserType == BrowserType.FROM_PROPERTIES && browserTypeProperty.equalsIgnoreCase("mozilla firefox"))) {
				WebDriverManager.firefoxdriver().setup();
				driver.set(new FirefoxDriver(getFirefoxOptions()));
				context.setAttribute("driver", driver.get());
				Waits.implicitWait(driver.get());
			} else {
				String warningMsg = "The driver is null! because the browser type [" + browserTypeProperty + "] is not valid/supported; Please choose a valid browser type from the given choices in the properties file";
				Logger.logMessage(warningMsg);
				fail(warningMsg);
			}
		} else {
			String warningMsg = "The driver is null! because the execution type [" + executionTypeProperty + "] is not valid/supported; Please choose a valid execution type from the given choices in the properties file";
			Logger.logMessage(warningMsg);
			fail(warningMsg);
		}
		return driver.get();
	}

	public static WebDriver getBrowser () {
		return getBrowser(BrowserType.FROM_PROPERTIES, ExecutionType.FROM_PROPERTIES);
	}

	public enum BrowserType {
		MOZILLA_FIREFOX("Mozilla Firefox"), GOOGLE_CHROME("Google Chrome"), FROM_PROPERTIES(browserTypeProperty);
		private final String value;

		BrowserType (String type) {
			this.value = type;
		}

		private String getValue () {
			return value;
		}
	}

	public enum ExecutionType {
		LOCAL("Local"), REMOTE("Remote"), LOCAL_HEADLESS("Local Headless"), FROM_PROPERTIES(executionTypeProperty);
		private final String value;

		ExecutionType (String type) {
			this.value = type;
		}

		private String getValue () {
			return value;
		}
	}

	private static ChromeOptions getChromeOptions () {
		ChromeOptions chOptions = new ChromeOptions();
		chOptions.setHeadless(true);
		chOptions.addArguments("--window-size=1920,1080");
//	chOptions.addArguments("--start-maximized");
//	chOptions.setCapability("platform", Platform.LINUX);
//	chOptions.addArguments("--headless");
//	chOptions.addArguments("disable--infobars");
//	chOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
//	chOptions.addArguments("--ignore-certificate-errors");
		return chOptions;
	}

	private static FirefoxOptions getFirefoxOptions () {
		FirefoxOptions ffOptions = new FirefoxOptions();
		ffOptions.setHeadless(true);
		ffOptions.addArguments("--window-size=1920,1080");
		return ffOptions;
	}

}
