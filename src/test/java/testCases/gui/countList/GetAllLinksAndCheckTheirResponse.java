package testCases.gui.countList;

import com.practice.gui.pages.homePage.HomePage;
import org.openqa.selenium.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.actions.ElementActions;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GetAllLinksAndCheckTheirResponse {

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
     *
     * Using driver.findElement"s" to get all the links (TagName = a )
     * and checks the API response codes of each link
     */
    @Test
    void returnAllLinksAndTheirResponse() {
        driver.get("https://the-internet.herokuapp.com/");
        // findElements returns list of web elements of the tag a
        List<WebElement> links = driver.findElements(By.tagName("a"));
        // prints out the number of the links in our web page
        System.out.println(links.size());
        for (
                WebElement link : links ) {
            // prints out the link text of each link in our web page
            System.out.println("The link text is : " + link.getText());
            // prints out the href attribute of each link in our web page
            System.out.println("The link href value and its response code are below : ");
            String url = link.getAttribute("href");
            VerifyLink(url);
        }
    }

    JavascriptExecutor js;
    By footer = By.cssSelector("div.footer");

    @Test
    public void checkTheFooterLinks() {
        new HomePage(driver).navigateToHomePage("https://the-internet.herokuapp.com/");
        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", footer);
        getFooterLinks();
        ElementActions.click(driver, By.xpath("//a[contains(text(),'Privacy notice')]"));
        ElementActions.click(driver, By.xpath("//a[contains(text(),'About us')]"));
        ElementActions.click(driver, By.xpath("//a[contains(text(),'Contact us')]"));
        ElementActions.click(driver, By.xpath("//a[contains(text(),'Conditions of Use')]"));
        driver.navigate().back();
    }

    public void getFooterLinks() {
        List<WebElement> links = footer.findElements((SearchContext) By.tagName("a"));
        System.out.println("The Number of The links is : " + links.size());
        for ( WebElement Link : links ) {
            System.out.println(Link.getAttribute("href"));
        }
    }

    /*
     *
     * Check the response code and response messages for all the links on the Page under test
     *
     */

    public void VerifyLink(String urlLink) {
        try {
            URL link = new URL(urlLink);
            // Create connection using URL object
            HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
            httpConn.setConnectTimeout(2000);
            httpConn.connect();
            // User getResponseCode() to get the response code
            if ( httpConn.getResponseCode() == 200 ) {
                System.out.println(urlLink + " - " + httpConn.getResponseMessage() + " - " + httpConn.getResponseCode());
            } else if ( httpConn.getResponseCode() == 404 ) {
                System.out.println(urlLink + " - " + httpConn.getResponseMessage() + " - " + httpConn.getResponseCode());
            } else {
                System.out.println(urlLink + " - " + httpConn.getResponseMessage() + " - " + httpConn.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
