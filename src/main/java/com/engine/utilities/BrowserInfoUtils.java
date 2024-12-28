package com.engine.utilities;

public class BrowserInfoUtils {
    private BrowserInfoUtils() {
        super();
    }

    private static final String OS = System.getProperty("os.name").toLowerCase();

//    public static String getBrowserInfo() {
//        String browser = "";
//        if (Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("BROWSER") == null) {
//            browser = FrameworkConstants.BROWSER_TYPE.toUpperCase();
//        } else {
//            browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("BROWSER").trim().toUpperCase();
//        }
//        return browser;
//    }

    public static String getOSInfo() {
        return System.getProperty("os.name");
    }

    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

}
