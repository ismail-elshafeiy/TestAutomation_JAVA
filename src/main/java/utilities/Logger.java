package utilities;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;

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


public class Logger {
    public static org.apache.logging.log4j.Logger log = LogManager.getLogger(Logger.class.getName());
    private static final org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(Logger.class);
    static Logs logs;
    static LogEntries logEntries;
    static PrintWriter writer;

    @Step( "{message}" )
    public static void logStep(String message) {
//	String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS a").format(new Date());
        System.out.println("<" + Helper.getCurrentTime("dd-MM-yyyy HH:mm:ss.SSS a") + "> " + message);
        ExtentReport.info(message);
    }

    public static void logMessage(String message) {
//	String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS a").format(new Date());
        System.out.println("<" + Helper.getCurrentTime("dd-MM-yyyy HH:mm:ss.SSS a") + "> " + message);
        ExtentReport.info(message);
    }
    private static void createAttachment(String attachmentType, String attachmentName, InputStream attachmentContent) {
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
//        logAttachmentAction(attachmentType, baos);
    }

    public static void attach(String attachmentType, String attachmentName, InputStream attachmentContent) {
        createAttachment(attachmentType, attachmentName, attachmentContent);
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
        String currentTime = Helper.getCurrentTime("dd-MM-yyyy HH-mm-ss");
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/src/test/resources/TestsScreenshots/TestFailed/" + screenshotName
                + currentTime + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    @Attachment( value = "Full Page Screenshot", type = "image/png" )
    public static byte[] attachScreenshotToAllureReport(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static Media attachScreenshotToExtentReport(WebDriver driver) {
        return MediaEntityBuilder.createScreenCaptureFromBase64String(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64), "Full Page Screenshot").build();
    }

    private static synchronized void attachBasedOnFileType(String attachmentType, ByteArrayOutputStream attachmentContent, String attachmentDescription) {
        if ( attachmentType.toLowerCase().contains("screenshot") ) {
            Allure.addAttachment(attachmentDescription, "image/png", new ByteArrayInputStream(attachmentContent.toByteArray()), ".png");
//            attachImageToExtentReport("image/png", new ByteArrayInputStream(attachmentContent.toByteArray()));
        } else if ( attachmentType.toLowerCase().contains("recording") ) {
            Allure.addAttachment(attachmentDescription, "video/mp4", new ByteArrayInputStream(attachmentContent.toByteArray()), ".mp4");
        } else {
            Allure.addAttachment(attachmentDescription, new ByteArrayInputStream(attachmentContent.toByteArray()));
        }
    }

    @Attachment( value = "API Request - {type}", type = "text/json" )
    public static byte[] attachApiRequestToAllureReport(String type, byte[] b) {
        return attachTextJson(b);
    }

    @Attachment( value = "API Response", type = "text/json" )
    public static byte[] attachApiResponseToAllureReport(byte[] b) {
        return attachTextJson(b);
    }

//      @Attachment(type = "text/json")
    public static byte[] attachTextJson(byte[] b) {
        try {
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    public static void logConsoleLogs(WebDriver driver1, ITestResult result) throws Throwable {

        logs = driver1.manage().logs();
        logEntries = logs.get(LogType.BROWSER);
        writer = new PrintWriter("Snapshots/" + result.getName() + ".txt", StandardCharsets.UTF_8);

        for ( LogEntry logEntry : logEntries ) {
            writer.println("Console log found in Test- " + result.getName());

            writer.println("__________________________________________________________");

            if ( logEntry.getMessage().toLowerCase().contains("error") ) {
                writer.println("Error Message in Console:" + logEntry.getMessage());

            } else if ( logEntry.getMessage().toLowerCase().contains("warning") ) {
                writer.println("Warning Message in Console:" + logEntry.getMessage());

            } else {
                writer.println("Information Message in Console:" + logEntry.getMessage());
            }
        }
        writer.close();
    }

    public static String getTestMethodName() {
        Reporter.getCurrentTestResult();
        return Reporter.getCurrentTestResult().getMethod().getMethodName();
    }

    public static Boolean isCurrentTestPassed() {
        Reporter.getCurrentTestResult();
        return Reporter.getCurrentTestResult().isSuccess();
    }
}
