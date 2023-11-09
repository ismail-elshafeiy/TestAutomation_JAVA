package com.engine.listeners;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.engine.evidence.Attachments;
import com.engine.evidence.RecordVideo;
import com.engine.report.ExtentReport;
import com.engine.evidence.RecordManager;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestngListener implements ISuiteListener, ITestListener, IInvokedMethodListener {
	private RecordVideo screenRecorder;

	public TestngListener() {
		try {
			screenRecorder = new RecordVideo();
		} catch (IOException | AWTException e) {
			CustomReporter.logMessage(e.getMessage());
			e.printStackTrace();
		}
	}


	String runBy = " Ismail ElShaFeiy ;)";
	String boundaryStartEnd = "======================================================";
	String boundaryBeforeAfter = "################################################################################################################################################";
	////////////////////////////////////////////////////
	///////////////// ISuiteListener //////////////////
	//////////////////////////////////////////////////

	@Override
	public void onStart (ISuite suite) {
		CustomReporter.createImportantReportEntry("Starting Test Suite;" + runBy);
		ExtentReport.initReports();
	}

	@Override
	public void onFinish (ISuite suite) {
		CustomReporter.createImportantReportEntry("Finished Test Suite;" + runBy);
		ExtentReport.flushReports();
	}

	///////////////////////////////////////////////////
	///////////////// ITestListener //////////////////
	/////////////////////////////////////////////////
	@Override
	public void onStart (ITestContext context) {
		CustomReporter.createImportantReportEntry(" Test: [ " + context.getName() + " ] Started ");
		// CustomReporter.logConsole();("\n" + boundaryStartEnd + " Test: [ " + context.getName() + " ] Started " + boundaryStartEnd + "\n");
	}

	@Override
	public void onTestStart (ITestResult result) {
		screenRecorder.startRecording(result.getMethod().getMethodName());
		//	ExtentReport.createTest(result.getName());
	}

	@Override
	public void onFinish (ITestContext context) {
		CustomReporter.createImportantReportEntry(" Test: [ " + context.getName() + " ] Finished ");
	}

	@Override
	public void onTestSuccess (ITestResult result) {
		ExtentReport.pass(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Passed!", ExtentColor.GREEN));
		screenRecorder.stopRecording(true);
	}

	@Override
	public void onTestFailure (ITestResult result) {
		ITestContext context = result.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute("driver");
		if ( driver != null ) {
			try {
				Attachments.attachScreenshotToAllureReport(driver);
				ExtentReport.fail(Attachments.attachScreenshotToExtentReport(driver));
//				ExtentReport.fail(Attachments.attachFullPageScreenShotToExtentReport((FirefoxDriver) driver));
//				Logger.logConsoleLogs(driver, result);
			} catch ( Throwable e ) {
				CustomReporter.logMessage("Error:  " + e.getMessage());
			}
		}
		ExtentReport.fail(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Failed!", ExtentColor.RED));
		if ( result.getThrowable() != null ) {
			ExtentReport.fail(result.getThrowable());
		}
		screenRecorder.stopRecording(true);
	}

	@Override
	public void onTestSkipped (ITestResult result) {
		ExtentReport.skip(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Skipped!", ExtentColor.YELLOW));
		if ( result.getThrowable() != null ) {
			ExtentReport.skip(result.getThrowable());
		}
		screenRecorder.stopRecording(true);
	}

	////////////////////////////////////////////////////////////
	///////////////// IInvokedMethodListener //////////////////
	//////////////////////////////////////////////////////////
	@Override
	public void beforeInvocation (IInvokedMethod method, ITestResult testResult) {
		ITestNGMethod testMethod = method.getTestMethod();
		if ( testMethod.getDescription() != null && ! testMethod.getDescription().equals("") ) {
			ExtentReport.createTest(testMethod.getDescription());
		} else {
			ExtentReport.createTest(testResult.getName());
		}
		if ( method.isConfigurationMethod() ) {
			CustomReporter.createImportantReportEntry("Starting Configuration Method (Setup or TearDown): [" + testResult.getName() + "]");
			if ( testMethod.getDescription() != null && ! testMethod.getDescription().equals("") ) {
				ExtentReport.removeTest(testMethod.getDescription());
			} else {
				ExtentReport.removeTest(testResult.getName());
			}
		} else {
			CustomReporter.logConsole("Starting Test Case: [" + testResult.getName() + "]");
		}
	}

	@Override
	public void afterInvocation (IInvokedMethod method, ITestResult testResult) {
		if ( method.isConfigurationMethod() ) {
			CustomReporter.createImportantReportEntry("Finished Configuration Method (Setup or TearDown): [" + testResult.getName() + "]");
		} else {
			//attachTestArtifacts(testResult);
			CustomReporter.createImportantReportEntry("Finished Test Case: [" + testResult.getName() + "]");
		}
	}

	public static void attachTestArtifacts (ITestResult iTestResult) {
		ITestNGMethod iTestNGMethod = iTestResult.getMethod();

		if ( ! Arrays.asList("suiteSetup", "suiteTeardown", "classTeardown").contains(iTestNGMethod.getMethodName()) ) {
			List<String> attachments = new ArrayList<>();
			String attachment;
			//		if ( System.getProperty("videoParams_scope").trim().equals("TestMethod") ) {
				RecordManager.attachVideoRecording();
				attachment = RecordManager.getVideoRecordingFilePath();
				if ( ! attachment.equals("") )
					attachments.add(attachment);
			//		}
			// attachment = ScreenshotManager.attachAnimatedGif();


		}
	}
}
