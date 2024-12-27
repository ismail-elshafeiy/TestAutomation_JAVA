package com.engine.actions;

import com.engine.Helper;
import com.engine.WaitsManager;
import com.engine.constants.FrameworkConstants;
import com.engine.dataDriven.PropertiesManager;
import com.engine.evidence.RecordVideo;
import com.engine.reports.AllureReport;
import com.engine.reports.ExtentReport;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.bidi.BiDiException;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevToolsException;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestResult;
import com.engine.reports.CustomReporter;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

import static com.engine.reports.CustomReporter.passAction;
import static org.testng.Assert.fail;

public class BrowserActions {
    static WebDriver driver;

    public BrowserActions(WebDriver driver) {
        BrowserActions.driver = driver;
    }

    public static BrowserActions getInstance() {
        return new BrowserActions(driver);
    }

    @Step("Navigate to URL: [{url}]")
    public static void navigateToUrl(WebDriver driver, String url) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Navigate to URL [" + url + "]");
            driver.get(url);
            ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        } catch (Exception e) {
            CustomReporter.logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
    }

    @Step("Navigate to URL: [{url}]")
    public BrowserActions navigateToUrl(String url) {
        navigateToUrl(driver, url);
        return this;
    }

    @Step("Navigate to URL using JavaScript: [{url}]")
    public static void navigateToUrlUsingJavaScript(WebDriver driver, String url) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Navigate to URL using JavaScript [" + url + "]");
            ((JavascriptExecutor) driver).executeScript("window.location = '" + url + "'");
        } catch (Exception e) {
            CustomReporter.logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
    }

    @Step("Navigate to URL using JavaScript: [{url}]")
    public BrowserActions navigateToUrlUsingJavaScript(String url) {
        navigateToUrlUsingJavaScript(driver, url);
        return this;
    }


    //*************************************    Windows Methods   **********************************//
    //*********************************************************************************************//

    //****** Window Positions and Size ******//
    @Step("Maximize the Browser Window")
    public static void maximizeWindow(WebDriver driver) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Maximize the Browser Window");
            driver.manage().window().maximize();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    @Step("Minimize the Browser Window")
    public static void minimizeWindow(WebDriver driver) {
        try {
            CustomReporter.logInfoStep("[Browser Action] minimize the Browser Window");
            driver.manage().window().minimize();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    @Step("Full Screen the Browser Window")
    public static void fullScreenWindow(WebDriver driver) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Full Screen the Browser Window");
            driver.manage().window().fullscreen();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    @Step("Set the Window Size [{width}], [{height}]")
    public static void setWindowSize(WebDriver driver, int width, int height) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Set Window Resolution as Width [" + width + "] and Height [" + height + "]");
            Dimension dimension = new Dimension(width, height);
            driver.manage().window().setSize(dimension);
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    @Step("Set the Window Size [{width}], [{height}]")
    public static void setWindowSize(WebDriver driver) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Set Window Resolution as Width [" + FrameworkConstants.WIDTH + "] and Height [" + FrameworkConstants.WIDTH + "]");
            Dimension dimension = new Dimension(Integer.parseInt(FrameworkConstants.WIDTH), Integer.parseInt(FrameworkConstants.HEIGHT));
            driver.manage().window().setSize(dimension);
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    @Step("Get Window Size")
    public static void getWindowSize(WebDriver driver) {
        try {
            Dimension dimension = driver.manage().window().getSize();
            CustomReporter.logInfoStep("[Browser Action] Window Size : [ " + dimension + " ]");
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    @Step("Set Window Positions")
    public static void setWindowPositions(WebDriver driver, int x, int y) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Set Window Positions");
            driver.manage().window().setPosition(new Point(x, y));
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    @Step("Get Window Positions")
    public static void getWindowPositions(WebDriver driver) {
        try {
            Point point = driver.manage().window().getPosition();
            CustomReporter.logInfoStep("[Browser Action] Window Positions : [ " + point + " ]");
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    public enum PageType {
        WINDOW("window"), TAB("tab");
        private final String windowType;

        PageType(String windowType) {
            this.windowType = windowType;
        }
    }

    @Step("Switch to Window [{windowType}]")
    public static void switchToNewPage(WebDriver driver, PageType pageType, String url) {
        try {
            switch (pageType) {
                case WINDOW -> {
                    CustomReporter.logInfoStep("[Browser Action] Switch to new Window Title: " + driver.getTitle() + " [" + url + "]");
                    driver.switchTo().newWindow(WindowType.WINDOW).get(url);
                }
                case TAB -> {
                    CustomReporter.logInfoStep("[Browser Action] Switch to new Tab Title: " + driver.getTitle() + " [" + url + "]");
                    driver.switchTo().newWindow(WindowType.TAB).get(url);
                }
            }
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    @Step("Switch to new window and focus on it")
    public static void switchToNewWindowByClickHyperLink(WebDriver driver, By elementLocator) {
        try {
            getPageTitle(driver);
            String parentWindow = driver.getWindowHandle();
            ElementActions.click(driver, elementLocator);
            // Return a set of all window handles that can be used
            Set<String> handles = driver.getWindowHandles();
            for (String childWindow : handles) {
                if (!parentWindow.equals(childWindow)) {
                    driver.switchTo().window(childWindow);
                    CustomReporter.logInfoStep("[Browser Action] Switch to new Window Title: " + driver.getTitle());
                }
            }
            CustomReporter.logError("All Windows= " + handles.size());
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }


    @Step("Check the test result and Close All Opened Browser Windows.....")
    public static void closeAllOpenedBrowserWindows(WebDriver driver, ITestResult result) throws Throwable {
        if (ITestResult.FAILURE == result.getStatus()) {
            AllureReport.attachScreenshotToAllureReport(driver);
            ExtentReport.attachScreenshotToExtentReport(driver);
            CustomReporter.logConsoleLogs(driver, result);
            closeAllOpenedBrowserWindows(driver);
            // eyesManager.abort();
        }
    }

    @Step("Close All Opened Browser Windows.....")
    public static void closeAllOpenedBrowserWindows(WebDriver driver) {
        CustomReporter.logInfoStep("[Browser Action] Close all Opened Browser Windows");
        RecordVideo.attachVideoRecording();
        if (driver != null) {
            try {
                driver.quit();
            } catch (WebDriverException rootCauseException) {
                CustomReporter.logError(rootCauseException.getMessage());
            }
        } else {
            CustomReporter.logError("Windows are already closed and the driver object is null.");
        }
    }


    //**************************************  Alerts Methods **************************************//
    //*********************************************************************************************//

    public enum AlertAction {
        ACCEPT("accept"),
        DISMISS("dismiss"),
        SET_TEXT("Text"),
        GET_TEXT("Get Text");
        private final String alertType;

        AlertAction(String alertType) {
            this.alertType = alertType;
        }
    }

    @Step("Confirm the Alert")
    public static void alertAction(WebDriver driver, AlertAction confirmAlertType) {
        WaitsManager.getExplicitWait(driver).until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        try {
            switch (confirmAlertType) {
                case ACCEPT -> {
                    CustomReporter.logInfoStep("[Browser Action] Confirm the Alert");
                    alert.accept();
                }
                case DISMISS -> {
                    CustomReporter.logInfoStep("[Browser Action] Dismiss the Alert");
                    alert.dismiss();
                }
                case GET_TEXT -> {
                    CustomReporter.logInfoStep("[Browser Action] Get Text from the Alert");
                    alert.getText();
                }
            }
        } catch (Exception e) {
            CustomReporter.logError("Alert is not present" + e.getMessage());
        }

    }

    @Step("Confirm the Alert")
    public static void alertAction(WebDriver driver, String text) {
        WaitsManager.getExplicitWait(driver).until(ExpectedConditions.alertIsPresent());
        try {
            CustomReporter.logInfoStep("[Browser Action] Send Keys the Alert");
            driver.switchTo().alert().sendKeys(text);
        } catch (Exception e) {
            CustomReporter.logError("Alert is not present" + e.getMessage());
        }
    }

    //************************************** Cookies Methods ************************************** //
    //*********************************************************************************************//

    public enum CookieBuilderType {
        ADD, DELETE
    }

    public static void cookieBuilder(WebDriver driver,
                                     CookieBuilderType cookieBuilderType,
                                     String name, String value, String domain) {
        Cookie cookie = new Cookie.Builder(name, value).domain(domain).build();
        switch (cookieBuilderType) {
            case ADD -> driver.manage().addCookie(cookie);
            case DELETE -> driver.manage().deleteCookie(cookie);
        }
    }

    public static void getAllCookies(WebDriver driver) {
        try {
            Set<Cookie> cookieSet = driver.manage().getCookies();
            CustomReporter.logInfoStep("Get All Cookies = " + cookieSet.size());
            for (Cookie cookie : cookieSet) {
                CustomReporter.logInfoStep("Cookie Domain: " + cookie.getDomain());
                CustomReporter.logInfoStep("Cookie Name: " + cookie.getName());
                CustomReporter.logInfoStep("Cookie Value: " + cookie.getValue());
                CustomReporter.logInfoStep("Cookie Path: " + cookie.getPath());
                CustomReporter.logInfoStep("Cookie Expiry: " + cookie.getExpiry());
            }
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());

        }
    }

    public boolean isCookiePresent(WebDriver driver, Cookie cookie) {
        return driver.manage().getCookieNamed(cookie.getName()) != null;
    }

    //**************************************  Frames Methods ************************************** //
    //*********************************************************************************************//

    /**
     * Using driver.switchTo().frame() to handle frames
     * using with iframe's ID
     **/
    public static void switchToFrame(WebDriver driver, By elementLocator) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Switch to Frame by Locator: " + elementLocator.toString());
            driver.switchTo().frame(driver.findElement(elementLocator));
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    public BrowserActions switchToFrame(By elementLocator) {
        switchToFrame(driver, elementLocator);
        return this;
    }

    /**
     * Using driver.switchTo().frame() to handle frames
     * using with iframe's ID Or name
     **/
    public static void switchToFrame(WebDriver driver, String nameOrId) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Switch to Frame by name Or id: " + nameOrId);
            driver.switchTo().frame(nameOrId);
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    public BrowserActions switchToFrame(String nameOrId) {
        switchToFrame(driver, nameOrId);
        return this;
    }

    /**
     * Using driver.switchTo().frame() to handle frames
     * using with iframe's index
     **/
    public static void switchToFrame(WebDriver driver, int index) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Switch to Frame by index : " + index);
            driver.switchTo().frame(index);
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
        }
    }

    public BrowserActions switchToFrame(int index) {
        switchToFrame(driver, index);
        return this;
    }

    //**************************************  Navigation Methods ************************************** //
    //*********************************************************************************************//


    public static void goBack(WebDriver driver) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Navigate Back from [" + getCurrentUrl(driver) + "]");
            driver.navigate().back();
        } catch (Exception e) {
            CustomReporter.logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
    }

    public BrowserActions goBack() {
        goBack(driver);
        return this;
    }

    public static void goBackUsingJavascript(WebDriver driver) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Navigate Back from [" + getCurrentUrl(driver) + "]");
            ((JavascriptExecutor) driver).executeScript("window.history.go(-1)");
        } catch (Exception e) {
            CustomReporter.logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
    }

    public BrowserActions goBackUsingJavascript() {
        goBackUsingJavascript(driver);
        return this;
    }

    public static void goForward(WebDriver driver) {
        try {
            driver.navigate().forward();
            CustomReporter.logInfoStep("[Browser Action] Navigate Forward [" + getCurrentUrl(driver) + "]");
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public BrowserActions goForward() {
        goForward(driver);
        return this;
    }

    public static void goForwardUsingJavascript(WebDriver driver) {
        try {
            ((JavascriptExecutor) driver).executeScript("window.history.forward()");
            CustomReporter.logInfoStep("[Browser Action] Navigate Forward [" + getCurrentUrl(driver) + "]");
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
    }

    public BrowserActions goForwardUsingJavascript() {
        goForwardUsingJavascript(driver);
        return this;
    }


    public static void refreshPage(WebDriver driver) {
        try {
            CustomReporter.logInfoStep("[Browser Action] Refresh current page [" + getCurrentUrl(driver) + "]");
            driver.navigate().refresh();
        } catch (Exception e) {
            CustomReporter.logError(e.getMessage());
            fail(e.getMessage());
        }
        driver.navigate().forward();
    }

    public BrowserActions refreshPage() {
        refreshPage(driver);
        return this;

    }

    //**************************************  Getters Methods **************************************//
    //*********************************************************************************************//
    public static String getPageTitle(WebDriver driver) {
        String title = "";
        try {
            title = driver.getTitle();
            CustomReporter.logInfoStep("[Browser Action] Get page Title [" + title + "]");
        } catch (Exception e) {
            CustomReporter.logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
        return title;
    }

    public BrowserActions getPageTitle() {
        getPageTitle(driver);
        return this;
    }

    public static String getCurrentUrl(WebDriver driver) {
        String url = "";
        try {
            url = driver.getCurrentUrl();
            CustomReporter.logInfoStep("[Browser Action] Get current URL [" + url + "]");
        } catch (
                Exception e) {
            CustomReporter.logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
        return url;
    }

    public static String getCurrentPageUsingJavaScript(WebDriver driver) {
        String page = "";
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            page = (String) js.executeScript("return document.title");
            CustomReporter.logInfoStep("[Browser Action] Get current page using JavaScript [" + page + "]");
        } catch (Exception e) {
            CustomReporter.logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
        return page;
    }

    public BrowserActions getCurrentPageUsingJavaScript() {
        getCurrentUrlUsingJavaScript(driver);
        return this;
    }

    public BrowserActions getCurrentUrl() {
        getCurrentUrl(driver);
        return this;
    }

    public static String getCurrentUrlUsingJavaScript(WebDriver driver) {
        String page = "";
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            page = (String) js.executeScript("return document.domain;");
            CustomReporter.logInfoStep("[Browser Action] Get current url using JavaScript [" + page + "]");
        } catch (Exception e) {
            CustomReporter.logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
        return page;
    }

    public BrowserActions getCurrentUrlUsingJavaScript() {
        getCurrentUrlUsingJavaScript(driver);
        return this;
    }

    //**********************************************************************************************//
    //**************************************  Others Methods **************************************//
    //*********************************************************************************************//
    public BrowserActions capturePageSnapshot() {
        var serializedPageData = capturePageSnapshot(driver);
        passAction(driver, serializedPageData);
        return this;
    }

    private static String capturePageSnapshot(WebDriver driver) {
        CustomReporter.logConsole("Capturing page snapshot...");
        var serializedPageData = "";
        try {
            if (driver instanceof ChromiumDriver chromiumDriver) {
                var result = chromiumDriver.executeCdpCommand("Page.captureSnapshot", new HashMap<>());
                serializedPageData = (String) ((Map<String, ?>) result).get("data");
            } else {
                // get page source
                serializedPageData = driver.getPageSource();
            }
            return serializedPageData;
        } catch (BiDiException | DevToolsException exception) {
            CustomReporter.logError(exception.getMessage());
            return capturePageSnapshot(driver);
        } catch (WebDriverException webDriverException) {
            // unknown error: unhandled inspector error: {"code":-32000,"message":"Failed to generate MHTML"
            // try again but just get the regular page source this time
            return capturePageSnapshot(driver);
        } catch (Exception rootCauseException) {
            CustomReporter.logError(rootCauseException.getMessage());
            return serializedPageData;
        }
    }

    static String pdfPath = PropertiesManager.getPropertyValue("paths", "pdfPath");

    /**
     * Print the page using the PrintOptions class and the PrintsPage interface of the WebDriver class
     *
     * @param driver    - WebDriver Instance of the Browser
     * @param pageRange - Page Range to be printed
     * @throws
     */
    public static void printPage(WebDriver driver, int pageRange) {
        try {
            CustomReporter.logInfoStep("Printing " + driver.getTitle() + " page....... ");
            PrintsPage printer = ((PrintsPage) driver);
            PrintOptions printOptions = new PrintOptions();
            printOptions.setPageRanges(String.valueOf(pageRange));
            Pdf pdf = printer.print(printOptions);
            Files.write(new File(pdfPath + driver.getTitle() + Helper.getCurrentTime() + ".pdf").toPath(), OutputType.BYTES.convertFromBase64Png(pdf.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            CustomReporter.logError("Page not printed: " + e.getMessage());
        }
    }
}
