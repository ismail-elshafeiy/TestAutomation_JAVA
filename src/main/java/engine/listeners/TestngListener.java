package engine.listeners;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import engine.evidence.Attachments;
import engine.ExtentReport;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.*;

public class TestngListener implements ISuiteListener, ITestListener, IInvokedMethodListener {


	String runBy = " Ismail ElShaFeiy ;)";
	String boundaryStartEnd = "======================================================";
	String boundaryBeforeAfter = "################################################################################################################################################";
	////////////////////////////////////////////////////
	///////////////// ISuiteListener //////////////////
	//////////////////////////////////////////////////

	@Override
	public void onStart (ISuite suite) {
		System.out.println("\n" + boundaryStartEnd);
		System.out.println("Starting Test Suite;" + runBy);
		System.out.println(boundaryStartEnd + "\n");
		ExtentReport.initReports();
	}

	@Override
	public void onFinish (ISuite suite) {
		System.out.println("\n" + boundaryStartEnd);
		System.out.println("Finished Test Suite;" + runBy);
		System.out.println(boundaryStartEnd + "\n");
		ExtentReport.flushReports();
	}

	///////////////////////////////////////////////////
	///////////////// ITestListener //////////////////
	/////////////////////////////////////////////////
	@Override
	public void onStart (ITestContext context) {
		System.out.println("\n" + boundaryStartEnd + " Test: [ " + context.getName() + " ] Started " + boundaryStartEnd + "\n");
	}

	@Override
	public void onTestStart (ITestResult result) {
		//	ExtentReport.createTest(result.getName());
	}

	@Override
	public void onFinish (ITestContext context) {
		System.out.println("\n" + boundaryStartEnd + " Test: [ " + context.getName() + "]  Finished " + boundaryStartEnd + "\n");
	}

	@Override
	public void onTestSuccess (ITestResult result) {
		ExtentReport.pass(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Passed!", ExtentColor.GREEN));
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
				Logger.logMessage("Error:  " + e.getMessage());
			}
		}
		ExtentReport.fail(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Failed!", ExtentColor.RED));
		if ( result.getThrowable() != null ) {
			ExtentReport.fail(result.getThrowable());
		}
	}

	@Override
	public void onTestSkipped (ITestResult result) {
		ExtentReport.skip(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Skipped!", ExtentColor.YELLOW));
		if ( result.getThrowable() != null ) {
			ExtentReport.skip(result.getThrowable());
		}
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
		System.out.println("\n" + boundaryBeforeAfter);
		if ( method.isConfigurationMethod() ) {
			System.out.println("Starting Configuration Method (Setup or TearDown): [" + testResult.getName() + "]");
			if ( testMethod.getDescription() != null && ! testMethod.getDescription().equals("") ) {
				ExtentReport.removeTest(testMethod.getDescription());
			} else {
				ExtentReport.removeTest(testResult.getName());
			}
		} else {
			System.out.println("Starting Test Case: [" + testResult.getName() + "]");
		}
		System.out.println(boundaryBeforeAfter + "\n");
	}

	@Override
	public void afterInvocation (IInvokedMethod method, ITestResult testResult) {
		System.out.println("\n" + boundaryBeforeAfter);
		if ( method.isConfigurationMethod() ) {
			System.out.println("Finished Configuration Method (Setup or TearDown): [" + testResult.getName() + "]");
		} else {
			System.out.println("Finished Test Case: [" + testResult.getName() + "]");
		}
		System.out.println(boundaryBeforeAfter + "\n");
	}
}
