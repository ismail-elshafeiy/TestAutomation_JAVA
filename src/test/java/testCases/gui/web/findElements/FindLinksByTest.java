package testCases.gui.web.findElements;

import com.practice.gui.pages.homePage.HomePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

@Feature("web")
@Epic("FindElements")
public class FindLinksByTest {
	private WebDriver driver;

	@Test
	public void textLinkText () {
		WebElement seleniumLink = driver.findElement(By.linkText("Elemental Selenium"));
		System.out.println("The Link = " + seleniumLink.getAttribute("href"));
	}

	@Test
	public void textPartialText () {
		WebElement seleniumLink = driver.findElement(By.partialLinkText("Elemental"));
		System.out.println("The Link 2 = " + seleniumLink.getAttribute("href"));
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
