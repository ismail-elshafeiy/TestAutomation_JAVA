package com.engine.constants;

import com.engine.actions.FileActions;
import com.engine.dataDriven.PropertiesManager;

public final class FrameworkConstants {

    private FrameworkConstants() {
    }

    static {
//        PropertiesManager.loadAllFiles();
    }

    public static final String PROJECT_PATH = FileActions.getDir();
    private static final String CONFIG_PROP = "config";
    private static final String PATH_PROP = "paths";
    public static final Boolean AUTOMATIC_DOWNLOAD_DRIVER = Boolean.valueOf(PropertiesManager.getPropertyValue(CONFIG_PROP, "automaticallyInstallDriver"));
    public static final String CHROME_DRIVER_PATH = PropertiesManager.getPropertyValue(CONFIG_PROP, "chromeDriverPath");
    public static final String FIREFOX_DRIVER_PATH = PropertiesManager.getPropertyValue(CONFIG_PROP, "firefoxDriverPath");
    public static final String EDGE_DRIVER_PATH = PropertiesManager.getPropertyValue(CONFIG_PROP, "edgeDriverPath");
    public static final String EXECUTION_TYPE = PropertiesManager.getPropertyValue(CONFIG_PROP, "executionType");
    public static final String BROWSER_TYPE = PropertiesManager.getPropertyValue(CONFIG_PROP, "browserType");
    public static final int SCRIPT_EXECUTION_TIMEOUT = Integer.parseInt(PropertiesManager.getPropertyValue(CONFIG_PROP, "scriptExecutionTimeout"));
    public static final int PAGE_LOAD_TIMEOUT = Integer.parseInt(PropertiesManager.getPropertyValue(CONFIG_PROP, "pageLoadTimeout"));
    public static final String BROWSER_DOWNLOAD_DIR = PropertiesManager.getPropertyValue(PATH_PROP, "downloadBrowserPath");
    public static final Boolean MAXIMIZE_OPTION = Boolean.valueOf(PropertiesManager.getPropertyValue(CONFIG_PROP, "startMaximize"));
    public static final Boolean HEADLESS_OPTION = Boolean.valueOf(PropertiesManager.getPropertyValue(CONFIG_PROP, "headless"));
    public static final Boolean RECORD_VIDEO = Boolean.valueOf(PropertiesManager.getPropertyValue(CONFIG_PROP, "recordVideo"));
    public static final String HOST = PropertiesManager.getPropertyValue(CONFIG_PROP, "host");
    public static final String PORT = PropertiesManager.getPropertyValue(CONFIG_PROP, "port");
    public static final String WIDTH = PropertiesManager.getPropertyValue(CONFIG_PROP, "width");
    public static final String HEIGHT = PropertiesManager.getPropertyValue(CONFIG_PROP, "height");
    public static final int ELEMENT_IDENTIFICATION_TIMEOUT = Integer.parseInt(PropertiesManager.getPropertyValue(CONFIG_PROP, "elementIdentificationTimeout"));
    public static final int EXPLICIT_TIMEOUT = Integer.parseInt(PropertiesManager.getPropertyValue(CONFIG_PROP, "explicitWaitTimeout"));

    public static final int IMPLICIT_TIMEOUT = Integer.parseInt(PropertiesManager.getPropertyValue(CONFIG_PROP, "implicitWaitTimeout"));
    public static final int POLLING = Integer.parseInt(PropertiesManager.getPropertyValue(CONFIG_PROP, "FluentWaitPolling"));

    public static final Boolean attemptClearBeforeTyping = Boolean.valueOf(PropertiesManager.getPropertyValue(CONFIG_PROP, "attemptClearBeforeTyping"));

    public static final Boolean attemptClearBeforeTypingUsingBackspace = Boolean.valueOf(PropertiesManager.getPropertyValue(CONFIG_PROP, "attemptClearBeforeTypingUsingBackspace"));
    public static final String PROJECT_NAME = PropertiesManager.getPropertyValue(CONFIG_PROP, "projectName");
    public static final String REPORT_TITLE = PropertiesManager.getPropertyValue(CONFIG_PROP, "reportTitle");
    public static final String EXTENT_REPORT_NAME = PropertiesManager.getPropertyValue(CONFIG_PROP, "reportName");
    public static final int MAX_TRY = Integer.parseInt(PropertiesManager.getPropertyValue(CONFIG_PROP, "retryFailedTest"));
    public static final String BASE_URL = PropertiesManager.getPropertyValue(CONFIG_PROP, "rest.baseUrl");
    public static final String HOME_URL_TAU = PropertiesManager.getPropertyValue("paths", "TAU.homeUrl");

    public static final String YES = "yes";
    public static final String NO = "no";

    public static final String BOLD_START = "<b>";
    public static final String BOLD_END = "</b>";
}
