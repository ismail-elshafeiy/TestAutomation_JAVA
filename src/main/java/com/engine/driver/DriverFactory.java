package com.engine.driver;

import com.engine.evidence.RecordVideo;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.engine.reports.CustomReporter;

import java.net.MalformedURLException;
import java.net.URL;

import static com.engine.constants.FrameworkConstants.*;
import static com.engine.driver.DriverHelper.*;
import static com.engine.driver.DriverOptions.*;
import static org.testng.Assert.fail;

public class DriverFactory {

    /**
     * This method will return the browser instance based on the browser type and execution type from the property file
     *
     * @param browserType   - Browser Type from the property file (chrome, firefox, edge.)
     * @param executionType - Execution Type from the property file (local, remote, headless)
     * @return - Browser instance based on the browser type and execution type from the property file (chrome, firefox, edge.)
     */
    @Step("Initializing a new Web GUI Browser!.....")
    public static synchronized WebDriver getBrowser(BrowserType browserType, ExecutionType executionType) {
        //eyesManager = new EyesManager(driver.get(), appName);
        CustomReporter.logInfoStep("Initialize [ " + browserType.getValue() + " ] Browser and the Execution Type is [ " + executionType.getValue() + " ] and Headless Mode is [ " + HEADLESS_OPTION + " ]");
        boolean googleChrome = browserType == BrowserType.CHROME || (browserType == BrowserType.FROM_CONFIG_FILE && BROWSER_TYPE.equalsIgnoreCase("chrome"));
        boolean mozillaFirefox = browserType == BrowserType.FIREFOX || (browserType == BrowserType.FROM_CONFIG_FILE && BROWSER_TYPE.equalsIgnoreCase("firefox"));
        boolean microsoftEdge = browserType == BrowserType.EDGE || (browserType == BrowserType.FROM_CONFIG_FILE && BROWSER_TYPE.equalsIgnoreCase("edge"));
        String browserTypeWarningMsg = "The driver is null! because the browser type [" + BROWSER_TYPE + "] is not valid/supported; Please choose a valid browser type from the given choices in the properties file";
        // Local execution condition
        if (executionType == ExecutionType.LOCAL || (executionType == ExecutionType.FROM_CONFIG_FILE && EXECUTION_TYPE.equalsIgnoreCase("local"))) {
            if (googleChrome) {
                checkDriverDownloadOption(BrowserType.CHROME.getValue());
                setITestContext();
            } else if (mozillaFirefox) {
                checkDriverDownloadOption(BrowserType.FIREFOX.getValue());
                setITestContext();
            } else if (microsoftEdge) {
                checkDriverDownloadOption(BrowserType.EDGE.getValue());
                setITestContext();
            } else {
                CustomReporter.logError(browserTypeWarningMsg);
                fail(browserTypeWarningMsg);
            }
            // Start video recording
            RecordVideo.startVideoRecording();
            // Remote execution condition
        } else if (executionType == ExecutionType.REMOTE || (executionType == ExecutionType.FROM_CONFIG_FILE && BROWSER_TYPE.equalsIgnoreCase("remote"))) {
            if (googleChrome) {
                try {
                    driver = new RemoteWebDriver(new URL("http://" + HOST + ":" + PORT + "/wd/hub"), getChromeOptions());
                    setITestContext();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else if (mozillaFirefox) {
                try {
                    driver = new RemoteWebDriver(new URL("http://" + HOST + ":" + PORT + "/wd/hub"), getFirefoxOptions());
                    setITestContext();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else if (microsoftEdge) {
                try {
                    driver = new RemoteWebDriver(new URL("http://" + HOST + ":" + PORT + "/wd/hub"), getEdgeOptions());
                    setITestContext();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                CustomReporter.logError(browserTypeWarningMsg);
                fail(browserTypeWarningMsg);
            }
        } else {
            String warningMsg = "The driver is null! because the execution type [" + EXECUTION_TYPE + "] is not valid/supported; Please choose a valid execution type from the given choices in the properties file";
            CustomReporter.logError(warningMsg);
            fail(warningMsg);
        }
        // start session
        return driver;
    }

    public static WebDriver getBrowser(BrowserType browserType) {
        return getBrowser(browserType, ExecutionType.FROM_CONFIG_FILE);
    }

    public static WebDriver getBrowser() {
        return getBrowser(BrowserType.FROM_CONFIG_FILE, ExecutionType.FROM_CONFIG_FILE);
    }


}
