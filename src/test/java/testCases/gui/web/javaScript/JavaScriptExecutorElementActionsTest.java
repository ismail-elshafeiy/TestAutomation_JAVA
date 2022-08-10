package testCases.gui.web.javaScript;

import engine.broswer.Waits;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import static org.testng.AssertJUnit.assertEquals;

/**
 * <a href="https://www.softwaretestingmaterial.com/javascriptexecutor-selenium-webdriver/">...</a>
 * JavaScript executor is an interface that gives a mechanism to execute JavaScript through Selenium WebDriver.
 * It provides two methods such as “execute-script” & “executeAsyncScript” to run javascript on the currently
 * selected frame or window or page.
 */
@Feature("web")
@Epic("JavaScript Executor")
public class JavaScriptExecutorElementActionsTest {
	private WebDriver driver;

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
	public void testContextMenu() {
		BrowserActions.navigateToUrl(driver, "https://swisnl.github.io/jQuery-contextMenu/demo/accesskeys.html");
		WebElement clickMenuBtn = driver.findElement(By.cssSelector("span.context-menu-one.btn.btn-neutral"));
		WebElement contextMenu = driver.findElement(By.cssSelector("li.context-menu-item.context-menu-icon.context-menu-icon-edit"));
		Actions actions = new Actions(driver);
		actions.contextClick(clickMenuBtn)
				.moveToElement(contextMenu)
				.click()
				.perform();
		WebDriverWait wait = null;
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertEquals("clicked: edit", alert.getText());
		System.out.println(alert.getText());
		alert.dismiss();
	}

	@BeforeMethod
	public void setUp () {
		driver = BrowserFactory.getBrowser();
	}

	@AfterMethod(enabled = false)
	public void tearDown (ITestResult result) {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}

}
