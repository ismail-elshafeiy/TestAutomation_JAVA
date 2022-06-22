package Course.A4_wait;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class W2_ExplicitWait {
    ChromeDriver driver;


    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.navigate().to("https://www.google.com/");
        driver.manage().window().maximize();
    }
    @Test
    public void testExplicitWait(){
        WebElement queryTxt = driver.findElement(By.name("q"));
        queryTxt.sendKeys("Selenium WebDriver");
        queryTxt.submit();

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.titleContains("Selenium"));

        assertTrue(driver.getTitle().toLowerCase().startsWith("selenium"));

    }

}
