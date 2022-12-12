package testCases.gui.web.browserInteractions;

import engine.broswer.BrowserActions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import testCases.gui.web.BaseTests;

@Epic("Browser Interactions")
@Feature("Windows")
public class WindowsTests extends BaseTests {
	@Story("Window management  ")
	@Test(description = "Minimize the Browser Window")
	public void testMinimizeWindows () {
		BrowserActions.navigateToUrl(driver, "https://www.w3schools.com/");
		BrowserActions.minimizeWindow(driver);
	}

	@Story("Window management ")
	@Test(description = "Full Screen the Browser Window")
	@Description("Fills the entire screen, similar to pressing F11 in most browsers")
	public void testFullScreenWindow () {
		BrowserActions.navigateToUrl(driver, "https://www.w3schools.com/");
		BrowserActions.fullScreenWindow(driver);
	}

	@Story("Window management ")
	@Test(description = "Set and Get Window Position")
	@Description("""
			- Move the window to the top left of the primary monitor
			- Fetches the coordinates of the top left coordinate of the browser window.""")
	public void testSetWindowPosition () {
		BrowserActions.navigateToUrl(driver, "https://www.w3schools.com/");
		BrowserActions.setWindowPositions(driver, 100, 500);
		BrowserActions.getWindowPositions(driver);
	}

	@Story("Window management ")
	@Test(description = "Set window size")
	public void testSetWindowSize () {
		BrowserActions.navigateToUrl(driver, "https://www.w3schools.com/");
		BrowserActions.setWindowSize(driver, 601, 962);
		BrowserActions.getWindowSize(driver);
	}

	@Story("Windows and tabs")
	@Test(description = "Switch To New Window or New Tab")
	public void testSwitchNewWindow () {
		BrowserActions.navigateToUrl(driver, "https://www.w3schools.com/");
		BrowserActions.switchToNewPage(driver, BrowserActions.PageType.TAB, "https://www.w3schools.com/");
		BrowserActions.switchToNewPage(driver, BrowserActions.PageType.WINDOW, "https://www.w3schools.com/");
	}

	@Story("Windows and tabs")
	@Test(description = "Switch To New Window and focus on the child Window then Switch Back to the Parent Window")
	@Description("""
			Clicking a link which opens in a new window will focus the new window or tab on screen,
			but WebDriver will not know which window the Operating System considers active
			To work with the new window you will need to switch to it
			""")
	public void testSwitchToWindowAndFocus () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/windows");
		//Store WindowHandle of parent window
		String parentWindow = driver.getWindowHandle();
		BrowserActions.switchToNewWindowByClickHyperLink(driver, By.xpath("//a[contains(text(),'Click Here')]"));
		By textNewWindow = By.xpath("//h3[contains(text(),'New Window')]");
		Assert.assertTrue(driver.findElement(textNewWindow).isDisplayed());
		System.out.println("The text is displayed " + driver.findElement(textNewWindow).getText());
		driver.close();
		driver.switchTo().window(parentWindow);
	}

}




