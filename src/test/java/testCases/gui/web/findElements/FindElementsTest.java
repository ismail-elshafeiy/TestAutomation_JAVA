package testCases.gui.web.findElements;

import com.practice.gui.pages.homePage.HomePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import java.util.List;

@Feature("web")
@Epic("FindElements")
public class FindElementsTest {
	private WebDriver driver;

	@Test
	public void list () {
		// get all Links displayed onm page
		List<WebElement> links = driver.findElements(By.tagName("a"));
		//verify there are 20 Links displayed on the page
		Assert.assertEquals(links.size(), 46);
		System.out.println("The number of elements = " + links.size());

		// Print each link value
		for (WebElement link : links) {
			System.out.println(link.getAttribute("href"));
		}
	}

	@BeforeMethod
	public void setUp_BeforeMethod () {
		driver = BrowserFactory.getBrowser();
		new HomePage(driver).navigateToHomePage("http://the-internet.herokuapp.com/dropdown");
	}

	@AfterMethod
	public void closeBrowser () {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}
}

