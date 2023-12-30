package com.engine.constants;

import com.engine.actions.FileActions;
import com.engine.dataDriven.PropertiesManager;

public final class FrameworkConstants {

    private FrameworkConstants() {
    }

    static {
        PropertiesManager.loadAllFiles();
    }

    public static final String PROJECT_PATH = FileActions.getDir();
    private static final String CONFIG_PROP = "config.properties";
    private static final String PATH_PROP = "paths.properties";
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
    public static final int TIMEOUT_EXPLICIT = Integer.parseInt(PropertiesManager.getPropertyValue(CONFIG_PROP, "timeoutImplicitDefault"));
    public static final int TIMEOUT_IMPLICIT = Integer.parseInt(PropertiesManager.getPropertyValue(CONFIG_PROP, "timeoutImplicitDefault"));
    public static final int POLLING = Integer.parseInt(PropertiesManager.getPropertyValue(CONFIG_PROP, "fluentWaitpolling"));

    public static final String PROJECT_NAME = PropertiesManager.getPropertyValue(CONFIG_PROP, "projectName");
    public static final String REPORT_TITLE = PropertiesManager.getPropertyValue(CONFIG_PROP, "reportTitle");
    public static final String EXTENT_REPORT_NAME = PropertiesManager.getPropertyValue(CONFIG_PROP, "reportName");
    public static final int MAX_TRY = Integer.parseInt(PropertiesManager.getPropertyValue(CONFIG_PROP, "retryFailedTest"));
    public static final String BASE_URL = PropertiesManager.getPropertyValue(CONFIG_PROP, "rest.baseUrl");
    public static final String HOME_URL_TAU = PropertiesManager.getPropertyValue("paths.properties", "TAU.homeUrl");

    public static final String YES = "yes";
    public static final String NO = "no";

    public static final String BOLD_START = "<b>";
    public static final String BOLD_END = "</b>";

    /* ICONS - START */

    public static final String ICON_SMILEY_PASS = "<i class='fa fa-smile-o' style='font-size:24px'></i>";
    public static final String ICON_SMILEY_SKIP = "<i class=\"fas fa-frown-open\"></i>";
    public static final String ICON_SMILEY_FAIL = "<i class='fa fa-frown-o' style='font-size:24px'></i>";

    public static final String ICON_OS_WINDOWS = "<i class='fa fa-windows' ></i>";
    public static final String ICON_OS_MAC = "<i class='fa fa-apple' ></i>";
    public static final String ICON_OS_LINUX = "<i class='fa fa-linux' ></i>";

    public static final String ICON_BROWSER_OPERA = "<i class=\"fa fa-opera\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_EDGE = "<i class=\"fa fa-edge\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_CHROME = "<i class=\"fa fa-chrome\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_FIREFOX = "<i class=\"fa fa-firefox\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_SAFARI = "<i class=\"fa fa-safari\" aria-hidden=\"true\"></i>";

    public static final String ICON_Navigate_Right = "<i class='fa fa-arrow-circle-right' ></i>";
    public static final String ICON_LAPTOP = "<i class='fa fa-laptop' style='font-size:18px'></i>";
    public static final String ICON_BUG = "<i class='fa fa-bug' ></i>";
    /* style="text-align:center;" */

    public static final String ICON_SOCIAL_GITHUB_PAGE_URL = "https://anhtester.com/";
    public static final String ICON_SOCIAL_LINKEDIN_URL = "https://www.linkedin.com/in/anhtester/";
    public static final String ICON_SOCIAL_GITHUB_URL = "https://github.com/anhtester";
    public static final String ICON_SOCIAL_LINKEDIN = "<a href='" + ICON_SOCIAL_LINKEDIN_URL
            + "'><i class='fa fa-linkedin-square' style='font-size:24px'></i></a>";
    public static final String ICON_SOCIAL_GITHUB = "<a href='" + ICON_SOCIAL_GITHUB_URL
            + "'><i class='fa fa-github-square' style='font-size:24px'></i></a>";

    public static final String ICON_CAMERA = "<i class=\"fa fa-camera\" aria-hidden=\"true\"></i>";

    public static final String ICON_BROWSER_PREFIX = "<i class=\"fa fa-";
    public static final String ICON_BROWSER_SUFFIX = "\" aria-hidden=\"true\"></i>";
    /* ICONS - END */


}
