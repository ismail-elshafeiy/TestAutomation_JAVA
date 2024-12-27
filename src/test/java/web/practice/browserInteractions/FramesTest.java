package web.practice.browserInteractions;

import com.engine.actions.BrowserActions;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import web.practice.base.BaseTests;


@Epic("Browser Interactions")
@Feature("Frames")
public class FramesTest extends BaseTests {
	@Test
	public void testSwitchIFrameByLocator () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/iframe");
		// Switch to the Frame with id
		BrowserActions.switchToFrame(driver, By.id("mce_0_ifr"));
		By tinyMce = By.xpath("//body[@id='tinymce']");
		driver.findElement(tinyMce).clear();
		Actions actions = new Actions(driver);
		// KeyDown SHIFT button and type in the text with UpperCase
		actions.keyDown(Keys.SHIFT).sendKeys(driver.findElement(tinyMce),
						"hello from the Text Area in the Iframe")
				.build().perform();
		// Returns back the default content
		driver.switchTo().defaultContent();
	}

	@Test
	public void testSwitchToFramesByNameOrId () {
		BrowserActions.navigateToUrl(driver,"http://cookbook.seleniumacademy.com/Frames.html");
		BrowserActions.switchToFrame(driver,"left");
		WebElement msgL = driver.findElement(By.tagName("p"));
		Assert.assertEquals(msgL.getText(), "This is Left Frame");
		System.out.println(msgL.getText());
		// to go back instead still in the same frame
		driver.switchTo().defaultContent();
		// go to another frame from default Content
	BrowserActions.switchToFrame(driver,"right");
		WebElement msgR = driver.findElement(By.tagName("p"));
		Assert.assertEquals(msgR.getText(), "This is Right Frame");
		System.out.println(msgR.getText());
		driver.switchTo().defaultContent();
		// go to another frame from default Content
		BrowserActions.switchToFrame(driver,1);
		WebElement msgS = driver.findElement(By.tagName("p"));
		Assert.assertEquals(msgS.getText(), "This Frame doesn't have id or name]");
		System.out.println(msgS.getText());
		driver.switchTo().defaultContent();
	}
}
