package gui.pages.keys;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import utilities.actions.ElementActions;

public class KeyPressesPage {

    private WebDriver driver;
    private By inputField = By.id("target");
    private By resultText = By.id("result");

    public KeyPressesPage(WebDriver driver) {
        this.driver = driver;
    }

    public KeyPressesPage enterText(String text) {
        ElementActions.type(driver, inputField, text);
        ElementActions.clickKeyboardKey(driver, inputField,Keys.BACK_SPACE);
        return this;
    }

    public KeyPressesPage enterPi() {
        enterText(Keys.chord(Keys.ALT, "p") + "=3.14");
        return this;
    }

    public String getResult() {
        return ElementActions.getText(driver, resultText);

    }

    // TODO create more Methods for Keyboard

}
