package examples.gui.web.javaScript;

import engine.Helper;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import examples.gui.web.BaseTests;

import static org.testng.AssertJUnit.assertEquals;

@Feature("web")
@Epic("JavaScript Executor")
public class JavaScriptExecutorBrowserActionsTest extends BaseTests {

	String currentTime = "< " + Helper.getCurrentTime("HH:mm:ss") + " >";

	@Test
	@Description("""
			- Get the Title of page 
			- Refresh the page
			""")
	public void getTitlePageAndRefresh () {
		BrowserActions.navigateToUrl(driver, " https://the-internet.herokuapp.com/");
		String titlePage = ((JavascriptExecutor) driver).executeScript("return document.title;").toString();
		System.out.println(currentTime + " The Url title Page is --> " + titlePage);
		((JavascriptExecutor) driver).executeScript("history.go(0)");
	}

	@Test
	@Description("""
			- Get the Title of page 
			- Get Links length
			""")
	public void getLinksLength () {
		BrowserActions.navigateToUrl(driver, "https://www.google.com/");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String title = (String) js.executeScript("return document.title");
		assertEquals("Google", title);
		System.out.println(currentTime + " Get Title Page " + title);
		Long links = (Long) js.executeScript("var links = document.getElementsByTagName('A'); return links.length");
		System.out.println(currentTime + "[" + links + "] links found on the page");
	}

	@Test
	@Description("""
			- Navigate to different 
			- Get the URL of page
			""")
	public void navigateToPageAndGetUrl () {
		BrowserActions.navigateToUrl(driver, " https://the-internet.herokuapp.com/");
		String url = ((JavascriptExecutor) driver).executeScript("return document.URL;").toString();
		System.out.println(currentTime + " The Url Page 1 is --> " + url);
		((JavascriptExecutor) driver).executeScript("window.location = 'https://the-internet.herokuapp.com/hovers'");
		String url2 = ((JavascriptExecutor) driver).executeScript("return document.URL;").toString();
		System.out.println(currentTime + " The Url  Page 2 is --> " + url2);
		String domain = ((JavascriptExecutor) driver).executeScript("return document.domain;").toString();
		System.out.println(currentTime + " The domain is --> " + domain);
	}


	@Test
	@Description("""
			- Get innertext of the entire page
			""")
	public void getInnerText () {
		BrowserActions.navigateToUrl(driver, " https://the-internet.herokuapp.com/");
		String innerText = ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;").toString();
		System.out.println(currentTime + "[ " + innerText + " ]");
	}

	@Test
	@Description("""
			- Change color of an element 
			- draw a border over the selected element
			""")
	public void changeColorAndDrawBorder () {
		BrowserActions.navigateToUrl(driver, " https://the-internet.herokuapp.com/");
		By element = By.xpath("//a[contains(text(),'Form Authentication')]");
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", driver.findElement(element));
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,300)");
	}



	@Test
	@Description("""
			- generate Alert Pop window in selenium
			-  js.executeScript("alert('"+ message +"')");
			""")
	public void generateAlertPage () {
		BrowserActions.navigateToUrl(driver, " https://the-internet.herokuapp.com/");
		((JavascriptExecutor) driver).executeScript("alert('Welcome to the-internet By Ismail Elshafeiy " + currentTime + "')");
	}

}