package engine.tools;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import engine.ExtentReport;
import engine.Helper;
import engine.broswer.BrowserActions;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v104.log.Log;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.print.PrintOptions;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;


public class Logger {
	public static org.apache.logging.log4j.Logger log = LogManager.getLogger(Logger.class.getName());
	private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(Logger.class);
	private static final boolean debugMode = false;
	static String currentTime = Helper.getCurrentTime("dd-MM-yyyy HH:mm:ss.SSS a");
	static PrintWriter writer;
	static LogEntries logEntries;
	static Logs logs;
	private static WebDriver driver;

	/**
	 * Log step that will be added as a step in the execution report
	 *
	 * @param logStep logged by action
	 */
	@AllureId(value = "1")
	@Step("{logStep}")
	public static void logStep (String logStep) {
		System.out.println("<" + currentTime + "> " +  logStep);
		ExtentReport.info(logStep);
	}

	public static void logMessage (String logMessage) {
		System.out.println("<" + currentTime + "> " + logMessage);
		ExtentReport.info(logMessage);
	}

	@Attachment(value = "log Console")
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

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	// *****************************************  Take Screen Shot Methods  *****************************************//
	//**************************************************************************************************************//
	public static void takeScreenShotToFile (WebDriver driver) {
		try {
			logStep("Screenshot taken for Test Case ..... [" + Helper.getTestMethodName() + "]");
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File("./ScreenShot/" + Helper.getTestMethodName() + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
			Logger.logMessage("Screenshot not taken: " + e.getMessage());
		}
	}

	/**
	 * Checks if the Test Result is Fail
	 */
	public static void takeScreenShotToFile (WebDriver driver, ITestResult result) {
		try {
			if (ITestResult.FAILURE == result.getStatus()) {
				logStep("Screenshot taken for Test Case Failed ..... [" + result.getName() + "]");
				takeScreenShotToFile(driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logger.logMessage("Screenshot not taken: " + e.getMessage());
		}
	}

	public static void takeElementScreenShot (WebDriver driver, By locator) {
		String locatorName = driver.findElement(locator).getText();
		try {
			Logger.logStep("Screenshot taken for Element ..... [" + locatorName + "]");
			File srcFile = driver.findElement(locator).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File("./ScreenShot/" + locatorName + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
			Logger.logMessage("Element Screenshot not taken: " + e.getMessage());
		}
	}

	public static void takeFullPageScreenshot (WebDriver driver, String imageName) throws IOException {
		try {
			File source = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("./ScreenShot/" + imageName + "_FullPage.png"));
		} catch (Exception e) {
			e.printStackTrace();
			Logger.logMessage("Full Page Screenshot not taken: " + e.getMessage());
		}
	}


	// *****************************************  Print Method  *****************************************//
	//******************************************************************************************************//
	public static void printPage (WebDriver driver, int pageRange) throws IOException {
		try {
			PrintsPage printer = ((PrintsPage) driver);
			PrintOptions printOptions = new PrintOptions();
			printOptions.setPageRanges(String.valueOf(pageRange));
			Pdf pdf = printer.print(printOptions);
			logStep("Printing" + driver.getTitle() + "page....... ");
			Files.write(new File("Pdf/" + driver.getTitle() + ".pdf").toPath(), OutputType.BYTES.convertFromBase64Png(pdf.getContent()));
		} catch (Exception e) {
			e.printStackTrace();
			Logger.logMessage("Page not printed: " + e.getMessage());
		}
	}

	// *****************************************  Attach Methods  *****************************************//
	//******************************************************************************************************//
	public static Media attachScreenshotToExtentReport (WebDriver driver) {
		return MediaEntityBuilder
				.createScreenCaptureFromBase64String(((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.BASE64), "Full Page Screenshot").build();
	}

	@Attachment(value = "Full Page Screenshot", type = "image/png")
	public static byte[] attachScreenshotToAllureReport (WebDriver driver) {
		return ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.BYTES);
	}

	@Attachment(value = "API Request - {type}", type = "text/json")
	public static byte[] attachApiRequestToAllureReport (String type, byte[] b) {
		return attachTextJson(b);
	}

	@Attachment(value = "API Response", type = "text/json")
	public static byte[] attachApiResponseToAllureReport (byte[] b) {
		return attachTextJson(b);
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


	// *****************************************  Print Method   *****************************************//
	//****************************************************************************************************//


}
