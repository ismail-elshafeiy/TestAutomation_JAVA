package com.engine.driver;

import com.engine.actions.FileActions;
import com.engine.reports.Logger;
import com.engine.validations.EyesManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.util.Objects;

import static com.engine.constants.FrameworkConstants.*;


public class DriverHelper {
    /**
     * This class will hold all the browser related methods and variables that we will use in our framework,
     * hold the ThreadLocal variable that will hold the driver instance for each thread,
     * hold the enum that will hold the browser types that we support in our framework (chrome, firefox, edge),
     * hold the enum that will hold the execution types that we support in our framework (local, remote, local headless) and will also be used to read the execution type from the properties file
     */
    protected static WebDriver driver;
    protected static EyesManager eyesManager;


    public static void setITestContext() {
        ITestResult result = Reporter.getCurrentTestResult();
        ITestContext context = result.getTestContext();
        context.setAttribute("driver", driver);
    }

    public static void checkDriverDownloadOption(String browserType) {
        switch (browserType.toLowerCase()) {
            case "chrome":
                if (Objects.equals(AUTOMATIC_DOWNLOAD_DRIVER, Boolean.TRUE)) {
                    Logger.logConsole("Automatic download driver is enabled and the chrome driver will be downloaded automatically");
                    WebDriverManager.chromedriver().setup();
                } else {
                    FileActions.getInstance().doesFileExist(CHROME_DRIVER_PATH);
                    System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
                }
                break;
            case "firefox":
                if (Objects.equals(Boolean.TRUE, AUTOMATIC_DOWNLOAD_DRIVER)) {
                    Logger.logConsole("Automatic download driver is enabled and the gecko driver will be downloaded automatically");
                    WebDriverManager.firefoxdriver().setup();
                } else {
                    FileActions.getInstance().doesFileExist(FIREFOX_DRIVER_PATH);
                    System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);
                }
                break;
            case "edge":
                if (Objects.equals(Boolean.TRUE, AUTOMATIC_DOWNLOAD_DRIVER)) {
                    Logger.logConsole("Automatic download driver is enabled and the edge driver will be downloaded automatically");
                    WebDriverManager.edgedriver().setup();
                } else {
                    FileActions.getInstance().doesFileExist(EDGE_DRIVER_PATH);
                    System.setProperty("webdriver.edge.driver", EDGE_DRIVER_PATH);
                }
                break;
            default:
                Logger.logError("The driver is null! because the browser type [ " + browserType + " ] is not valid/supported; Please choose a valid browser type from the given choices in the properties file");
        }
    }

    /**
     * This enum will hold the browser types that we support in our framework (chrome, firefox, edge)
     */
    public enum BrowserType {
        FIREFOX("Firefox"),
        CHROME("Chrome"),
        EDGE("Edge"),
        FROM_CONFIG_FILE(BROWSER_TYPE);
        private final String value;

        BrowserType(String type) {
            this.value = type;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * This enum will hold the execution types that we support in our framework (local, remote, local headless) and will also be used to read the execution type from the properties file
     */
    public enum ExecutionType {
        LOCAL("Local"),
        REMOTE("Remote"),
        FROM_CONFIG_FILE(EXECUTION_TYPE);
        private final String value;

        ExecutionType(String type) {
            this.value = type;
        }

        String getValue() {
            return value;
        }
    }

    public enum Environment {
        DEV("dev"),
        STG("stg"),
        UAT("uat"),
        PROD("prod");
        private final String value;

        Environment(String type) {
            this.value = type;
        }

        String getValue() {
            return value;
        }
    }

    public enum TestDataAccount {
        DEV("dev"),
        STG("stg"),
        UAT("uat"),
        PROD("prod");
        private final String value;

        TestDataAccount(String type) {
            this.value = type;
        }

        String getValue() {
            return value;
        }
    }

    public enum ArgumentsBrowserOptions {
        HEADLESS("--headless"),
        START_MAXIMIZED("--start-maximized"),
        IN_PRIVATE("inprivate"),
        DISABLE_NOTIFICATIONS("--disable-notifications");

        private final String value;

        ArgumentsBrowserOptions(String type) {
            this.value = type;
        }

        String getValue() {
            return value;
        }
    }
}
