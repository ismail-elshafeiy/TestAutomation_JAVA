package testCases.gui.web.ElementActions.Locators;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;
import testCases.gui.web.BaseTests;

@Feature("web")
@Epic("FindElements")
public class ElementPositionTest extends BaseTests {

	/*
	 * Notes:
	 * - getRect method. It returns a position and dimension of an element = 2 methods: getLocation and getSize.
	 * - position (x,y) --> X = horizontally from the left to the right, Y = vertically from the top to the bottom.
	 * - get position from DOM by  open Properties to see the location.
	 * - dimension (width, height) --> measure the size of an element.
	 * - get dimension from DOM by hover on element.
	 *
	 * */

	@Test
	public void getPositionDimension () {
		BrowserActions.navigateToUrl(driver, "https://testautomationu.applitools.com/learningpaths.html");
		WebElement logoTAU = driver.findElement(By.xpath("//div[@id='app']//header/a/img"));
		Rectangle rectTAULogo = logoTAU.getRect();
		System.out.println("x: " + rectTAULogo.getX());
		System.out.println("y: " + rectTAULogo.getY());
		System.out.println("Width: " + rectTAULogo.getWidth());
		System.out.println("Height: " + rectTAULogo.getHeight());
	}

}
