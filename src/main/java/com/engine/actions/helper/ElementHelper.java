package com.engine.actions.helper;

import com.engine.Waits;
import com.engine.actions.ElementActions;
import com.engine.constants.FrameworkConstants;
import com.engine.reports.CustomReporter;
import io.appium.java_client.AppiumDriver;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.engine.actions.BrowserActions.AlertAction.GET_TEXT;
import static com.engine.reports.CustomReporter.failAction;

import static org.testng.Assert.fail;

public class ElementHelper {
    public enum SelectBy {
        TEXT, VALUE, INDEX
    }

    public static void locatingElementStrategy(WebDriver driver, By elementLocator) {
        try {
            // Wait for the element to be visible
            Waits.getExplicitWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            Waits.getFluentWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            // Scroll the element into view to handle some browsers cases
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(elementLocator));
            // Check if the element is displayed
            if (!driver.findElement(elementLocator).isDisplayed()) {
                CustomReporter.logInfoStep("The element [" + elementLocator.toString() + "] is not Displayed");
                fail("The element [" + elementLocator + "] is not Displayed");
            }
        } catch (Exception toe) {
            CustomReporter.logError(toe.getMessage());
            fail(toe.getMessage());
        }
    }

    public static void verifyLink(String urlLink) {
        try {
            URL link = new URL(urlLink);
            HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
            httpConn.setConnectTimeout(2000);
            httpConn.connect();
            if (httpConn.getResponseCode() == 200) {
                System.out.println("[" + urlLink + "] - " + httpConn.getResponseMessage() + " - " + httpConn.getResponseCode());
            } else if (httpConn.getResponseCode() == 404) {
                System.out.println("[" + urlLink + "] - " + httpConn.getResponseMessage() + " - " + httpConn.getResponseCode());
            } else {
                System.out.println("[" + urlLink + "] - " + httpConn.getResponseMessage() + " - " + httpConn.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getLinks(WebElement elementLocator) {
        List<WebElement> links = elementLocator.findElements(By.tagName("a"));
        System.out.println("The Number of The links is : " + links.size());
        for (WebElement Link : links) {
            System.out.println(Link.getAttribute("href"));
        }
    }


    public static void getCellFromTable(List<WebElement> rows, By tagName) {
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(tagName);
            for (WebElement cell : cols) {
                System.out.println(cell.getText() + "\t");
            }
            // To Print Empty line between each row
            System.out.println();
        }
    }

    private static void performType(WebDriver driver, ElementInformation elementInformation, String text) {
        try {
            (elementInformation.getFirstElement()).sendKeys(text);
        } catch (WebDriverException webDriverException2) {
            performActionAgainstUniqueElement(driver, elementInformation.getLocator(), ElementAction.SEND_KEYS, text);
        }
    }

    public static List<Object> identifyUniqueElementIgnoringVisibility(WebDriver driver, By elementLocator) {
        return identifyUniqueElement(driver, elementLocator, false);
    }

    private static List<Object> identifyUniqueElement(WebDriver driver, By elementLocator, boolean checkForVisibility, Object... action) {
        var matchingElementsInformation = getMatchingElementsInformation(driver, elementLocator, 1, checkForVisibility, action);
        if (elementLocator != null) {
            // in case of regular locator
            switch (Integer.parseInt(matchingElementsInformation.get(0).toString())) {
                case 0 -> {
                    if (matchingElementsInformation.size() > 2 && matchingElementsInformation.get(2) instanceof Throwable) {
                        CustomReporter.logError("Failed to identify unique element using this locator \"" + formatLocatorToString(elementLocator) + "\"" + (Throwable) matchingElementsInformation.get(2));
                    }
                    CustomReporter.logError("Failed to identify unique element using this locator \"" + formatLocatorToString(elementLocator) + "\"");
                }
                case 1 -> {
                    return matchingElementsInformation;
                }
                default -> {
                    CustomReporter.logWarning("Failed to identify unique element, Multiple elements found matching this locator \"" + formatLocatorToString(elementLocator) + "\"");
                    return matchingElementsInformation;
                }
            }
        } else {
            // in case locator is null
            failAction(driver + "element locator is NULL.");
        }
        //unreachable code
        return matchingElementsInformation;
    }

    public static List<Object> getMatchingElementsInformation(WebDriver driver, By elementLocator, int numberOfAttempts, boolean checkForVisibility, Object... action) {
        if (elementLocator == null) {
            var elementInformation = new ArrayList<>();
            elementInformation.add(0);
            elementInformation.add(null);
            return elementInformation;
        }
        if (!elementLocator.equals(By.tagName("html"))) {
            return waitForElementPresence(driver, elementLocator, numberOfAttempts, checkForVisibility, action);
        } else {
            //if locator is just tag-name html
            var elementInformation = new ArrayList<>();
            elementInformation.add(1);
            elementInformation.add(null);
            return elementInformation;
        }
    }

    public static List<Object> waitForElementPresence(WebDriver driver, By elementLocator, int numberOfAttempts, boolean checkForVisibility, Object... action) {
        boolean isValidToCheckForVisibility = isValidToCheckForVisibility(elementLocator, checkForVisibility);

        try {
//            JavaScriptWaitManager.waitForLazyLoading(driver);
            return new FluentWait<>(driver)
                    .withTimeout(Duration.ofMillis((long) (FrameworkConstants.ELEMENT_IDENTIFICATION_TIMEOUT * 1000L * numberOfAttempts)))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoreAll(getExpectedExceptions(isValidToCheckForVisibility))
                    .until(nestedDriver -> {
                        WebElement targetElement;
                        ElementInformation elementInformation = new ElementInformation();
                        // BLOCK #1 :: GETTING THE ELEMENT
                        if (ShadowLocatorBuilder.shadowDomLocator != null && ShadowLocatorBuilder.cssSelector == elementLocator) {
                            targetElement = driver.findElement(ShadowLocatorBuilder.shadowDomLocator).getShadowRoot().findElement(ShadowLocatorBuilder.cssSelector);
                        } else if (LocatorBuilder.getIFrameLocator() != null) {
                            try {
                                targetElement = driver.switchTo().frame(driver.findElement(LocatorBuilder.getIFrameLocator())).findElement(elementLocator);
                            } catch (NoSuchElementException exception) {
                                targetElement = driver.findElement(elementLocator);
                            }
                        } else {
                            targetElement = driver.findElement(elementLocator);
                        }
                        // BLOCK #2 :: GETTING THE ELEMENT LOCATION (RECT)
                        try {
                            elementInformation.setElementRect(targetElement.getRect());
                        } catch (ElementNotInteractableException elementNotInteractableException) {
                            // this exception happens sometimes with certain browsers and causes a timeout
                            // this empty block should handle that issue
                        }
                        // BLOCK #3 :: SCROLLING TO ELEMENT | CONFIRMING IT IS DISPLAYED
                        if (isValidToCheckForVisibility) {
                            targetElement.isDisplayed();
                        }
                        // BLOCK #4 :: GETTING THE NUMBER OF FOUND ELEMENTS
                        if (ShadowLocatorBuilder.shadowDomLocator != null && ShadowLocatorBuilder.cssSelector == elementLocator) {
                            elementInformation.setNumberOfFoundElements(driver.findElement(ShadowLocatorBuilder.shadowDomLocator).getShadowRoot().findElements(ShadowLocatorBuilder.cssSelector).size());
                        } else {
                            elementInformation.setNumberOfFoundElements(driver.findElements(elementLocator).size());
                        }

                        var elementName = formatLocatorToString(elementLocator);
                        try {
                            var accessibleName = targetElement.getAccessibleName();
                            if (accessibleName != null && !accessibleName.isBlank()) {
                                elementName = accessibleName;
                            }
                        } catch (Throwable throwable) {
                            //happens on some elements that show unhandled inspector error
                            //this exception is thrown on some older selenium grid instances, I saw it with firefox running over selenoid
                            //ignore
                        }
                        elementInformation.setElementName(elementName);
                        elementInformation.setFirstElement(targetElement);
                        elementInformation.setLocator(elementLocator);

                        // BLOCK #6 :: PERFORMING ACTION  (WITH OPTIONAL ARGS)
                        // attempt to perform action inside the loop to guarantee higher odds of success and reduced WebDriver calls
                        var len = action != null ? action.length : 0;
                        switch (len) {
                            case 1 ->
                                    elementInformation.setActionResult(performAction(driver, elementInformation, (ElementAction) action[0], ""));
                            case 2 ->
                                    elementInformation.setActionResult(performAction(driver, elementInformation, (ElementAction) action[0], action[1]));
                        }
                        return elementInformation.toList();
                        // int numberOfFoundElements
                        // WebElement firstElement
                        // By locator
                        // String outerHTML (or empty string)
                        // String innerHTML (or empty string)
                        // String elementName (or empty string)
                    });
        } catch (org.openqa.selenium.TimeoutException timeoutException) {
            // In case the element was not found / not visible and the timeout expired
            var causeMessage = timeoutException.getCause().getMessage();
            causeMessage = !causeMessage.isBlank() && causeMessage.contains("\n") ? timeoutException.getMessage() + " || " + causeMessage.substring(0, causeMessage.indexOf("\n")) : timeoutException.getMessage();
            CustomReporter.logError(causeMessage);
            var elementInformation = new ArrayList<>();
            elementInformation.add(0);
            elementInformation.add(null);
            elementInformation.add(timeoutException);
            return elementInformation;
        } catch (org.openqa.selenium.InvalidSelectorException invalidSelectorException) {
            // In case the selector is not valid
            CustomReporter.logError(invalidSelectorException.getMessage());
            var elementInformation = new ArrayList<>();
            elementInformation.add(0);
            elementInformation.add(null);
            elementInformation.add(invalidSelectorException);
            return elementInformation;
        }
    }

    private static boolean isValidToCheckForVisibility(By elementLocator, boolean checkForVisibility) {
        var locatorString = formatLocatorToString(elementLocator).toLowerCase();
        return checkForVisibility
                && !locatorString.contains("type='file'")
                && !locatorString.contains("type=\"file\"")
                && !locatorString.contains("frame")
                && !elementLocator.equals(By.tagName("html"));
    }

    public static String formatLocatorToString(By locator) {
        if (locator instanceof RelativeLocator.RelativeBy relativeLocator) {
            return "Relative Locator: " + relativeLocator.getRemoteParameters().value().toString();
        } else {
            return locator.toString();
        }
    }

    public static ArrayList<Class<? extends Exception>> getExpectedExceptions(boolean isValidToCheckForVisibility) {
        ArrayList<Class<? extends Exception>> expectedExceptions = new ArrayList<>();
        expectedExceptions.add(java.lang.ClassCastException.class);
        expectedExceptions.add(org.openqa.selenium.NoSuchElementException.class);
        expectedExceptions.add(org.openqa.selenium.StaleElementReferenceException.class);
        expectedExceptions.add(org.openqa.selenium.JavascriptException.class);
        expectedExceptions.add(org.openqa.selenium.ElementClickInterceptedException.class);
        if (isValidToCheckForVisibility) {
            expectedExceptions.add(org.openqa.selenium.ElementNotInteractableException.class);
            expectedExceptions.add(org.openqa.selenium.InvalidElementStateException.class);
            expectedExceptions.add(org.openqa.selenium.interactions.MoveTargetOutOfBoundsException.class);
        }
        // to handle failure inside a virtual thread
        expectedExceptions.add(ExecutionException.class);
        expectedExceptions.add(InterruptedException.class);
        expectedExceptions.add(RuntimeException.class);
        return expectedExceptions;
    }

    public static List<Object> performActionAgainstUniqueElement(WebDriver driver, By elementLocator, Object... action) {
        return identifyUniqueElement(driver, elementLocator, true, action);
    }

    public static List<Object> performActionAgainstUniqueElementIgnoringVisibility(WebDriver driver, By elementLocator, Object... action) {
        return identifyUniqueElement(driver, elementLocator, false, action);
    }

    private static String performAction(WebDriver driver, ElementInformation elementInformation, ElementAction action, Object parameter) {
        switch (action) {
            case CLICK -> {
                //move to element
                try {
                    (new Actions(driver)).moveToElement(elementInformation.getFirstElement()).perform();
                    CustomReporter.logConsole("Moved the mouse to the middle of the element.");
                } catch (Throwable throwable) {
                    //ignored
                }
                try {
                    elementInformation.getFirstElement().click();
                } catch (Throwable throwable) {
                    var scriptResult = ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementInformation.getFirstElement());

                }
            }
            case CLEAR -> elementInformation.getFirstElement().clear();
            case BACKSPACE -> elementInformation.getFirstElement().sendKeys(Keys.BACK_SPACE);
            case GET_TEXT -> {
                return elementInformation.getFirstElement().getText();
            }
            case GET_VALUE -> {
                return elementInformation.getFirstElement().getAttribute(TextDetectionStrategy.VALUE.getValue());
            }
            case GET_CONTENT -> {
                return elementInformation.getFirstElement().getAttribute(TextDetectionStrategy.CONTENT.getValue());
            }
            case GET_ATTRIBUTE -> {
                return elementInformation.getFirstElement().getAttribute((String) parameter);
            }
            case SEND_KEYS -> elementInformation.getFirstElement().sendKeys((CharSequence) parameter);
            case IS_DISPLAYED -> {
                return String.valueOf(elementInformation.getFirstElement().isDisplayed());
            }
            case SET_VALUE_USING_JAVASCRIPT ->
                    ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", elementInformation.getFirstElement(), parameter);
        }
        return "";
    }


    @Getter
    public enum TextDetectionStrategy {
        TEXT("text"), CONTENT("textContent"), VALUE("value"), UNDEFINED("undefined");
        private final String value;

        TextDetectionStrategy(String strategy) {
            this.value = strategy;
        }

    }

    public static String newTypeWrapper(WebDriver driver, ElementInformation elementInformation, String targetText) {
        newClearBeforeTyping(driver, elementInformation);
        var adjustedTargetText = targetText != null && !targetText.isEmpty() ? targetText : "";
        performType(driver, elementInformation, adjustedTargetText);
        //sometimes the text is returned as empty
        return adjustedTargetText;
    }


    private static void newClearBeforeTyping(WebDriver driver, ElementInformation elementInformation) {
        if (FrameworkConstants.attemptClearBeforeTyping) {
            if (FrameworkConstants.attemptClearBeforeTypingUsingBackspace) {
                clearBeforeTypingUsingBackSpace(driver, elementInformation);
            } else {
                clearBeforeTypingUsingNativeClear(driver, elementInformation);
            }
        }
    }

    private static void clearBeforeTypingUsingBackSpace(WebDriver driver, ElementInformation elementInformation) {
        var currentText = (elementInformation.getFirstElement()).getText();
        // try deleting letter by letter using backspaces
        for (var ignored : currentText.toCharArray()) {
            try {
                (elementInformation.getFirstElement()).sendKeys(Keys.BACK_SPACE);
            } catch (WebDriverException webDriverException) {
                performActionAgainstUniqueElement(driver, elementInformation.getLocator(), ElementAction.BACKSPACE);
            }
        }
        var currentTextAfterClearingUsingBackSpace = readElementText(driver, elementInformation);
        if (currentTextAfterClearingUsingBackSpace.isBlank()) {
            CustomReporter.logConsole("text cleared Using BackSpace");
        } else {
            failAction("Expected to clear existing text, but ended up with: \"" + currentTextAfterClearingUsingBackSpace + "\"" + elementInformation.getLocator());
        }
    }

    private static void clearBeforeTypingUsingNativeClear(WebDriver driver, ElementInformation elementInformation) {
        // try clearing text
        try {
            elementInformation.getFirstElement().clear();
        } catch (Throwable throwable) {
            performActionAgainstUniqueElement(driver, elementInformation.getLocator(), ElementAction.CLEAR);
        }
        var currentTextAfterClearingUsingNativeClear = readElementText(driver, elementInformation);
        if (currentTextAfterClearingUsingNativeClear.isBlank()) {
            CustomReporter.logConsole("text cleared Using Native Clear");
        } else {
            failAction("Expected to clear existing text, but ended up with: \"" + currentTextAfterClearingUsingNativeClear + "\"" + elementInformation.getLocator());
        }
    }

    public static String readElementText(WebDriver driver, ElementInformation elementInformation) {
        String elementText;
        try {
            elementText = (elementInformation.getFirstElement()).getText();
        } catch (WebDriverException webDriverException) {
            elementText = ElementInformation.fromList(performActionAgainstUniqueElementIgnoringVisibility(driver, elementInformation.getLocator(), ElementAction.GET_TEXT)).getActionResult();
        }
        if ((elementText == null || elementText.isBlank())) {
            try {
                elementText = (elementInformation.getFirstElement()).getAttribute(TextDetectionStrategy.CONTENT.getValue());
            } catch (WebDriverException webDriverException) {
                elementText = ElementInformation.fromList(performActionAgainstUniqueElementIgnoringVisibility(driver, elementInformation.getLocator(), ElementAction.GET_CONTENT)).getActionResult();
            }
        }
        if ((elementText == null || elementText.isBlank())) {
            try {
                elementText = (elementInformation.getFirstElement()).getAttribute(TextDetectionStrategy.VALUE.getValue());
            } catch (WebDriverException webDriverException) {
                elementText = ElementInformation.fromList(performActionAgainstUniqueElementIgnoringVisibility(driver, elementInformation.getLocator(), ElementAction.GET_VALUE)).getActionResult();
            }
        }
        if (elementText == null) {
            elementText = "";
        }
        return elementText;
    }

    public enum ElementAction {
        CLEAR, BACKSPACE, GET_TEXT, GET_CONTENT, GET_VALUE, SEND_KEYS, IS_DISPLAYED, SET_VALUE_USING_JAVASCRIPT, GET_ATTRIBUTE, CLICK
    }


}