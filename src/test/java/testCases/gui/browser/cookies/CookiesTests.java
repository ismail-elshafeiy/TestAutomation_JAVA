package testCases.gui.browser.cookies;

import org.openqa.selenium.WebDriver;
import testAutomationU.base.BaseTests;
import org.openqa.selenium.Cookie;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
public class CookiesTests {
    private WebDriver driver;
    public utils.CookieManager getCookieManager(){
        return new utils.CookieManager(driver);
    }


    @Test
    public void testDeleteCookie(){
        var cookieManager = getCookieManager();
        Cookie cookie = cookieManager.buildCookie("optimizelyBuckets", "%7B%TD");
        cookieManager.deleteCookie(cookie);
        assertFalse(cookieManager.isCookiePresent(cookie), "Cookie was not deleted");
    }
}