package com.engine;

import com.engine.reports.Logger;
import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.engine.dataDriven.PropertiesManager.getPropertyValue;

public class WaitsManager {


    public static void implicitWait(WebDriver driver, int timeout) {
        Logger.logInfoStep("Implicit wait for [ " + timeout + " ] seconds");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

    public static void implicitWait(WebDriver driver) {
        implicitWait(driver, Integer.parseInt(getPropertyValue("implicitWaitTimeout")));
    }

    public static Wait<WebDriver> getExplicitWait(WebDriver driver, int timeout) {
        Logger.logInfoStep("Explicit wait for [ " + timeout + " ] seconds");
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public static Wait<WebDriver> getExplicitWait(WebDriver driver) {
        return getExplicitWait(driver, Integer.parseInt(getPropertyValue("explicitWaitTimeout")));
    }

    public static <T extends WebDriver> FluentWait<T> getFluentWait(T driver, int timeoutInSeconds, int pollingEveryInMillis) {
        if (driver instanceof AndroidDriver || driver instanceof IOSDriver) {
            return new AppiumFluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                    .pollingEvery(Duration.ofMillis(pollingEveryInMillis))
                    .ignoreAll(expectedExceptions);
        } else {
            return new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                    .pollingEvery(Duration.ofMillis(pollingEveryInMillis))
                    .ignoreAll(expectedExceptions);
        }
    }

    public static <T extends WebDriver> FluentWait<T> getFluentWait(T driver, int timeoutInSeconds) {
        return getFluentWait(driver,
                timeoutInSeconds,
                Integer.parseInt(getPropertyValue("fluentWaitPolling"))
        );
    }

    public static <T extends WebDriver> FluentWait<T> getFluentWait(T driver) {
        return getFluentWait(driver,
                Integer.parseInt(getPropertyValue("fluentWaitTimeout")),
                Integer.parseInt(getPropertyValue("fluentWaitPolling"))
        );
    }

    private static final ArrayList<Class<? extends Exception>> expectedExceptions = new ArrayList<>();

    static {
        expectedExceptions.add(java.lang.ClassCastException.class);
        expectedExceptions.add(org.openqa.selenium.NoSuchElementException.class);
        expectedExceptions.add(org.openqa.selenium.StaleElementReferenceException.class);
        expectedExceptions.add(org.openqa.selenium.JavascriptException.class);
        expectedExceptions.add(org.openqa.selenium.ElementClickInterceptedException.class);
        expectedExceptions.add(org.openqa.selenium.ElementNotInteractableException.class);
        expectedExceptions.add(org.openqa.selenium.InvalidElementStateException.class);
        expectedExceptions.add(org.openqa.selenium.interactions.MoveTargetOutOfBoundsException.class);
        expectedExceptions.add(org.openqa.selenium.WebDriverException.class);
        expectedExceptions.add(ExecutionException.class);
        expectedExceptions.add(InterruptedException.class);
    }
}
