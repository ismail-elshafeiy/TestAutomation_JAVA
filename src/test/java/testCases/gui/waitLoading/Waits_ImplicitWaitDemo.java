package testCases.gui.waitLoading;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class Waits_ImplicitWaitDemo {
    WebDriver driver;

    @BeforeTest
    public void setup() {
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.manage().window().maximize();

	driver.get("http://cookbook.seleniumacademy.com/AjaxDemo.html");
	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Test
    public void testImplicitlyWait() {
	// set the implicit wait time to 20 Seconds
	driver.findElement(By.linkText("Page 4")).click();
	WebElement message = driver.findElement(By.id("page4"));
	assertTrue(message.getText().contains("Nunc nibh tortor"));
    }

    @AfterTest
    public void closeBrowser() {
	driver.quit();
    }

}
