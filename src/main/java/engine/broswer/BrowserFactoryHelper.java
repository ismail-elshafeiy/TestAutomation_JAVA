package engine.broswer;

import engine.Waits;
import engine.validations.EyesManager;
import engine.dataDriven.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import static engine.dataDriven.PropertiesReader.*;

public class BrowserFactoryHelper {

	protected static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	protected static EyesManager eyesManager;

	protected static final String browserType = PropertiesReader.getProperty(propertiesFileName, "browser.type");
	protected static final String executionType = PropertiesReader.getProperty(propertiesFileName, "execution.type");
	protected static final String host = PropertiesReader.getProperty(propertiesFileName, "remote.execution.host");
	protected static final String port = PropertiesReader.getProperty(propertiesFileName, "remote.execution.port");
	//protected static final String appName = PropertiesReader.getProperty(propertiesFileName, "app.name");

	/**
	 * This enum will hold the browser types that we support in our framework (chrome, firefox, edge)
	 */
	public enum BrowserType {
		MOZILLA_FIREFOX("Mozilla Firefox"),
		GOOGLE_CHROME("Google Chrome"),
		MICROSOFT_EDGE("Edge"),
		FROM_PROPERTIES(browserType);
		private final String value;

		BrowserType (String type) {
			this.value = type;
		}

		String getValue () {
			return value;
		}
	}

	/**
	 * This enum will hold the execution types that we support in our framework (local, remote, local headless) and will also be used to read the execution type from the properties file
	 */
	public enum ExecutionType {
		LOCAL("Local"), REMOTE("Remote"), LOCAL_HEADLESS("Local Headless"), FROM_PROPERTIES(executionType);
		private final String value;

		ExecutionType (String type) {
			this.value = type;
		}

		String getValue () {
			return value;
		}
	}

	protected static ChromeOptions getChromeOptions () {
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

	protected static FirefoxOptions getFirefoxOptions () {
		FirefoxOptions ffOptions = new FirefoxOptions();
		ffOptions.setHeadless(true);
		ffOptions.addArguments("--window-size=1920,1080");
		return ffOptions;
	}

	protected static EdgeOptions getEdgeOptions () {
		EdgeOptions edgeOptions = new EdgeOptions();
		edgeOptions.setHeadless(true);
		edgeOptions.addArguments("--window-size=1920,1080");
		edgeOptions.addArguments("inprivate");
		return edgeOptions;
	}

	/**
	 * This method will check if the user wants to maximize the browser window or not and will maximize it if the
	 */
	protected static void checkMaximizeOptionFromProperty () {
		if ( PropertiesReader.getProperty(propertiesFileName, "maximize").equalsIgnoreCase("true") ) {
			BrowserActions.maximizeWindow(driver.get());
		} else {
			BrowserActions.setWindowSize(driver.get());
		}
	}

	protected static void setITestContext () {
		ITestResult result = Reporter.getCurrentTestResult();
		ITestContext context = result.getTestContext();
		context.setAttribute("driver", driver.get());
		try {
			Waits.implicitWait(driver.get());
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
