package com.engine.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.engine.Helper;
import com.engine.constants.FrameworkConstants;
import com.engine.utilities.IconUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class ExtentReport {
    private static ExtentTest extentTest;
    private static ExtentReports extentReports;

    public static void initializeExtentReport() {
        extentReports = new ExtentReports();
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("ExtentReport/ExtentReports.html").viewConfigurer().viewOrder().as(new ViewName[]{ViewName.DASHBOARD, ViewName.TEST, ViewName.EXCEPTION, ViewName.CATEGORY, ViewName.AUTHOR, ViewName.DEVICE}).apply();
        extentReports.attachReporter(extentSparkReporter);
        extentSparkReporter.config().setTheme(Theme.STANDARD);
        extentSparkReporter.config().setEncoding("utf-8");
        extentSparkReporter.config().thumbnailForBase64(true);
        extentSparkReporter.config().setDocumentTitle("Extent Report");
        extentSparkReporter.config().setReportName("Test Automation-Extent Report");
        extentReports.setSystemInfo("Framework Name", "Test Automation");
        extentReports.setSystemInfo("Author", "Ismail elshafeiy");
        extentReports.addTestRunnerOutput("Test Runner Output");
        extentReports.setSystemInfo("Framework version", "1.1.1");
        extentReports.setSystemInfo("Operating System", IconUtils.getOSIcon() + " " + System.getProperty("os.name"));
        extentReports.setSystemInfo("Browser", IconUtils.getBrowserIcon() + " " + FrameworkConstants.BROWSER_TYPE);
        extentReports.setSystemInfo("Java", "v " + System.getProperty("java.version") + " - " + System.getProperty("java.home"));
        extentReports.setSystemInfo("Java vendor", System.getProperty("java.vendor") + " - " + System.getProperty("java.vendor.url"));
        extentReports.setSystemInfo("User name", System.getProperty("user.name"));
        extentReports.setSystemInfo("User Home", System.getProperty("user.home"));
        extentReports.setSystemInfo("User dir", System.getProperty("user.dir"));
        extentReports.setSystemInfo("User Time Zone", System.getProperty("user.timezone"));
        extentReports.setSystemInfo("User Country", System.getProperty("user.country"));
        extentReports.setSystemInfo("User Language", System.getProperty("user.language"));
    }

    public static void addTestRunnerOutput(String data) {
        extentReports.addTestRunnerOutput(data);
    }

    public static void addTestRunnerOutput(List<String> data) {
        extentReports.addTestRunnerOutput(data);
    }

    public static void addSystemInfo(String key, String value) {
        extentReports.setSystemInfo(key, value);
    }

    //****************************************************      test methods          ******************************************************************//
    public static void createTest(String testCaseName) {
        extentTest = extentReports.createTest(testCaseName);
    }

    public static void createTestAndDescription(String testCaseName, String description) {
        extentTest = extentReports.createTest(testCaseName, description);
    }

    public static void createTest(String testCaseName, String author) {
        extentTest = extentReports.createTest(testCaseName).assignAuthor(author);
    }

    public static void createTest(String testCaseName, String author, String category) {
        extentTest = extentReports.createTest(testCaseName).assignAuthor(author).assignCategory(category);
    }

    public static void createTest(String testCaseName, String author, String category, String device) {
        extentTest = extentReports.createTest(testCaseName).assignAuthor(author).assignCategory(category).assignDevice(device);
    }

    public static void createTestNode(String testCaseName, String nodeName) {
        extentTest = extentReports.createTest(testCaseName).createNode(nodeName);
    }

    public static void createTestNode(String testCaseName, String nodeName, String nodeDescription) {
        extentTest = extentReports.createTest(testCaseName).createNode(nodeName, nodeDescription);
    }

    public static void removeTest(String testCaseName) {
        extentReports.removeTest(testCaseName);
    }


    public static void flushReports() {
        extentReports.flush();
    }

    //************* info methods *************//
    public static void info(String message) {
        if (extentTest != null) {
            extentTest.info(message);
        }
    }

    public static void info(Throwable t) {
        extentTest.info(t);
    }

    public static void info(Media m) {
        extentTest.info(m);
    }

    public static void info(Markup m) {
        extentTest.info(m);
    }

    public static void info(String message, Media media) {
        extentTest.info(message, media);
    }

    public static void info(Throwable t, Media media) {
        extentTest.info(t, media);
    }

    //************* pass methods *************//
    public static void pass(String message) {
        extentTest.pass(message);
    }

    public static void pass(Throwable t) {
        extentTest.pass(t);
    }

    public static void pass(Media media) {
        extentTest.pass(media);
    }

    public static void pass(Markup m) {
        extentTest.pass(m);
    }

    public static void pass(String message, Media media) {
        extentTest.pass(message, media);
    }

    public static void pass(Throwable t, Media media) {
        extentTest.pass(t, media);
    }

    //************* fail methods *************//
    public static void fail(String message) {
        extentTest.fail(message);
    }

    public static void fail(Throwable t) {
        extentTest.fail(t);
    }

    public static void fail(Media media) {
        extentTest.fail(media);
    }

    public static void fail(Markup m) {
        extentTest.fail(m);
    }

    public static void fail(String message, Media media) {
        extentTest.fail(message, media);
    }

    public static void fail(Throwable t, Media media) {
        extentTest.fail(t, media);
    }

    //************* waring methods *************//
    public static void warning(String message) {
        extentTest.warning(message);
    }

    public static void warning(Throwable t) {
        extentTest.warning(t);
    }

    public static void warning(Media media) {
        extentTest.warning(media);
    }

    public static void warning(Markup m) {
        extentTest.warning(m);
    }

    public static void warning(String message, Media media) {
        extentTest.warning(message, media);
    }

    public static void warning(Throwable t, Media media) {
        extentTest.warning(t, media);
    }
    //************* skip methods *************//

    public static void skip(String message) {
        extentTest.skip(message);
    }

    public static void skip(Throwable t) {
        extentTest.skip(t);
    }

    public static void skip(Media media) {
        extentTest.skip(media);
    }

    public static void skip(Markup m) {
        extentTest.skip(m);
    }

    public static void skip(String message, Media media) {
        extentTest.skip(message, media);
    }

    public static void skip(Throwable t, Media media) {
        extentTest.skip(t, media);
    }

    //************* helper ****************/
    public static void logCodeBlock(String codeBlock) {
        extentTest.log(Status.INFO, MarkupHelper.createCodeBlock(codeBlock));
    }

    public static void logTable(String[][] data) {
        extentTest.log(Status.INFO, MarkupHelper.createTable(data));
    }

    public static void logOrderList(Object data) {
        extentTest.log(Status.INFO, MarkupHelper.createOrderedList(data));
    }

    public static void logJsonCodeBlock(Object data) {
        extentTest.log(Status.INFO, MarkupHelper.createJsonCodeBlock(data));
    }

    /**
     * Attach the Screenshot to the Extent Report
     *
     * @param driver - WebDriver Instance of the Browser
     * @return Media - Media Entity Builder Instance of the Screenshot
     */
    public static Media attachScreenshotToExtentReport(WebDriver driver) {
        return MediaEntityBuilder.createScreenCaptureFromBase64String(((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64), Helper.getTestMethodName() + Helper.getCurrentTime() + "_Screenshot").build();
    }

    public static Media attachFullPageScreenShotToExtentReport(FirefoxDriver driver) {
        return MediaEntityBuilder.createScreenCaptureFromBase64String(String.valueOf((driver).getFullPageScreenshotAs(OutputType.FILE)), Helper.getTestMethodName() + Helper.getCurrentTime() + "_fullPage").build();
    }

    public static void attachImageToExtentReport(String attachmentType, InputStream attachmentContent) {
        if (extentTest != null) {
            try {
                var image = Base64.getEncoder().encodeToString(IOUtils.toByteArray(attachmentContent));
                if (attachmentType.toLowerCase().contains("gif")) {
                    extentTest.addScreenCaptureFromBase64String(image);
                } else {
                    extentTest.info(MediaEntityBuilder.createScreenCaptureFromBase64String(image).build());
                }
            } catch (IOException e) {
                CustomReporter.logError("Failed to attach screenshot to extentReport.");
            }
        }
    }

    public static void attachCodeBlockToExtentReport(String attachmentType, InputStream attachmentContent) {
        if (extentTest != null) {
            try {
                var codeBlock = IOUtils.toString(attachmentContent, StandardCharsets.UTF_8);
                switch (attachmentType) {
                    case "text/json" -> extentTest.info(MarkupHelper.createCodeBlock(codeBlock, CodeLanguage.JSON));
                    case "text/xml" -> extentTest.info(MarkupHelper.createCodeBlock(codeBlock, CodeLanguage.XML));
                    default -> extentTest.info(MarkupHelper.createCodeBlock(codeBlock));
                }
            } catch (IOException e) {
                CustomReporter.logError("Failed to attach code block to extentReport.");
            }
        }
    }

}
