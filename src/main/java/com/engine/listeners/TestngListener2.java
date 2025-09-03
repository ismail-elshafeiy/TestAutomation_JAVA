package com.engine.listeners;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.pages.tobi.TobiHelper;
import engine.actions.FileActions;
import engine.constants.FrameworkConstants;
import engine.constants.Styles;
import engine.dataDriven.PropertiesManager;
import engine.reports.AllureReport;
import engine.reports.CustomReporter;
import engine.reports.ExtentReport;
import engine.utils.Helper;
import org.testng.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static engine.constants.FrameworkConstants.CUSTOM_PROPERTIES;
import static engine.constants.FrameworkConstants.PATH_PROPERTIES;

public class TestngListener2 implements IAlterSuiteListener, IAnnotationTransformer, IExecutionListener, ISuiteListener, IInvokedMethodListener, ITestListener {

    private static final String RUN_BY = "Ismail ElShafeiy \uD83D\uDC4B😉";
    private static int count_totalTCs, count_passedTCs, count_skippedTCs, count_failedTCs;
    private long durationInSeconds;
    public final String extentReportsFolderPath = PropertiesManager.getInstance().getProperty(PATH_PROPERTIES, "extentReportsFolderPath");
    public final String frameWorkVersion = PropertiesManager.getInstance().getProperty(CUSTOM_PROPERTIES, "framework.version");

    @Override
    public void onExecutionStart() {
        System.gc();
        CustomReporter.getInstance().logImportantMessage("Start Execution by " + RUN_BY, "34");
        ExtentReport.getInstance().initializeExtentReport();
        FileActions.getInstance().copySeleniumFolderFromLocalToProject();
        FileActions.getInstance().copySeleniumFolderFromProjectToLocalMachine();
        AllureReport.getInstance().copyAllureLibToLocalMachine();
        FileActions.getInstance().deleteReportsFolder();
        FileActions.getInstance().checkTicketFileIsExist();
        CustomReporter.getInstance()
                .logConsole("Total memory is : [ " + Runtime.getRuntime().totalMemory() + " ]")
                .logConsole("Max memory is : [ " + Runtime.getRuntime().maxMemory() + " ]")
                .logConsole("Free memory is : [ " + Runtime.getRuntime().freeMemory() + " ]")
                .logConsole("Available Processors is : [ " + Runtime.getRuntime().availableProcessors() + " ]");
    }

    @Override
    public void onExecutionFinish() {
        CustomReporter.getInstance().logImportantMessage("Finished execution by " + RUN_BY, "34");
        CustomReporter.getInstance()
                .logConsole("Total memory is : [ " + Runtime.getRuntime().totalMemory() + " ] Before system.gc")
                .logConsole("Max memory is : [ " + Runtime.getRuntime().maxMemory() + " ] Before system.gc")
                .logConsole("Free memory is : [ " + Runtime.getRuntime().freeMemory() + " ] Before system.gc")
                .logConsole("Available Processors is : [ " + Runtime.getRuntime().availableProcessors() + " ] Before system.gc");
        CustomReporter.getInstance().logConsole("Starting garbage collection");
        System.gc();
        CustomReporter.getInstance()
                .logConsole("Current total memory is : [ " + Runtime.getRuntime().totalMemory() + " ]")
                .logConsole("Current max memory is : [ " + Runtime.getRuntime().maxMemory() + " ]")
                .logConsole("Current free memory is : [ " + Runtime.getRuntime().freeMemory() + " ]")
                .logConsole("Current available Processors is : [ " + Runtime.getRuntime().availableProcessors() + " ]");
        FileActions.getInstance().zipReportsFolder();
      //  FileActions.getInstance().getFilesEndWith("allure-results", "-result.json");
    }

    @Override
    public void onStart(ISuite suite) {
    }

    @Override
    public void onFinish(ISuite suite) {
        double passedPercentage = (double) count_passedTCs / count_totalTCs * 100;
        double failedPercentage = (double) count_failedTCs / count_totalTCs * 100;
        double skippedPercentage = (double) count_skippedTCs / count_totalTCs * 100;
        String passedPercentageString = String.format("%.2f", passedPercentage);
        String failedPercentageString = String.format("%.2f", failedPercentage);
        String skippedPercentageString = String.format("%.2f", skippedPercentage);
        ExtentReport.getInstance().addSystemInfo("Total Tests", count_totalTCs + "    ( 1 test case take " + durationInSeconds / count_totalTCs + " seconds )");
        ExtentReport.getInstance().addSystemInfo("Tests Passed", Styles.getInstance().ICON_SMILEY_PASS + "   " + count_passedTCs + "   ( " + passedPercentageString + "% )");
        ExtentReport.getInstance().addSystemInfo("Tests Failed", Styles.getInstance().ICON_SMILEY_FAIL + "   " + count_failedTCs + "   ( " + failedPercentageString + "% )");
        ExtentReport.getInstance().addSystemInfo("Tests Skipped", Styles.getInstance().ICON_SMILEY_SKIP + "   " + count_skippedTCs + "   ( " + skippedPercentageString + "% )");
        ExtentReport.getInstance().flushReports();
        String extentReportPath = extentReportsFolderPath + "Test_TOBi_Report_" + Helper.getCurrentDay() + ".html";
        if (FrameworkConstants.OPEN_EXTENT_REPORT.equalsIgnoreCase("yes")) {
            try {
                Desktop.getDesktop().browse(new File(extentReportPath).toURI());
            } catch (IOException e) {
                CustomReporter.getInstance().logConsole("Couldn't open the extent report after execution");
            }
        } else {
            CustomReporter.getInstance().logConsole("Extent Report won't be opened after execution");
        }
       // FileActions.getInstance().getFilesEndWith("allure-results", "-result.json");
    }


    @Override
    public void onStart(ITestContext context) {
        CustomReporter.getInstance().logImportantMessage("Start test context: [ " + context.getName() + " " + frameWorkVersion + " ], Date : [ " + context.getStartDate() + " ]  ", "32");
        AllureReport.getInstance().attachStaticFileToAllureReport();
    }


    @Override
    public void onFinish(ITestContext context) {
        CustomReporter.getInstance().logImportantMessage("Finish test context: [ " + context.getName() + "  " + frameWorkVersion + " ], Date : [ " + context.getEndDate() + " ]", "32");
        long duration = context.getEndDate().getTime() - context.getStartDate().getTime();
        long durationInHours = duration / 1000 / 60 / 60;
        long durationInMinutes = duration / 1000 / 60;
        durationInSeconds = duration / 1000;
        System.out.println("duration =[ " + duration + " ], durationInHours = [ " + durationInHours + " ], durationInMinutes = [ " + durationInMinutes + " ], durationInSeconds = [ " + durationInSeconds + " ]");
        if (duration > 1000 * 60 * 60) {
            CustomReporter.getInstance().logImportantMessage("Execution Time: [ " + durationInHours + " ] hours", "32");
            ExtentReport.getInstance().addSystemInfo("Execution Time", durationInHours + " hours");
        } else if (duration > 1000 * 60) {
            CustomReporter.getInstance().logImportantMessage("Execution Time: [ " + durationInMinutes + " ] minutes", "32");
            ExtentReport.getInstance().addSystemInfo("Execution Time", durationInMinutes + " minutes");
        } else {
            CustomReporter.getInstance().logImportantMessage("Execution Time: [ " + durationInSeconds + " ] seconds", "32");
            ExtentReport.getInstance().addSystemInfo("Execution Time", durationInSeconds + " seconds");
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        ITestNGMethod testMethod = method.getTestMethod();
        if (testMethod.isBeforeSuiteConfiguration()) {
            ExtentReport.getInstance().removeTest(testMethod.getMethodName());
        } else if (testMethod.isAfterSuiteConfiguration())
            ExtentReport.getInstance().removeTest(testMethod.getMethodName());
        if (!method.isConfigurationMethod()) {
            CustomReporter.getInstance().logImportantMessage("Starting Execute Test Case :" + testResult.getName(), "34");
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isConfigurationMethod()) {
            TobiHelper.getInstance().isTestCasePassed();
            CustomReporter.getInstance().logImportantMessage("Finished Execute Test Case :" + Arrays.toString(testResult.getParameters()), "34");
            CustomReporter.getInstance().logImportantMessage("Finished Execute Test Case :" + testResult.getName(), "34");
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        count_totalTCs++;
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        count_passedTCs++;
        ExtentReport.getInstance().pass(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Passed!", ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        count_failedTCs++;
        ExtentReport.getInstance().fail(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Failed!", ExtentColor.RED));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        count_skippedTCs++;
        ExtentReport.getInstance().skip(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Skipped!", ExtentColor.YELLOW));
        if (result.getThrowable() != null) {
            ExtentReport.getInstance().skip(result.getThrowable());
        }
    }
}
