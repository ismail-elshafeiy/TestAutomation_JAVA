package engine.broswer;

import engine.Waits;
import engine.dataDriven.ExcelFileManager;
import engine.validations.EyesManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

public class BrowserFactoryHelper {

	protected static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	protected static EyesManager eyesManager;
	public static String browserType = ExcelFileManager.getCellData(2, "values");
	protected static final String executionType = ExcelFileManager.getCellData(3, "values");
	protected static final String host = ExcelFileManager.getCellData(4, "values");
	protected static final String port = ExcelFileManager.getCellData(5, "values");
	//protected static final String appName = PropertiesReader.getProperty(propertiesFileName, "app.name");

	/**
	 * This enum will hold the browser types that we support in our framework (chrome, firefox, edge)
	 */
	public enum BrowserType {
		MOZILLA_FIREFOX("Mozilla Firefox"),
		GOOGLE_CHROME("Google Chrome"),
		MICROSOFT_EDGE("Edge"),
		FROM_EXCEL(browserType);
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
		LOCAL("Local"),
		REMOTE("Remote"),
		LOCAL_HEADLESS("Local Headless"),
		FROM_EXCEL(executionType);
		private final String value;

		ExecutionType (String type) {
			this.value = type;
		}

		String getValue () {
			return value;
		}
	}

	public enum Environment {
		DEV("Local"),
		STG("Remote"),
		UAT("Local Headless"),
		PROD("Local Headless");
		private final String value;

		Environment (String type) {
			this.value = type;
		}

		String getValue () {
			return value;
		}
	}

	public enum TestDataAccount {
		DEV("Local"),
		STG("Remote"),
		UAT("Local Headless"),
		PROD("Local Headless");
		private final String value;

		TestDataAccount (String type) {
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
		try {
			if ( ExcelFileManager.getCellData(6, "values").equalsIgnoreCase("true") ) {
				BrowserActions.maximizeWindow(driver.get());
			} else {
				BrowserActions.setWindowSize(driver.get());
			}
		} catch ( Exception e ) {
			e.printStackTrace();
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
