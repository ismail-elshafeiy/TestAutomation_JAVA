package web.capabilities;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.engine.actions.BrowserActions;

import java.net.URL;

/*
 * Steps to execute the grid with Docker:
 *
 * After installing Docker successfully:
 * docker version
 * docker pull selenium/hub
 * docker images (to see the images installed)
 * docker pull selenium/node-chrome
 * docker pull selenium/node-chrome-debug
 * docker pull selenium/node-firefox
 * docker pull selenium/node-firefox-debug
 *
 * Create the containers and connect them together in one command using docker compose:
 * put the .yml file into the project and cd to it from the terminal
 * docker-compose up -d
 * docker ps -a (to see what we just ran)
 * http://localhost:4444/grid/console
 *
 *
 * Excecute from the grid.xml suite
 */

public class SeleniumGrid {
	RemoteWebDriver driver;
	public static final String host = "localhost";
	public static final String port = "4444";

	@Parameters("browserName")
	@BeforeClass
	public void setup (String browserName) {
		driver = null;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browserName);
		capabilities.setCapability("version", "ANY");
//		capabilities.setCapability("platform", "win10"); // If this cap isn't specified, it will just get the any
//		capabilities.setCapability("network", true); // To enable network logs
//		capabilities.setCapability("visual", true); // To enable step by step screenshot
//		capabilities.setCapability("video", true); // To enable video recording
//		capabilities.setCapability("console", true); // To capture console logs
		try {
			driver = new RemoteWebDriver(new URL("http://" + host + ":" + port + "/wd/hub"), capabilities);
		} catch (Exception e) {
			System.out.println("Invalid grid URL" + e.getMessage());
		}
	}

	@BeforeClass
	public void setUpOptions () {
		// https://peter.sh/experiments/chromium-command-line-switches/

		// Chrome Options
		ChromeOptions options = new ChromeOptions();
//		options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		options.addArguments("--start-fullscreen");
		options.addArguments("--incognito");
		options.addArguments("--mute-audio");
        //WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);
		driver.get("http://google.com");

//		//FireFox Options
//		FirefoxOptions options1 = new FirefoxOptions();
//		options1.setCapability("", "");
//
//		//WebDriver driver1 = new FirefoxDriver(options1);

	}


	@AfterClass
	public void closeBrowser () {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}

	@Test
	public void seleniumGridTest () {
		driver.get("http://www.google.com/ncr");
		driver.findElement(By.name("q")).sendKeys("Selenium Grid", Keys.ENTER);
	}


}
