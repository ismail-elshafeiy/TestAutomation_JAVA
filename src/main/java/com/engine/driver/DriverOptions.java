package com.engine.driver;

import com.engine.actions.FileActions;
import com.engine.constants.FrameworkConstants;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverOptions {
    protected static Boolean isHeadless = FrameworkConstants.HEADLESS_OPTION;
    protected static Boolean isStartMaximize = FrameworkConstants.MAXIMIZE_OPTION;

    public static ChromeOptions getChromeOptions() {
        ChromeOptions chOptions = new ChromeOptions();
        if (Boolean.TRUE.equals(isHeadless))
            chOptions.addArguments(DriverHelper.ArgumentsBrowserOptions.HEADLESS.getValue());
        if (Boolean.TRUE.equals(isStartMaximize))
            chOptions.addArguments(DriverHelper.ArgumentsBrowserOptions.START_MAXIMIZED.getValue());
        chOptions.addArguments(DriverHelper.ArgumentsBrowserOptions.DISABLE_NOTIFICATIONS.getValue());
        chOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        chOptions.setPageLoadTimeout(Duration.ofSeconds(FrameworkConstants.PAGE_LOAD_TIMEOUT));
        chOptions.setScriptTimeout(Duration.ofSeconds(FrameworkConstants.SCRIPT_EXECUTION_TIMEOUT));
        Map<String, Object> prefs = getStringObjectMap(FileActions.getDir() + FrameworkConstants.BROWSER_DOWNLOAD_DIR);
        chOptions.setExperimentalOption("prefs", prefs);
        return chOptions;
    }


    public static EdgeOptions getEdgeOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();
        if (Boolean.TRUE.equals(isHeadless))
            edgeOptions.addArguments(DriverHelper.ArgumentsBrowserOptions.HEADLESS.getValue());
        if (Boolean.TRUE.equals(isStartMaximize))
            edgeOptions.addArguments(DriverHelper.ArgumentsBrowserOptions.START_MAXIMIZED.getValue());
        edgeOptions.addArguments("inprivate");
        edgeOptions.addArguments(DriverHelper.ArgumentsBrowserOptions.DISABLE_NOTIFICATIONS.getValue());
        edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        edgeOptions.setPageLoadTimeout(Duration.ofSeconds(FrameworkConstants.PAGE_LOAD_TIMEOUT));
        edgeOptions.setScriptTimeout(Duration.ofSeconds(FrameworkConstants.SCRIPT_EXECUTION_TIMEOUT));
        Map<String, Object> prefs = getStringObjectMap(FileActions.getDir() + FrameworkConstants.BROWSER_DOWNLOAD_DIR);
        edgeOptions.setExperimentalOption("prefs", prefs);
        return edgeOptions;
    }

    public static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions ffOptions = new FirefoxOptions();
        var ffProfile = new FirefoxProfile();
        if (Boolean.TRUE.equals(isHeadless))
            ffOptions.addArguments(DriverHelper.ArgumentsBrowserOptions.HEADLESS.getValue());
        if (Boolean.TRUE.equals(isStartMaximize))
            ffOptions.addArguments(DriverHelper.ArgumentsBrowserOptions.START_MAXIMIZED.getValue());
        ffOptions.addArguments(DriverHelper.ArgumentsBrowserOptions.DISABLE_NOTIFICATIONS.getValue());
        ffOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        ffOptions.setPageLoadTimeout(Duration.ofSeconds(FrameworkConstants.PAGE_LOAD_TIMEOUT));
        ffOptions.setScriptTimeout(Duration.ofSeconds(FrameworkConstants.SCRIPT_EXECUTION_TIMEOUT));
        ffProfile.setPreference("browser.download.dir", FileActions.getDir() + FrameworkConstants.BROWSER_DOWNLOAD_DIR);
        ffOptions.setProfile(ffProfile);
        return ffOptions;
    }

    private static Map<String, Object> getStringObjectMap(String downloadFilepath) {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFilepath);
        prefs.put("download.prompt_for_download", false);
        return prefs;
    }
}
