package testCases.gui.alerts;

import gui.pages.alerts.AlertsPage;
import gui.pages.homePage.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

import static org.testng.Assert.assertEquals;

public class Alerts_Tests {

    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
        new HomePage(driver).navigateTo_homePage("https://the-internet.herokuapp.com/javascript_alerts");
    }

    @AfterMethod(enabled = false)
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }


    @Test
    public void testAcceptAlert() {
        String textResult = new AlertsPage(driver).triggerAlert_Btn()
                .clickToAccept_alert()
                .getResult();
        assertEquals(textResult, "You successfully clicked an alert", "Results text incorrect");
    }

    @Test
    public void testGetTextFromAlert() {
        String text = new AlertsPage(driver).triggerConfirm_Btn()
                .getText_alert();
        new AlertsPage(driver).clickToDismiss_alert();
        assertEquals(text, "I am a JS Confirm", "Alert text incorrect");
    }

    @Test
    public void testSetInputInAlert() {

        String text = "TAU rocks!";
        String getText = new AlertsPage(driver).triggerPrompt_Btn()
                .setInput_alert(text)
                .clickToAccept_alert()
                .getResult();
        assertEquals(getText, "You entered: " + text, "Results text incorrect");
    }
@Test
    public void testInput(){
}

}
