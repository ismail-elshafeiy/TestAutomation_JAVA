package engine.gui.actions;

import engine.broswer.Waits;
import engine.tools.Logger;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

public class ElementActions {
    private WebDriver driver;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
    }

    public ElementActions mouseHover(By elementLocator) {
        mouseHover(driver, elementLocator);
        return this;
    }

    public static void mouseHover(WebDriver driver, By elementLocator) {
        ElementActionsHelper.locatingElementStrategy(driver, elementLocator);
        try {
            Actions actions = new Actions(driver);
            Logger.logStep("[Element Action] Hover on [" + driver.findElement(elementLocator).getText() + "]");
            actions.moveToElement(driver.findElement(elementLocator)).perform();
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
    }


    public ElementActions click(By elementLocator) {
        click(driver, elementLocator);
        return this;
    }

    @Step( "Click on element: [{elementLocator}]" )
    public static void click(WebDriver driver, By elementLocator) {
        // Mouse hover on the element before clicking
        mouseHover(driver, elementLocator);
        try {
            Waits.getExplicitWait(driver).until(ExpectedConditions.elementToBeClickable(elementLocator));
        } catch (TimeoutException toe) {
            Logger.logMessage(toe.getMessage());
            fail(toe.getMessage());
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
        try {
            // Log element text if not empty. Else, log clicking
            if ( ! driver.findElement(elementLocator).getText().isBlank() ) {
                Logger.logStep("[Element Action] Click on [" + driver.findElement(elementLocator).getText() + "] Button");
            } else {
                Logger.logStep("[Element Action] Click on element [" + elementLocator + "]");
            }
            driver.findElement(elementLocator).click();
        } catch (Exception exception) {
            // Click using JavascriptExecutor in case of the click is not performed
            // successfully and got an exception using the Selenium click method
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[arguments.length - 1].click();",
                        driver.findElement(elementLocator));
            } catch (Exception rootCauseException) {
                rootCauseException.initCause(exception);
                Logger.logMessage(exception.getMessage());
                Logger.logMessage(rootCauseException.getMessage());
                // Force fails the test case if you couldn't perform the click
                fail("Couldn't click on the element:" + elementLocator, rootCauseException);
            }
        }
    }


    public static void type(WebDriver driver, By elementLocator, String text) {
        type(driver, elementLocator, text, true);
    }

    @Step( "Type data: [{text}] on element: [{elementLocator}]" )
    public static void type(WebDriver driver, By elementLocator, String text, boolean clearBeforeTyping) {
        ElementActionsHelper.locatingElementStrategy(driver, elementLocator);
        try {
            // Clear before typing condition
            if ( ! driver.findElement(elementLocator).getAttribute("value").isBlank() && clearBeforeTyping ) {
                Logger.logStep("[Element Action] Clear and Type [" + text + "] on element [" + elementLocator + "]");
                driver.findElement(elementLocator).clear();
                driver.findElement(elementLocator).sendKeys(text);
                // Type using JavascriptExecutor in case of the data is not typed successfully
                if ( ! driver.findElement(elementLocator).getAttribute("value").equals(text) ) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + text + "')", driver.findElement(elementLocator));
                }
            } else {
                Logger.logStep("[Element Action] Type [" + text + "] on element [" + elementLocator + "]");
                driver.findElement(elementLocator).sendKeys(text);
                // Type using JavascriptExecutor in case of the data is not typed successfully
                if ( ! driver.findElement(elementLocator).getAttribute("value").contains(text) ) {
                    String currentValue = driver.findElement(elementLocator).getAttribute("value");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + currentValue + text + "')", driver.findElement(elementLocator));
                }
            }
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
        // Make sure that the data is inserted correctly to the field
        Assert.assertTrue(driver.findElement(elementLocator).getAttribute("value").contains(text),
                "The data is not inserted successfully to the field, the inserted data should be: [" + text + "]; But the current field value is: ["
                        + driver.findElement(elementLocator).getAttribute("value") + "]");
    }

    public ElementActions type(By elementLocator, String text) {
        type(driver, elementLocator, text, true);
        return this;
    }

    public ElementActions type(By elementLocator, String text, boolean clearBeforeTyping) {
        type(driver, elementLocator, text, clearBeforeTyping);
        return this;
    }

    public enum SelectType {
        TEXT, VALUE, INDEX;
    }

    public ElementActions select(By elementLocator, SelectType selectType, String option) {
        select(driver, elementLocator, selectType, option);
        return this;
    }

    public ElementActions select(By elementLocator, SelectType selectType, int option) {
        select(driver, elementLocator, selectType, String.valueOf(option));
        return this;
    }

    // Select by string Text or Value
    public static void select(WebDriver driver, By elementLocator, SelectType selectType, String option) {
        ElementActionsHelper.locatingElementStrategy(driver, elementLocator);
        try {
            Select s = new Select(driver.findElement(elementLocator));
            Logger.logStep("[Element Action] Select [" + option + "] on element [" + elementLocator + "]");
            assertFalse(s.isMultiple());
            switch ( selectType ) {
                case TEXT -> s.selectByVisibleText(option);
                case VALUE -> s.selectByValue(option);
                case INDEX -> s.selectByIndex(Integer.parseInt(option));
                default -> Logger.logMessage("Unexpected value: " + selectType);
            }
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions doubleClick(By elementLocator) {
        doubleClick(driver, elementLocator);
        return this;
    }

    public static void doubleClick(WebDriver driver, By elementLocator) {
        ElementActionsHelper.locatingElementStrategy(driver, elementLocator);
        try {
            Actions actions = new Actions(driver);
            Logger.logStep("[Element Action] Double Click on element [" + elementLocator + "]");
            actions.doubleClick(driver.findElement(elementLocator)).perform();
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
    }


    public ElementActions clickKeyboardKey(By elementLocator, Keys key) {
        clickKeyboardKey(driver, elementLocator, key);
        return this;
    }

    @Step( "Click a Keyboard Key on element: [{elementLocator}]" )
    public static void clickKeyboardKey(WebDriver driver, By elementLocator, Keys key) {
        ElementActionsHelper.locatingElementStrategy(driver, elementLocator);
        try {
            Logger.logStep("[Element Action] Click a Keyboard key [" + key.name() + "] on element [" + elementLocator + "]");
            // We click ENTER here! :D
            driver.findElement(elementLocator).sendKeys(key);
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
        }
    }


    @Step( "Get the Text of element: [{elementLocator}]" )
    public static String getText(WebDriver driver, By elementLocator) {
        ElementActionsHelper.locatingElementStrategy(driver, elementLocator);
        try {
            String text = driver.findElement(elementLocator).getText();
            Logger.logStep("[Element Action] Get the Text of element [" + elementLocator + "]; The Text is [" + text + "]");
            return text;
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
        }
        return null;
    }

    public static String getAttributeValue(WebDriver driver, By elementLocator, String attributeName) {
        ElementActionsHelper.locatingElementStrategy(driver, elementLocator);
        try {
            String attributeValue = driver.findElement(elementLocator).getAttribute(attributeName);
            Logger.logStep("[Element Action] Get the Attribute [" + attributeName + "] Value of element [" + elementLocator + "]; The Value is [" + attributeValue + "]");
            return attributeValue;
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
        }
        return null;
    }
}
