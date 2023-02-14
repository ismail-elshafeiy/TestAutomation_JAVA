package engine;

import engine.dataDriven.ExcelFileManager;
import engine.dataDriven.ExcelFileManager1;
import engine.listeners.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import engine.dataDriven.PropertiesReader;

import java.time.Duration;
import java.util.Objects;

import static engine.dataDriven.PropertiesReader.propertiesFileName;

public class Waits {
	private static final int TIMEOUT = Integer.parseInt(Objects.requireNonNull(ExcelFileManager.getCellData(9, "values")));
	//private static final int TIMEOUT = Integer
	//		.parseInt(PropertiesReader.getProperty(propertiesFileName, "webDriver.wait"));
	private static final int MOBILE_TIMEOUT = Integer
			.parseInt(PropertiesReader.getProperty(propertiesFileName, "mobileDriver.wait"));
	private static final int POLLING = Integer.parseInt(Objects.requireNonNull(ExcelFileManager.getCellData(10, "values")));

	public static void implicitWait (WebDriver driver) {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
		Logger.logStep("Implicit wait for [ " + TIMEOUT + " ] seconds");
	}

	public static void implicitWait (WebDriver driver, int timeout) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
		Logger.logStep("Implicit wait for [ " + timeout + " ] seconds");
	}

	public static WebDriverWait getExplicitWait (WebDriver driver) {
		return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
	}

	public static FluentWait<WebDriver> getFluentWait (WebDriver driver, int POLLING) {
		return new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(TIMEOUT)).pollingEvery(Duration.ofSeconds(POLLING)).ignoring(Exception.class);
	}


/*
	public static WebDriverWait getExplicitWait (MobileDriver<MobileElement> mobile) {
		return new WebDriverWait(mobile, Duration.ofSeconds(MOBILE_TIMEOUT));
	}

	public static void implicitWait (MobileDriver<MobileElement> mobile) {
		mobile.manage().timeouts().implicitlyWait(Duration.ofSeconds(MOBILE_TIMEOUT));
	}
*/

}
