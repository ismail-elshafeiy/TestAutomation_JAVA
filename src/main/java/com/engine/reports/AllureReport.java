package com.engine.reports;

import com.engine.constants.FrameworkConstants;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import io.qameta.allure.Allure;
import org.openqa.selenium.remote.Browser;
import com.github.automatedowl.tools.AllureEnvironmentWriter;

import java.io.File;
import java.io.IOException;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class AllureReport {
//    public static void addAttachmentVideoAVI() {
//   //     if (FrameworkConstants.VIDEO_RECORD.toLowerCase().trim().equals(FrameworkConstants.YES)) {
//            try {
//                //Get file Last Modified in folder
//                File video = FileHelpers.getFileLastModified("ExportData/Videos");
//                if (video != null) {
//                    Allure.addAttachment("Failed test Video record AVI", "video/avi", Files.asByteSource(video).openStream(), ".avi");
//                } else {
//                    CustomReporter.logWarn("Video record not found.");
//                    CustomReporter.logWarn("Can not attachment Video in Allure report");
//                }
//
//            } catch (IOException e) {
//                CustomReporter.logMessage("Can not attachment Video in Allure report");
//                e.printStackTrace();
//            }
//        }
// //   }

    private AllureReport() {
    }

    public static void setAllureEnvironmentInformation() {
        AllureEnvironmentWriter.allureEnvironmentWriter(
                ImmutableMap.<String, String>builder().
                        put("Test URL", "").
                        put("Target Execution", "").
                        put("Global Timeout", "").
                        put("Page Load Timeout", "").
                        build());
        CustomReporter.logConsole("Allure Reports is installed.");

    }
//    public static void addAttachmentVideoAVI() {
//        if (FrameworkConstants.VIDEO_RECORD.toLowerCase().trim().equals(FrameworkConstants.YES)) {
//            try {
//                //Get file Last Modified in folder
//                File video = FileHelpers.getFileLastModified("ExportData/Videos");
//                if (video != null) {
//                    Allure.addAttachment("Failed test Video record AVI", "video/avi", Files.asByteSource(video).openStream(), ".avi");
//                } else {
//                    LogUtils.warn("Video record not found.");
//                    LogUtils.warn("Can not attachment Video in Allure report");
//                }
//
//            } catch (IOException e) {
//                LogUtils.error("Can not attachment Video in Allure report");
//                e.printStackTrace();
//            }
//        }
//    }
}
