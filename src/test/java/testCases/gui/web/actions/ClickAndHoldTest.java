package testCases.gui.web.actions;

import com.practice.gui.pages.homePage.HomePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

@Feature("web")
@Epic("Element Actions")
@Description("""
		 Using Actions Class to perform a Click on element and Hold it
		 	then goes to specific location or and Release the Hold button
			Mainly used in horizontal bars , Draw something in Canvas
		""")
public class ClickAndHoldTest {
	private WebDriver driver;
	Actions actions;

	@Test
	public void ClickAndHold () {
		actions = new Actions(driver);
		new HomePage(driver).navigateToHomePage("https://the-internet.herokuapp.com/horizontal_slider");
		// clicks on a horizontal slider and hold it and then slide it to x = 10px and y = 0
		actions.clickAndHold(driver.findElement(By.xpath("//input[@type='range']")))
				.moveByOffset(10, 0)
				.release()
				.build().perform();
	}

	@BeforeMethod
	public void setUp_BeforeMethod () {
		driver = BrowserFactory.getBrowser();
	}

	@AfterMethod(enabled = false)
	public void closeBrowser () {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}
}
