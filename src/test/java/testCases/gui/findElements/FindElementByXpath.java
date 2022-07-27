package testCases.gui.findElements;

import gui.pages.homePage.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.broswer.BrowserActions;
import utilities.broswer.BrowserFactory;

public class FindElementByXpath {

    private WebDriver driver;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
        new HomePage(driver).navigateTo_homePage("http://the-internet.herokuapp.com/dropdown");
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }


    @Test(enabled = false)
    public void testFindAbsoluteXpath() {
        WebElement userNameTxt = driver.findElement(By.xpath("//*[@id=\"username\"]"));
        WebElement passwordTxt = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"login\"]/button/i"));

        System.out.println(userNameTxt.getTagName());
        System.out.println(passwordTxt.getTagName());
        System.out.println(loginBtn.getText());
    }

    @Test
    public void testFindRelativeXpath() {
        // by Tag Name of the element
        WebElement userNameTxt = driver.findElement(By.xpath("//input"));

        // By Tag Name and [index]
        WebElement passwordTxt = driver.findElement(By.xpath("//input[1]"));

        // syntax
        WebElement loginBtn = driver.findElement(By.xpath("//button[@class='radius']"));

        System.out.println(userNameTxt.getTagName());
        System.out.println(passwordTxt.getTagName());
        System.out.println(loginBtn.getText());
    }

}
