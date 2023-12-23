package com.engine.evidence;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;

import com.engine.Helper;
import com.engine.reports.CustomReporter;
import com.engine.reports.Attachments;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.*;
import java.nio.file.Path;
import java.util.Base64;

import static com.automation.remarks.video.RecordingUtils.doVideoProcessing;

public class RecordManager {
    private static final Boolean RECORD_VIDEO = true;
    private static final ThreadLocal<IVideoRecorder> recorder = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> videoDriver = new ThreadLocal<>();
    private static boolean isRecordingStarted = false;

    private RecordManager () {
        throw new IllegalStateException("Utility class");
    }

    //TODO: the animated GIF should follow the same path as the video
    public static void startVideoRecording (WebDriver driver) {
        startVideoRecording();
    }

    public static void startVideoRecording () {
        recorder.set(RecorderFactory.getRecorder(VideoRecorder.conf().recorderType()));
        recorder.get().start();

    }

    public static void attachVideoRecording (Path pathToRecording) {
        if ( pathToRecording != null ) {
            String testMethodName = Helper.getTestMethodName();
            try {
                Attachments.attach("Video Recording", testMethodName,
                        new FileInputStream(pathToRecording.toString()));
            } catch ( FileNotFoundException e ) {
                CustomReporter.logErrorMessage(e.getMessage());
            }
        }
    }

    public static void attachVideoRecording () {
        Attachments.attach("Video Recording", Helper.getTestMethodName(), getVideoRecording());
    }

    public static String getVideoRecordingFilePath () {
        try {
            String tempFilePath = "target/tempVideoFile/";
            FileUtils.copyInputStreamToFile(getVideoRecording(), new File(tempFilePath));
            return tempFilePath;
        } catch ( IOException e ) {
            CustomReporter.logErrorMessage(e.getMessage());
            return "";
        }
    }

    public static InputStream getVideoRecording () {
        InputStream inputStream = null;
        String pathToRecording = "";
        String testMethodName = Helper.getTestMethodName();

        if ( Boolean.TRUE.equals(RECORD_VIDEO) && recorder.get() != null ) {
            pathToRecording = doVideoProcessing(Helper.isCurrentTestPassed(), recorder.get().stopAndSave(System.currentTimeMillis() + "_" + testMethodName));
            try {
                inputStream = new FileInputStream(encodeRecording(pathToRecording));
            } catch ( FileNotFoundException e ) {
                CustomReporter.logErrorMessage(e.getMessage());
//                inputStream = new ByteArrayInputStream(new byte[0]);
            }
            recorder.set(null);

        } else if ( Boolean.TRUE.equals(RECORD_VIDEO) && videoDriver.get() != null ) {
            String base64EncodedRecording = "";
            if ( videoDriver.get() instanceof AndroidDriver androidDriver ) {
                base64EncodedRecording = androidDriver.stopRecordingScreen();
            } else if ( videoDriver.get() instanceof IOSDriver iosDriver ) {
                base64EncodedRecording = iosDriver.stopRecordingScreen();
            }
            inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64EncodedRecording));
            videoDriver.set(null);
            isRecordingStarted = false;
        }
        return inputStream;
    }

    private static File encodeRecording (String pathToRecording) {
        File source = new File(pathToRecording);
        File target = new File(pathToRecording.replace("avi", "mp4"));
        try {

            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libvorbis");
            VideoAttributes video = new VideoAttributes();
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp4");
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);
        } catch (EncoderException e) {
            CustomReporter.logErrorMessage(e.getMessage());
        }
        return target;
    }
}
