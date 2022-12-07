package testCases.gui.web.actions;

import com.practice.gui.pages.keys.KeyPressesPage;
import engine.broswer.Waits;
import engine.gui.actions.DeviceActions;
import engine.gui.actions.ElementActions;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;

import static org.apache.poi.hssf.usermodel.HSSFShapeTypes.TextBox;
import static org.testng.Assert.assertEquals;

@Epic("Keys")
@Feature("web")
public class ActionsClassKeyboardTest {
	private WebDriver driver;

	Actions actions;

	@Test
	@Description("Performing KeyBoard actions Test Case")
	void SendingKeysAndCLick () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/login");
		actions = new Actions(driver);
		// send value to textbox using sendKeys of actions class
		actions.sendKeys(driver.findElement(By.id("username")), "tomsmith")
				.perform();
		// some textbox needs the user to click on them first before sending text to them to make them enabled
		// when we have more than one action like clicking and sending text we should build before perform
//		actions.click(driver.findElement(By.id("password")))
//				.sendKeys("SuperSecretPassword!")
//				.build().perform();

		DeviceActions.clickAndSendKeys(driver, By.id("password"), "SuperSecretPassword!");
		String ccs = ElementActions.getCssValue(driver, By.id("password"), "background-color");
		System.out.println("This ccs "+ccs);
		// moveToElement moves the cursor to the center of the element and then clicks it by using click
		actions.moveToElement(driver.findElement(By.cssSelector("button.radius"))).click()
				.build().perform();
	}

	@Test
	public void keyboard () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/login");
		Waits.implicitWait(driver, 60);
//		Keys.chord(Keys.CONTROL, Keys.getKeyFromUnicode('p'));
		Keys.chord(Keys.CONTROL, "p");


	}

	@Test
	public void testBackspace () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/key_presses");
		String getResult = new KeyPressesPage(driver)
				.enterText("A")
				.getResult();
		assertEquals(getResult, "You entered: BACK_SPACE", "Input result incorrect");
	}

	@Test
	@Description("""
			Handling Different KeyPresses Test Case
			          We can use Keys.chrod() if we want to press multiple keys simultaneously
			            for example clicking CTRL and then S
			            TextBox.sendKeys(Keys.chord(Keys.CONTROL),"S");
			            Or
			            TextBox.sendKeys("S",Keys.CONTROL)
			""")
	void KeyPress () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/key_presses");
		WebElement TextBox = driver.findElement(By.id("target"));
		WebElement TheUsedKeyPressLabel = driver.findElement(By.id("result"));

		TextBox.sendKeys(Keys.ARROW_DOWN);
		System.out.println("Pressing down");
		System.out.println(TheUsedKeyPressLabel.getText());

		TextBox.sendKeys(Keys.ARROW_LEFT);
		System.out.println("Pressing left");
		System.out.println(TheUsedKeyPressLabel.getText());

		TextBox.sendKeys(Keys.ARROW_RIGHT);
		System.out.println("Pressing right");
		System.out.println(TheUsedKeyPressLabel.getText());

		TextBox.sendKeys(Keys.ARROW_UP);
		System.out.println("Pressing up");
		System.out.println(TheUsedKeyPressLabel.getText());

		TextBox.sendKeys(Keys.BACK_SPACE);
		System.out.println("Pressing Backspace");
		System.out.println(TheUsedKeyPressLabel.getText());

		TextBox.sendKeys(Keys.CONTROL);
		System.out.println("Pressing CTRL");
		System.out.println(TheUsedKeyPressLabel.getText());

		TextBox.sendKeys(Keys.TAB);
		System.out.println("Pressing Tab");
		System.out.println(TheUsedKeyPressLabel.getText());

		TextBox.sendKeys(Keys.SPACE);
		System.out.println("Pressing space");
		System.out.println(TheUsedKeyPressLabel.getText());
	}


	@BeforeMethod
	public void setUp_BeforeMethod () {
		driver = BrowserFactory.getBrowser();
	}

	@AfterMethod(enabled = false)
	public void closeBrowser () {
		BrowserActions.closeAllOpenedBrowserWindows(driver);
	}
}
