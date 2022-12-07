package engine.gui.actions;

import engine.tools.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import static org.testng.Assert.fail;

public class DeviceActions {
	public DeviceActions (WebDriver driver) {
		this.driver = driver;
	}

	private WebDriver driver;

	public DeviceActions mouseHover (By elementLocator) {
		mouseHover(driver, elementLocator);
		return this;
	}

	public static void mouseHover (WebDriver driver, By elementLocator) {
		ActionsHelper.locatingElementStrategy(driver, elementLocator);
		try {
			Actions actions = new Actions(driver);
			Logger.logStep("[Element Action] Hover on [" + driver.findElement(elementLocator).getText() + "]");
			actions.moveToElement(driver.findElement(elementLocator)).perform();
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
			fail(e.getMessage());
		}
	}
	public static void clickAndHold (WebDriver driver, By elementLocator, int xOffset, int yOffset) {
		ActionsHelper.locatingElementStrategy(driver, elementLocator);
		try {
			Actions actions = new Actions(driver);
			Logger.logStep("[Element Action] Click and hold on [" + driver.findElement(elementLocator).getText() + "]");
			actions.clickAndHold(driver.findElement(elementLocator))
					.moveByOffset(xOffset, yOffset)
					.release()
					.build()
					.perform();
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
			fail(e.getMessage());
		}
	}

	public static void clickAndHold (WebDriver driver, By elementLocator, By toElementLocator2) {
		ActionsHelper.locatingElementStrategy(driver, elementLocator);
		try {

			Actions actions = new Actions(driver);
			Logger.logStep("[Element Action] Click and hold on [" + driver.findElement(elementLocator).getText() + "]");
			actions.clickAndHold(driver.findElement(elementLocator))
					.moveToElement(driver.findElement(toElementLocator2))
					.build()
					.perform();
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
			fail(e.getMessage());
		}
	}
	public DeviceActions doubleClick (By elementLocator) {
		doubleClick(driver, elementLocator);
		return this;
	}

	public static void doubleClick (WebDriver driver, By elementLocator) {
		ActionsHelper.locatingElementStrategy(driver, elementLocator);
		try {
			Actions actions = new Actions(driver);
			Logger.logStep("[Element Action] Double Click on element [" + elementLocator + "]");
			actions.doubleClick(driver.findElement(elementLocator)).perform();
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
			fail(e.getMessage());
		}
	}

	public static void dragAndDrop (WebDriver driver, By sourceLocator, By targetLocator) {
		ActionsHelper.locatingElementStrategy(driver, sourceLocator);
		try {
			Actions actions = new Actions(driver);
			Logger.logStep("[Element Action] Drag and Drop [" + driver.findElement(sourceLocator).getText() + "] to [" + driver.findElement(targetLocator).getText() + "]");
			actions.dragAndDrop(driver.findElement(sourceLocator), driver.findElement(targetLocator)).perform();
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
			fail(e.getMessage());
		}
	}
	public static void clickAndSendKeys (WebDriver driver, By elementLocator, String text) {
		ActionsHelper.locatingElementStrategy(driver, elementLocator);
		try {
			Logger.logStep("[Element Action] Sending Keys [" + text + "] to element [" + elementLocator + "]");
			((Actions) driver)
					.click(driver.findElement(elementLocator))
					.sendKeys(text).build().perform();
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
		}
	}
}
