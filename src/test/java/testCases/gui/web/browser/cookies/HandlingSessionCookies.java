package testCases.gui.web.browser.cookies;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Set;

public class HandlingSessionCookies {
    WebDriver driver;
    Select select;


    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.navigate().to("http://magento-demo.lexiconn.com/");
        driver.manage().window().maximize();
    }

    @Test
    public void testCookies() {
        WebElement languageSelect = driver.findElement(By.id("select-language"));
        select = new Select(languageSelect);
        Assert.assertEquals(select.getFirstSelectedOption().getText(), "English");

        //Store Cookies should be none (null)
        Cookie storeCookie = driver.manage().getCookieNamed("store");
        Assert.assertEquals(storeCookie, null);

        //Select German Language
        select.selectByVisibleText("German");

        //Store Cookie Should be populated with selected country
        storeCookie = driver.manage().getCookieNamed("store");
        Assert.assertEquals(storeCookie.getValue(), "german");
        System.out.println(storeCookie.getValue());

        // Get All cookies
        Set<Cookie> cookieSet = driver.manage().getCookies();
        System.out.println(("Number of Cookies "+cookieSet.size()));
        Iterator<Cookie> itr =cookieSet.iterator();
        while (itr.hasNext()){
            Cookie cookie = itr.next();
            System.out.println(cookie.getDomain());
            System.out.println(cookie.getName());
            System.out.println(cookie.getPath());
            System.out.println(cookie.getValue());
            System.out.println(cookie.getExpiry());

        }


    }


}
