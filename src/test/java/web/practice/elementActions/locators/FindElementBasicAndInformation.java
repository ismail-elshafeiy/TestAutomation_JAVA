package web.elementActions.locators;

import com.engine.actions.ElementActions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import com.engine.actions.BrowserActions;
import web.BaseTests;

@Epic("ElementActions")
@Feature("Locators")
public class FindElementBasicAndInformation extends BaseTests {
	// Selenium provides support for these 8 traditional location strategies in WebDriver:
	@Test
	@Description("""
						Locates elements whose ID attribute matches the search value
			<input type="text" name="username" id="username" spellcheck="false" data-ms-editor="true" fdprocessedid="f3l1fq">""")
	public void ById () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/login");
		By userName_txtBox = By.id("username");
		ElementActions.type(driver, userName_txtBox, "ismail");
		// check if the connected Element is displayed on a webpage.
		System.out.println("Is Displayed = " + driver.findElement(userName_txtBox).isDisplayed());
		// check if the connected Element is enabled or disabled on a webpage.
		System.out.println("Is Enabled = " + driver.findElement(userName_txtBox).isEnabled());
		// fetch the TagName of the referenced Element
		System.out.println("The Tag Name = " + driver.findElement(userName_txtBox).getTagName());
		System.out.println("The Rect = " + driver.findElement(userName_txtBox).getRect());
		System.out.println("The Attribute name = " + driver.findElement(userName_txtBox).getAttribute("id"));
		// Retrieves the rendered text content of the specified element
		System.out.println("The text = " + driver.findElement(userName_txtBox).getText());
		System.out.println("The size = " + driver.findElement(userName_txtBox).getSize());
		System.out.println("The getAccessibleName = " + driver.findElement(userName_txtBox).getAccessibleName());
		// Retrieves the value of specified computed style property of an element
		System.out.println("The background-color = " + driver.findElement(userName_txtBox).getCssValue("background-color"));
		System.out.println("The color = " + driver.findElement(userName_txtBox).getCssValue("color"));
		System.out.println("The font-size = " + driver.findElement(userName_txtBox).getCssValue("font-size"));
		System.out.println("The font-family = " + driver.findElement(userName_txtBox).getCssValue("font-family"));
		System.out.println("The font-weight = " + driver.findElement(userName_txtBox).getCssValue("font-weight"));
		System.out.println("The font-style = " + driver.findElement(userName_txtBox).getCssValue("font-style"));
		Rectangle rect = driver.findElement(userName_txtBox).getRect();
		System.out.println("The X = " + rect.getX());
		System.out.println("The Y = " + rect.getY());
		System.out.println("The Width = " + rect.getWidth());
		System.out.println("The Height = " + rect.getHeight());
	}

	@Test
	@Description("""
					 Locates elements whose NAME attribute matches the search value
			<input type="text" name="username" id="username" spellcheck="false" data-ms-editor="true" fdprocessedid="f3l1fq">""")
	public void ByName () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/login");
		By userName_txtBox = By.name("username");
		ElementActions.type(driver, userName_txtBox, "ismail");
		System.out.println("The Attribute name = " + driver.findElement(userName_txtBox).getAttribute("name"));
	}


	@Test
	@Description("""
					 Locates anchor elements whose visible text matches the search value
			<a target="_blank" href="http://elementalselenium.com/">Elemental Selenium</a>""")
	public void ByLinkText () {
		BrowserActions.navigateToUrl(driver, "http://the-internet.herokuapp.com/dropdown");
		By seleniumLink = By.linkText("Elemental Selenium");
		System.out.println("The Link = " + driver.findElement(seleniumLink).getAttribute("href"));
	}

	@Test
	@Description("""
						Locates anchor elements whose visible text contains the search value
			If multiple elements are matching, only the first one will be selected
			<a target="_blank" href="http://elementalselenium.com/">Elemental Selenium</a>""")
	public void ByPartialLinkText () {
		BrowserActions.navigateToUrl(driver, "http://the-internet.herokuapp.com/dropdown");
		By seleniumLink = By.partialLinkText("Elemental");
		System.out.println("The Link  = " + driver.findElement(seleniumLink).getAttribute("href"));
	}

	@Test
	@Description("""
					 Locates elements whose tag name matches the search value
			<input type="text" name="username" id="username" spellcheck="false" data-ms-editor="true" fdprocessedid="f3l1fq">""")
	public void ByTagName () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/login");
		By userName_txtBox = By.tagName("input");
		ElementActions.type(driver, userName_txtBox, "ismail");
		System.out.println("The Attribute name = " + driver.findElement(userName_txtBox).getAttribute("tagName"));
	}

	@Test
	@Description("""
				Locates elements whose class name contains the search value (compound class names are not permitted)
			<a target="_blank" href="http://elementalselenium.com/">Elemental Selenium</a>""")
	public void ByClass () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/login");
		By userName_txtBox = By.className("large-6 small-12 columns");
		ElementActions.type(driver, userName_txtBox, "ismail");
		System.out.println("The class name = " + driver.findElement(userName_txtBox).getAttribute("href"));
		System.out.println("The class name = " + driver.findElement(userName_txtBox).getClass());
	}

	/**
	 * Notes:
	 * - getRect method. It returns a position and dimension of an element = 2 methods: getLocation and getSize.
	 * - position (x,y) --> X = horizontally from the left to the right, Y = vertically from the top to the bottom.
	 * - get position from DOM by  open Properties to see the location.
	 * - dimension (width, height) --> measure the size of an element.
	 * - get dimension from DOM by hover on element.
	 */
	@Test
	public void getPositionDimension () {
		BrowserActions.navigateToUrl(driver, "https://testautomationu.applitools.com/learningpaths.html");
		WebElement logoTAU = driver.findElement(By.xpath("//div[@id='app']//header/a/img"));
		Rectangle rectTAULogo = logoTAU.getRect();
		System.out.println("The position of TAU logo = " + rectTAULogo);
		System.out.println("x: " + rectTAULogo.getX());
		System.out.println("y: " + rectTAULogo.getY());
		System.out.println("Width: " + rectTAULogo.getWidth());
		System.out.println("Height: " + rectTAULogo.getHeight());
	}
}
