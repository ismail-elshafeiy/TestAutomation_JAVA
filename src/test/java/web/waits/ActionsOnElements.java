package web.waits;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionsOnElements {
    public static WebDriverWait wait ;
    public static WebDriver driver;

    /*
     * This method takes the retrieved elements and waits for it until the element is
     * visible and locates it
     */
    public static WebElement locateElement(By locator) {
        wait.until(ExpectedConditions.visibilityOf(retrieveElement(locator)));
        return driver.findElement(locator);
    }

    /*
     * This Method makes the driver waits for specific period of time until the Element is present on the DOM
     * then it retrieves the element and locate it
     */
     public static WebElement retrieveElement(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElement(locator);
    }
    /*
     * This Method makes the driver wait for the element to be clickable first then clicks it
     */
    public static void clickOn(WebElement element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    /*
     * This Method waits for the element to be present and visible and then send value to it
     */
    public static void sendTextTo(WebElement element, String value){
        locateElement((By) element).sendKeys(value);
    }
}
