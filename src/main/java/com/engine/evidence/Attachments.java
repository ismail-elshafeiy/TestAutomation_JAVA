package com.engine.evidence;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Media;
import com.engine.Helper;
import com.engine.listeners.CustomReporter;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.print.PrintOptions;
import org.testng.Reporter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.stream.Collectors;

public class Attachments {
    private static org.apache.logging.log4j.Logger logger4j;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final String currentTime = Helper.getCurrentTime("dd-MM-yyyy HH:mm:ss");
    // *****************************************  Take Screen Shot Methods  *****************************************//
    //**************************************************************************************************************//


    // *****************************************  Attach Methods  *****************************************//
    //******************************************************************************************************//

    /**
     * Attach the Screenshot to the Extent Report
     *
     * @param driver - WebDriver Instance of the Browser
     * @return Media - Media Entity Builder Instance of the Screenshot
     */
    public static Media attachScreenshotToExtentReport(WebDriver driver) {
        return MediaEntityBuilder
                .createScreenCaptureFromBase64String(((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BASE64), Helper.getTestMethodName() + currentTime + "_Screenshot").build();
    }

    public static Media attachFullPageScreenShotToExtentReport(FirefoxDriver driver) {
        return MediaEntityBuilder
                .createScreenCaptureFromBase64String(String.valueOf((driver)
                        .getFullPageScreenshotAs(OutputType.FILE)), Helper.getTestMethodName() + currentTime + "_fullPage").build();
    }

    /**
     * Attach the Screenshot to the allure report
     *
     * @param driver - WebDriver Instance of the Browser
     * @return byte[] - Byte Array of the Screenshot
     */
    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] attachScreenshotToAllureReport(WebDriver driver) {
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
     * @return byte[] - Byte Array of the API Request
     */
    @Attachment(value = "API Request - {type}", type = "text/json")
    public static byte[] attachApiRequestToAllureReport(String type, byte[] b) {
        return attachTextJson(b);
    }

    @Attachment(value = "API Response", type = "text/json")
    public static byte[] attachApiResponseToAllureReport(byte[] b) {
        return attachTextJson(b);
    }

    @Attachment(value = "Log Console", type = "text/json")
    public static byte[] attachLogConsoleToAllureReport(byte[] b) {
        return attachTextJson(b);
    }

    // *****************************************  Print Method  *****************************************//
    //******************************************************************************************************//

    /**
     * Print the page using the PrintOptions class and the PrintsPage interface of the WebDriver class
     *
     * @param driver    - WebDriver Instance of the Browser
     * @param pageRange - Page Range to be printed
     * @throws
     */
    public static void printPage(WebDriver driver, int pageRange) throws IOException {
        try {
            CustomReporter.logStep("Printing" + driver.getTitle() + "page....... ");
            PrintsPage printer = ((PrintsPage) driver);
            PrintOptions printOptions = new PrintOptions();
            printOptions.setPageRanges(String.valueOf(pageRange));
            Pdf pdf = printer.print(printOptions);
            Files.write(new File("Pdf/" + driver.getTitle() + currentTime + ".pdf").toPath(), OutputType.BYTES.convertFromBase64Png(pdf.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            CustomReporter.logMessage("Page not printed: " + e.getMessage());
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    private static void attachBasedOnFileType(String attachmentType, String attachmentName,
                                              ByteArrayOutputStream attachmentContent, String attachmentDescription) {
        var content = new ByteArrayInputStream(attachmentContent.toByteArray());
        if (attachmentType.toLowerCase().contains("screenshot")) {
            Allure.addAttachment(attachmentDescription, "image/png", content, ".png");
            attachImageToExtentReport("image/png", new ByteArrayInputStream(attachmentContent.toByteArray()));
        } else if (attachmentType.toLowerCase().contains("recording")) {
            Allure.addAttachment(attachmentDescription, "video/mp4", content, ".mp4");
        } else if (attachmentType.toLowerCase().contains("gif")) {
            Allure.addAttachment(attachmentDescription, "image/gif", content, ".gif");
            attachImageToExtentReport("image/gif", new ByteArrayInputStream(attachmentContent.toByteArray()));
        } else if (attachmentType.toLowerCase().contains("csv") || attachmentName.toLowerCase().contains("csv")) {
            Allure.addAttachment(attachmentDescription, "text/csv", content, ".csv");
            attachCodeBlockToExtentReport("text/csv", new ByteArrayInputStream(attachmentContent.toByteArray()));
        } else if (attachmentType.toLowerCase().contains("xml") || attachmentName.toLowerCase().contains("xml")) {
            Allure.addAttachment(attachmentDescription, "text/xml", content, ".xml");
            attachCodeBlockToExtentReport("text/xml", new ByteArrayInputStream(attachmentContent.toByteArray()));
        } else if (attachmentType.toLowerCase().contains("excel") || attachmentName.toLowerCase().contains("excel")) {
            Allure.addAttachment(attachmentDescription, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", content, ".xlsx");
        } else if (attachmentType.toLowerCase().contains("json") || attachmentName.toLowerCase().contains("json")) {
            Allure.addAttachment(attachmentDescription, "text/json", content, ".json");
            attachCodeBlockToExtentReport("text/json", new ByteArrayInputStream(attachmentContent.toByteArray()));
        } else if (attachmentType.toLowerCase().contains("properties")) {
            Allure.addAttachment(attachmentDescription, "text/plain", content, ".properties");
        } else if (attachmentType.toLowerCase().contains("link")) {
            Allure.addAttachment(attachmentDescription, "text/uri-list", content, ".uri");
        } else if (attachmentType.toLowerCase().contains("engine logs")) {
            Allure.addAttachment(attachmentDescription, "text/plain", content, ".txt");
        } else if (attachmentType.toLowerCase().contains("page snapshot")) {
            Allure.addAttachment(attachmentDescription, "multipart/related", content, ".mhtml");
        } else if (attachmentType.toLowerCase().contains("html")) {
            Allure.addAttachment(attachmentDescription, "text/html", content, ".html");
        } else {
            Allure.addAttachment(attachmentDescription, content);
        }
    }

    private static void attachImageToExtentReport(String attachmentType, InputStream attachmentContent) {
        if (extentTest.get() != null) {
            try {
                var image = Base64.getEncoder().encodeToString(IOUtils.toByteArray(attachmentContent));
                if (attachmentType.toLowerCase().contains("gif")) {
                    extentTest.get().addScreenCaptureFromBase64String(image);
                } else {
                    extentTest.get().info(MediaEntityBuilder.createScreenCaptureFromBase64String(image).build());
                }
            } catch (IOException e) {
                CustomReporter.logMessage("Failed to attach screenshot to extentReport.");
            }
        }
    }

    private static void attachCodeBlockToExtentReport(String attachmentType, InputStream attachmentContent) {
        if (extentTest.get() != null) {
            try {
                var codeBlock = IOUtils.toString(attachmentContent, StandardCharsets.UTF_8);
                switch (attachmentType) {
                    case "text/json" ->
                            extentTest.get().info(MarkupHelper.createCodeBlock(codeBlock, CodeLanguage.JSON));
                    case "text/xml" -> extentTest.get().info(MarkupHelper.createCodeBlock(codeBlock, CodeLanguage.XML));
                    default -> extentTest.get().info(MarkupHelper.createCodeBlock(codeBlock));
                }
            } catch (IOException e) {
                CustomReporter.logMessage("Failed to attach code block to extentReport.");
            }
        }
    }

    //      @Attachment(type = "text/json")
    public static byte[] attachTextJson(byte[] b) {
        try {
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Adds a new attachment using the input parameters provided. The attachment is
     * displayed as a step in the execution report. Used for Screenshots.
     *
     * @param attachmentType    the type of this attachment
     * @param attachmentName    the name of this attachment
     * @param attachmentContent the content of this attachment
     */
    public static void attach(String attachmentType, String attachmentName, InputStream attachmentContent) {
        createAttachment(attachmentType, attachmentName, attachmentContent);
    }

    private static void createAttachment(String attachmentType, String attachmentName, InputStream attachmentContent) {
        if (attachmentContent != null) {
            var byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                attachmentContent.transferTo(byteArrayOutputStream);
            } catch (IOException e) {
                var error = "Error while creating Attachment";
//				if (logger == null) {
//					initializeLogger();
//				}
                logger4j.info(error, e);
                Reporter.log(error, false);
            }
            String attachmentDescription = attachmentType + " - " + attachmentName;
            attachBasedOnFileType(attachmentType, attachmentName, byteArrayOutputStream, attachmentDescription);
            logAttachmentAction(attachmentType, attachmentName, byteArrayOutputStream);
        }
    }
//	private static void initializeLogger() {
//		Configurator.initialize(null, PropertyFileManager.getCUSTOM_PROPERTIES_FOLDER_PATH() + "/log4j2.properties");
//		logger = LogManager.getLogger(ReportManager.class.getName());
//	}

    private static synchronized void logAttachmentAction(String attachmentType, String
            attachmentName, ByteArrayOutputStream attachmentContent) {
        CustomReporter.logStep("Successfully created attachment \"" + attachmentType + " - " + attachmentName + "\"");
        String timestamp = Helper.getCurrentTime();
        String theString;
        var br = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(attachmentContent.toByteArray()), StandardCharsets.UTF_8));
        theString = br.lines().collect(Collectors.joining(System.lineSeparator()));
    }
}


