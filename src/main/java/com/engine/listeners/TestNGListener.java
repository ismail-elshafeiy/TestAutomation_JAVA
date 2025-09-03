package com.engine.listeners;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.engine.reports.AllureReport;
import com.engine.reports.CustomReporter;
import com.engine.reports.ExtentReport;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestNGListener implements IAlterSuiteListener, IAnnotationTransformer,
        IExecutionListener, ISuiteListener, IInvokedMethodListener, ITestListener {
    static int count_totalTCs;
    static int count_passedTCs;
    static int count_skippedTCs;
    static int count_failedTCs;


    String runBy = " Ismail ElShaFeiy 😉";

    ////////////////////////////////////////////////////
    ///////////////// ISuiteListener //////////////////
    //////////////////////////////////////////////////
    @Override
    public void onExecutionStart() {
        CustomReporter.createImportantReportEntry("Start execution by " + runBy);
        Allure.getLifecycle();
        ExtentReport.initializeExtentReport();
        AllureReport.cleanAllureResultsDirectory();
    }

    @Override
    public void onExecutionFinish() {
        ExtentReport.flushReports();
        AllureReport.writeAllureReport();
        AllureReport.openAllureReportAfterExecution();
        //EmailSendUtils.sendEmail(count_totalTCs, count_passedTCs, count_failedTCs, count_skippedTCs);
        CustomReporter.createImportantReportEntry("Finished execution by " + runBy);
    }

    @Override
    public void onStart(ISuite suite) {
        //FileActions.getInstance().createFolder(Properties.paths.services());
        //FileActions.getInstance().writeToFile(Properties.paths.services(), "org.testng.ITestNGListener", "com.engine.listeners.TestngListener");

    }

    @Override
    public void onFinish(ISuite suite) {
    }

    @Override
    public void onStart(ITestContext context) {
        CustomReporter.createImportantReportEntry(" Test: [ " + context.getName() + " ] Started ");
    }

    @Override
    public void onFinish(ITestContext context) {
        CustomReporter.createImportantReportEntry(" Test: [ " + context.getName() + " ] Finished ");
    }


    ////////////////////////////////////////////////////////////
    ///////////////// IInvokedMethodListener //////////////////
    //////////////////////////////////////////////////////////
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        ITestNGMethod testMethod = method.getTestMethod();
        if (testMethod.getDescription() != null && !testMethod.getDescription().equals("")) {
            ExtentReport.createTest(testMethod.getDescription());
        } else {
            ExtentReport.createTest(testResult.getName());
        }
        if (method.isConfigurationMethod()) {
            CustomReporter.createImportantReportEntry("Starting Configuration Method (Setup or TearDown): [" + testResult.getName() + "]");
            if (testMethod.getDescription() != null && !testMethod.getDescription().equals("")) {
                ExtentReport.removeTest(testMethod.getDescription());
            } else {
                ExtentReport.removeTest(testResult.getName());
            }
        } else {
            CustomReporter.createImportantReportEntry("Starting Test Case: [ " + testResult.getName() + " ]");
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isConfigurationMethod()) {
            CustomReporter.createImportantReportEntry("Finished Configuration Method (Setup or TearDown): [" + testResult.getName() + "]");
        } else {
            CustomReporter.createImportantReportEntry("Finished Test Case: [ " + testResult.getName() + " ]");
        }
    }

    ///////////////////////////////////////////////////
    ///////////////// ITestListener //////////////////
    /////////////////////////////////////////////////

    @Override
    public void onTestStart(ITestResult result) {
        count_totalTCs = count_totalTCs + 1;
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        count_passedTCs = count_passedTCs + 1;
        ExtentReport.pass(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Passed!", ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        count_failedTCs = count_failedTCs + 1;
        ITestContext context = result.getTestContext();
        WebDriver driver = (WebDriver) context.getAttribute("driver");
        if (driver != null) {
            try {
                AllureReport.attachScreenshotToAllureReport(driver);
                ExtentReport.fail(ExtentReport.attachScreenshotToExtentReport(driver));
            } catch (Throwable e) {
                CustomReporter.logError("Error:  " + e.getMessage());
            }
        }
        ExtentReport.fail(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Failed!", ExtentColor.RED));
        if (result.getThrowable() != null) {
            ExtentReport.fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        count_skippedTCs = count_skippedTCs + 1;
        ExtentReport.skip(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Skipped!", ExtentColor.YELLOW));
        if (result.getThrowable() != null) {
            ExtentReport.skip(result.getThrowable());
        }
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(Retry.class);
    }

}
