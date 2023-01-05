package com.practice.gui.pages.select;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import engine.gui.actions.ElementActions;

public class CheckBoxPage {
    private WebDriver driver;
    public static By checkBox (int orderOfList){ return By.xpath("//form[@id='checkboxes']//child::input[@type='checkbox']["+ orderOfList +"]");}
    public CheckBoxPage(WebDriver driver) {
        this.driver = driver;
    }

    public CheckBoxPage clickOn_CheckBox(String state, int orderOfList) {
        if (state.equalsIgnoreCase("uncheck") && driver.findElement(checkBox(orderOfList)).isSelected()) {
            ElementActions.click(driver, checkBox(orderOfList));
        } else if (state.equalsIgnoreCase("check") && !driver.findElement(checkBox(orderOfList)).isSelected()) {
            ElementActions.click(driver, checkBox(orderOfList));
        }
        return this;
    }

}
