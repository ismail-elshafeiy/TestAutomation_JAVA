package engine.broswer;

import engine.Waits;
import engine.dataDriven.ExcelFileManager;
import engine.dataDriven.PropertiesReader;
import engine.validations.EyesManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

public class BrowserHelper {
	protected static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	protected static EyesManager eyesManager;
	public static final String BROWSER_TYPE = ExcelFileManager.getCellData("browser type ", "value");
	protected static final String EXECUTION_TYPE = ExcelFileManager.getCellData("execution type ", "value");
	protected static final String HOST = ExcelFileManager.getCellData("host", "value");
	protected static final String PORT = ExcelFileManager.getCellData("port", "value");
	private static final String IS_MAXIMIZE = ExcelFileManager.getCellData("maximize", "value");
	protected static String width = PropertiesReader.getProperty("project.properties", "width");
	protected static String height = PropertiesReader.getProperty("project.properties", "height");

	/**
	 * This enum will hold the browser types that we support in our framework (chrome, firefox, edge)
	 */
	public enum BrowserType {
		MOZILLA_FIREFOX("Mozilla Firefox"),
		GOOGLE_CHROME("Google Chrome"),
		MICROSOFT_EDGE("Edge"),
		FROM_EXCEL(BROWSER_TYPE);
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
		FROM_EXCEL(EXECUTION_TYPE);
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
			if ( IS_MAXIMIZE.equalsIgnoreCase("true") ) {
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

	private static String checkBrowserType (String browserType) {
		if ( browserType.equalsIgnoreCase("chrome") ) {
			return "Google Chrome";
		} else if ( browserType.equalsIgnoreCase("firefox") ) {
			return "Mozilla Firefox";
		} else if ( browserType.equalsIgnoreCase("edge") ) {
			return "Edge";
		} else {
			return "Google Chrome";
		}
	}

}
