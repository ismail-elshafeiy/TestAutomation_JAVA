package testCases.gui.web.ElementActions.Locators;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import engine.broswer.BrowserActions;
import testCases.gui.web.BaseTests;

@Feature("web")
@Epic("FindElements")
public class FindLinksByTest extends BaseTests {
	@Test
	public void textLinkText () {
		BrowserActions.navigateToUrl(driver,"http://the-internet.herokuapp.com/dropdown");
		WebElement seleniumLink = driver.findElement(By.linkText("Elemental Selenium"));
		System.out.println("The Link = " + seleniumLink.getAttribute("href"));
	}

	@Test
	public void textPartialText () {
		BrowserActions.navigateToUrl(driver,"http://the-internet.herokuapp.com/dropdown");
		WebElement seleniumLink = driver.findElement(By.partialLinkText("Elemental"));
		System.out.println("The Link 2 = " + seleniumLink.getAttribute("href"));
	}
}
