package com.engine.actions;

import com.engine.WaitsManager;
import com.engine.actions.helper.ElementHelper;
import com.engine.actions.helper.ElementInformation;
import com.engine.constants.FrameworkConstants;
import com.engine.reports.Logger;
import com.engine.utilities.DecodeData;
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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

public class ElementActions {
    private static WebDriver driver = null;

    public ElementActions(WebDriver driver) {
        ElementActions.driver = driver;
    }

    public static ElementActions getInstance() {
        return new ElementActions(driver);
    }

    @Step("Type data: [ {text} ] on element: [ {locator} ]")
    public static void type(WebDriver driver, By locator, String text, boolean clearBeforeTyping) {
        ElementHelper.locatingElementStrategy(driver, locator);
        try {
            if (!driver.findElement(locator).getAttribute("value").isBlank() && clearBeforeTyping) {
                Logger.logInfoStep("Clear and Type [" + text + "] on element [" + locator + "]");
                driver.findElement(locator).clear();
            } else {
                Logger.logInfoStep("Type [" + text + "] on element [" + locator + "]");
            }
            driver.findElement(locator).sendKeys(text);

            if (!driver.findElement(locator).getAttribute("value").equals(text)) {
                Logger.logInfoStep("Type [" + text + "] on element [" + locator + "] using JavascriptExecutor");
                ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + text + "')", driver.findElement(locator));
            }
        } catch (Exception e) {
            Logger.logError(e.getMessage());
            fail(e.getMessage());
        }
        // Make sure that the data is inserted correctly to the field
        Assert.assertTrue(driver.findElement(locator).getAttribute("value").contains(text),
                "The data is not inserted successfully to the field, the inserted data should be: [" + text + "]; But the current field value is: ["
                        + driver.findElement(locator).getAttribute("value") + "]");
    }

    public ElementActions type(By locator
            , String text) {
        var elementInformation = ElementInformation.fromList(ElementHelper.identifyUniqueElementIgnoringVisibility(driver, locator
        ));
        String actualTextAfterTyping = ElementHelper.newTypeWrapper(driver, elementInformation, text);
        var elementName = elementInformation.getElementName();
        if (actualTextAfterTyping.equals(text)) {
            //     passAction(driver, locator
            //     , Thread.currentThread().getStackTrace()[1].getMethodName(), text, null, elementName);
        } else {
            //          ElementHelper.failAction(driver, "Expected to type: \"" + text + "\", but ended up with: \"" + actualTextAfterTyping + "\"", locator
            //          );
        }
        return this;

    }


    public static void type(WebDriver driver, By locator
            , String text) {
        type(driver, locator
                , text, true);
    }

//    public ElementActions type(By locator
//    , String text) {
//        type(driver, locator
//        , text, true);
//        return this;
//    }

    public ElementActions type(By locator
            , String text, boolean clearBeforeTyping) {
        type(driver, locator
                , text, clearBeforeTyping);
        return this;
    }

    @Step("Type data: [ {text} ] on element: [ {locator" +
            "} ]")
    public static void typeEncrypt(WebDriver driver, By locator
            , String text, boolean clearBeforeTyping) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            // Clear before typing condition
            if (!driver.findElement(locator
            ).getAttribute("value").isBlank() && clearBeforeTyping) {
                Logger.logInfoStep("[Element Action] Clear and Type [" + DecodeData.encrypt(text) + "] on element [" + locator
                        + "]");
                driver.findElement(locator
                ).clear();
                driver.findElement(locator
                ).sendKeys(text);
                // Type using JavascriptExecutor in case of the data is not typed successfully
                if (!driver.findElement(locator
                ).getAttribute("value").equals(text)) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + text + "')", driver.findElement(locator
                    ));
                }
            } else {
                Logger.logInfoStep("[Element Action] Type [" + DecodeData.encrypt(text) + "] on element [" + locator
                        + "]");
                driver.findElement(locator
                ).sendKeys(text);
                // Type using JavascriptExecutor in case of the data is not typed successfully
                if (!driver.findElement(locator
                ).getAttribute("value").contains(text)) {
                    String currentValue = driver.findElement(locator
                    ).getAttribute("value");
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].setAttribute('value', '" + currentValue + text + "')", driver.findElement(locator
                            ));
                }
            }
        } catch (Exception e) {
            Logger.logError(e.getMessage());
            fail(e.getMessage());
        }
        // Make sure that the data is inserted correctly to the field
        Assert.assertTrue(driver.findElement(locator
                ).getAttribute("value").contains(text),
                "The data is not inserted successfully to the field, the inserted data should be: [" + text + "]; But the current field value is: ["
                        + driver.findElement(locator
                ).getAttribute("value") + "]");
    }

    public static void typeEncrypt(WebDriver driver, By locator
            , String text) {
        typeEncrypt(driver, locator
                , text, true);
    }

    public ElementActions typeEncrypt(By locator
            , String text) {
        typeEncrypt(driver, locator
                , text, true);
        return this;
    }

    public ElementActions typeEncrypt(By locator
            , String text, boolean clearBeforeTyping) {
        typeEncrypt(driver, locator
                , text, clearBeforeTyping);
        return this;
    }

    @Step("Click on element: [{locator" +
            "}]")
    public static void click(WebDriver driver, By locator
    ) {
        hover(driver, locator
        );
        try {
            WaitsManager.getExplicitWait(driver).until(ExpectedConditions.elementToBeClickable(locator
            ));
        } catch (Exception toe) {
            Logger.logError(toe.getMessage());
            fail(toe.getMessage());
        }
        try {
            if (!driver.findElement(locator
            ).getText().isBlank()) {
                Logger.logInfoStep("[Element Action] Click on [" + driver.findElement(locator
                ).getText() + "] Button");
            } else {
                Logger.logInfoStep("[Element Action] Click on element [" + locator
                        + "]");
            }
            driver.findElement(locator
            ).click();
        } catch (Exception exception) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[arguments.length - 1].click();", driver.findElement(locator
                ));
            } catch (Exception rootCauseException) {
                rootCauseException.initCause(exception);
                Logger.logError(exception.getMessage());
                Logger.logError(rootCauseException.getMessage());
                fail("Couldn't click on the element:" + locator
                        , rootCauseException);
            }
        }
    }

    public ElementActions click(By locator
    ) {
        click(driver, locator
        );
        return this;
    }


    //**************************************      Select Actions         **********************************************//
    //******************************************************************************************************************//
    public static void select(WebDriver driver, By locator
            , ElementHelper.SelectBy selectBy, String option) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            Select select = new Select(driver.findElement(locator
            ));
            Logger.logInfoStep("[Element Action] Select [" + option + "] on element [" + locator
                    + "]");
            assertFalse(select.isMultiple());
            switch (selectBy) {
                case TEXT -> select.selectByVisibleText(option);
                case VALUE -> select.selectByValue(option);
                case INDEX -> select.selectByIndex(Integer.parseInt(option));
                default -> Logger.logError("Unexpected value: " + selectBy);
            }
        } catch (Exception e) {
            Logger.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions select(By locator
            , ElementHelper.SelectBy selectBy, String option) {
        select(driver, locator
                , selectBy, option);
        return this;
    }

    public ElementActions select(By locator
            , ElementHelper.SelectBy selectBy, int option) {
        select(driver, locator
                , selectBy, String.valueOf(option));
        return this;
    }


    //**************************************       Device Actions         **********************************************//
    //******************************************************************************************************************//
    static Actions actions;

    public static void doubleClick(WebDriver driver, By locator
    ) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            actions = new Actions(driver);
            Logger.logInfoStep("[Element Action] Double Click on element [" + locator
                    + "]");
            actions.doubleClick(driver.findElement(locator
            )).perform();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions doubleClick(By locator
    ) {
        doubleClick(driver, locator
        );
        return this;
    }

    public static void rightClick(WebDriver driver, By locator
    ) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            actions = new Actions(driver);
            Logger.logInfoStep("[Element Action] Right Click on element [" + locator
                    + "]");
            actions.contextClick(driver.findElement(locator
            )).perform();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions rightClick(By locator
    ) {
        rightClick(driver, locator
        );
        return this;
    }


    public static void hover(WebDriver driver, By locator
    ) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            actions = new Actions(driver);
            Logger.logInfoStep("[Element Action] Hover on [" + driver.findElement(locator
            ).getText() + "]");
            actions.moveToElement(driver.findElement(locator
            )).perform();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions hover(By locator
    ) {
        hover(driver, locator
        );
        return this;
    }

    public static void hoverAndClick(WebDriver driver, By locator
    ) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            actions = new Actions(driver);
            Logger.logInfoStep("[Element Action] Hover on [" + driver.findElement(locator
            ).getText() + "]");
            actions.moveToElement(driver.findElement(locator
            )).click().perform();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions hoverAndClick(By locator
    ) {
        hoverAndClick(driver, locator
        );
        return this;
    }

    @Step("Click a Keyboard Key on element: [{locator" +
            "}]")
    public static void keyPress(WebDriver driver, By locator
            , Keys key) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            actions = new Actions(driver);
            Logger.logInfoStep("[Element Action] Click a Keyboard key [" + key.name() + "] on element [" + locator
                    + "]");
            // We click ENTER here! :D
            driver.findElement(locator
            ).sendKeys(key);
            actions.sendKeys(key).perform();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
    }

    public ElementActions keyPress(By locator
            , Keys key) {
        keyPress(driver, locator
                , key);
        return this;
    }

    public static void clickAndSendKeys(WebDriver driver, By locator
            , String text) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            actions = new Actions(driver);
            Logger.logInfoStep("[Element Action] Sending Keys [" + text + "] to element [" + locator
                    + "]");
            actions.click(driver.findElement(locator
                    ))
                    .sendKeys(text).build().perform();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
    }

    public ElementActions clickAndSendKeys(By locator
            , String text) {
        clickAndSendKeys(driver, locator
                , text);
        return this;
    }

    public static void clickAndHoldTo(WebDriver driver, By fromLocator, By toLocator) {
        ElementHelper.locatingElementStrategy(driver, fromLocator);
        try {
            actions = new Actions(driver);
            Logger.logInfoStep("[Element Action] Click and hold on [" + driver.findElement(fromLocator).getText() + "]");
            actions.clickAndHold(driver.findElement(fromLocator))
                    .moveToElement(driver.findElement(toLocator))
                    .build()
                    .perform();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
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
            Logger.logInfoStep("[Element Action] Click and hold on [" + driver.findElement(fromLocator).getText() + "] to [" + xOffset + "," + yOffset + "]");
            actions.clickAndHold(driver.findElement(fromLocator))
                    .moveByOffset(xOffset, yOffset)
                    .release()
                    .build()
                    .perform();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
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
            Logger.logInfoStep("[Element Action] Drag and Drop [" + driver.findElement(sourceLocator).getText() + "] to [" + driver.findElement(targetLocator).getText() + "]");
            actions.dragAndDrop(driver.findElement(sourceLocator), driver.findElement(targetLocator)).perform();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
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
            Logger.logInfoStep("[Element Action] Drag and Drop [" + driver.findElement(sourceLocator).getText() + "] to [" + xOffset + "," + yOffset + "]");
            actions.dragAndDropBy(driver.findElement(sourceLocator), xOffset, yOffset).perform();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
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
            Logger.logError(e.getMessage());
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
            Logger.logInfoStep("[Element Action] Drawing [" + driver.findElement(canvasElement).getText() + "] ");
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
            Logger.logError(e.getMessage());
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

    public static void clickUsingJavaScript(WebDriver driver, By locator
    ) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            Logger.logInfoStep("[Element Action] Click on element [" + locator
                    + "] using JavaScript");
            ((JavascriptExecutor) driver).executeScript("arguments[arguments.length - 1].click();", driver.findElement(locator
            ));
        } catch (Exception e) {
            Logger.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions clickUsingJavaScript(By locator
    ) {
        clickUsingJavaScript(driver, locator
        );
        return this;
    }

    public static void sendKeysUsingJavaScript(WebDriver driver, By locator
            , String text) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            Logger.logInfoStep("[Element Action] Send Keys [" + text + "] to element [" + locator
                    + "] using JavaScript");
            ((JavascriptExecutor) driver).executeScript("arguments[arguments.length - 1].value='" + text + "';", driver.findElement(locator
            ));
        } catch (Exception e) {
            Logger.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions sendKeysUsingJavaScript(By locator
            , String text) {
        sendKeysUsingJavaScript(driver, locator
                , text);
        return this;
    }

    public static void scrollToElement(WebDriver driver, By locator
    ) {
        try {
            Logger.logInfoStep("Scroll to element [" + locator
                    + "]");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator
            ));
        } catch (Exception e) {
            Logger.logError("Failed to scroll to element [" + locator
                    + "]");
            e.printStackTrace();
        }
    }

    public ElementActions scrollToElement(By locator
    ) {
        scrollToElement(driver, locator
        );
        return this;
    }

    public static void scrollToTop(WebDriver driver) {
        try {
            Logger.logInfoStep("Scroll to top of the page");
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        } catch (Exception e) {
            Logger.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public ElementActions scrollToTop() {
        scrollToTop(driver);
        return this;
    }


    //*******************************************    Element helper Methods    **********************************************************//
    //***********************************************************************************************************************************//
    @Step("Check if element: [{locator" +
            "}] is Displayed")
    public static boolean isDisplayed(WebDriver driver, By locator
    ) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            Logger.logInfoStep("[Element Action] Check if element [" + locator
                    + "] is Displayed");
            return driver.findElement(locator
            ).isDisplayed();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
        return false;
    }

    public ElementActions isDisplayed(By locator
    ) {
        isDisplayed(driver, locator
        );
        return this;
    }

    @Step("Check if element: [{locator" +
            "}] is Enabled")
    public static boolean isEnabled(WebDriver driver, By locator
    ) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            Logger.logInfoStep("[Element Action] Check if element [" + locator
                    + "] is Enabled");
            return driver.findElement(locator
            ).isEnabled();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
        return false;
    }

    public ElementActions isEnabled(By locator
    ) {
        isEnabled(driver, locator
        );
        return this;
    }

    @Step("Check if element: [{locator" +
            "}] is Selected")
    public static boolean isSelected(WebDriver driver, By locator
    ) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            Logger.logInfoStep("[Element Action] Check if element [" + locator
                    + "] is Selected");
            return driver.findElement(locator
            ).isSelected();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
        return false;
    }

    public ElementActions isSelected(By locator
    ) {
        isSelected(driver, locator
        );
        return this;
    }

//**********************************************     Getter Methods    **************************************************************//
//***********************************************************************************************************************************//

    @Step("Get the Text of element: [{locator" +
            "}]")
    public static String getText(WebDriver driver, By locator
    ) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            String text = driver.findElement(locator
            ).getText();
            Logger.logInfoStep("[Element Action] Get the Text of element [" + locator
                    + "]; The Text is [" + text + "]");
            return text;
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
        return null;
    }

    public static String getAttributeValue(WebDriver driver, By locator
            , String attributeName) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            String attributeValue = driver.findElement(locator
            ).getAttribute(attributeName);
            Logger.logInfoStep("[Element Action] Get the Attribute [" + attributeName + "] Value of element [" + locator
                    + "]; The Value is [" + attributeValue + "]");
            return attributeValue;
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
        return null;
    }

    public static String getCssValue(WebDriver driver, By locator
            , String cssValue) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        try {
            String cssValueOfElement = driver.findElement(locator
            ).getCssValue(cssValue);
            Logger.logInfoStep("[Element Action] Get the CSS Value [" + cssValue + "] of element [" + locator
                    + "]; The Value is [" + cssValueOfElement + "]");
            return cssValueOfElement;
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
        return null;
    }

    public static int getElementsCount(WebDriver driver, By locator
    ) {
        ElementHelper.locatingElementStrategy(driver, locator
        );
        int elementsCount = driver.findElements(locator
        ).size();
        Logger.logInfoStep("[Element Action] Get the Elements Count of element [" + locator
                + "]; The Count is [" + elementsCount + "]");
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
            Logger.logError(throwable.getMessage());
            return null;
        }
        WebElement table = driver.findElement(tableLocator);
        try {
            //Wait until any row is loaded because some websites use lazy loading,
            //and you need to wait for rows to be loaded
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tbody tr")));
        } catch (Exception e) {
            Logger.logError(e.getMessage() + " Table\"" + tableLocator + "\" is empty");

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
    public static boolean verifyElementNotVisible(By locator
            , int timeout) {
        try {
            WaitsManager.getExplicitWait(driver, timeout).until(ExpectedConditions.invisibilityOfElementLocated(locator
            ));
            return true;
        } catch (Exception e) {
            Logger.logWarning("Element is still visible after [" + timeout + "] seconds of waiting element [" + locator
                    + "] : " + e.getMessage());
            return false;
        }
    }

}
