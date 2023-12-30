package com.engine.reports;

import com.engine.actions.FileActions;
import com.engine.actions.TerminalActions;
import com.engine.constants.FrameworkConstants;
import com.engine.dataDriven.PropertiesManager;
import com.google.common.collect.ImmutableMap;
import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.google.common.io.Files;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.engine.reports.CustomReporter.createLog;


public class AllureReport {
    private static final String allureExtractionLocation = System.getProperty("user.home") + File.separator + ".m2" + File.separator + "repository" + File.separator + "allure" + File.separator;
    static String allureBinaryPath = "";
    static String allureResultsFolderPath = "allure-results/";
    public static String videoRecordedPath = PropertiesManager.getPropertyValue("paths.properties", "videoRecordedPath");

    private AllureReport() {
    }

    public static void setAllureEnvironmentInformation() {
        AllureEnvironmentWriter.allureEnvironmentWriter(
                ImmutableMap.<String, String>builder().
                        put("Test URL", "").
                        put("Target Execution", "").
                        put("Global Timeout", "").
                        put("Page Load Timeout", "").
                        build());
        CustomReporter.logConsole("Allure Reports is installed.");
    }

    public static void addAttachmentVideoAVI() {
        try {
            //Get file Last Modified in folder
            File video = FileActions.getInstance().getFileLastModified("./" + videoRecordedPath);
            if (video != null) {
                Allure.addAttachment("Video record AVI", "video/avi", Files.asByteSource(video).openStream(), ".avi");
            } else {
                CustomReporter.logWarning("Video record not found.");
                CustomReporter.logWarning("Can not attachment Video in Allure report");
            }

        } catch (IOException e) {
            CustomReporter.logError("Can not attachment Video in Allure report");
            e.printStackTrace();
        }
    }

    public static void addAttachmentVideoMP4() {
        try {
            //Get file Last Modified in folder
            File video = FileActions.getInstance().getFileLastModified(videoRecordedPath);
            if (video != null) {
                Allure.addAttachment("Failed test Video record MP4", "video/mp4", Files.asByteSource(video).openStream(), ".mp4");
            } else {
                CustomReporter.logWarning("Video record not found.");
                CustomReporter.logWarning("Can not attachment Video in Allure report");
            }

        } catch (IOException e) {
            CustomReporter.logError("Can not attachment Video in Allure report");
            e.printStackTrace();
        }
    }

    /**
     * Attach the Screenshot to the allure report
     *
     * @param driver - WebDriver Instance of the Browser
     * @return byte[] - Byte Array of the Screenshot
     */
    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] attachScreenshotToAllureReport(WebDriver driver) {
        return ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Attach the api request to the allure report
     *
     * @param type - Type
     * @param b    - Byte Array of the API Request
     * @return byte[] - Byte Array of the API Request
     */
    @Attachment(value = "API Request - {type}", type = "text/json")
    public static byte[] attachApiRequestToAllureReport(String type, byte[] b) {
        return attachTextJson(b);
    }

    @Attachment(value = "API Response", type = "text/json")
    public static byte[] attachApiResponseToAllureReport(byte[] b) {
        return attachTextJson(b);
    }

    @Attachment(value = "Log Console", type = "text/json")
    public static byte[] attachLogConsoleToAllureReport(byte[] b) {
        return attachTextJson(b);
    }

    public static byte[] attachTextJson(byte[] b) {
        try {
            return b;
        } catch (Exception e) {
            return null;
        }
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

    static Status getAllureStepStatus(String logText) {
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

    public static void openAllureReportAfterExecution() {
        String commandToOpenAllureReport;
        if (SystemUtils.IS_OS_WINDOWS) {
            commandToOpenAllureReport = ("generate_allure_report.bat");
        } else {
            commandToOpenAllureReport = ("sh generate_allure_report.sh");
        }
        TerminalActions.getInstance().performTerminalCommand(commandToOpenAllureReport);
    }

    public static void cleanAllureResultsDirectory() {
        // clean allure-results directory before execution
        try {
            FileActions.getInstance().deleteFile("allure-report/");
            FileActions.getInstance().deleteFolder(allureResultsFolderPath.substring(0, allureResultsFolderPath.length() - 1));
        } catch (Exception t) {
            CustomReporter.logError("Failed to delete allure-results as it is currently open. Kindly restart your device to unlock the directory.");
        }
    }

    // allure generate --single-file allure-results -o allure-report
    public static void writeAllureReport() {
        // add correct file extension based on target OS
        String commandToCreateAllureReport;
        allureBinaryPath = allureExtractionLocation + "allure-" + "2.25.0" + "/bin/allure";

        if (SystemUtils.IS_OS_WINDOWS) {
            commandToCreateAllureReport = allureBinaryPath + ".bat" + " generate --single-file --clean '"
                    + allureResultsFolderPath.substring(0, allureResultsFolderPath.length() - 1)
                    + "' -o 'allure-report'";
        } else {
            commandToCreateAllureReport = allureBinaryPath + " generate --single-file --clean "
                    + allureResultsFolderPath.substring(0, allureResultsFolderPath.length() - 1)
                    + " -o allure-report";
        }

        TerminalActions.getInstance(false, false).performTerminalCommand(commandToCreateAllureReport);
    }
}
