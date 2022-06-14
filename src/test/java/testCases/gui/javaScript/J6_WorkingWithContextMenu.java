package testCases.gui.javaScript;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class J6_WorkingWithContextMenu {
    WebDriver driver;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.navigate().to("https://swisnl.github.io/jQuery-contextMenu/demo/accesskeys.html");
        driver.manage().window().maximize();
    }

    @Test
    public void testContextMenu() {
        WebElement clickMenuBtn = driver.findElement(By.cssSelector("span.context-menu-one.btn.btn-neutral"));
        WebElement contextMenu = driver.findElement(By.cssSelector("li.context-menu-item.context-menu-icon.context-menu-icon-edit"));
        Actions actions = new Actions(driver);
        actions
                .contextClick(clickMenuBtn)
                .moveToElement(contextMenu)
                .click()
                .perform();

        WebDriverWait wait = null;

//        WebDriverWait wait = new WebDriverWait(driver, 20);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("clicked: edit", alert.getText());
        System.out.println(alert.getText());
        alert.dismiss();


    }
}
