package Course.A4_wait;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertTrue;

public class W1_ImplicitWait {
    ChromeDriver driver;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.navigate().to("http://cookbook.seleniumacademy.com/AjaxDemo.html");
        driver.manage().window().maximize();
    }
    @Test
    public void testImplicitWait (){

        // set implicit wait time to 20 Seconds
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Page 4")).click();

        WebElement message = driver.findElement(By.id("page4"));
        assertTrue(message.getText().contains("Nunc nibh tortor"));

    }

}

