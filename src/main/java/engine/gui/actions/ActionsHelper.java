package engine.gui.actions;

import engine.broswer.Waits;
import engine.tools.Logger;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.testng.Assert.fail;

public class ActionsHelper {
	public enum SelectBy {
		TEXT, VALUE, INDEX
	}

	public static void locatingElementStrategy (WebDriver driver, By elementLocator) {
		try {
			// Wait for the element to be visible
			Waits.getExplicitWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
			// Scroll the element into view to handle some browsers cases
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(elementLocator));
			// Check if the element is displayed
			if (!driver.findElement(elementLocator).isDisplayed()) {
				Logger.logStep("The element [" + elementLocator.toString() + "] is not Displayed");
				fail("The element [" + elementLocator.toString() + "] is not Displayed");
			}
		} catch (Exception toe) {
			Logger.logMessage(toe.getMessage());
			fail(toe.getMessage());
		}
	}

	public static void locatingElementStrategy (MobileDriver<MobileElement> mobile, By elementLocator) {
		try {
			Waits.getExplicitWait(mobile).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
			if (!mobile.findElement(elementLocator).isDisplayed()) {
				Logger.logStep("The element [" + elementLocator.toString() + "] is not Displayed");
				fail("The element [" + elementLocator.toString() + "] is not Displayed");
			}
		} catch (TimeoutException toe) {
			Logger.logMessage(toe.getMessage());
			fail(toe.getMessage());
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
			fail(e.getMessage());
		}
	}
}