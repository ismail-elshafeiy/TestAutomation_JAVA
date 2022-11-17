package testCases.gui.web.findElements;

import com.practice.gui.pages.homePage.HomePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import java.util.List;

@Feature("web")
@Epic("FindElements")
public class FindElementByTagNameTest {

	private WebDriver driver;

	@Test
	public void Register () {
		new HomePage(driver).navigateToHomePage("http://the-internet.herokuapp.com/tables");
		WebElement table = driver.findElement(By.id("table"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		System.out.println(rows.size());
		System.out.println(rows.get(3).getText());
	}

	@BeforeMethod
	public void setUp_BeforeMethod () {
		driver = BrowserFactory.getBrowser();
	}

	@AfterMethod
	public void closeBrowser () {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}

}