package examples.gui.web.browserInteractions;


import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import examples.gui.web.BaseTests;

@Epic("Browser Interactions")
@Feature("Windows")
@Story("Cookies")
public class CookiesTests extends BaseTests {

	Select select;

	@Test
	public void AddCookie () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/");
		BrowserActions.cookieBuilder(driver, BrowserActions.CookieBuilderType.ADD,
				"optimizelyBucketsss",
				"%7B%TD",
				"the-internet.herokuapp.com");
	}

	@Test
	public void getAllCookies () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/");
		BrowserActions.getAllCookies(driver);
	}

	@Test
	public void deleteCookie () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/");
		BrowserActions.cookieBuilder(driver, BrowserActions.CookieBuilderType.DELETE,
				"optimizelyBucketsss",
				"%7B%TD",
				"the-internet.herokuapp.com");
	}

	@Test
	public void testCookies () {
		WebElement languageSelect = driver.findElement(By.id("select-language"));
		select = new Select(languageSelect);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "English");

		//Store Cookies should be none (null)
		Cookie storeCookie = driver.manage().getCookieNamed("store");
		Assert.assertEquals(storeCookie, null);

		//Select German Language
		select.selectByVisibleText("German");

		//Store Cookie Should be populated with selected country
		storeCookie = driver.manage().getCookieNamed("store");
		Assert.assertEquals(storeCookie.getValue(), "german");
		System.out.println(storeCookie.getValue());

	}

}
