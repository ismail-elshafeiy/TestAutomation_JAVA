package testCases.gui.web.ElementActions.Locators;

import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

@Feature("web")
@Epic("FindElements")
public class RelativeXpathTest {
	private WebDriver driver;

	@Test
	public void SearchAndAssertTheFirstResultText () {
		driver.get("https://www.google.com/ncr");
		driver.findElement(By.xpath("//input[@name='q']")).sendKeys("Selenium", Keys.ENTER);
		String firstSearchResultText = driver.findElement(By.xpath("(//h3[@class='LC20lb'])" + "[" + 1 + "]")).getText();
		Assert.assertTrue(firstSearchResultText.contains("Selenium"));
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
