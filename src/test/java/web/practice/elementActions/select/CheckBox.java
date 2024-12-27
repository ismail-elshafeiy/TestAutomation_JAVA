package web.practice.elementActions.select;

import com.engine.actions.BrowserActions;
import com.engine.dataDriven.ExcelFileManager;
import com.engine.driver.DriverFactory;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import web.practice.homePage.HomePage;
import web.practice.select.CheckBoxPage;

public class CheckBox {
    private WebDriver driver;
    private ExcelFileManager testDataFile;

    @BeforeMethod
    public void setUp_BeforeMethod() {
		driver = DriverFactory.getBrowser();
//        testDataFile = new ExcelFileManager(new File("src/test/resources/TestData/TestData.xlsx"));
//        testDataFile.switchToSheet("checkboxes");
    }

    @AfterMethod
    public void closeBrowser() {
        BrowserActions.closeAllOpenedBrowserWindows(driver);
    }

    @Test
    public void checkbox1() {
        String state = "checkbox 1";
        int orderOfList = 1;
        new HomePage(driver).navigateToHomePage()
                .clickCheckBoxesPage()
                .clickOn_CheckBox(state, orderOfList);
        Assert.assertTrue(driver.findElement(CheckBoxPage.checkBox(orderOfList)).isSelected());
    }

    @Test
    public void checkbox2() {
		String state = testDataFile.getCellData(3, "state");
		int orderOfList = Integer.parseInt(testDataFile.getCellData(3, "orderOfList"));
		new HomePage(driver).navigateToHomePage()
				.clickCheckBoxesPage()
				.clickOn_CheckBox(state, orderOfList);
		Assert.assertTrue(! driver.findElement(CheckBoxPage.checkBox(orderOfList)).isSelected());
	}

    @Test
    public void checkBoxesTest() {
        new HomePage(driver).navigateToHomePage("https://the-internet.herokuapp.com/checkboxes");
        WebElement cb = driver.findElement(By.xpath("//input[@type = 'checkbox'][1]"));
        cb.click();

        WebElement cb2 = driver.findElement(By.xpath("//input[@type = 'checkbox'][2]"));
//		cb2.click();

        if ( ! (cb2.isSelected()) ) {
            cb2.click();
        }

    }

    @Test
    @Severity( SeverityLevel.CRITICAL )
    @Description( "Handling Checkboxes Test Case" )
    @Epic( "Selenium Actions on Elements" )
    @Story( "CheckBox Tutorial" )
    public void Checkboxes() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");
//        if the Checkboxes options are in a Select Tag we can use the select Class but if its a form we then cant
//        Select select = new Select(driver.findElement(By.id("checkboxes")));
//        select.selectByVisibleText(" checkbox 1");
//        select.selectByVisibleText(" checkbox 2");
        // We find the Checkbox options using xpath since its a child of the form with id checkboxes
        WebElement checkbox1 = driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]"));
        WebElement checkbox2 = driver.findElement(By.xpath("//form[@id='checkboxes']/input[2]"));
        // Check if the checkbox 1 is not select if yes then click it
        // if the IF statement has only one statement we don't have to use {} we only use it when its more than one statement
        if ( ! (checkbox1.isSelected()) ) checkbox1.click();
        // returns True if the checkbox is selected and false if not
        System.out.println("Is the checkbox 1 selected? " + checkbox1.isSelected());
        // returns True if the checkbox is enabled and ready to be checked
        System.out.println("Is the checkbox 2 enabled to be checked? " + checkbox2.isEnabled());
        // returns True if the checkbox 2 is displayed
        System.out.println("Is the checkbox 2 displayed ? " + checkbox2.isDisplayed());
        // Check if the checkbox 2 is selected if yes then uncheck it
        if ( checkbox2.isSelected() ) checkbox2.click();
    }


}


