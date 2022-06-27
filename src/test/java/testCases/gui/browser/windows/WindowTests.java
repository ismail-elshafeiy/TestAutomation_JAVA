package testCases.gui.browser.windows;


import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class WindowTests {
    public utils.WindowManager getWindowManager(){
        return new utils.WindowManager(driver);
    }
// TODO : TAU
    @Test
    public void testWindowTabs(){
        var buttonPage = homePage.clickDynamicLoading().rightClickOnExample2Link();
        getWindowManager().switchToNewTab();
        assertTrue(buttonPage.isStartButtonDisplayed(), "Start button is not displayed");
    }
}