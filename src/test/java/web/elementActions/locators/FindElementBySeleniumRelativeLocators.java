package web.elementActions.locators;

import com.engine.actions.ElementActions;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

import org.testng.annotations.Test;
import web.BaseTests;

import java.util.List;

@Epic("ElementActions")
@Feature("Locators")
public class FindElementBySeleniumRelativeLocators extends BaseTests {


	/*
	 * Notes:
	 * - RelativeBy is a class that extends the ByClass
	 * - You can use Relative Locators to get a list of elements
	 * - User "getBoundingClientRect()" to return a DOMRect object
	 * */

	@Test
	public void testRelativeLocator () {
		driver.get("https://demo.nopcommerce.com/login?returnUrl=%2F");
		By passwordField = By.id("Password");
		By emailField = RelativeLocator.with(By.tagName("input")).above(driver.findElement(passwordField));
		ElementActions.type(driver, emailField, "admin");
		ElementActions.type(driver, passwordField, "admin");
		System.out.println(driver.findElement(emailField).getText());
	}
	@Test
	public void testChainingRelativeLocator2 () {
		driver.get("https://demo.nopcommerce.com/login?returnUrl=%2F");
		By passwordField = By.id("Password");
		By emailField = RelativeLocator.with(By.tagName("input")).above(driver.findElement(passwordField));
		ElementActions.type(driver, emailField, "admin");
		ElementActions.type(driver, passwordField, "admin");
		System.out.println(driver.findElement(emailField).getText());
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