/*
package engine.gui.actions;

import engine.Waits;
import engine.tools.Logger;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import static engine.gui.actions.ElementHelper.locatingElementStrategy;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

public class MobileElementActions {
    @SuppressWarnings( "unused" )
    private MobileDriver<MobileElement> mobile;

    public MobileElementActions(MobileDriver<MobileElement> mobile) {
        this.mobile = mobile;
    }

    public MobileElementActions click(By elementLocator) {
        click(mobile, elementLocator);
        return this;
    }

    public static void click(MobileDriver<MobileElement> mobile, By elementLocator) {
        locatingElementStrategy(mobile, elementLocator);
        try {
            Waits.getExplicitWait(mobile).until(ExpectedConditions.elementToBeClickable(elementLocator));
        } catch (TimeoutException toe) {
            Logger.logMessage(toe.getMessage());
            fail(toe.getMessage());
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
        try {
            if ( ! mobile.findElement(elementLocator).getText().isBlank() ) {
                Logger.logStep("[Mobile Element Action] Click on [" + mobile.findElement(elementLocator).getText() + "] Button");
            } else {
                Logger.logStep("[Mobile Element Action] Click on element [" + elementLocator + "]");
            }
            mobile.findElement(elementLocator).click();
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
    }

    public MobileElementActions type(By elementLocator, String text) {
        type(mobile, elementLocator, text);
        return this;
    }

    public static void type(MobileDriver<MobileElement> mobile, By elementLocator, String text) {
        locatingElementStrategy(mobile, elementLocator);
        try {
            Logger.logStep("[Mobile Element Action] Type [" + text + "] on element [" + elementLocator + "]");
            mobile.findElement(elementLocator).sendKeys(text);
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
        // Make sure that the data is inserted correctly to the field
        Assert.assertTrue(mobile.findElement(elementLocator).getAttribute("value").contains(text),
                "The data is not inserted successfully to the field, the inserted data should be: [" + text + "]; But the current field value is: ["
                        + mobile.findElement(elementLocator).getAttribute("value") + "]");
    }

    public static String getText(MobileDriver<MobileElement> mobile, By elementLocator) {
        locatingElementStrategy(mobile, elementLocator);
        String text = mobile.findElement(elementLocator).getText();
        return text;
    }

    public MobileElementActions select(By elementLocator, ElementHelper.SelectBy selectBy, String option) {
        select(mobile, elementLocator, selectBy, option);
        return this;
    }

    public MobileElementActions select(By elementLocator, ElementHelper.SelectBy selectBy, int option) {
        select(mobile, elementLocator, selectBy, String.valueOf(option));
        return this;
    }

    public static void select(MobileDriver<MobileElement> mobile, By elementLocator, ElementHelper.SelectBy selectBy, String option) {
        locatingElementStrategy(mobile, elementLocator);
        try {
            Select s = new Select(mobile.findElement(elementLocator));
            Logger.logStep("[Mobile Element Action] Select [" + option + "] on element [" + elementLocator + "]");
            assertFalse(s.isMultiple());
            switch ( selectBy ) {
                case TEXT -> s.selectByVisibleText(option);
                case INDEX -> s.selectByIndex(Integer.parseInt(option));
                case VALUE -> s.selectByValue(option);
                default -> Logger.logMessage("Unexpected value: " + selectBy);
            }

        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
    }

}



*/
