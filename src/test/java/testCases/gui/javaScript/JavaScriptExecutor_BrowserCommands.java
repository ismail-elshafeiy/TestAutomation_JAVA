package testCases.gui.javaScript;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

public class JavaScriptExecutor_BrowserCommands  {
	private WebDriver driver;

	@BeforeMethod
	public void setUp_BeforeMethod() {
		driver = BrowserFactory.getBrowser();
	}

	@AfterMethod
	public void closeBrowser()  {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}

	/*
	 *
	 * Using JavaScriptExecutor to perform some actions such as
	 * input text , click , scrollDown to element or to x and y
	 * refresh the page or draw a border to an element
	 * since selenium doesnt always work in all the cases so we need to use the JavaScriptExecutor
	 * to perform these actions
	 *
	 */

    @Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Performing JavaScript Executor commands for browser navigation Test Case")
	@Epic("Selenium Actions on Elements")
	@Story("JavaScript Executor Tutorial")
    void NavigationUsingJse() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("https://the-internet.herokuapp.com/");


		/*
		* to get the Title of our webpage
		*/
		String TitleText =  js.executeScript("return document.title;").toString();
		System.out.println(TitleText);

        /*
         * to refresh browser window using Javascript
         */
        js.executeScript("history.go(0)");

       /*
		* to get the URL of our webpage
		*/
		String UrlText =  js.executeScript("return document.URL;").toString();
		System.out.println(UrlText);

	   /*
		* to perform Scroll on application using  Selenium
		* Vertical scroll - down by 50  pixels
		*
		* js.executeScript("window.scrollBy(x,y)")
		*/
		js.executeScript("window.scrollBy(0,300)");

		/*
		 * to perform scroll down to element view
		 */
		WebElement Element = driver.findElement(By.linkText(""));
		js.executeScript("arguments[0].scrollIntoView();", Element);

		/*
		 * to perform scroll down to the end of the page
		 */
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
}
