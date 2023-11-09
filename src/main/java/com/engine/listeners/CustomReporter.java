package com.engine.listeners;

import com.engine.Helper;
import com.engine.dataDriven.PropertiesReader;
import com.engine.report.ExtentReport;
import com.google.common.base.Throwables;
import com.engine.actions.ElementActions;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class CustomReporter {

    private static final String TIMESTAMP_FORMAT = "dd-MM-yyyy HH:mm:ss.SSSS aaa";
    private static final boolean debugMode = false;
    private static String currentTime = Helper.getCurrentTime("dd-MM-yyyy HH:mm:ss");
    static PrintWriter writer;
    private static Logger logger;
    static LogEntries logEntries;
    static Logs logs;
    private static WebDriver driver;


    /**
     * Log step that will be added as a step in the execution report
     *
     * @param text logged by action that will be added as a step in the execution report (e.g. click on button)
     */

    @Step("{text}")
    public static void logStep(String text) {
        createLogEntry(text, Level.INFO);
        ExtentReport.info(text);
    }

    @Step("{text}")
    public static void logWarn(String text) {
        createLogEntry(text, Level.WARN);
        ExtentReport.info(text);
    }
    @Step("{logStep}")
    public static void logError(String text) {
        createLogEntry(text, Level.ERROR);
        ExtentReport.info(text);
    }

    public static void logConsole(String text) {
        createLogEntry(text, Level.INFO);
    }

    /**
     * Log message that will be added as a message in the execution report
     *
     * @param message logged by exception / assertion message that will be added as a message in the execution report
     */
    public static void logMessage(String message) {
        createLogEntry(message, Level.ERROR);
        ExtentReport.fail(message);
    }

    public static void fail(String message) {
        CustomReporter.logMessage(message);
        ExtentReport.fail(message);
        Assert.fail(message);
    }


    public static void createImportantReportEntry(String logText) {
        String log = System.lineSeparator() +
                "\033[0;7m" +
                createSeparator('-') +
                addSpacing(logText.trim()) +
                createSeparator('-') +
                System.lineSeparator() +
                "\033[0m";
        Reporter.log(log, false);
        if (logger == null) {
            initializeLogger();
        }
        logger.log(Level.INFO, log);
    }

    private static String createSeparator(@SuppressWarnings("SameParameterValue") char ch) {
        return String.valueOf(ch).repeat(144);
    }

    private static String addSpacing(String log) {
        StringBuilder augmentedText = new StringBuilder();
        StringBuilder lineByLine = new StringBuilder();

        augmentedText.append(System.lineSeparator());
        Arrays.stream(log.split("\n")).toList().forEach(line -> {
            var trailingSpacing = "";
            var spaces = Math.round((float) (144 - line.trim().length()) / 2);
            if (spaces > 0) {
                lineByLine.append(" ".repeat(spaces));
                trailingSpacing = lineByLine.toString();
            }
            lineByLine.append(line.trim());
            lineByLine.append(trailingSpacing);
            augmentedText.append(lineByLine);
            augmentedText.append(System.lineSeparator());
            lineByLine.delete(0, lineByLine.length());
        });
        return augmentedText.toString();
    }

    public static void logThrowable(Throwable t) {
        createLogEntry(formatStackTraceToLogEntry(t), Level.ERROR);
    }

    public static void createLogEntry(String logText, Level loglevel) {
        String timestamp = (new SimpleDateFormat(TIMESTAMP_FORMAT)).format(new Date(System.currentTimeMillis()));
        if (logText == null) {
            logText = "null";
        }
        String log = logText.trim() + " @" + timestamp;
        Reporter.log(log, false);
        if (logger == null) {
            initializeLogger();
        }
        logger.log(loglevel, logText.trim());
    }

    private static void createLogEntry(String logText, boolean addToConsoleLog) {
        String timestamp = (new SimpleDateFormat(TIMESTAMP_FORMAT)).format(new Date(System.currentTimeMillis()));
        if (logText == null) {
            logText = "null";
        }
        String log = logText.trim() + " @" + timestamp;
        Reporter.log(log, false);
        if (addToConsoleLog) {
            if (logger == null) {
                initializeLogger();
            }
            logger.log(Level.INFO, logText.trim());
        }
    }
    private static void initializeLogger() {
        Configurator.initialize(null, PropertiesReader.getCUSTOM_PROPERTIES_FOLDER_PATH() + "/log4j2.properties");
        logger = LogManager.getLogger(CustomReporter.class.getName());
    }


    public static String formatStackTraceToLogEntry(Throwable t) {
        return formatStackTraceToLogEntry(t, false);
    }

    private static String formatStackTraceToLogEntry(Throwable t, boolean isCause) {
        var logBuilder = new StringBuilder();
        if (t != null) {
            StackTraceElement[] trace = t.getStackTrace();
            if (isCause) {
                logBuilder.append(System.lineSeparator()).append("Caused by: ");
            }
            logBuilder.append(t.getClass().getName()).append(":").append(" ").append(t.getMessage()).append(System.lineSeparator());
            for (StackTraceElement stackTraceElement : trace) {
                logBuilder.append(" ").append(stackTraceElement.toString()).append(System.lineSeparator());
            }
            logBuilder.append(formatStackTraceToLogEntry(t.getCause(), true));
        }
        return logBuilder.toString();
    }
    public static void failReporter(Class<?> failedFileManager, String message, Throwable throwable) {
        String actionName = "fail";
        String rootCause = " Root cause: \"" + Throwables.getRootCause(throwable).getClass().getName() + ": " + Throwables.getRootCause(throwable).getLocalizedMessage().split("\n")[0] + "\"";

        for (StackTraceElement stackTraceElement : Arrays.stream(Thread.currentThread().getStackTrace()).toList()) {
            var methodName = stackTraceElement.getMethodName();
            if (!methodName.toLowerCase().contains("fail")) {
                actionName = methodName;
                break;
            }
        }
        actionName = Helper.convertToSentenceCase(actionName);

        List<List<Object>> attachments = new ArrayList<>();
        List<Object> actualValueAttachment = Arrays.asList(Helper.convertToSentenceCase(failedFileManager.getSimpleName()) + " - " +
                        Helper.convertToSentenceCase(actionName),
                "Exception Stacktrace", formatStackTraceToLogEntry(throwable));
        attachments.add(actualValueAttachment);
        if (failedFileManager != ElementActions.class)
            CustomReporter.logMessage(message + rootCause);
        Assert.fail(message + rootCause, throwable);
    }



    /**
     * Log console error, warming and info that will be added as a error in the execution report
     *
     * @param driver driver instance
     * @param result test result
     * @throws IOException
     */
    @Attachment(value = "log Console", type = "text/plain")
    public static void logConsoleLogs(WebDriver driver, ITestResult result) throws IOException {
        logs = driver.manage().logs();
        logEntries = logs.get(LogType.BROWSER);
        String filePath = System.getProperty("user.dir") + "/ConsoleLogs/" + result.getMethod().getMethodName() + ".txt";
        writer = new PrintWriter(filePath, StandardCharsets.UTF_8);
        CustomReporter.logStep("Console logs for Test Case: [" + result.getMethod().getMethodName() + "] are saved in [ " + filePath + " ]");
        try {
            for (LogEntry logEntry : logEntries) {
                writer.println("Console log found in Test- " + result.getName());
                writer.println("__________________________________________________________");
                if (logEntry.getMessage().toLowerCase().contains("error")) {
                    writer.println(currentTime + "Error Message in Console: [" + logEntry.getMessage() + "]");
                    CustomReporter.logStep(currentTime + "Error Message in Console: [" + logEntry.getMessage() + "]");
                } else if (logEntry.getMessage().toLowerCase().contains("warning")) {
                    writer.println(currentTime + "Warning Message in Console: [" + logEntry.getMessage() + "]");
                    CustomReporter.logStep(currentTime + "Warning Message in Console: [" + logEntry.getMessage() + "]");
                } else {
                    writer.println(currentTime + "Information Message in Console: [" + logEntry.getMessage() + "]");
                    CustomReporter.logStep(currentTime + "Information Message in Console: [" + logEntry.getMessage() + "]");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            CustomReporter.logMessage("Console logs not found: " + e.getMessage());
        } finally {
            writer.close();
        }
    }
//
//	public static void logConsoleLogs (ChromeDriver chromeDriver, String url) {
//		try {
//			// Get The DevTools class & Create A Session
//			DevTools devTools = chromeDriver.getDevTools();
//			devTools.createSession();
//			// Enable The Console Logs
//			devTools.send(Log.enable());
//			// Add A Listener For The Logs
//			devTools.addListener(Log.entryAdded(), logEntry -> {
//				Logger.logStep("----------");
//				Logger.logStep("Source: " + logEntry.getSource());
//				Logger.logStep("Timestamp: " + logEntry.getTimestamp());
//				Logger.logStep("Level: " + logEntry.getLevel());
//				Logger.logStep("Text: " + logEntry.getText());
//				Logger.logStep("Broken URL: " + logEntry.getUrl());
//			});
//			// Load The AUT
//			BrowserActions.navigateToUrl(chromeDriver, url);
//		} catch ( Exception e ) {
//			e.printStackTrace();
//		}
//	}
}


