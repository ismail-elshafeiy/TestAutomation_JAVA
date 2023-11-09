package com.engine.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.engine.Helper;

public class ExtentReport {

	private static ExtentReports report;
	private static ExtentTest test;
	static String currentTime = Helper.getCurrentTime("yyyy-MM-dd_HH-mm");

	public static void initReports () {
		report = new ExtentReports();
		ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("ExtentReport/ExtentReports.html");
		report.attachReporter(extentSparkReporter);
		extentSparkReporter.config().setTheme(Theme.STANDARD);
		extentSparkReporter.config().setDocumentTitle("Extent Report");
		extentSparkReporter.config().setReportName("Test Automation-Extent Report");
	}

	public static void createTest (String testCaseName) {
		test = report.createTest(testCaseName);
	}

	public static void removeTest (String testCaseName) {
		report.removeTest(testCaseName);
	}

	public static void flushReports () {
		report.flush();
	}

	//************* info methods *************//
	public static void info (Markup m) {
		test.info(m);
	}

	public static void info (String message) {
		if ( test != null ) {
			test.info(message);
		}
	}

	//************* pass methods *************//
	public static void pass (Markup m) {
		test.pass(m);
	}

	public static void pass (String message) {
		test.pass(message);
	}


	public static void pass (Media media) {
		test.pass(media);
	}

	//************* fail methods *************//
	public static void fail (Markup m) {
		test.fail(m);
	}

	public static void fail (String message) {
		test.fail(message);
	}

	public static void fail (Media media) {
		test.fail(media);
	}

	public static void fail (Throwable t) {
		test.fail(t);
	}

	//************* skip methods *************//

	public static void skip (String message) {
		test.skip(message);
	}

	public static void skip (Markup m) {
		test.skip(m);
	}

	public static void skip (Throwable t) {
		test.skip(t);
	}

}
