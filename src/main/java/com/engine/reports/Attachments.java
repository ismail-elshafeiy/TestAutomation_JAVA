package com.engine.reports;

import com.engine.Helper;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.print.PrintOptions;
import org.testng.Reporter;

import java.io.*;
import java.nio.file.Files;

import static com.engine.reports.CustomReporter.logAttachmentAction;
import static com.engine.reports.ExtentReport.attachCodeBlockToExtentReport;
import static com.engine.reports.ExtentReport.attachImageToExtentReport;

public class Attachments {
    private static org.apache.logging.log4j.Logger logger4j;
    private static final String currentTime = Helper.getCurrentTime("dd-MM-yyyy HH:mm:ss");

    // *****************************************  Print Method  *****************************************//
    //******************************************************************************************************//

    /**
     * Print the page using the PrintOptions class and the PrintsPage interface of the WebDriver class
     *
     * @param driver    - WebDriver Instance of the Browser
     * @param pageRange - Page Range to be printed
     * @throws
     */
    public static void printPage(WebDriver driver, int pageRange) {
        try {
            CustomReporter.logInfoStep("Printing" + driver.getTitle() + "page....... ");
            PrintsPage printer = ((PrintsPage) driver);
            PrintOptions printOptions = new PrintOptions();
            printOptions.setPageRanges(String.valueOf(pageRange));
            Pdf pdf = printer.print(printOptions);
            Files.write(new File("Pdf/" + driver.getTitle() + currentTime + ".pdf").toPath(), OutputType.BYTES.convertFromBase64Png(pdf.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            CustomReporter.logError("Page not printed: " + e.getMessage());
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

    /**
     * Adds a new attachment using the input parameters provided. The attachment is
     * displayed as a step in the execution report. Used for Screenshots.
     *
     * @param attachmentType    the type of this attachment
     * @param attachmentName    the name of this attachment
     * @param attachmentContent the content of this attachment
     */
    public static void attach(String attachmentType, String attachmentName, String attachmentContent) {
        if (!attachmentContent.trim().isEmpty()) {
            createAttachment(attachmentType, attachmentName, new ByteArrayInputStream(attachmentContent.getBytes()));
        }
    }

    public static void createAttachment(String attachmentType, String attachmentName, InputStream attachmentContent) {
        if (attachmentContent != null) {
            var byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                attachmentContent.transferTo(byteArrayOutputStream);
            } catch (IOException e) {
                var error = "Error while creating Attachment";
                logger4j.info(error, e);
                Reporter.log(error, false);
            }
            String attachmentDescription = attachmentType + " - " + attachmentName;
            attachBasedOnFileType(attachmentType, attachmentName, byteArrayOutputStream, attachmentDescription);
            logAttachmentAction(attachmentType, attachmentName, byteArrayOutputStream);
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    private static void attachBasedOnFileType(String attachmentType, String attachmentName, ByteArrayOutputStream attachmentContent, String attachmentDescription) {
        var content = new ByteArrayInputStream(attachmentContent.toByteArray());
        CustomReporter.logConsole("Start attaching " + attachmentType + " to the report");
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

    public enum AttachmentType {
        SCREENSHOT("Screenshot"),
        RECORDING("Recording"),
        GIF("GIF"),
        CSV("CSV"),
        XML("XML"),
        EXCEL("Excel"),
        JSON("JSON"),
        PROPERTIES("Properties"),
        LINK("Link"),
        ENGINE_LOGS("Engine Logs"),
        PAGE_SNAPSHOT("Page Snapshot"),
        HTML("HTML"),
        TEXT("Text"),
        OTHER("Other");

        private final String value;

        AttachmentType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}


