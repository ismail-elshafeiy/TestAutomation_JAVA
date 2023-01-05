package engine;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import engine.tools.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.*;
import java.util.Base64;

import static com.automation.remarks.video.RecordingUtils.doVideoProcessing;

public class RecordManager {
    // TODO: Refactor Record Video class
    private static final Boolean RECORD_VIDEO = true;
    private static final ThreadLocal<IVideoRecorder> recorder = new ThreadLocal<>();
    private static final ThreadLocal<WebDriver> videoDriver = new ThreadLocal<>();
    private static boolean isRecordingStarted = false;

    private RecordManager() {
        throw new IllegalStateException("Utility class");
    }

    public static synchronized void startVideoRecording() {
        try {
            if ( Boolean.TRUE.equals(RECORD_VIDEO)
                    && RECORD_VIDEO
                    && recorder.get() == null ) {
                recorder.set(RecorderFactory.getRecorder(VideoRecorder.conf().recorderType()));
                recorder.get().start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logMessage("Failed to start video recording with execution type = Local : Stack Trace --> " + e);
        }
    }

    public static synchronized String attachVideoRecording() {
        String pathToRecording = "";
        String testMethodName = Helper.getTestMethodName();
        if ( Boolean.TRUE.equals(RECORD_VIDEO) && recorder.get() != null ) {
            pathToRecording = doVideoProcessing(
                    Helper.isCurrentTestPassed(), recorder.get().stopAndSave(System.currentTimeMillis() + "_" + testMethodName));
            try {
                Logger.attach("Video Recording", testMethodName, new FileInputStream(encodeRecording(pathToRecording)));
                FileUtils.copyFile(new File(pathToRecording), new File("./videoRecords/" + testMethodName + ".mp4"));
            } catch (FileNotFoundException e) {
                Logger.logMessage(String.valueOf(e));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            recorder.set(null);
        } else if ( Boolean.TRUE.equals(RECORD_VIDEO) && videoDriver.get() != null ) {
            String base64EncodedRecording = "";
            Logger.attach("Video Recording", testMethodName,
                    new ByteArrayInputStream(Base64.getDecoder().decode(base64EncodedRecording)));
            videoDriver.set(null);
            isRecordingStarted = false;
        }
        return pathToRecording;
    }

    private static synchronized File encodeRecording(String pathToRecording) {
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
            Logger.logMessage(String.valueOf(e));
        }
        return target;
    }
}
