package engine.evidence;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import engine.Helper;
import engine.listeners.Logger;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.print.PrintOptions;
import org.testng.Reporter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class Attachments {
	private static final String currentTime = Helper.getCurrentTime("dd-MM-yyyy HH:mm:ss");
	// *****************************************  Take Screen Shot Methods  *****************************************//
	//**************************************************************************************************************//


	// *****************************************  Attach Methods  *****************************************//
	//******************************************************************************************************//

	/**
	 * Attach the Screenshot to the Extent Report
	 *
	 * @param driver - WebDriver Instance of the Browser
	 *
	 * @return Media - Media Entity Builder Instance of the Screenshot
	 */
	public static Media attachScreenshotToExtentReport (WebDriver driver) {
		return MediaEntityBuilder
				.createScreenCaptureFromBase64String(((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.BASE64), Helper.getTestMethodName() + currentTime + "_Screenshot").build();
	}

	public static Media attachFullPageScreenShotToExtentReport (FirefoxDriver driver) {
		return MediaEntityBuilder
				.createScreenCaptureFromBase64String(String.valueOf((driver)
						.getFullPageScreenshotAs(OutputType.FILE)), Helper.getTestMethodName() + currentTime + "_fullPage").build();
	}

	/**
	 * Attach the Screenshot to the allure report
	 *
	 * @param driver - WebDriver Instance of the Browser
	 *
	 * @return byte[] - Byte Array of the Screenshot
	 */
	@Attachment (value = "Screenshot", type = "image/png")
	public static byte[] attachScreenshotToAllureReport (WebDriver driver) {
		return ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.BYTES);
	}
//	@Attachment (value = "Full Page Screenshot", type = "image/png")
//	public static byte[] attachScreenshotToAllureReport (WebDriver driver) {
//		return ((TakesScreenshot) driver)
//				.getScreenshotAs(OutputType.BYTES);
//	}

	/**
	 * Attach the api request to the allure report
	 *
	 * @param type - Type
	 * @param b    - Byte Array of the API Request
	 *
	 * @return byte[] - Byte Array of the API Request
	 */
	@Attachment (value = "API Request - {type}", type = "text/json")
	public static byte[] attachApiRequestToAllureReport (String type, byte[] b) {
		return attachTextJson(b);
	}

	@Attachment (value = "API Response", type = "text/json")
	public static byte[] attachApiResponseToAllureReport (byte[] b) {
		return attachTextJson(b);
	}

	@Attachment (value = "Log Console", type = "text/json")
	public static byte[] attachLogConsoleToAllureReport (byte[] b) {
		return attachTextJson(b);
	}

	// *****************************************  Print Method  *****************************************//
	//******************************************************************************************************//

	/**
	 * Print the page using the PrintOptions class and the PrintsPage interface of the WebDriver class
	 *
	 * @param driver    - WebDriver Instance of the Browser
	 * @param pageRange - Page Range to be printed
	 *
	 * @throws
	 */
	public static void printPage (WebDriver driver, int pageRange) throws IOException {
		try {
			Logger.logStep("Printing" + driver.getTitle() + "page....... ");
			PrintsPage printer = ((PrintsPage) driver);
			PrintOptions printOptions = new PrintOptions();
			printOptions.setPageRanges(String.valueOf(pageRange));
			Pdf pdf = printer.print(printOptions);
			Files.write(new File("Pdf/" + driver.getTitle() + currentTime + ".pdf").toPath(), OutputType.BYTES.convertFromBase64Png(pdf.getContent()));
		} catch ( Exception e ) {
			e.printStackTrace();
			Logger.logMessage("Page not printed: " + e.getMessage());
		}
	}

	private static synchronized void attachBasedOnFileType (String attachmentType, ByteArrayOutputStream
			attachmentContent, String attachmentDescription) {
		if ( attachmentType.toLowerCase().contains("screenshot") ) {
			Allure.addAttachment(attachmentDescription, "image/png", new ByteArrayInputStream(attachmentContent.toByteArray()), ".png");
//            attachImageToExtentReport("image/png", new ByteArrayInputStream(attachmentContent.toByteArray()));
		} else if ( attachmentType.toLowerCase().contains("recording") ) {
			Allure.addAttachment(attachmentDescription, "video/mp4", new ByteArrayInputStream(attachmentContent.toByteArray()), ".mp4");
		} else {
			Allure.addAttachment(attachmentDescription, new ByteArrayInputStream(attachmentContent.toByteArray()));
		}
	}


	//      @Attachment(type = "text/json")
	public static byte[] attachTextJson (byte[] b) {
		try {
			return b;
		} catch ( Exception e ) {
			return null;
		}
	}

	public static void attach (String attachmentType, String attachmentName, InputStream attachmentContent) {
		createAttachment(attachmentType, attachmentName, attachmentContent);
	}

	private static void createAttachment (String attachmentType, String attachmentName, InputStream attachmentContent) {
		if ( attachmentContent != null && Boolean.FALSE.equals(Boolean.parseBoolean(System.getProperty("disableLogging"))) ) {
			var baos = new ByteArrayOutputStream();
			try {
				attachmentContent.transferTo(baos);
			} catch ( IOException e ) {
				var error = "Error while creating Attachment";
				Logger.logMessage(error + e.getMessage());
				Reporter.log(error, false);
			}
			String attachmentDescription = attachmentType + " - " + attachmentName;
			logAttachmentAction(attachmentType, attachmentName, baos);
		}
	}

	private static synchronized void logAttachmentAction (String attachmentType, String
			attachmentName, ByteArrayOutputStream attachmentContent) {
		Logger.logStep("Successfully created attachment \"" + attachmentType + " - " + attachmentName + "\"");
		String timestamp = Helper.getCurrentTime();
		String theString;
		var br = new BufferedReader(
				new InputStreamReader(new ByteArrayInputStream(attachmentContent.toByteArray()), StandardCharsets.UTF_8));
		theString = br.lines().collect(Collectors.joining(System.lineSeparator()));
	}
}


