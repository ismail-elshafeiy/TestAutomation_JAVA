package com.engine.reports;

import com.engine.Helper;
import com.engine.actions.ElementActions;
import com.engine.actions.FileActions;
import com.engine.dataDriven.PropertiesManager;
import com.google.common.base.Throwables;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.engine.reports.AllureReport.getAllureStepStatus;
import static com.engine.reports.AllureReport.writeStepToReport;
import static com.engine.utilities.IconUtils.ICON_SMILEY_FAIL;


public class CustomReporter {
    private static org.apache.logging.log4j.Logger logger;
    private static final String TIMESTAMP_FORMAT = "dd-MM-yyyy HH:mm:ss.SSSS aaa";
    private static final boolean debugMode = false;
    private static PrintWriter writer;
    private static LogEntries logEntries;
    private static Logs logs;

    private static CustomReporter customReporterInstance;

    private CustomReporter() {

    }

    public static CustomReporter getInstance() {
        if (customReporterInstance == null) {
            customReporterInstance = new CustomReporter();
        }
        return customReporterInstance;
    }

    @Step("{text}")
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

    public static void logError(String message) {
        createLog(message, Level.ERROR);
        ExtentReport.fail(ICON_SMILEY_FAIL + " " + message);
    }

    public static void logException(Exception e) {
        initializeLogger();
        logger.error("Exception occurred: ", e);
    }

    public static void logThrowable(Throwable t) {
        createLog(formatStackTraceToLogEntry(t), Level.ERROR);
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

    static synchronized void logAttachmentAction(String attachmentType, String attachmentName, ByteArrayOutputStream attachmentContent) {
        CustomReporter.logInfoStep("Successfully created attachment \"" + attachmentType + " - " + attachmentName + "\"");
        String timestamp = Helper.getCurrentTime();
        String theString;
        var br = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(attachmentContent.toByteArray()), StandardCharsets.UTF_8));
        theString = br.lines().collect(Collectors.joining(System.lineSeparator()));
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


    public static String formatStackTraceToLogEntry(Throwable t) {
        return formatStackTraceToLogEntry(t, false);
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

    public static void passAction(WebDriver driver, String testData) {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        passAction(driver, actionName, testData);
    }

    public static void passAction(String testData) {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        reportActionResult(actionName, testData, null, true);
    }

    public static void passAction(String testData, String log) {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        reportActionResult(actionName, testData, log, true);
    }

    public static void passAction(WebDriver driver, String actionName, String testData) {
        reportActionResult(driver, actionName, testData, true);
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
        failReporter(FileActions.class, message, rootCauseException[0]);
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

    public static String reportActionResult(WebDriver driver, String actionName, String testData, Boolean passFailStatus, Exception... rootCauseException) {
        actionName = Helper.convertToSentenceCase(actionName);
        String message;
        if (Boolean.TRUE.equals(passFailStatus)) {
            message = "Browser Action: " + actionName;
        } else {
            message = "Browser Action: " + actionName + " failed";
        }
        List<List<Object>> attachments = new ArrayList<>();
        if (testData != null && !testData.isEmpty()) {
            if (testData.length() >= 500 || testData.contains("</iframe>") || testData.contains("</html>") || testData.startsWith("From: <Saved by Blink>")) {
                List<Object> actualValueAttachment = Arrays.asList("Browser Action Test Data - " + actionName, "Actual Value", testData);
                attachments.add(actualValueAttachment);
            } else {
                message = message + " \"" + testData.trim() + "\"";
            }
        }
        if (rootCauseException != null && rootCauseException.length >= 1) {
            List<Object> actualValueAttachment = Arrays.asList("Browser Action Exception - " + actionName, "Stacktrace", CustomReporter.formatStackTraceToLogEntry(rootCauseException[0]));
            attachments.add(actualValueAttachment);
        }
        message = message + ".";
        message = message.replace("Browser Action: ", "");
        if (!attachments.equals(new ArrayList<>())) {
            CustomReporter.logAttachments(message, attachments);
        } else {
            CustomReporter.logInfoStep(message);
        }
        return message;
    }


    private static void createLog(String logText, Level loglevel) {
        String timestamp = Helper.getCurrentTime(TIMESTAMP_FORMAT);
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
                    writer.println(Helper.getCurrentTime() + "Error Message in Console: [" + logEntry.getMessage() + "]");
                    CustomReporter.logInfoStep(Helper.getCurrentTime() + "Error Message in Console: [" + logEntry.getMessage() + "]");
                } else if (logEntry.getMessage().toLowerCase().contains("warning")) {
                    writer.println(Helper.getCurrentTime() + "Warning Message in Console: [" + logEntry.getMessage() + "]");
                    CustomReporter.logInfoStep(Helper.getCurrentTime() + "Warning Message in Console: [" + logEntry.getMessage() + "]");
                } else {
                    writer.println(Helper.getCurrentTime() + "Information Message in Console: [" + logEntry.getMessage() + "]");
                    CustomReporter.logInfoStep(Helper.getCurrentTime() + "Information Message in Console: [" + logEntry.getMessage() + "]");
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


}


