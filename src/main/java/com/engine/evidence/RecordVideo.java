package com.engine.evidence;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;

import com.engine.Helper;
import com.engine.constants.FrameworkConstants;
import com.engine.reports.Logger;
import com.engine.reports.Attachments;

import org.apache.commons.io.FileUtils;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.*;
import java.nio.file.Path;

import static com.automation.remarks.video.RecordingUtils.doVideoProcessing;
import static com.engine.constants.FrameworkConstants.RECORD_VIDEO;

public class RecordVideo {

    private static final ThreadLocal<IVideoRecorder> recorder = new ThreadLocal<>();

    public static void startVideoRecording() {
        if (Boolean.TRUE.equals(RECORD_VIDEO) && recorder.get() == null && !Boolean.TRUE.equals(FrameworkConstants.HEADLESS_OPTION)) {
            Logger.logInfoStep("Started recording device screen");
            recorder.set(RecorderFactory.getRecorder(VideoRecorder.conf().recorderType()));
            recorder.get().start();
        } else {
            Logger.logConsole("Video recording is disabled");
        }
    }

    public static InputStream getVideoRecording() {
        InputStream inputStream = null;
        String pathToRecording = "";
        String testMethodName = Helper.getTestMethodName();
        if (Boolean.TRUE.equals(RECORD_VIDEO) && recorder.get() != null) {
            pathToRecording = doVideoProcessing(Helper.isCurrentTestPassed(), recorder.get().stopAndSave(System.currentTimeMillis() + "_" + testMethodName));
            try {
                inputStream = new FileInputStream(encodeRecording(pathToRecording));
            } catch (FileNotFoundException e) {
                Logger.logError(e.getMessage());
            }
            recorder.remove();
        }
        return inputStream;
    }

    public static String getVideoRecordingFilePath() {
        try {
            String tempFilePath = "target/tempVideoFile/";
            FileUtils.copyInputStreamToFile(getVideoRecording(), new File(tempFilePath));
            return tempFilePath;
        } catch (IOException e) {
            Logger.logError(e.getMessage());
            return "";
        }
    }

    public static void attachVideoRecording() {
        if (RECORD_VIDEO) {
            Attachments.attach("Video Recording", Helper.getTestMethodName(), getVideoRecording());
        } else {
            Logger.logConsole("There is no video recording to attach");
        }
    }

    public static void attachVideoRecording(Path pathToRecording) {
        if (pathToRecording != null) {
            String testMethodName = Helper.getTestMethodName();
            try {
                Attachments.attach("Video Recording", testMethodName, new FileInputStream(pathToRecording.toString()));
            } catch (FileNotFoundException e) {
                Logger.logError(e.getMessage());
            }
        }
    }

    private static File encodeRecording(String pathToRecording) {
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
            Logger.logError(e.getMessage());
        }
        return target;
    }


    private RecordVideo() {
        throw new IllegalStateException("Utility class");
    }
}
