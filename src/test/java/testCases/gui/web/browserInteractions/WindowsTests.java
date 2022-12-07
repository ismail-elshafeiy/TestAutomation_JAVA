package testCases.gui.web.browserInteractions;

import engine.broswer.BrowserActions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import testCases.gui.web.base.BaseTests;

@Epic("Browser Interactions")
@Feature("Windows")
public class WindowsTests extends BaseTests {
	@Story("Set Window Position")
	@Test(description = "Minimize the Browser Window")
	public void testMinimizeWindows () {
		BrowserActions.navigateToUrl(driver, "https://www.w3schools.com/");
		BrowserActions.minimizeWindow(driver);
	}
	@Story("Set Window Position")
	@Test(description = "Full Screen the Browser Window")
	@Description("Fills the entire screen, similar to pressing F11 in most browsers")
	public void testFullScreenWindow () {
		BrowserActions.navigateToUrl(driver, "https://www.w3schools.com/");
		BrowserActions.fullScreenWindow(driver);
	}
	@Story("Set Window Position")
	@Test(description = "Set and Get Window Position")
	@Description("""
			- Move the window to the top left of the primary monitor
			- Fetches the coordinates of the top left coordinate of the browser window.""")
	public void testSetWindowPosition () {
		BrowserActions.navigateToUrl(driver, "https://www.w3schools.com/");
		BrowserActions.setWindowPositions(driver, 100, 500);
		BrowserActions.getWindowPositions(driver);
	}
	@Story("Set Window Position")
	@Test(description = "Set window size")
	@Description("")
	public void testSetWindowResolution () {
		BrowserActions.navigateToUrl(driver, "https://www.w3schools.com/");
		BrowserActions.setWindowResolution(driver, 400, 568);
	}
//TODO: Add test for windows handling
}
