package testCases.gui.web.actions;

import com.practice.gui.pages.homePage.HomePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import static org.testng.Assert.assertEquals;

@Feature("web")
@Epic("Element Actions")
public class DoubleClickTest {

	private WebDriver driver;

	@Test
	public void DoubleClick () {
		new HomePage(driver)
				.navigateToHomePage("http://cookbook.seleniumacademy.com/DoubleClickDemo.html");
		WebElement box = driver.findElement(By.id("message"));
		System.out.println(box.getCssValue("background-color"));
		assertEquals(box.getCssValue("background-color"), "rgba(0, 0, 255, 1)");
		// Double Click
		Actions a = new Actions(driver);
		a.doubleClick(box).perform();
		// get the value to asset on it
		System.out.println(box.getCssValue("background-color"));
		assertEquals(box.getCssValue("background-color"), "rgba(255, 255, 0, 1)");
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
