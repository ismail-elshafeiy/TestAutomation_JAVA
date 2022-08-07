package testCases.gui.select;

import com.practice.gui.pages.homePage.HomePage;
import com.practice.gui.pages.select.DropdownPage;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserActions;
import engine.broswer.BrowserFactory;
import engine.actions.ElementActions;
import engine.tools.io.ExcelFileManager;

import java.io.IOException;

import static org.testng.Assert.*;

public class Dropdown {
    private WebDriver driver;
    private ExcelFileManager testDataFile;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
//        testDataFile = new ExcelFileManager(new File("src/test/resources/TestData/TestData.xlsx"));
//        testDataFile.switchToSheet("dropdown");
    }

    @AfterMethod()
    public void closeBrowser(ITestResult result) throws IOException {
        BrowserActions.closeAllOpenedBrowserWindows(driver, result);
    }

    @Test
    public void testSelectOption() {
        String option = "Options 1";
        var selectedOptions = new HomePage(driver).navigateTo_homePage()
                .clickDropDown()
                .selectFromDropDown(option)
                .getSelectedOptions();
        assertEquals(selectedOptions.size(), 1, "Incorrect Number of selection");
        assertTrue(selectedOptions.contains(option), "option not selected");
    }

    @Test
    public void selectOptionByValue() {
        String option1 = testDataFile.getCellData("option", 2);
        String option1TextSelected = new HomePage(driver).navigateTo_homePage()
                .clickDropDown()
                .selectByValue(option1)
                .getSelectOptionText("Option 1");
        Assert.assertEquals(option1TextSelected, "Option 1");
    }

    @Test
    public void selectOptionByVisible() {
        String option2 = testDataFile.getCellData("option", 3);
        new HomePage(driver).navigateTo_homePage()
                .clickDropDown()
                .selectByVisible(option2);
        Assert.assertEquals(ElementActions.getText(driver, DropdownPage.optionSelected("Option 2")), "Option 2");
    }

    @Test
    public void dropDownListTest() {
        new HomePage(driver).navigateTo_homePage("https://the-internet.herokuapp.com/dropdown");

        // get the dropdown as a select using it'select name attribute
        Select select = new Select(driver.findElement(By.id("dropdown")));

        // assert that it doesn't support multiple selection
        assertFalse(select.isMultiple());

        // Verify that it has 2 options only
        assertEquals(select.getOptions().size(), 3);

        // Select by visibleText
        select.selectByVisibleText("Option 1");
        assertEquals("Option 1", select.getFirstSelectedOption().getText());

        // Select by value
        select.selectByValue("2");
        assertEquals("Option 2", select.getFirstSelectedOption().getText());

        // Select by index
        select.selectByIndex(1);
        assertEquals("Option 1", select.getFirstSelectedOption().getText());

    }


    @Test
    @Severity( SeverityLevel.CRITICAL )
    @Description( "Handling radio buttons Test Case" )
    @Epic( "Selenium Actions on Elements" )
    @Story( "Radio buttons Tutorial" )
    void RadioButtons() {
        driver.get("https://formy-project.herokuapp.com/radiobutton");
        WebElement radioBtn1 = driver.findElement(By.id("radio-button-1"));
        // Verify that Radio Button 1 is displayed
        Assert.assertTrue(radioBtn1.isDisplayed(), "Radio button 1 is not displayed");
        WebElement radioBtn2 = driver.findElement(By.name("exampleRadios"));
        // Verify That Radio button 2 is selected
        radioBtn2.click();
        Assert.assertTrue(radioBtn2.isSelected(), "Radio button 2 is not selected");
        WebElement radioBtn3 = driver.findElement(By.cssSelector("input.form-check-input"));
        // Verify That Radio button 3 is enabled
        Assert.assertTrue(radioBtn3.isEnabled(), "Radio button 3 is not enabled ");
    }


}
