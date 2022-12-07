package engine.broswer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import engine.PropertiesReader;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

import java.time.Duration;

public class Waits {
	private static final int TIMEOUT = Integer
			.parseInt(PropertiesReader.getProperty("project.properties", "webDriver.wait"));
	private static final int MOBILE_TIMEOUT = Integer
			.parseInt(PropertiesReader.getProperty("project.properties", "mobileDriver.wait"));
	private static final int POLLING = Integer
			.parseInt(PropertiesReader.getProperty("project.properties", "fluentWait.polling"));

	public static void implicitWait (WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
	}
	public static void implicitWait (WebDriver driver, int timeout) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
	}

	public static WebDriverWait getExplicitWait (WebDriver driver) {
		return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
	}

	public static FluentWait<WebDriver> getFluentWait (WebDriver driver, int POLLING) {
		return new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(TIMEOUT)).pollingEvery(Duration.ofSeconds(POLLING)).ignoring(Exception.class);
	}


	public static WebDriverWait getExplicitWait (MobileDriver<MobileElement> mobile) {
		return new WebDriverWait(mobile, Duration.ofSeconds(MOBILE_TIMEOUT));
	}

	public static void implicitWait (MobileDriver<MobileElement> mobile) {
		mobile.manage().timeouts().implicitlyWait(Duration.ofSeconds(MOBILE_TIMEOUT));
	}

}
