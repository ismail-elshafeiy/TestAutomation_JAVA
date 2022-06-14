package testCases.gui.cookies;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class Cookies_Test {

    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }


    @Test
    public void testAddCookie() {
//        new HomePage(driver).navigateTo_yourHomePage("");
        Cookie cookie = new Cookie.Builder("test", "123").domain("the-internet.herokuapp.com").build();
        driver.manage().addCookie(cookie);
        assertTrue(driver.manage().getCookieNamed(cookie.getName()) != null, "Cookie was not added");
    }

    @Test
    public void testDeleteCookie() {
        Cookie cookie = new Cookie.Builder("optimizelyBuckets", "%7B%TD").domain("the-internet.herokuapp.com").build();
        driver.manage().deleteCookie(cookie);
        assertFalse(driver.manage().getCookieNamed(cookie.getName()) != null, "Cookie was not deleted");
    }


}
