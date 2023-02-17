package engine.listeners;

import engine.ExtentReport;
import engine.Helper;
import engine.broswer.BrowserActions;
import io.qameta.allure.AllureId;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v109.log.Log;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class Logger {
	public static org.apache.logging.log4j.Logger log = LogManager.getLogger(Logger.class.getName());
	public static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(Logger.class);
	private static final boolean debugMode = false;
	private static String currentTime = Helper.getCurrentTime("dd-MM-yyyy HH:mm:ss");
	static PrintWriter writer;
	static LogEntries logEntries;
	static Logs logs;
	private static WebDriver driver;

	/**
	 * Log step that will be added as a step in the execution report
	 *
	 * @param logStep logged by action that will be added as a step in the execution report (e.g. click on button)
	 */
	@AllureId (value = "1")
	@Step ("{logStep}")
	public static void logStep (String logStep) {
		System.out.println(currentTime + " INFO : " + logStep);
		ExtentReport.info(logStep);
	}

	/**
	 * Log message that will be added as a message in the execution report
	 *
	 * @param logMessage logged by exception / assertion message that will be added as a message in the execution report
	 */
	public static void logMessage (String logMessage) {
		System.out.println(currentTime + " Message: " + System.lineSeparator() + logMessage);
		ExtentReport.info(logMessage);
	}

	/**
	 * Log console error, warming and info that will be added as a error in the execution report
	 *
	 * @param driver driver instance
	 * @param result test result
	 *
	 * @throws IOException
	 */
	@Attachment (value = "log Console", type = "text/plain")
	public static void logConsoleLogs (WebDriver driver, ITestResult result) throws IOException {
		logs = driver.manage().logs();
		logEntries = logs.get(LogType.BROWSER);
		String filePath = System.getProperty("user.dir") + "/ConsoleLogs/" + result.getMethod().getMethodName() + ".txt";
		writer = new PrintWriter(filePath, StandardCharsets.UTF_8);
		Logger.logStep("Console logs for Test Case: [" + result.getMethod().getMethodName() + "] are saved in [ " + filePath + " ]");
		try {
			for ( LogEntry logEntry : logEntries ) {
				writer.println("Console log found in Test- " + result.getName());
				writer.println("__________________________________________________________");
				if ( logEntry.getMessage().toLowerCase().contains("error") ) {
					writer.println(currentTime + "Error Message in Console: [" + logEntry.getMessage() + "]");
					Logger.logStep(currentTime + "Error Message in Console: [" + logEntry.getMessage() + "]");
				} else if ( logEntry.getMessage().toLowerCase().contains("warning") ) {
					writer.println(currentTime + "Warning Message in Console: [" + logEntry.getMessage() + "]");
					Logger.logStep(currentTime + "Warning Message in Console: [" + logEntry.getMessage() + "]");
				} else {
					writer.println(currentTime + "Information Message in Console: [" + logEntry.getMessage() + "]");
					Logger.logStep(currentTime + "Information Message in Console: [" + logEntry.getMessage() + "]");
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			Logger.logMessage("Console logs not found: " + e.getMessage());
		} finally {
			writer.close();
		}
	}

	public static void logConsoleLogs (ChromeDriver chromeDriver, String url) {
		try {
			// Get The DevTools class & Create A Session
			DevTools devTools = chromeDriver.getDevTools();
			devTools.createSession();
			// Enable The Console Logs
			devTools.send(Log.enable());
			// Add A Listener For The Logs
			devTools.addListener(Log.entryAdded(), logEntry -> {
				Logger.logStep("----------");
				Logger.logStep("Source: " + logEntry.getSource());
				Logger.logStep("Timestamp: " + logEntry.getTimestamp());
				Logger.logStep("Level: " + logEntry.getLevel());
				Logger.logStep("Text: " + logEntry.getText());
				Logger.logStep("Broken URL: " + logEntry.getUrl());
			});
			// Load The AUT
			BrowserActions.navigateToUrl(chromeDriver, url);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}


