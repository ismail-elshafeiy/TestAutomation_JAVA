package engine.tools;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import engine.ExtentReport;
import engine.Helper;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Logger {
	public static org.apache.logging.log4j.Logger log = LogManager.getLogger(Logger.class.getName());
	private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(Logger.class);
	private static final boolean debugMode = false;
	static String currentTime = Helper.getCurrentTime("dd-MM-yyyy HH:mm:ss.SSS a");
	static PrintWriter writer;
	static LogEntries logEntries;
	static Logs logs;

	/**
	 * Log step that will be added as a step in the execution report
	 *
	 * @param logStep logged by action
	 */
	@Step("{logStep}")
	public static void logStep (String logStep) {
		System.out.println("<" + currentTime + "> " + logStep);
		ExtentReport.info(logStep);
	}

	public static void logMessage (String logMessage) {
		System.out.println("<" + currentTime + "> " + logMessage);
		ExtentReport.info(logMessage);
	}

	public static void logConsoleLogs (WebDriver driver, ITestResult result) throws IOException {
		logs = driver.manage().logs();
		logEntries = logs.get(LogType.BROWSER);
		String filePath = System.getProperty("user.dir") + "/ConsoleLogs/" + result.getMethod().getMethodName() + ".txt";
		writer = new PrintWriter(filePath, StandardCharsets.UTF_8);
		Logger.logStep("Console logs for Test Case: [" + result.getMethod().getMethodName() + "] are saved in [ " + filePath + " ]");
		try {
			for (LogEntry logEntry : logEntries) {
				writer.println("Console log found in Test- " + result.getName());
				writer.println("__________________________________________________________");
				if (logEntry.getMessage().toLowerCase().contains("error")) {
					writer.println("Error Message in Console: [" + logEntry.getMessage() + "]");
					Logger.logStep("Error Message in Console: [" + logEntry.getMessage() + "]");
				} else if (logEntry.getMessage().toLowerCase().contains("warning")) {
					writer.println("Warning Message in Console: [" + logEntry.getMessage() + "]");
					Logger.logStep("Warning Message in Console: [" + logEntry.getMessage() + "]");
				} else {
					writer.println("Information Message in Console: [" + logEntry.getMessage() + "]");
					Logger.logStep("Information Message in Console: [" + logEntry.getMessage() + "]");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logger.logMessage("Console logs not found: " + e.getMessage());
		} finally {
			writer.close();
		}
	}


	@Attachment(value = "API Request - {type}", type = "text/json")
	public static byte[] attachApiRequestToAllureReport (String type, byte[] b) {
		return attachTextJson(b);
	}

	@Attachment(value = "API Response", type = "text/json")
	public static byte[] attachApiResponseToAllureReport (byte[] b) {
		return attachTextJson(b);
	}

	public static Media attachScreenshotToExtentReport (WebDriver driver) {
		return MediaEntityBuilder
				.createScreenCaptureFromBase64String(((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64), "Full Page Screenshot").build();
	}

	@Attachment(value = "Full Page Screenshot", type = "image/png")
	public static byte[] attachScreenshotToAllureReport (WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	private static synchronized void attachBasedOnFileType (String attachmentType, ByteArrayOutputStream
			attachmentContent, String attachmentDescription) {
		if (attachmentType.toLowerCase().contains("screenshot")) {
			Allure.addAttachment(attachmentDescription, "image/png", new ByteArrayInputStream(attachmentContent.toByteArray()), ".png");
//            attachImageToExtentReport("image/png", new ByteArrayInputStream(attachmentContent.toByteArray()));
		} else if (attachmentType.toLowerCase().contains("recording")) {
			Allure.addAttachment(attachmentDescription, "video/mp4", new ByteArrayInputStream(attachmentContent.toByteArray()), ".mp4");
		} else {
			Allure.addAttachment(attachmentDescription, new ByteArrayInputStream(attachmentContent.toByteArray()));
		}
	}


	//      @Attachment(type = "text/json")
	public static byte[] attachTextJson (byte[] b) {
		try {
			return b;
		} catch (Exception e) {
			return null;
		}
	}


	public static String getTestMethodName () {
		Reporter.getCurrentTestResult();
		return Reporter.getCurrentTestResult().getMethod().getMethodName();
	}

	public static Boolean isCurrentTestPassed () {
		Reporter.getCurrentTestResult();
		return Reporter.getCurrentTestResult().isSuccess();
	}

	public static void attach (String attachmentType, String attachmentName, InputStream attachmentContent) {
		createAttachment(attachmentType, attachmentName, attachmentContent);
	}

	private static void createAttachment (String attachmentType, String attachmentName, InputStream
			attachmentContent) {
		var baos = new ByteArrayOutputStream();
		try {
			attachmentContent.transferTo(baos);
		} catch (IOException e) {
			var error = "Error while creating Attachment";
			slf4jLogger.info(error, e);
			Reporter.log(error, false);
		}
		String attachmentDescription = attachmentType + " - ";
//        String attachmentDescription = "Attachment: " + attachmentType + " - " + attachmentName;
		attachBasedOnFileType(attachmentType, baos, attachmentDescription);
		logAttachmentAction(attachmentType, attachmentName, baos);
	}

	private static synchronized void logAttachmentAction (String attachmentType, String
			attachmentName, ByteArrayOutputStream attachmentContent) {
		logStep("Successfully created attachment \"" + attachmentType + " - " + attachmentName + "\"");
		String timestamp = Helper.getCurrentTime();
		String theString;
		var br = new BufferedReader(
				new InputStreamReader(new ByteArrayInputStream(attachmentContent.toByteArray()), StandardCharsets.UTF_8));
		theString = br.lines().collect(Collectors.joining(System.lineSeparator()));
	}
}
