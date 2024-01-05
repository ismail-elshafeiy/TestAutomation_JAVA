package com.engine.actions;

import com.engine.Waits;
import com.engine.actions.helper.ElementHelper;
import com.engine.actions.helper.ElementInformation;
import com.engine.constants.FrameworkConstants;
import com.engine.reports.CustomReporter;
import com.engine.utils.DecodeData;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.engine.reports.CustomReporter.passAction;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

public class ElementActions {
    private static WebDriver driver = null;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
    }

    public static ElementActions getInstance() {
        return new ElementActions(driver);
    }


    @Step("Click on element: [{elementLocator}]")
    public static void click(WebDriver driver, By elementLocator) {
        // Mouse hover on the element before clicking
        hover(driver, elementLocator);
        try {
            Waits.getExplicitWait(driver).until(ExpectedConditions.elementToBeClickable(elementLocator));
        } catch (Exception toe) {
            CustomReporter.logError(toe.getMessage());
            fail(toe.getMessage());
        }
        try {
            // Log element text if not empty. Else, log clicking
            if (!driver.findElement(elementLocator).getText().isBlank()) {
                CustomReporter.logInfoStep("[Element Action] Click on [" + driver.findElement(elementLocator).getText() + "] Button");
            } else {
                CustomReporter.logInfoStep("[Element Action] Click on element [" + elementLocator + "]");
            }
            driver.findElement(elementLocator).click();
        } catch (Exception exception) {
            // Click using JavascriptExecutor in case of the click is not performed
            // successfully and got an exception using the Selenium click method
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[arguments.length - 1].click();", driver.findElement(elementLocator));
            } catch (Exception rootCauseException) {
                rootCauseException.initCause(exception);
                CustomReporter.logError(exception.getMessage());
                CustomReporter.logError(rootCauseException.getMessage());
                // Force fails the test case if you couldn't perform the click
                fail("Couldn't click on the element:" + elementLocator, rootCauseException);
            }
        }
    }

    public ElementActions click(By elementLocator) {
        click(driver, elementLocator);
        return this;
    }

    public ElementActions type(By elementLocator, String text) {
        var elementInformation = ElementInformation.fromList(ElementHelper.identifyUniqueElementIgnoringVisibility(driver, elementLocator));
        String actualTextAfterTyping = ElementHelper.newTypeWrapper(driver, elementInformation, text);
        var elementName = elementInformation.getElementName();
        if (actualTextAfterTyping.equals(text)) {
            //     passAction(driver, elementLocator, Thread.currentThread().getStackTrace()[1].getMethodName(), text, null, elementName);
        } else {
            //          ElementHelper.failAction(driver, "Expected to type: \"" + text + "\", but ended up with: \"" + actualTextAfterTyping + "\"", elementLocator);
        }
        return this;

    }
    @Step("Type data: [ {text} ] on element: [{elementLocator}]")
    public static void type(WebDriver driver, By elementLocator, String text, boolean clearBeforeTyping) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            // Clear before typing condition
            if (!driver.findElement(elementLocator).getAttribute("value").isBlank() && clearBeforeTyping) {
                CustomReporter.logInfoStep("[Element Action] Clear and Type [" + text + "] on element [" + elementLocator + "]");
                driver.findElement(elementLocator).clear();
                driver.findElement(elementLocator).sendKeys(text);
                // Type using JavascriptExecutor in case of the data is not typed successfully
                if (!driver.findElement(elementLocator).getAttribute("value").equals(text)) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + text + "')", driver.findElement(elementLocator));
                }
            } else {
                CustomReporter.logInfoStep("[Element Action] Type [" + text + "] on element [" + elementLocator + "]");
                driver.findElement(elementLocator).sendKeys(text);
                // Type using JavascriptExecutor in case of the data is not typed successfully
                if (!driver.findElement(elementLocator).getAttribute("value").contains(text)) {
                    String currentValue = driver.findElement(elementLocator).getAttribute("value");
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].setAttribute('value', '" + currentValue + text + "')", driver.findElement(elementLocator));
                }
            }
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
        // Make sure that the data is inserted correctly to the field
        Assert.assertTrue(driver.findElement(elementLocator).getAttribute("value").contains(text),
                "The data is not inserted successfully to the field, the inserted data should be: [" + text + "]; But the current field value is: ["
                        + driver.findElement(elementLocator).getAttribute("value") + "]");
    }

    public static void type(WebDriver driver, By elementLocator, String text) {
        type(driver, elementLocator, text, true);
    }

//    public ElementActions type(By elementLocator, String text) {
//        type(driver, elementLocator, text, true);
//        return this;
//    }

    public ElementActions type(By elementLocator, String text, boolean clearBeforeTyping) {
        type(driver, elementLocator, text, clearBeforeTyping);
        return this;
    }

    @Step("Type data: [{text}] on element: [{elementLocator}]")
    public static void typeEncrypt(WebDriver driver, By elementLocator, String text, boolean clearBeforeTyping) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            // Clear before typing condition
            if (!driver.findElement(elementLocator).getAttribute("value").isBlank() && clearBeforeTyping) {
                CustomReporter.logInfoStep("[Element Action] Clear and Type [" + DecodeData.encrypt(text) + "] on element [" + elementLocator + "]");
                driver.findElement(elementLocator).clear();
                driver.findElement(elementLocator).sendKeys(text);
                // Type using JavascriptExecutor in case of the data is not typed successfully
                if (!driver.findElement(elementLocator).getAttribute("value").equals(text)) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + text + "')", driver.findElement(elementLocator));
                }
            } else {
                CustomReporter.logInfoStep("[Element Action] Type [" + DecodeData.encrypt(text) + "] on element [" + elementLocator + "]");
                driver.findElement(elementLocator).sendKeys(text);
                // Type using JavascriptExecutor in case of the data is not typed successfully
                if (!driver.findElement(elementLocator).getAttribute("value").contains(text)) {
                    String currentValue = driver.findElement(elementLocator).getAttribute("value");
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].setAttribute('value', '" + currentValue + text + "')", driver.findElement(elementLocator));
                }
            }
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
        // Make sure that the data is inserted correctly to the field
        Assert.assertTrue(driver.findElement(elementLocator).getAttribute("value").contains(text),
                "The data is not inserted successfully to the field, the inserted data should be: [" + text + "]; But the current field value is: ["
                        + driver.findElement(elementLocator).getAttribute("value") + "]");
    }

    public static void typeEncrypt(WebDriver driver, By elementLocator, String text) {
        typeEncrypt(driver, elementLocator, text, true);
    }

    public ElementActions typeEncrypt(By elementLocator, String text) {
        typeEncrypt(driver, elementLocator, text, true);
        return this;
    }

    public ElementActions typeEncrypt(By elementLocator, String text, boolean clearBeforeTyping) {
        typeEncrypt(driver, elementLocator, text, clearBeforeTyping);
        return this;
    }


    //**************************************      Select Actions         **********************************************//
    //******************************************************************************************************************//
    public static void select(WebDriver driver, By elementLocator, ElementHelper.SelectBy selectBy, String option) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            Select select = new Select(driver.findElement(elementLocator));
            CustomReporter.logInfoStep("[Element Action] Select [" + option + "] on element [" + elementLocator + "]");
            assertFalse(select.isMultiple());
            switch (selectBy) {
                case TEXT -> select.selectByVisibleText(option);
                case VALUE -> select.selectByValue(option);
                case INDEX -> select.selectByIndex(Integer.parseInt(option));
                default -> CustomReporter.logError("Unexpected value: " + selectBy);
            }
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions select(By elementLocator, ElementHelper.SelectBy selectBy, String option) {
        select(driver, elementLocator, selectBy, option);
        return this;
    }

    public ElementActions select(By elementLocator, ElementHelper.SelectBy selectBy, int option) {
        select(driver, elementLocator, selectBy, String.valueOf(option));
        return this;
    }


    //**************************************       Device Actions         **********************************************//
    //******************************************************************************************************************//
    static Actions actions;

    public static void doubleClick(WebDriver driver, By elementLocator) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            actions = new Actions(driver);
            CustomReporter.logInfoStep("[Element Action] Double Click on element [" + elementLocator + "]");
            actions.doubleClick(driver.findElement(elementLocator)).perform();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions doubleClick(By elementLocator) {
        doubleClick(driver, elementLocator);
        return this;
    }

    public static void rightClick(WebDriver driver, By elementLocator) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            actions = new Actions(driver);
            CustomReporter.logInfoStep("[Element Action] Right Click on element [" + elementLocator + "]");
            actions.contextClick(driver.findElement(elementLocator)).perform();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions rightClick(By elementLocator) {
        rightClick(driver, elementLocator);
        return this;
    }


    public static void hover(WebDriver driver, By elementLocator) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            actions = new Actions(driver);
            CustomReporter.logInfoStep("[Element Action] Hover on [" + driver.findElement(elementLocator).getText() + "]");
            actions.moveToElement(driver.findElement(elementLocator)).perform();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions hover(By elementLocator) {
        hover(driver, elementLocator);
        return this;
    }

    public static void hoverAndClick(WebDriver driver, By elementLocator) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            actions = new Actions(driver);
            CustomReporter.logInfoStep("[Element Action] Hover on [" + driver.findElement(elementLocator).getText() + "]");
            actions.moveToElement(driver.findElement(elementLocator)).click().perform();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions hoverAndClick(By elementLocator) {
        hoverAndClick(driver, elementLocator);
        return this;
    }

    @Step("Click a Keyboard Key on element: [{elementLocator}]")
    public static void keyPress(WebDriver driver, By elementLocator, Keys key) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            actions = new Actions(driver);
            CustomReporter.logInfoStep("[Element Action] Click a Keyboard key [" + key.name() + "] on element [" + elementLocator + "]");
            // We click ENTER here! :D
            driver.findElement(elementLocator).sendKeys(key);
            actions.sendKeys(key).perform();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    public ElementActions keyPress(By elementLocator, Keys key) {
        keyPress(driver, elementLocator, key);
        return this;
    }

    public static void clickAndSendKeys(WebDriver driver, By elementLocator, String text) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            actions = new Actions(driver);
            CustomReporter.logInfoStep("[Element Action] Sending Keys [" + text + "] to element [" + elementLocator + "]");
            actions.click(driver.findElement(elementLocator))
                    .sendKeys(text).build().perform();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    public ElementActions clickAndSendKeys(By elementLocator, String text) {
        clickAndSendKeys(driver, elementLocator, text);
        return this;
    }

    public static void clickAndHoldTo(WebDriver driver, By fromLocator, By toLocator) {
        ElementHelper.locatingElementStrategy(driver, fromLocator);
        try {
            actions = new Actions(driver);
            CustomReporter.logInfoStep("[Element Action] Click and hold on [" + driver.findElement(fromLocator).getText() + "]");
            actions.clickAndHold(driver.findElement(fromLocator))
                    .moveToElement(driver.findElement(toLocator))
                    .build()
                    .perform();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions clickAndHoldTo(By fromLocator, By toLocator) {
        clickAndHoldTo(driver, fromLocator, toLocator);
        return this;
    }

    public static void clickAndHoldTo(WebDriver driver, By fromLocator, int xOffset, int yOffset) {
        ElementHelper.locatingElementStrategy(driver, fromLocator);
        try {
            actions = new Actions(driver);
            CustomReporter.logInfoStep("[Element Action] Click and hold on [" + driver.findElement(fromLocator).getText() + "] to [" + xOffset + "," + yOffset + "]");
            actions.clickAndHold(driver.findElement(fromLocator))
                    .moveByOffset(xOffset, yOffset)
                    .release()
                    .build()
                    .perform();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions clickAndHoldTo(By fromLocator, int xOffset, int yOffset) {
        clickAndHoldTo(driver, fromLocator, xOffset, yOffset);
        return this;
    }

    public static void dragAndDrop(WebDriver driver, By sourceLocator, By targetLocator) {
        ElementHelper.locatingElementStrategy(driver, sourceLocator);
        try {
            actions = new Actions(driver);
            CustomReporter.logInfoStep("[Element Action] Drag and Drop [" + driver.findElement(sourceLocator).getText() + "] to [" + driver.findElement(targetLocator).getText() + "]");
            actions.dragAndDrop(driver.findElement(sourceLocator), driver.findElement(targetLocator)).perform();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions dragAndDrop(By sourceLocator, By targetLocator) {
        dragAndDrop(driver, sourceLocator, targetLocator);
        return this;
    }

    public static void dragAndDrop(WebDriver driver, By sourceLocator, int xOffset, int yOffset) {
        ElementHelper.locatingElementStrategy(driver, sourceLocator);
        try {
            actions = new Actions(driver);
            CustomReporter.logInfoStep("[Element Action] Drag and Drop [" + driver.findElement(sourceLocator).getText() + "] to [" + xOffset + "," + yOffset + "]");
            actions.dragAndDropBy(driver.findElement(sourceLocator), xOffset, yOffset).perform();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions dragAndDrop(By sourceLocator, int xOffset, int yOffset) {
        dragAndDrop(driver, sourceLocator, xOffset, yOffset);
        return this;
    }

    public enum ClipboardAction {
        COPY("copy"), PASTE("paste"), CUT("cut"), SELECT_ALL("select all"), UNSELECT_ALL("unselect");
        final String value;

        ClipboardAction(String value) {
            this.value = value;
        }

    }

    public static boolean ClipboardActions(WebDriver driver, ClipboardAction action) {
        try {
            Keys cmdCtrl = FrameworkConstants.BROWSER_TYPE.equalsIgnoreCase(Platform.MAC.name()) ? Keys.COMMAND : Keys.CONTROL;
            switch (action) {
                case COPY -> (new Actions(driver)).keyDown(cmdCtrl).sendKeys("c").keyUp(cmdCtrl).perform();
                case PASTE -> (new Actions(driver)).keyDown(cmdCtrl).sendKeys("v").keyUp(cmdCtrl).perform();
                case CUT -> (new Actions(driver)).keyDown(cmdCtrl).sendKeys("x").keyUp(cmdCtrl).perform();
                case SELECT_ALL -> (new Actions(driver)).keyDown(cmdCtrl).sendKeys("a").keyUp(cmdCtrl).perform();
                case UNSELECT_ALL -> (new Actions(driver)).sendKeys(Keys.ESCAPE).perform();
                default -> {
                    return false;
                }
            }
            return true;
        } catch (HeadlessException e) {
            CustomReporter.logError(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    //**************************************       drawing Actions         **********************************************//
    //******************************************************************************************************************//

    public static void drawCircle(WebDriver driver, By canvasElement) {
        ElementHelper.locatingElementStrategy(driver, canvasElement);
        try {
            actions = new Actions(driver);
            CustomReporter.logInfoStep("[Element Action] Drawing [" + driver.findElement(canvasElement).getText() + "] ");
            actions.moveToElement(driver.findElement(canvasElement)).clickAndHold();
            int numPoints = 10;
            int radius = 50;
            for (int i = 0; i < numPoints; i++) {
                double theta = (2 * Math.PI * i) / numPoints;
                int x = (int) (radius * Math.cos(theta));
                int y = (int) (radius * Math.sin(theta));
                actions.moveByOffset(x, y).perform();
            }
            actions.release(driver.findElement(canvasElement)).build().perform();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public static void drawUsingJavaScript(WebDriver driver, By canvasElement) {
        int canvasWidth = driver.findElement(canvasElement).getSize().getWidth();
        int canvasHeight = driver.findElement(canvasElement).getSize().getHeight();
        // Calculate the offsets for drawing the signature
        int startX = canvasWidth / 4;  // Adjust as needed
        int startY = canvasHeight / 2; // Adjust as needed
        // Use JavaScript to simulate mouse movements for drawing
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('mousedown', {" +
                "clientX: " + startX + ", clientY: " + startY + "}));", driver.findElement(canvasElement));
        // Simulate drawing strokes (adjust offsets and add more points as needed)
        jsExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('mousemove', {" +
                "clientX: " + (startX + 100) + ", clientY: " + (startY + 30) + "}));", driver.findElement(canvasElement));
        jsExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('mousemove', {" +
                "clientX: " + (startX + 80) + ", clientY: " + (startY + 20) + "}));", driver.findElement(canvasElement));
        jsExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('mousemove', {" +
                "clientX: " + (startX + 60) + ", clientY: " + (startY + -20) + "}));", driver.findElement(canvasElement));
        jsExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('mousemove', {" +
                "clientX: " + (startX + 40) + ", clientY: " + (startY + -40) + "}));", driver.findElement(canvasElement));
        jsExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('mousemove', {" +
                "clientX: " + (startX + 20) + ", clientY: " + (startY + -60) + "}));", driver.findElement(canvasElement));
        jsExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('mousemove', {" +
                "clientX: " + (startX + 10) + ", clientY: " + (startY + -80) + "}));", driver.findElement(canvasElement));
        jsExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('mousemove', {" +
                "clientX: " + (startX + 5) + ", clientY: " + (startY + -100) + "}));", driver.findElement(canvasElement));
        // Simulate drawing strokes (adjust offsets and add more points as needed)
        jsExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('mousemove', {" +
                "clientX: " + (startX + 70) + ", clientY: " + (startY + 10) + "}));", driver.findElement(canvasElement));
        jsExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('mousemove', {" +
                "clientX: " + (startX + 30) + ", clientY: " + (startY + 30) + "}));", driver.findElement(canvasElement));
        // Release the mouse button to complete the signature
        jsExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseup', {" +
                "clientX: " + (startX + 100) + ", clientY: " + (startY + 100) + "}));", driver.findElement(canvasElement));

    }
    //*******************************************    Java script actions    **********************************************************//
    //***********************************************************************************************************************************//

    public static void clickUsingJavaScript(WebDriver driver, By elementLocator) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            CustomReporter.logInfoStep("[Element Action] Click on element [" + elementLocator + "] using JavaScript");
            ((JavascriptExecutor) driver).executeScript("arguments[arguments.length - 1].click();", driver.findElement(elementLocator));
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions clickUsingJavaScript(By elementLocator) {
        clickUsingJavaScript(driver, elementLocator);
        return this;
    }

    public static void sendKeysUsingJavaScript(WebDriver driver, By elementLocator, String text) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            CustomReporter.logInfoStep("[Element Action] Send Keys [" + text + "] to element [" + elementLocator + "] using JavaScript");
            ((JavascriptExecutor) driver).executeScript("arguments[arguments.length - 1].value='" + text + "';", driver.findElement(elementLocator));
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions sendKeysUsingJavaScript(By elementLocator, String text) {
        sendKeysUsingJavaScript(driver, elementLocator, text);
        return this;
    }

    public static void scrollToElement(WebDriver driver, By elementLocator) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            CustomReporter.logInfoStep("[Element Action] Scroll to element [" + elementLocator + "]");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(elementLocator));
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions scrollToElement(By elementLocator) {
        scrollToElement(driver, elementLocator);
        return this;
    }

    public static void scrollToTop(WebDriver driver) {
        try {
            CustomReporter.logInfoStep("[Element Action] Scroll to top of the page");
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions scrollToTop() {
        scrollToTop(driver);
        return this;
    }


    //*******************************************    Element helper Methods    **********************************************************//
    //***********************************************************************************************************************************//
    @Step("Check if element: [{elementLocator}] is Displayed")
    public static boolean isDisplayed(WebDriver driver, By elementLocator) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            CustomReporter.logInfoStep("[Element Action] Check if element [" + elementLocator + "] is Displayed");
            return driver.findElement(elementLocator).isDisplayed();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
        return false;
    }

    public ElementActions isDisplayed(By elementLocator) {
        isDisplayed(driver, elementLocator);
        return this;
    }

    @Step("Check if element: [{elementLocator}] is Enabled")
    public static boolean isEnabled(WebDriver driver, By elementLocator) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            CustomReporter.logInfoStep("[Element Action] Check if element [" + elementLocator + "] is Enabled");
            return driver.findElement(elementLocator).isEnabled();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
        return false;
    }

    public ElementActions isEnabled(By elementLocator) {
        isEnabled(driver, elementLocator);
        return this;
    }

    @Step("Check if element: [{elementLocator}] is Selected")
    public static boolean isSelected(WebDriver driver, By elementLocator) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            CustomReporter.logInfoStep("[Element Action] Check if element [" + elementLocator + "] is Selected");
            return driver.findElement(elementLocator).isSelected();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
        return false;
    }

    public ElementActions isSelected(By elementLocator) {
        isSelected(driver, elementLocator);
        return this;
    }

//**********************************************     Getter Methods    **************************************************************//
//***********************************************************************************************************************************//

    @Step("Get the Text of element: [{elementLocator}]")
    public static String getText(WebDriver driver, By elementLocator) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            String text = driver.findElement(elementLocator).getText();
            CustomReporter.logInfoStep("[Element Action] Get the Text of element [" + elementLocator + "]; The Text is [" + text + "]");
            return text;
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
        return null;
    }

    public static String getAttributeValue(WebDriver driver, By elementLocator, String attributeName) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            String attributeValue = driver.findElement(elementLocator).getAttribute(attributeName);
            CustomReporter.logInfoStep("[Element Action] Get the Attribute [" + attributeName + "] Value of element [" + elementLocator + "]; The Value is [" + attributeValue + "]");
            return attributeValue;
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
        return null;
    }

    public static String getCssValue(WebDriver driver, By elementLocator, String cssValue) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        try {
            String cssValueOfElement = driver.findElement(elementLocator).getCssValue(cssValue);
            CustomReporter.logInfoStep("[Element Action] Get the CSS Value [" + cssValue + "] of element [" + elementLocator + "]; The Value is [" + cssValueOfElement + "]");
            return cssValueOfElement;
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
        return null;
    }

    public static int getElementsCount(WebDriver driver, By elementLocator) {
        ElementHelper.locatingElementStrategy(driver, elementLocator);
        int elementsCount = driver.findElements(elementLocator).size();
        CustomReporter.logInfoStep("[Element Action] Get the Elements Count of element [" + elementLocator + "]; The Count is [" + elementsCount + "]");
        return elementsCount;
    }


    /**
     * Get any simple table rows' data that has
     * thead which include all the column labels and tbody which includes all table data
     *
     * @param tableLocator the locator of the table which should be a table tag
     * @return List of Map format and each Map Object follows the following format (Key:column label, value: cell data)
     */
    public java.util.List<Map<String, String>> getTableRowsData(By tableLocator) {
        java.util.List<Map<String, String>> tableData = new ArrayList<>();
        // Wait for the table to be present and visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));
        } catch (Exception throwable) {
            CustomReporter.logError(throwable.getMessage());
            return null;
        }
        WebElement table = driver.findElement(tableLocator);
        try {
            //Wait until any row is loaded because some websites use lazy loading,
            //and you need to wait for rows to be loaded
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tbody tr")));
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage() + " Table\"" + tableLocator + "\" is empty");

            //Will return empty list to be used in case you want to assert if the table is empty
            return new ArrayList<>();
        }
        java.util.List<WebElement> rows = table.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        java.util.List<WebElement> headerCells = table.findElement(By.tagName("thead")).findElements(By.tagName("th"));

        //extract the data into a List of Maps
        for (WebElement row : rows) {
            WebElement currentRow = wait.until(ExpectedConditions.visibilityOf(row));
            List<WebElement> cells = row.findElements(By.tagName("td"));
            Map<String, String> rowData = new HashMap<>();
            for (int cellIndex = 0; cellIndex < cells.size(); cellIndex++) {
                String columnName = headerCells.get(cellIndex).getText();
                String cellValue = cells.get(cellIndex).getText();
                rowData.put(columnName, cellValue);
            }

            tableData.add(rowData);
        }

        return tableData;
    }

    //**********************************************     Verify element Methods    **************************************************************//
//***********************************************************************************************************************************//
    @Step("Verify element NOT visible {0}")
    public static boolean verifyElementNotVisible(By elementLocator, int timeout) {
        try {
            Waits.getExplicitWait(driver, timeout).until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));
            return true;
        } catch (Exception e) {
            CustomReporter.logWarning("Element is still visible after [" + timeout + "] seconds of waiting element [" + elementLocator + "] : " + e.getMessage());
            return false;
        }
    }

}
