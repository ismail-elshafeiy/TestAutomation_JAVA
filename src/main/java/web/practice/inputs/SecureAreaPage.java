package web.practice.inputs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.engine.actions.ElementActions;

public class SecureAreaPage {

    private static WebDriver driver;
    private static By statusAlert = By.id("flash");

    public SecureAreaPage(WebDriver driver) {
        this.driver = driver;
    }

    public static String getAlertText() {
        return ElementActions.getText(driver, statusAlert);
    }
}
