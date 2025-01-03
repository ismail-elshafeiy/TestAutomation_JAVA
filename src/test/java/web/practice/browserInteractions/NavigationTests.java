package web.practice.browserInteractions;

import com.engine.actions.BrowserActions;
import com.engine.evidence.ScreenShot;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import web.practice.base.BaseTests;


@Epic("Browser Interactions")
@Feature("Navigation")
public class NavigationTests extends BaseTests {

	@Test
	public void verifyNavigator () {
		Allure.parameter("Test Type", "Functional");
		new BrowserActions(driver)
				.navigateToUrl("https://the-internet.herokuapp.com/")
				.refreshPage()
				.goForward()
				.navigateToUrl("https://github.com/ismail-elshafeiy")
				.goBack();
		BrowserActions.getInstance().capturePageSnapshot();
		ScreenShot.takeFullScreenShoot(driver);
	}
}