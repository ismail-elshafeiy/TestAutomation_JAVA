package engine.broswer;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestResult;

import engine.tools.Logger;
import engine.PropertiesReader;
import engine.RecordManager;


import static org.testng.Assert.fail;

public class BrowserActions {
    static WebDriver driver;


    @Step( "Navigate to URL: [{url}]" )
    public static void navigateToUrl(WebDriver driver, String url) {
        try {
            Logger.logStep("[Browser Action] Navigate to URL [" + url + "]");
            driver.get(url);
            ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        } catch (Exception e) {
            Logger.logStep(e.getMessage());
            fail(e.getMessage());
        }
    }

    @Step( "Check the test result and Close All Opened Browser Windows....." )
    public static void closeAllOpenedBrowserWindows(WebDriver driver, ITestResult result) {
//        if ( System.getProperty("videoParams_scope").trim().equals("DriverSession") ) {
//            RecordManager.attachVideoRecording();
//        }
        // checks if the TestResult is failure
        if ( ITestResult.FAILURE == result.getStatus() ) {
            Logger.attachScreenshotToAllureReport(driver);
            Logger.attachScreenshotToExtentReport(driver);
        }
        RecordManager.attachVideoRecording();
        Logger.logStep("[Browser Action] Close all Opened Browser Windows");
        if ( driver != null ) {
            try {
                driver.quit();
            } catch (WebDriverException rootCauseException) {
                Logger.logMessage(rootCauseException.getMessage());
            } finally {
                driver = null;
            }
        } else {
            Logger.logMessage("Windows are already closed and the driver object is null.");
        }
    }

    @Step( "Close All Opened Browser Windows....." )
    public static void closeAllOpenedBrowserWindows(WebDriver driver) {
        Logger.logStep("[Browser Action] Close all Opened Browser Windows");
        if ( driver != null ) {
            try {
                driver.quit();
            } catch (WebDriverException rootCauseException) {
                Logger.logMessage(rootCauseException.getMessage());
            } finally {
                driver = null;
            }
        } else {
            Logger.logMessage("Windows are already closed and the driver object is null.");
        }
    }

    public static synchronized void closeDriver(int hashCode) {
        if ( System.getProperty("videoParams_scope").trim().equals("DriverSession") ) {
            RecordManager.attachVideoRecording();
        }

    }

    @Step( "Maximize the Browser Window" )
    public static void maximizeWindow(WebDriver driver) {
        try {
            Logger.logStep("[Browser Action] Maximize the Browser Window");
            driver.manage().window().maximize();
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
        }
    }

    @Step( "Set the WindowResolution [{width}], [{height}]" )
    public static void setWindowResolution(WebDriver driver) {
        String width = PropertiesReader.getProperty("project.properties", "width");
        String height = PropertiesReader.getProperty("project.properties", "height");
        try {
            Logger.logStep("[Browser Action] Set Window Resolution as Width [" + width + "] and Height [" + height + "]");
            Dimension dimension = new Dimension(Integer.parseInt(width), Integer.parseInt(height));
            driver.manage().window().setSize(dimension);
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
        }
    }


    public enum ConfirmAlertType {
        ACCEPT, DISMISS;
    }

    @Step( "Confirm the Alert" )
    public static void confirmAlert(WebDriver driver, ConfirmAlertType confirmAlerType) {
        Waits.getExplicitWait(driver).until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        switch ( confirmAlerType ) {
            case ACCEPT:
                alert.accept();
                break;
            case DISMISS:
                Waits.getExplicitWait(driver).until(ExpectedConditions.alertIsPresent());
                alert.dismiss();
                break;
        }
    }

    public enum CookieBuilderType {
        ADD, DELETE;
    }

    public static void cookieBuilder(WebDriver driver, CookieBuilderType cookieBuilderType, String name, String value,
                                     String domain) {
        Cookie cookie = new Cookie.Builder(name, value).domain(domain).build();

        switch ( cookieBuilderType ) {
            case ADD:
                driver.manage().addCookie(cookie);
                break;
            case DELETE:
                driver.manage().deleteCookie(cookie);
                break;
        }
    }

    public boolean isCookiePresent(Cookie cookie) {
        return driver.manage().getCookieNamed(cookie.getName()) != null;
    }

    public Cookie buildCookie(String name, String value) {
        Cookie cookie = new Cookie.Builder(name, value)
                .domain("the-internet.herokuapp.com")
                .build();
        return cookie;
    }

    private static WebDriver.Navigation navigate;

    public static void setNavigate(WebDriver.Navigation navigate) {
        BrowserActions.navigate = navigate;
    }

    public static void goBack() {
        try {
            String currentPage1 = driver.getCurrentUrl();
            Logger.logStep("[Browser Action] Navigate Back from" + currentPage1);
            navigate.back();
        } catch (Exception e) {
            Logger.logStep(e.getMessage());
            fail(e.getMessage());
        }
    }

    public static void goForward() {
        try {
            navigate.forward();
            String currentPage = driver.getCurrentUrl();
            Logger.logStep("[Browser Action] Navigate Forward " + currentPage + "]");
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
    }

    public static void refreshPage() {
        try {
            String currentPage = driver.getCurrentUrl();
            Logger.logStep("[Browser Action] refresh current page" + currentPage + "]");
            navigate.refresh();
        } catch (Exception e) {
            Logger.logMessage(e.getMessage());
            fail(e.getMessage());
        }
        navigate.forward();

    }

    public void switchToTab(String tabTitle) {
        var windows = driver.getWindowHandles();
        Logger.logMessage("Number of tabs: " + windows.size());
        System.out.println("Window handles:");
        windows.forEach(System.out::println);
        for ( String window : windows ) {
            Logger.logStep("Switching to window: " + window);
            driver.switchTo().window(window);
            Logger.logMessage("Current window title: " + driver.getTitle());
            if ( tabTitle.equals(driver.getTitle()) ) {
                break;
            }
        }
    }

    public void switchToNewTab() {
        var windows = driver.getWindowHandles();
        windows.forEach(driver.switchTo()::window);
    }

}
