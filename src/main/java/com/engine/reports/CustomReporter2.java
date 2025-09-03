package com.engine.reports;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.pages.itsm.AmeliaConfig;
import com.pages.tobi.TestCase;
import com.shaft.tools.io.ReportManager;
import com.shaft.tools.io.internal.ReportManagerHelper;
import engine.constants.FrameworkConstants;
import engine.dataDriven.PropertiesManager;
import engine.utils.Helper;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;

import static engine.enums.EnumHelper.getData;

public class CustomReporter2 {
    private Logger logger;
    private static CustomReporter customReporterInstance;

    private CustomReporter2() {
    }

    public static CustomReporter getInstance() {
        if (customReporterInstance == null) {
            customReporterInstance = new CustomReporter();
        }
        return customReporterInstance;
    }


    public void print(String data) {
        System.out.println(data);
    }

    public void testCaseInfo(LinkedHashMap<String, String> data) {
        String testCaseID = getData(data, TestCase.TC_ID);
        String epicName = getData(data, TestCase.EPIC_NAME);
        String testCaseNameALM = getData(data, TestCase.TC_TITLE_ALM);
        String testDescription = "Tc ID: " + testCaseID + " : [ " + testCaseNameALM + " ]";
        ExtentReport.getInstance().createTest(getData(data, TestCase.RUN_ID) + " " + testCaseID + " - " + testCaseNameALM + ", using email [" + getData(data, TestCase.CURRENT_LOGIN_EMAIL) + "]", getData(data, TestCase.EPICS_OWNER), epicName, getData(data, TestCase.FLOW_TYPE));
        ExtentReport.getInstance().info("Login via email: [ " + getData(data, TestCase.CURRENT_LOGIN_EMAIL) + " ] On [ " + FrameworkConstants.ENVIRONMENT + " ]");
        Allure.epic(epicName);
        if (epicName.equalsIgnoreCase(FrameworkConstants.FSD_SHEET_NAME)) {
            Allure.story(getData(data, AmeliaConfig.SERVICE_CI) + " - FSD");
        }
        Allure.description(testDescription);
        Allure.parameter("Description", testDescription);
        Allure.parameter("Priority", "P " + getData(data, TestCase.PRIORITY));
        Allure.parameter("Market", getData(data, TestCase.MARKET));
        Allure.parameter("Integration", getData(data, TestCase.INTEGRATION));
        Allure.parameter("Last status run", getData(data, TestCase.STATUS));
        Allure.parameter("Qualifications", "Key Phrase: [ " + getData(data, AmeliaConfig.KEY_PHRASE) + " ], " +
                "Action Type: [ " + getData(data, AmeliaConfig.ACTION_TYPE) + " ]," +
                " Application Name: [ " + getData(data, AmeliaConfig.SERVICE_CI) + " ]");
        logInfoStep(" Run Epic: [ " + epicName + " ], Domain: [ " + getData(data, TestCase.DOMAIN) + " ]");
    }

    public void testCaseStepAgainstNumberOfTC(int testCaseRow, int numberOfStepsFromTestCase) {
        logImportantMessage(">>>>>     >>>>>   Step [ Test Case row: " + testCaseRow + " / " + numberOfStepsFromTestCase + " ]", "32");
        ReportManager.log(">>>>>     >>>>>   Step [ Test Case row: " + testCaseRow + " / " + numberOfStepsFromTestCase + " ]");
        ExtentReport.getInstance().info(MarkupHelper.createLabel(">>>>> Step [ Test Case row: " + testCaseRow + " / " + numberOfStepsFromTestCase + " ]", ExtentColor.GREEN));
    }

    public void logImportantMessageAndStep(String text) {
        logImportantMessage(text, "32");
        Allure.step(text);
    }

    public CustomReporter logInfoStep(String text) {
        ReportManager.log(text);
        ExtentReport.getInstance().info(text);
        return this;
    }


    public CustomReporter logInfo(String text) {
        ReportManagerHelper.createLogEntry(text, Level.INFO);
        ExtentReport.getInstance().info(text);
        return this;
    }

    public void logPassed(String text) {
        ReportManager.log(text);
        ExtentReport.getInstance().pass(text);
    }

    public void logError(String text) {
        logConsole("Kindly check the error message below: ", Level.ERROR, "31");
        try {
            ReportManager.log(text, Level.ERROR);
            ExtentReport.getInstance().fail(text);
        } catch (Exception e) {
            logConsole("Error occurred while logging the error message: ");
        }
    }

    public void logErrorFailed(String exceptionMessage) {
        ReportManager.log(exceptionMessage, Level.ERROR);
        ExtentReport.getInstance().fail(exceptionMessage);

    }


    public void logWarning(String text) {
        logConsole("Kindly check the warning message below: ", Level.WARN, "33");
        ReportManager.log(text, Level.WARN);
        ExtentReport.getInstance().warning(text);
    }

    public CustomReporter logConsole(String text) {
        if (FrameworkConstants.DEBUG_MODE_LOGGER.equalsIgnoreCase("true")) {
            String log = "\033[37m" + text.trim();
            createLogger(log, Level.INFO);
        }
        return this;
    }

    public CustomReporter logConsole(String text, Level level) {
        if (FrameworkConstants.DEBUG_MODE_LOGGER.equalsIgnoreCase("true")) {
            String log = "\033[37m" + text.trim() + "\033[0m";
            createLogger(log, level);
        }
        return this;
    }

    /**
     * This method is used to log in console with custom level and color
     *
     * @param text  to be logged in console
     * @param level of the log
     * @param color Red: 31, Green: 32, Yellow: 33, Blue: 34, Magenta: 35, Cyan: 36, White: 37.
     */
    public void logConsole(String text, Level level, String color) {
        if (FrameworkConstants.DEBUG_MODE_LOGGER.equalsIgnoreCase("true")) {
            String log = "\033[" + color + "m" + text.trim();
            createLogger(log, level);
        }
    }

    public void logImportantMessage(String text) {
        String log = System.lineSeparator() +
                "\033[0;7m" +
                createSeparator('-') +
                addSpacing(text.trim()) +
                createSeparator('-') +
                System.lineSeparator() +
                "\033[0m";
        createLogger(log, Level.INFO);
    }

    /**
     * This method is used to log important message  in console with custom color
     *
     * @param text  to be logged in console
     * @param color Red: 31, Green: 32, Yellow: 33, Blue: 34, Magenta: 35, Cyan: 36, White: 37.
     */
    public void logImportantMessage(String text, String color) {
        String log = System.lineSeparator() +
                "\033[" + color + "m" +
                createSeparator('-') +
                addSpacing(text.trim()) +
                createSeparator('-') +
                System.lineSeparator() +
                "\033[0m";
        createLogger(log, Level.INFO);
    }


    private String createSeparator(@SuppressWarnings("SameParameterValue") char ch) {
        return String.valueOf(ch).repeat(144);
    }

    private String addSpacing(String log) {
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

    private void createLogger(String log, Level level) {
        Reporter.log(log, false);
        if (logger == null) {
            initializeLogger();
        }
        logger.log(level, log);
    }

    private void initializeLogger() {
        Configurator.initialize(null, PropertiesManager.getCUSTOM_PROPERTIES_FOLDER_PATH() + "log4j2.properties");
        logger = LogManager.getLogger(CustomReporter.class.getName());
    }

    // @Attachment(value = "log Console", type = "text/plain")
    public void logConsoleLogs(WebDriver driver, ITestResult result) {
        Logs logs = driver.manage().logs();
        LogEntries logEntries = logs.get(LogType.BROWSER);
        String filePath = System.getProperty("user.dir") + "/ConsoleLogs/" + result.getMethod().getMethodName() + ".txt";
        try (PrintWriter writer = new PrintWriter(filePath, StandardCharsets.UTF_8)) {
            logInfoStep("Console logs for Test Case: [" + result.getMethod().getMethodName() + "] are saved in [ " + filePath + " ]");
            for (LogEntry logEntry : logEntries) {
                writer.println("Console log found in Test- " + result.getName());
                writer.println("__________________________________________________________");
                if (logEntry.getMessage().toLowerCase().contains("error")) {
                    writer.println(Helper.getCurrentTime() + "Error Message in Console: [" + logEntry.getMessage() + "]");
                    logInfoStep(Helper.getCurrentTime() + "Error Message in Console: [" + logEntry.getMessage() + "]");
                } else if (logEntry.getMessage().toLowerCase().contains("warning")) {
                    writer.println(Helper.getCurrentTime() + "Warning Message in Console: [" + logEntry.getMessage() + "]");
                    logInfoStep(Helper.getCurrentTime() + "Warning Message in Console: [" + logEntry.getMessage() + "]");
                } else {
                    writer.println(Helper.getCurrentTime() + "Information Message in Console: [" + logEntry.getMessage() + "]");
                    logInfoStep(Helper.getCurrentTime() + "Information Message in Console: [" + logEntry.getMessage() + "]");
                }
            }
        } catch (Exception e) {
            logError("Console logs not found: " + e.getMessage());
        }
    }
}


