package com.engine.utils;

import com.engine.constants.FrameworkConstants;

import static com.engine.constants.FrameworkConstants.*;

public class IconUtils {
    private IconUtils() {
        super();
    }

    public static String getBrowserIcon() {
        String browser = "";
        if (FrameworkConstants.BROWSER_TYPE.contains("Google Chrome")) {
            browser = ICON_BROWSER_CHROME;
        } else if (FrameworkConstants.BROWSER_TYPE.contains("Edge")) {
            browser = ICON_BROWSER_EDGE;
        } else if (FrameworkConstants.BROWSER_TYPE.contains("Mozila Firefox")) {
            browser = ICON_BROWSER_FIREFOX;
        }
        return browser;
    }

    public static String getOSIcon() {
        String operationSystem = BrowserInfoUtils.getOSInfo().toLowerCase();
        if (operationSystem.contains("win")) {
            return ICON_OS_WINDOWS;
        } else if (operationSystem.contains("nix") || operationSystem.contains("nux") || operationSystem.contains("aix")) {
            return ICON_OS_LINUX;
        } else if (operationSystem.contains("mac")) {
            return ICON_OS_MAC;
        }
        return operationSystem;
    }
}

