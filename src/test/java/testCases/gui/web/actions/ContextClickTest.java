package testCases.gui.web.actions;

import com.practice.gui.pages.homePage.HomePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import jdk.jfr.Description;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import static org.testng.AssertJUnit.assertEquals;

@Feature("web")
@Epic("Element Actions")
@Description("""
		Using Actions Class to perform context Click ( Right Mouse Click ) on Elements
		""")
public class ContextClickTest {

	private WebDriver driver;

	Actions actions;
	Alert alert;

	@Test
	public void testContextMenu () {
		actions = new Actions(driver);
		new HomePage(driver).navigateToHomePage("https://the-internet.herokuapp.com/context_menu");
		// perform context click ( right click ) on element
		actions.contextClick(driver.findElement(By.id("hot-spot")))
				.perform();
		// performing context click generates Alert
		// switching to the generated alert
		alert = driver.switchTo().alert();
		// accepts the generated alert
		alert.accept();
	}

	@Test
	public void testContextMenu2 () {
		BrowserActions.navigateToUrl(driver, "https://swisnl.github.io/jQuery-contextMenu/demo/accesskeys.html");
		WebElement clickMenuBtn = driver.findElement(By.cssSelector("span.context-menu-one.btn.btn-neutral"));
		WebElement contextMenu = driver.findElement(By.cssSelector("li.context-menu-item.context-menu-icon.context-menu-icon-edit"));
		Actions actions = new Actions(driver);
		actions.contextClick(clickMenuBtn)
				.moveToElement(contextMenu)
				.click()
				.perform();
		WebDriverWait wait = null;
		assert false;
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		assertEquals("clicked: edit", alert.getText());
		System.out.println(alert.getText());
		alert.dismiss();
	}

	@BeforeMethod
	public void setUp_BeforeMethod () {
		driver = BrowserFactory.getBrowser();
	}

	@AfterMethod(enabled = false)
	public void closeBrowser () {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}

}
