package web.practice.browserInteractions;

import com.engine.actions.BrowserActions;
import com.engine.driver.DriverFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import web.practice.alerts.AlertsPage;
import web.practice.homePage.HomePage;

import static org.testng.Assert.assertEquals;
@Epic("Browser Interactions")
@Feature("Alerts")
@Story("Alerts")
public class AlertsTests {

	private WebDriver driver;

	@Test
	public void testGetTextFromAlert () {
		String text = new AlertsPage(driver).triggerConfirm_Btn()
				.getText_alert();
		new AlertsPage(driver).clickToDismiss_alert();
		assertEquals(text, "I am a JS Confirm", "Alert text incorrect");
	}



	@Test
	public void testSetInputInAlert () {
		String text = "TAU rocks!";
		String getText = new AlertsPage(driver).triggerPrompt_Btn()
				.setInput_alert(text)
				.clickToAccept_alert()
				.getResult();
		assertEquals(getText, "You entered: " + text, "Results text incorrect");
	}
	@Test
	public void testSetInputInAlert2 () {
		String text = "TAU rocks!";
		 new AlertsPage(driver).triggerPrompt_Btn();
		BrowserActions.alertAction(driver, text);
//		BrowserActions.alertAction(driver,GET_TEXT);
//		BrowserActions.alertAction(driver, ACCEPT);
	}


	@BeforeMethod
	public void setUp_BeforeMethod () {
		driver = DriverFactory.getBrowser();
		new HomePage(driver).navigateToHomePage("https://the-internet.herokuapp.com/javascript_alerts");
	}

	@AfterMethod(enabled = false)
	public void closeBrowser () {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}


}
