package testAutomationU.exercises.chapter7.contextmenu;

import testAutomationU.base.BaseTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ContextMenuTests {

    @Test
    public void testRightClick(){
        var menuPage = homePage.clickContextMenu();
        menuPage.rightClickInBox();
        String message = menuPage.getPopUpText();
        menuPage.acceptPopUp();
        assertEquals(message, "You selected a context menu", "Popup message incorrect");
    }
}
