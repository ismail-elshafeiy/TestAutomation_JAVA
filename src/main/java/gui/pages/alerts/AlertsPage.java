package gui.pages.alerts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.actions.ElementActions;

public class AlertsPage {

    // driver
    private static WebDriver driver;

    // Constructor
    public AlertsPage(WebDriver driver) {
        this.driver = driver;
    }

    //////////////////////////// Elements Locators ////////////////////////////
    private By triggerAlertButton = By.xpath(".//button[text()='Click for JS Alert']");
    private By triggerConfirmButton = By.xpath(".//button[text()='Click for JS Confirm']");
    private By triggerPromptButton = By.xpath(".//button[text()='Click for JS Prompt']");

    private By results = By.id("result");


    //////////////////////////// Business Actions ////////////////////////////

    public AlertsPage triggerAlert_Btn() {
        ElementActions.click(driver, triggerAlertButton);
        return this;
    }

    public AlertsPage triggerConfirm_Btn() {
        ElementActions.click(driver, triggerConfirmButton);
        return this;
    }

    public AlertsPage triggerPrompt_Btn() {
        ElementActions.click(driver, triggerPromptButton);
        return this;
    }

    public AlertsPage clickToAccept_alert() {
        driver.switchTo().alert().accept();
        return this;
    }

    public AlertsPage clickToDismiss_alert() {
        driver.switchTo().alert().dismiss();
        return this;
    }

    public String getText_alert() {
        return driver.switchTo().alert().getText();
    }

    public AlertsPage setInput_alert(String text) {
        driver.switchTo().alert().sendKeys(text);
        return this;
    }

    public String getResult() {
        return ElementActions.getText(driver, results);
    }


}
