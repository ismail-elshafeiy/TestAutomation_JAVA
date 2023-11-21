package webPractice.browserInteractions;

import com.engine.evidence.ScreenShot;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import com.engine.actions.BrowserActions;
import webPractice.BaseTests;

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
        ScreenShot.takeFullScreenShoot(driver);
	}
}