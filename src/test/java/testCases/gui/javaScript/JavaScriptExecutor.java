package testCases.gui.javaScript;


import io.qameta.allure.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

public class JavaScriptExecutor  {


    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }



    /*
     * for More JavaScriptExecutor methods check the following URL
     * https://www.softwaretestingmaterial.com/javascriptexecutor-selenium-webdriver/
     * JavaScript executor is an interface that gives a mechanism to execute JavaScript through Selenium WebDriver.
     *  It provides two methods such as “executescript” & “executeAsyncScript” to run javascript on the currently
     *  selected frame or window or page.
     * There are locators in Selenium WebDriver like ID, Class, XPath, etc., to work with elements on a web page.
     *  Sometimes these default Selenium locators may not work.
     *  Here comes the JavaScriptExecutor in the picture.
     */

     /*
      * to type text in Selenium WebDriver without using sendKeys() method
      *
      js.executeScript("document.getElementById('some id').value='someValue';");
      js.executeScript("document.getElementById('Email').value='SoftwareTestingMaterial.com';");
      */

      /*
      * to click a button in Selenium WebDriver using JavaScript
      *
      js.executeScript("arguments[0].click();", loginButton);
      * or
      js.executeScript("document.getElementById('enter your element id').click();");
      */

      /*
      * to handle checkbox
      *
      js.executeScript("document.getElementById('enter element id').checked=false;");
      */

       /*
       * to generate Alert Pop window in selenium
       *
	   js.executeScript("alert('hello world');");
	   */

		/*
		* to refresh browser window using Javascript
		*
		js.executeScript("history.go(0)");
		*/

		/*
		* to get innertext of the entire webpage in Selenium
		*
		String sText =  js.executeScript("return document.documentElement.innerText;").toString();
		System.out.println(sText);
		*/

		/*
		* to get the Title of our webpage
		*
		String sText =  js.executeScript("return document.title;").toString();
		System.out.println(sText);
		*/

		/*
		* to get the domain
		*
		String sText =  js.executeScript("return document.domain;").toString();
		System.out.println(sText);
		*/

		/*
		* to get the URL of our webpage
		*
		String sText =  js.executeScript("return document.URL;").toString();
		System.out.println(sText);
		*/

		/*
		* to perform Scroll on application using  Selenium
		* Vertical scroll - down by 50  pixels
		*
		*
		js.executeScript("window.scrollBy(0,50)");
		*
		*
		* for scrolling till the bottom of the page we can use the code like
		*
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		*/

		/*
		* to click on a SubMenu which is only visible on mouse hover on Menu?
		* Hover on Automation Menu on the MenuBar
		*
	    js.executeScript("$('ul.menus.menu-secondary.sf-js-enabled.sub-menu li').hover()");
	    */

		/*
		* to navigate to different page using Javascript?
	    * Navigate to new Page
	    *
	    js.executeScript("window.location = 'https://www.softwaretestingmaterial.com");
	    */

       /*
        * to Find a hidden element in selenium using javascript Executor
        *
        * js.executeScript("arguments[0].click();", element);
        */

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Performing JavaScript Executor commands to scroll down a page Test Case")
    @Epic("Selenium Actions on Elements")
    @Story("JavaScript Executor Tutorial")
    void ScrollDownInPageUsingJavaScriptExecutor() {
        driver.get(" https://the-internet.herokuapp.com/large");
        JavascriptExecutor js = (JavascriptExecutor) driver;
//         Vertical scroll down by 3000 pixels and horizontal by 200 pixels
        js.executeScript("window.scrollBy(200,3000)");
//        for scrolling till the bottom of the page we can use the code like
//        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }
}
