package engine.gui.actions;

import engine.broswer.Waits;
import engine.tools.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.testng.Assert.fail;

public class ElementHelper {
	public enum SelectBy {
		TEXT, VALUE, INDEX
	}

	public static void locatingElementStrategy (WebDriver driver, By elementLocator) {
		try {
			// Wait for the element to be visible
			Waits.getExplicitWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
			Waits.getFluentWait(driver, 2).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
			// Scroll the element into view to handle some browsers cases
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(elementLocator));
			// Check if the element is displayed
			if (!driver.findElement(elementLocator).isDisplayed()) {
				Logger.logStep("The element [" + elementLocator.toString() + "] is not Displayed");
				fail("The element [" + elementLocator + "] is not Displayed");
			}
		} catch (Exception toe) {
			Logger.logMessage(toe.getMessage());
			fail(toe.getMessage());
		}
	}

/*	public static void locatingElementStrategy (MobileDriver<MobileElement> mobile, By elementLocator) {
		try {
			Waits.getExplicitWait(mobile).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
			if (!mobile.findElement(elementLocator).isDisplayed()) {
				Logger.logStep("The element [" + elementLocator.toString() + "] is not Displayed");
				fail("The element [" + elementLocator + "] is not Displayed");
			}
		} catch (TimeoutException toe) {
			Logger.logMessage(toe.getMessage());
			fail(toe.getMessage());
		} catch (Exception e) {
			Logger.logMessage(e.getMessage());
			fail(e.getMessage());
		}
	}*/
	public static void verifyLink (String urlLink) {
		try {
			URL link = new URL(urlLink);
			HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
			httpConn.setConnectTimeout(2000);
			httpConn.connect();
			if (httpConn.getResponseCode() == 200) {
				System.out.println("[" + urlLink + "] - " + httpConn.getResponseMessage() + " - " + httpConn.getResponseCode());
			} else if (httpConn.getResponseCode() == 404) {
				System.out.println("[" + urlLink + "] - " + httpConn.getResponseMessage() + " - " + httpConn.getResponseCode());
			} else {
				System.out.println("[" + urlLink + "] - " + httpConn.getResponseMessage() + " - " + httpConn.getResponseCode());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getLinks (WebElement elementLocator) {
		List<WebElement> links = elementLocator.findElements(By.tagName("a"));
		System.out.println("The Number of The links is : " + links.size());
		for (WebElement Link : links) {
			System.out.println(Link.getAttribute("href"));
		}
	}


	public static void getCellFromTable (List<WebElement> rows, By tagName) {
		for (WebElement row : rows) {
			List<WebElement> cols = row.findElements(tagName);
			for (WebElement cell : cols) {
				System.out.println(cell.getText() + "\t");
			}
			// To Print Empty line between each row
			System.out.println();
		}
	}
}