package web.browserInteractions;

import com.engine.actions.BrowserActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class ResponsiveTests {
	WebDriver driver;

	@Test
	public void validateResponsive () {
		driver.get("https://www.google.com");
	}

	@BeforeMethod
	public void setUp () {
        //WebDriverManager.chromedriver().setup();
		Map<String, String> deviceMobEmu = new HashMap<>();
		deviceMobEmu.put("deviceName", "iPhone 12 Pro");
		ChromeOptions chromeOpt = new ChromeOptions();
		chromeOpt.setExperimentalOption("mobileEmulation", deviceMobEmu);
		driver = new ChromeDriver(chromeOpt);
		BrowserActions.getWindowSize(driver);
	}
}

