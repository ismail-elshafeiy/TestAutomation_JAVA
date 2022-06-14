package gui.pages.inputs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.actions.ElementActions;

public class LoginPage {

    private WebDriver driver;
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("#login button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage setUsername(String userName) {
        ElementActions.type(driver, usernameField, userName, true);
        return this;
    }

    public LoginPage setPassword(String password) {
        ElementActions.type(driver, passwordField, password);
        return this;
    }

    public SecureAreaPage clickLoginButton() {
        ElementActions.click(driver, loginButton);
        return new SecureAreaPage(driver);
    }
}