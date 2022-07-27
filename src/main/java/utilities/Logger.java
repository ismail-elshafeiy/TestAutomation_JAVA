package utilities;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
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
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Logger {
    public static org.apache.logging.log4j.Logger log = LogManager.getLogger(Logger.class.getName());
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

    @Attachment( value = "Full Page Screenshot", type = "image/png" )
    public static byte[] attachScreenshotToAllureReport(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static Media attachScreenshotToExtentReport(WebDriver driver) {
        return MediaEntityBuilder.createScreenCaptureFromBase64String(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64), "Full Page Screenshot").build();
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/src/test/resources/TestsScreenshots/" + screenshotName
                + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);

        return destination;
    }

    @Attachment( value = "API Request - {type}", type = "text/json" )
    public static byte[] attachApiRequestToAllureReport(String type, byte[] b) {
        return attachTextJson(b);
    }

    @Attachment( value = "API Response", type = "text/json" )
    public static byte[] attachApiResponseToAllureReport(byte[] b) {
        return attachTextJson(b);
    }

    //  @Attachment(type = "text/json")
    public static byte[] attachTextJson(byte[] b) {
        try {
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    public static void getConsoleLogs(WebDriver driver1,ITestResult result) throws Throwable {

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
}
