package engine;

import engine.dataDriven.ExcelFileManager;
import engine.listeners.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import engine.dataDriven.PropertiesReader;
import java.time.Duration;

import static engine.dataDriven.PropertiesReader.propertiesFileName;

public class Waits {

	private static final int TIMEOUT_IMPLICIT = Integer.parseInt(ExcelFileManager.getCellData("timeout implicit", "value"));
	private static final int TIMEOUT_EXPLICIT = Integer.parseInt(ExcelFileManager.getCellData("timeout explicit", "value"));
	private static final int MOBILE_TIMEOUT = Integer
			.parseInt(PropertiesReader.getProperty(propertiesFileName, "mobileDriver.wait"));
	private static final int POLLING = Integer.parseInt(ExcelFileManager.getCellData("polling fluent", "value"));

	/**
	 * Implicit wait for the driver instance
	 *
	 * @param driver - WebDriver instance
	 */
	public static void implicitWait (WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT_IMPLICIT));
		Logger.logStep("Implicit wait for [ " + TIMEOUT_IMPLICIT + " ] seconds");
	}

	/**
	 * Implicit wait for the driver instance
	 *
	 * @param driver  - WebDriver instance
	 * @param timeout - timeout in seconds
	 */
	public static void implicitWait (WebDriver driver, int timeout) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
		Logger.logStep("Implicit wait for [ " + timeout + " ] seconds");
	}

	/**
	 * Explicit wait for the driver instance for wait elements
	 *
	 * @param driver - WebDriver instance
	 */

	public static WebDriverWait getExplicitWait (WebDriver driver) {
		return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_EXPLICIT));
	}

	/**
	 * Fluent wait for the driver instance for wait elements
	 *
	 * @param driver - WebDriver instance
	 *
	 * @return FluentWait<WebDriver> - FluentWait instance
	 */
	public static FluentWait<WebDriver> getFluentWait (WebDriver driver) {
		return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(TIMEOUT_EXPLICIT)).pollingEvery(Duration.ofSeconds(POLLING)).ignoring(Exception.class);
	}

	public static FluentWait<WebDriver> getFluentWait (WebDriver driver, int polling) {
		return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(TIMEOUT_EXPLICIT)).pollingEvery(Duration.ofSeconds(polling)).ignoring(Exception.class);
	}


}
