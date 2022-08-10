package testCases.gui.web.findElements;

import com.practice.gui.pages.homePage.HomePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;
@Feature("web")
@Epic("FindElements")
public class FindElementByXpathTest {

	private WebDriver driver;

	@Test(enabled = false)
	public void testFindAbsoluteXpath () {
		WebElement userNameTxt = driver.findElement(By.xpath("//*[@id=\"username\"]"));
		WebElement passwordTxt = driver.findElement(By.xpath("//*[@id=\"password\"]"));
		WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"login\"]/button/i"));
		System.out.println(userNameTxt.getTagName());
		System.out.println(passwordTxt.getTagName());
		System.out.println(loginBtn.getText());
	}

	@Test
	public void testFindRelativeXpath () {
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

	@BeforeMethod
	public void setUp_BeforeMethod () {
		driver = BrowserFactory.getBrowser();
		new HomePage(driver).navigateToHomePage("http://the-internet.herokuapp.com/dropdown");
	}

	@AfterMethod
	public void closeBrowser () {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}

}
