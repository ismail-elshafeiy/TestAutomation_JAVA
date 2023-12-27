package com.engine.reports;

import com.engine.Helper;
import com.engine.actions.FileActions;
import com.engine.constants.FrameworkConstants;
import com.engine.dataDriven.PropertiesManager;
import com.google.common.base.Throwables;
import com.engine.actions.ElementActions;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

import io.qameta.allure.model.Status;
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

    private static final String BOUNDARY_SPACES = "                                                                                                                  ";

    private CustomReporter() {
        throw new IllegalStateException("Utility class");
    }

    static void print(String data) {
        System.out.println(data);
    }

    public static void lineSeparator() {
        print(BOUNDARY_SPACES);
    }

    /**
     * Log step that will be added as a step in the execution report
     *
     * @param text logged by action that will be added as a step in the execution report (e.g. click on button)
     */

//    @Step("{text}")
    public static void logInfoStep(String text) {
        createLog(text, Level.INFO);
        ExtentReport.info(text);
    }

    public static void logInfoStep(String text, boolean addToAllureReport) {
        createLog(text, Level.INFO);
        ExtentReport.info(text);
        if (addToAllureReport) {
            Allure.step(text, getAllureStepStatus(text));
        }
    }


    public static void logPassed(String text) {
        createLog(text, Level.INFO);
        ExtentReport.pass(text);
        Allure.step(text, getAllureStepStatus(text));
    }

    public static void logWarning(String text) {
        CustomReporter.logConsole("Kindly check the warning message below: ", Level.WARN, "33");
        createLog(text, Level.WARN);
        ExtentReport.warning(text);
    }

    /**
     * Log message that will be added as a message in the execution report
     *
     * @param message logged by exception / assertion message that will be added as a message in the execution report
     */
    public static void logError(String message) {
        createLog(message, Level.ERROR);
        ExtentReport.fail(FrameworkConstants.ICON_SMILEY_FAIL + " " + message);
        Assert.fail(message);
    }

    public static void logConsole(String text, Level level) {
        String log = "\033[37m" + text.trim() + "\033[0m";
        createLog(log, level);
    }

    public static void logConsole(String text) {
        createLog(text, Level.INFO);
    }

    /**
     * This method is used to log in console with custom level and color
     *
     * @param text  to be logged in console
     * @param level of the log
     * @param color Red: 31, Green: 32, Yellow: 33, Blue: 34, Magenta: 35, Cyan: 36, White: 37.
     */
    public static void logConsole(String text, Level level, String color) {
        String log = "\033[" + color + "m" + text.trim();
        createLog(log, level);
    }


    public static void logAttachments(String logText, List<List<Object>> attachments) {
        if (attachments != null && !attachments.isEmpty() && (attachments.size() > 1 || (attachments.get(0) != null && !attachments.get(0).isEmpty()))) {
            writeStepToReport(logText, attachments);
        } else {
            writeStepToReport(logText);
        }
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
            CustomReporter.logError(message + rootCause);
        Assert.fail(message + rootCause, throwable);
    }


    @Step("{logText}")
    static void writeStepToReport(String logText, List<List<Object>> attachments) {
        createLog(logText, false);
        if (attachments != null && !attachments.isEmpty()) {
            attachments.forEach(attachment -> {
                if (attachment != null
                        && !attachment.isEmpty()
                        && attachment.get(2).getClass().toString().toLowerCase().contains("string")
                        && !attachment.get(2).getClass().toString().contains("StringInputStream")) {
                    if (!attachment.get(2).toString().isEmpty()) {
                        Attachments.attach(attachment.get(0).toString(), attachment.get(1).toString(), attachment.get(2).toString());
                    }
                } else if (attachment != null && !attachment.isEmpty()) {
                    if (attachment.get(2) instanceof byte[]) {
                        Attachments.attach(attachment.get(0).toString(), attachment.get(1).toString(), new ByteArrayInputStream((byte[]) attachment.get(2)));
                    } else {
                        Attachments.attach(attachment.get(0).toString(), attachment.get(1).toString(), (InputStream) attachment.get(2));
                    }
                }
            });
        }
    }

    public static void writeStepToReport(String logText) {
        createLog(logText, true);
        Allure.step(logText, getAllureStepStatus(logText));
    }

    private static Status getAllureStepStatus(String logText) {
        if (logText != null && logText.toLowerCase().contains("failed")) {
            return Status.FAILED;
        }
        if (Reporter.getCurrentTestResult() != null) {
            var testNgStatus = Reporter.getCurrentTestResult().getStatus();
            return switch (testNgStatus) {
                case ITestResult.FAILURE -> Status.FAILED;
                case ITestResult.SKIP -> Status.SKIPPED;
                default -> Status.PASSED;
            };
        } else {
            return Status.PASSED;
        }
    }

    public static void passAction(String testData) {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        reportActionResult(actionName, testData, null, true);
    }

    public static void passAction(String testData, String log) {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        reportActionResult(actionName, testData, log, true);
    }

    public static void failAction(String testData, Exception... rootCauseException) {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        failAction(actionName, testData, rootCauseException);

    }

    public static void failAction(Exception... rootCauseException) {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        failAction(actionName, null, rootCauseException);
    }

    public static void failAction(String actionName, String testData, Exception... rootCauseException) {
        String message = reportActionResult(actionName, testData, null, false, rootCauseException);
        CustomReporter.failReporter(FileActions.class, message, rootCauseException[0]);
    }


    public static String reportActionResult(String actionName, String testData, String log, Boolean passFailStatus, Exception... rootCauseException) {
        actionName = actionName.substring(0, 1).toUpperCase() + actionName.substring(1);
        String message;
        if (Boolean.TRUE.equals(passFailStatus)) {
            message = "File Action \"" + actionName + "\" successfully performed.";
        } else {
            message = "File Action \"" + actionName + "\" failed.";
        }
        List<List<Object>> attachments = new ArrayList<>();
        if (testData != null && testData.length() >= 500) {
            List<Object> actualValueAttachment = Arrays.asList("File Action Test Data - " + actionName, "Actual Value", testData);
            attachments.add(actualValueAttachment);
        } else if (testData != null && !testData.isEmpty()) {
            message = message + " With the following test data \"" + testData + "\".";
        }
        if (log != null && !log.trim().equals("")) {
            attachments.add(Arrays.asList("File Action Actual Result", "Command Log", log));
        }
        if (rootCauseException != null && rootCauseException.length >= 1) {
            List<Object> actualValueAttachment = Arrays.asList("File Action Exception - " + actionName, "Stacktrace", CustomReporter.formatStackTraceToLogEntry(rootCauseException[0]));
            attachments.add(actualValueAttachment);
        }
        // Minimize File Action log steps and move them to discrete logs if called
        // within SHAFT_Engine itself
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement parentMethod = stackTrace[4];
        if (parentMethod.getClassName().contains("engine")) {
            CustomReporter.logInfoStep(message);
        } else {
            if (!attachments.equals(new ArrayList<>())) {
                CustomReporter.logInfoStep(attachments.toString());
            } else {
                CustomReporter.logInfoStep(message);
            }
        }
        return message;
    }

    public static void logThrowable(Throwable t) {
        createLog(formatStackTraceToLogEntry(t), Level.ERROR);
    }

    public static void createLog(String logText, Level loglevel) {
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

    private static void createLog(String logText, boolean addToConsoleLog) {
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
        CustomReporter.logInfoStep("Console logs for Test Case: [" + result.getMethod().getMethodName() + "] are saved in [ " + filePath + " ]");
        try {
            for (LogEntry logEntry : logEntries) {
                writer.println("Console log found in Test- " + result.getName());
                writer.println("__________________________________________________________");
                if (logEntry.getMessage().toLowerCase().contains("error")) {
                    writer.println(currentTime + "Error Message in Console: [" + logEntry.getMessage() + "]");
                    CustomReporter.logInfoStep(currentTime + "Error Message in Console: [" + logEntry.getMessage() + "]");
                } else if (logEntry.getMessage().toLowerCase().contains("warning")) {
                    writer.println(currentTime + "Warning Message in Console: [" + logEntry.getMessage() + "]");
                    CustomReporter.logInfoStep(currentTime + "Warning Message in Console: [" + logEntry.getMessage() + "]");
                } else {
                    writer.println(currentTime + "Information Message in Console: [" + logEntry.getMessage() + "]");
                    CustomReporter.logInfoStep(currentTime + "Information Message in Console: [" + logEntry.getMessage() + "]");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            CustomReporter.logError("Console logs not found: " + e.getMessage());
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

    private static void initializeLogger() {
        Configurator.initialize(null, PropertiesManager.CUSTOM_PROPERTIES_FOLDER_PATH + "/log4j2.properties");
        logger = LogManager.getLogger(CustomReporter.class.getName());
    }
}


