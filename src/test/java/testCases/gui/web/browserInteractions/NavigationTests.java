package testCases.gui.web.browserInteractions;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import testCases.gui.web.BaseTests;
@Epic("Browser Interactions")
@Feature("Navigation")
public class NavigationTests extends BaseTests {

	@Test
	public void verifyNavigator () {
		new BrowserActions(driver)
				.navigateToUrl("https://the-internet.herokuapp.com/")
				.refreshPage()
				.goForward()
				.navigateToUrl("https://github.com/ismail-elshafeiy")
				.goBack();
	}
}