package com.practice.gui.pages.select;

import engine.gui.actions.ElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import engine.gui.actions.ElementActions;
import java.util.List;
import java.util.stream.Collectors;

public class DropdownPage {

    private WebDriver driver;
    private By dropdown_list = By.id("dropdown");

    public static By optionSelected(String option) {
        return By.xpath("//option[contains(text(),'" + option + "')]");
    }

    public DropdownPage(WebDriver driver) {
        this.driver = driver;
    }

    private Select findDropDownElement() {
        return new Select(driver.findElement(dropdown_list));
    }

    public DropdownPage selectFromDropDown(String option) {
        ElementActions.select(driver, dropdown_list, ElementHelper.SelectBy.TEXT, option);
//        findDropDownElement().selectByVisibleText(option);
        return this;
    }

    public List<String> getSelectedOptions() {
        List<WebElement> selectedElements = findDropDownElement().getAllSelectedOptions();
        return selectedElements.stream().map(e -> e.getText()).collect(Collectors.toList());
    }

    public void addMultipleAttribute() {
        String script = "arguments[0].setAttribute('multiple','')";
        ((JavascriptExecutor) driver).executeScript(script, findDropDownElement());
    }

    public DropdownPage selectByValue(String valueOption) {
        ElementActions.select(driver, dropdown_list, ElementHelper.SelectBy.VALUE, valueOption);
        return this;
    }

    public DropdownPage selectByVisible(String visibleOption) {
        ElementActions.select(driver, dropdown_list, ElementHelper.SelectBy.TEXT, visibleOption);
        return this;
    }


    // TODO: Create Business action by Select using INDEX

    public String getSelectOptionText(String option) {
        return ElementActions.getText(driver, optionSelected(option));
    }
}
