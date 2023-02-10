package examples.gui.web.javaScript;

import engine.Waits;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import examples.gui.web.BaseTests;

import static org.testng.AssertJUnit.assertEquals;

/**
 * <a href="https://www.softwaretestingmaterial.com/javascriptexecutor-selenium-webdriver/">...</a>
 * JavaScript executor is an interface that gives a mechanism to execute JavaScript through Selenium WebDriver.
 * It provides two methods such as “execute-script” & “executeAsyncScript” to run javascript on the currently
 * selected frame or window or page.
 */
@Feature("web")
@Epic("JavaScript Executor")
public class JavaScriptExecutorElementActionsTest extends BaseTests {

	@Test
	@Description("""
			send keys and click instead of selenium commands Test Case
						""")
	public void sendKeysAndClickByJS () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/login");
		((JavascriptExecutor) driver).executeScript("document.getElementById('username').value='tomsmith';");
		((JavascriptExecutor) driver).executeScript("document.getElementById('password').value='SuperSecretPassword!';");
		WebElement loginButton = driver.findElement(By.cssSelector("button.radius"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
	}

	@Test
	@Description("""
				click on a SubMenu which is only visible on mouse hover on Menu
			 	 Hover on Automation Menu on the MenuBar
			 	 -> "$('ul.menus.menu-secondary.sf-js-enabled.sub-menu li').hover()"
			""")
	public void hoverAndClickOnMenu () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/hovers");
	}


	@Test
	@Description("""
			Vertical scroll down by 3000 pixels and horizontal by 200 pixels by points
			js.executeScript("window.scrollBy(x,y)")
			""")
	public void ScrollDownVerticalAndHorizontalByPoint () {
		BrowserActions.navigateToUrl(driver, " https://the-internet.herokuapp.com/large");
		((JavascriptExecutor) driver).executeScript("window.scrollBy(500,3000)");
//        for  we can use the code like
//        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	@Test
	@Description("scrolling till the bottom of the page")
	public void ScrollDownToPageBottom () {
		BrowserActions.navigateToUrl(driver, " https://the-internet.herokuapp.com/large");
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	By amazonLogoFooter = By.cssSelector("div.nav-logo-base.nav-sprite");

	@Test
	@Description("scrolling to the logo in footer")
	public void scrollToBottom () {
		BrowserActions.navigateToUrl(driver, "https://www.amazon.com/");
		((JavascriptExecutor) driver).executeScript("scrollBy(0,document.body.scrollHeight)");
		Assert.assertTrue(driver.findElement(amazonLogoFooter).isDisplayed());
	}

	@Test
	@Description("scrolling to the logo in footer")
	public void scrollDownToElement () {
		BrowserActions.navigateToUrl(driver, "https://www.amazon.com/");
		WebElement element = driver.findElement(amazonLogoFooter);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Waits.getExplicitWait(driver).until(ExpectedConditions.visibilityOf(element));
		Assert.assertTrue(driver.findElement(amazonLogoFooter).isDisplayed());
	}

	@Test
	@Description("""
			- Select Date from DatePicker 
			""")
	public void selectDateByJs () {
		BrowserActions.navigateToUrl(driver, "http://spicejet.com/");
		By date = By.xpath("(//div[@class='css-76zvg2 css-bfa6kz r-homxoj r-ubezar'])[1]");
		String dateVal = "30-12-2017";
		selectDateByJS(driver, date, dateVal);
	}

	@Test
	@Description("""
			- Change color of an element 
			""")
	public void changeColor () {
		BrowserActions.navigateToUrl(driver, " https://the-internet.herokuapp.com/");
		By element = By.xpath("//h1[@class='heading']");
		changeColor(driver, element, "red");
		flash(driver, element);
	}
	public static void changeColor (WebDriver driver, By element, String color) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", driver.findElement(element));
		try {
			Thread.sleep(20);
		} catch (InterruptedException ignored) {
		}
	}

	public static void flash (WebDriver driver, By element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String bgcolor = driver.findElement(element).getCssValue("backgroundColor");
		for (int i = 0; i < 10; i++) {
			changeColor(driver, element, "rgb(0,200,0)");//1
			changeColor(driver, element, bgcolor);//2
		}
	}

	public static void selectDateByJS (WebDriver driver, By element, String dateVal) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].setAttribute('value','" + dateVal + "');", driver.findElement(element));
	}
}
