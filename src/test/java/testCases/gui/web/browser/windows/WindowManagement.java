package testCases.gui.web.browser.windows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import java.util.Iterator;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class WindowManagement {
	private WebDriver driver;

	@BeforeMethod
	public void setUp_BeforeMethod() {
		driver = BrowserFactory.getBrowser();
		driver.get("http://automationpractice.com/index.php");
		System.out.println("Title: " + driver.getTitle());
	}

	@AfterMethod
	public void closeBrowser() {
		BrowserActions.closeAllOpenedBrowserWindows(driver);

	}

	/*
	* Notes:
	* - An alphanumeric id assigned to each window Handle.
	* -
	* */
    @Test
    public void testNewWindowTab() {
	WebDriver newWindow = driver.switchTo().newWindow(WindowType.WINDOW);
	newWindow.get("http://automationpractice.com/index.php?controller=prices-drop");
	System.out.println("Title: " + driver.getTitle());
    }

    @Test
    public void testWorkingInBothWindowTabs() {
	// Automatically Open & Switch To The New Window Or Tab
	String mainWindowHandle = driver.getWindowHandle();
	driver.switchTo().newWindow(WindowType.TAB)
		.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
	System.out.println("Title: " + driver.getTitle());

	// Work In The New Window Or Tab
	driver.findElement(By.id("email_create")).sendKeys("Selenium4@TAU.com");
	driver.findElement(By.id("SubmitCreate")).click();

	// Get The Window ID Handles

	// purpose of getWindowHandles is to return a set of all window handles that can be used to iterate over all open windows.
	Set<String> allWindowTabs = driver.getWindowHandles();
	// return an Iterator for the collection of both window IDs
	Iterator<String> iterate = allWindowTabs.iterator();
	// this is a main window and the first element in the iteration.
	String mainFirstWindow = iterate.next();


	// Switch & Work In The Main Window Or Tab
	driver.switchTo().window(mainFirstWindow);
	driver.switchTo().window(mainWindowHandle);
	driver.findElement(By.id("search_query_top")).sendKeys("Shirt");
	driver.findElement(By.name("submit_search")).click();
	System.out.println("Title: " + driver.getTitle());
	assertEquals(driver.getTitle(), "Search - My Store");
    }

}