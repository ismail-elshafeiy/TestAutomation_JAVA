package examples.gui.web.elementActions.locators;

import engine.broswer.BrowserActions;
import engine.gui.actions.ElementActions;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import examples.gui.web.BaseTests;

import java.util.List;

import static engine.gui.actions.ElementHelper.*;

@Epic("ElementActions")
@Feature("Locators")
public class Finders extends BaseTests {
	@Test
	@Story("Tables Tutorial")
	public void getTableContent () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/tables");
		WebElement webTable = driver.findElement(By.id("table1"));
		// Get all rows at table 1 ( rows = tr )
		List<WebElement> rows = webTable.findElements(By.tagName("tr"));
		Assert.assertEquals(rows.size(), 5, "The Rows number is incorrect");
		// Get all Cells data (cols and rows) ( cols = td )
		getCellFromTable(rows, By.tagName("td"));
		/**
		 * <tbody>
		 *       <tr>
		 *         <td>Smith</td>
		 *         <td>John</td>
		 *         <td>jsmith@gmail.com</td>
		 *         <td>$50.00</td>
		 *         <td>http://www.jsmith.com</td>
		 *         <td>
		 *           <a href="#edit">edit</a>
		 *           <a href="#delete">delete</a>
		 *         </td>
		 *       </tr>
		 *     </tbody>
		 */
	}

	@Test
	public void getAllLinksAndPrint () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/");
		List<WebElement> links = driver.findElements(By.tagName("a"));
		Assert.assertEquals(links.size(), 46);
		System.out.println("The number of elements = " + links.size());
		// Print each link value
		for (WebElement link : links) {
			String hyperLink = link.getAttribute("href");
			System.out.println("The text of hyper link: " + link.getText());
			verifyLink(hyperLink);
		}
	}

	@Test
	void returnAllLinksAndTheirResponse () {
		driver.get("https://the-internet.herokuapp.com/");
		// findElements returns list of web elements of the tag a
		List<WebElement> links = driver.findElements(By.tagName("a"));
		// prints out the number of the links in our web page
		System.out.println(links.size());
		for (WebElement link : links) {
			// prints out the link text of each link in our web page
			System.out.println("The link text is : " + link.getText());
			System.out.println("The link href value and its response code are below : ");
			String url = link.getAttribute("href");
			verifyLink(url);
		}
	}

	@Test
	public void checkTheFooterLinks () {
		BrowserActions.navigateToUrl(driver, "https://demo.nopcommerce.com/");
		By footer = By.cssSelector("div.footer");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(footer));
		getLinks(driver.findElement(footer));
		ElementActions.click(driver, By.xpath("//a[contains(text(),'Privacy notice')]"));
		ElementActions.click(driver, By.xpath("//a[contains(text(),'About us')]"));
		ElementActions.click(driver, By.xpath("//a[contains(text(),'Contact us')]"));
		ElementActions.click(driver, By.xpath("//a[contains(text(),'Conditions of Use')]"));
		driver.navigate().back();
	}

	@Test
	public void getActiveElement () {
		BrowserActions.navigateToUrl(driver, "https://demo.nopcommerce.com/");
		String active = driver.switchTo().activeElement().getTagName();
		System.out.println("Active Elements = " + active);
	}

	@Test
	public void getLinksByLocator () {
		BrowserActions.navigateToUrl(driver, "https://the-internet.herokuapp.com/");
		By test = By.xpath("//a[.]");
		System.out.println("The number of elements = " + driver.findElements(test).size());
		System.out.println("The number of elements = " + driver.findElements(test).get(2).getText());
	}
}