package testCases.gui.web.ElementActions.Locators;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;
import testCases.gui.web.BaseTests;

import java.util.List;

@Feature("web")
@Epic("FindElements")
public class RelativeLocatorsTest extends BaseTests {


	/*
	 * Notes:
	 * - RelativeBy is a class that extends the ByClass
	 * - You can use Relative Locators to get a list of elements
	 * - User "getBoundingClientRect()" to return a DOMRect object
	 * */

	@Test
	public void testRelativeLocator () {
		driver.get("https://opensource-demo.orangehrmlive.com/");
		WebElement loginPanel = driver.findElement(By.id("logInPanelHeading"));
		WebElement credentials = driver.findElement(RelativeLocator.with(
						By.tagName("span"))
				.above(loginPanel));
		System.out.println(credentials.getText());
	}

	@Test
	public void testListOfElements () {
		List<WebElement> allSocialMedia = driver.findElements(with(
				By.tagName("img"))
				.near(By.id("footer")));

		for (WebElement socialMedia : allSocialMedia) {
			System.out.println(socialMedia.getAttribute("alt"));
		}
	}
}