package com.engine.listeners;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.engine.evidence.Attachments;
import com.engine.evidence.RecordVideo;
import com.engine.mail.EmailSendUtils;
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
	static int count_totalTCs;
	static int count_passedTCs;
	static int count_skippedTCs;
	static int count_failedTCs;

	public TestngListener() {
		try {
			screenRecorder = new RecordVideo();
		} catch (IOException | AWTException e) {
			CustomReporter.logErrorMessage(e.getMessage());
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
		//FileActions.getInstance().createFolder(Properties.paths.services());
		//FileActions.getInstance().writeToFile(Properties.paths.services(), "org.testng.ITestNGListener", "com.engine.listeners.TestngListener");
		CustomReporter.createImportantReportEntry("Starting Test Suite;" + runBy);
		ExtentReport.initReports();
	}

	@Override
	public void onFinish (ISuite suite) {
		CustomReporter.createImportantReportEntry("Finished Test Suite;" + runBy);
		ExtentReport.flushReports();
		EmailSendUtils.sendEmail(count_totalTCs, count_passedTCs, count_failedTCs, count_skippedTCs);
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
		count_totalTCs = count_totalTCs + 1;
		screenRecorder.startRecording(result.getMethod().getMethodName());
		//	ExtentReport.createTest(result.getName());
	}

	@Override
	public void onFinish (ITestContext context) {
		CustomReporter.createImportantReportEntry(" Test: [ " + context.getName() + " ] Finished ");
	}

	@Override
	public void onTestSuccess (ITestResult result) {
		count_passedTCs = count_passedTCs + 1;
		ExtentReport.pass(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Passed!", ExtentColor.GREEN));
		screenRecorder.stopRecording(true);
	}

	@Override
	public void onTestFailure (ITestResult result) {
		count_failedTCs = count_failedTCs + 1;
		ITestContext context = result.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute("driver");
		if ( driver != null ) {
			try {
				Attachments.attachScreenshotToAllureReport(driver);
				ExtentReport.fail(Attachments.attachScreenshotToExtentReport(driver));
//				ExtentReport.fail(Attachments.attachFullPageScreenShotToExtentReport((FirefoxDriver) driver));
//				Logger.logConsoleLogs(driver, result);
			} catch ( Throwable e ) {
				CustomReporter.logErrorMessage("Error:  " + e.getMessage());
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
		count_skippedTCs = count_skippedTCs + 1;
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
