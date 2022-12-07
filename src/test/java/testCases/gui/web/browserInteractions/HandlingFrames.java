package testCases.gui.web.browserInteractions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HandlingFrames {

	WebDriver driver;

	@BeforeTest
	public void setUp () {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.manage().window().maximize();
	}

	@Test
	public void testHandlingFramesWithIdOrName () {
		driver.navigate().to("http://cookbook.seleniumacademy.com/Frames.html");
		driver.switchTo().frame("left");

		// Before find element should be switch to the frame first
		WebElement msgL = driver.findElement(By.tagName("p"));
		Assert.assertEquals(msgL.getText(), "This is Left Frame");
		System.out.println(msgL.getText());

		// to go back instead still in the same frame
		driver.switchTo().defaultContent();

		// go to another frame from default Content
		driver.switchTo().frame("right");
		WebElement msgR = driver.findElement(By.tagName("p"));
		Assert.assertEquals(msgR.getText(), "This is Right Frame");
		System.out.println(msgR.getText());
		driver.switchTo().defaultContent();

		// go to another frame from default Content
		driver.switchTo().frame(1);
		WebElement msgS = driver.findElement(By.tagName("p"));
		Assert.assertEquals(msgS.getText(), "This Frame doesn't have id or name]");
		System.out.println(msgS.getText());
		driver.switchTo().defaultContent();

	}

	public void switchToFrameByElement (By by) {
		int numberOfFrames = driver.findElements(By.tagName("iframe")).size();
		for (int i = 0; i < numberOfFrames; i++) {
			driver.switchTo().frame(i);
			if (driver.findElements(by).size() > 0) {
				break;

			}
		}
	}

	/**
	 * Using driver.switchTo().frame() to handle frames
	 * mainly used with iframe's ID , name , index or as WebElement
	 **/

	@Test
	@Severity(SeverityLevel.MINOR)
	@Description("Handling Frame using index Test Case")
	@Epic("Selenium Actions on Elements")
	@Story("Frames Tutorial")
	void NestedFramesUsingIndex () {
		driver.get("https://cookbook.seleniumacademy.com/Frames.html");
		// Switch to the frame with index 1
		driver.switchTo().frame(1);
		System.out.println(driver.findElement(By.tagName("p")).getText());
		driver.switchTo().defaultContent();
	}

	/*
	 * Using driver.switchTo().frame() to handle frames
	 * mainly used with iframe's ID , name , index or as WebElement
	 *
	 */
	@Test
	public void NestedFrames () {
		driver.get("https://cookbook.seleniumacademy.com/Frames.html");
		// prints out the number of frames in the WebPage
		System.out.println("There are " + driver.findElements(By.tagName("frame")).size() +
				" frames inside the Web page");
		// Switch to the frame with specific Id ( left )
		driver.switchTo().frame("left");
		System.out.println(driver.findElement(By.tagName("p")).getText());
		driver.switchTo().defaultContent();
		// Switch to the frame with specific name ( right )
		driver.switchTo().frame("right");
		System.out.println(driver.findElement(By.tagName("p")).getText());
		driver.switchTo().defaultContent();
	}

	@Test
	public void TextAreaInIFrame () throws Throwable {
		driver.navigate().to("https://the-internet.herokuapp.com/iframe");
		// Switch to the Frame with id
		driver.switchTo().frame("mce_0_ifr");
		WebElement tinymceTextArea = driver.findElement(By.xpath("//body[@id='tinymce']"));
		// Clear the text in the Text Area
		tinymceTextArea.clear();
		Actions actions = new Actions(driver);
		// Send keys using Actions class to perform KeyDown SHIFT button and type in the text with UpperCase
		actions.keyDown(Keys.SHIFT).sendKeys(tinymceTextArea, "hello from the Text Area in the Iframe")
				.build().perform();
		// Returns back the default content
		driver.switchTo().defaultContent();
		// prints out a text from outside the frame
		System.out.println(driver.findElement(By.xpath("//div[@class='example']/h3")).getText());
	}
}
