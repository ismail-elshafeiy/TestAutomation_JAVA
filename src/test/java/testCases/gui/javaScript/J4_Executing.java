package testCases.gui.javaScript;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class J4_Executing {
    WebDriver driver;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.navigate().to("https://www.google.com/");
        driver.manage().window().maximize();
    }

    @Test
    public void testJavaScriptCalls() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String title = (String) js
                .executeScript("return document.title");
        assertEquals("Google", title);
        System.out.println(title);

        Long links = (Long) js
                .executeScript("var links = document.getElementsByTagName('A'); return links.length");
        System.out.println(links);




    }
}
