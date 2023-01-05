package examples.gui.web.elementActions.locators;

import engine.broswer.BrowserActions;
import engine.gui.actions.ElementActions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import examples.gui.web.BaseTests;

@Epic("ElementActions")
@Feature("Locators")
public class FindElementByRelativeLocator extends BaseTests {


	@Test
	public void testFindRelativeXpath () {
		BrowserActions.navigateToUrl(driver, "http://the-internet.herokuapp.com/dropdown");
		// by Tag Name of the element
		WebElement userNameTxt = driver.findElement(By.xpath("//input"));
		// By Tag Name and [index]
		WebElement passwordTxt = driver.findElement(By.xpath("//input[1]"));
		// syntax
		WebElement loginBtn = driver.findElement(By.xpath("//button[@class='radius']"));

		System.out.println(userNameTxt.getTagName());
		System.out.println(passwordTxt.getTagName());
		System.out.println(loginBtn.getText());
	}

	@Test
	public void SearchAndAssertTheFirstResultText () {
		driver.get("https://www.google.com/ncr");
		driver.findElement(By.xpath("//input[@name='q']")).sendKeys("Selenium", Keys.ENTER);
		String firstSearchResultText = driver.findElement(By.xpath("(//h3[@class='LC20lb'])" + "[" + 1 + "]")).getText();
		Assert.assertTrue(firstSearchResultText.contains("Selenium"));
	}

	@Test
	@Description("""
			- //tagName[contains(@attribute, 'value')]
			- //div[contains(@class, 'className')][contains(., 'value')]
			- //div[contains(@class, 'result__snippet')][not(contains(., 'bamboo'))]
			- (//div[@class='className']/div[@class='className']//h3)[2][contains(.,'value')]
			- //div[contains(text(),'textName')]
			""")
	public void xpathByFunctionContains () {
		BrowserActions.navigateToUrl(driver, "https://demo.nopcommerce.com/");
		By attributeContains = By.xpath("//h2[contains(@class,'tit')]");
		System.out.println("The xpath attributeContains = " + driver.findElement(attributeContains).getText());
		By containsAndText = By.xpath("//ul[contains(@class,'top-menu not')]//a[contains(text(),'Computers')]");
		System.out.println("The xpath containsAndText = " + driver.findElement(containsAndText).getText());
		By containsAndContainsIndex = By.xpath("(//div[contains(@class,'pict')]//img[contains(@alt,'Picture')])[1]");
		ElementActions.click(driver, containsAndContainsIndex);
		System.out.println("The xpath ContainsAndContainsIndex = " + driver.findElement(containsAndContainsIndex).getText());
	}

	@Test
	@Description("""
			- //tagName[text()=‘textName']
			""")
	public void xpathByFunctionText () {
		BrowserActions.navigateToUrl(driver, "https://demo.nopcommerce.com/");
		By text = By.xpath("//div[text()='Categories']");
		System.out.println("The xpath text = " + driver.findElement(text).getText());
	}

	@Test
	@Description("""
			- Xpath    //input[@type='submit' and @name='btnLogin']
			- //tagName [@attribute='value' and @attribute='value']
			- //img[@width<20 and @height<20]
			- CssSelector  tagName[attribute1='value1'][attribute2='value2']
			""")
	public void xpathByFunctionMultipleAttributeByOr_And () {
		BrowserActions.navigateToUrl(driver, "https://demo.nopcommerce.com/register?returnUrl=%2Felectronics");
		By multipleAttributeXpath = By.xpath("//input[@id='FirstName' and @name='FirstName']");
		ElementActions.type(driver, multipleAttributeXpath, "ismail");
		By multipleAttributeCss = By.cssSelector("input[id='LastName'][name='LastName']");
		ElementActions.type(driver, multipleAttributeCss, "elshafeiy");
	}
}
