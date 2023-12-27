package com.engine.evidence;

import com.engine.Helper;
import com.engine.actions.FileActions;
import com.engine.reports.CustomReporter;
import com.engine.reports.Attachments;
import lombok.AccessLevel;
import lombok.Getter;
import org.imgscalr.Scalr;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriverException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotManager {
    private static final int GIF_SIZE = 1280;
    // TODO: parameterize the detailed gif value
    private static final Boolean DETAILED_GIF = true;
    private static final String DETAILED_GIF_REGEX = "(verify.*)|(assert.*)|(click.*)|(tap.*)|(key.*)|(navigate.*)";
    private static String gifRelativePathWithFileName = "";
    private static String testCaseName = "";
    @Getter(AccessLevel.PUBLIC)
    private static final org.openqa.selenium.Dimension TARGET_WINDOW_SIZE = new Dimension(1920, 1080);
    private static ThreadLocal<ImageOutputStream> gifOutputStream = new ThreadLocal<>();
    private static ThreadLocal<AnimatedGifManager> gifWriter = new ThreadLocal<>();
    private static String gifOptions = System.getProperty("config.properties", "createAnimatedGif");
    private static String gifDelay = System.getProperty("config.properties", "animatedGif_frameDelay");

    public static String attachAnimatedGif() {
        // stop and attach
        if (Boolean.TRUE.equals(gifOptions) && !"".equals(gifRelativePathWithFileName)) {
            try {
                Attachments.attach("Animated Gif", testCaseName, new FileInputStream(gifRelativePathWithFileName));
                if (!gifWriter.equals(new ThreadLocal<>())) {
                    gifWriter.get().close();
                }
                if (!gifOutputStream.equals(new ThreadLocal<>())) {
                    gifOutputStream.get().close();
                }

                gifOutputStream = new ThreadLocal<>();
                gifWriter = new ThreadLocal<>();
                String gifRelativePath = gifRelativePathWithFileName;
                gifRelativePathWithFileName = "";
                return gifRelativePath;
            } catch (FileNotFoundException e) {
                // this happens when the gif fails to start, maybe the browser window was
                // already closed
            } catch (IOException | NullPointerException | IllegalStateException e) {
                CustomReporter.logError(e.getMessage());
                e.printStackTrace();
            }
        }
        return "";
    }

    private static void startAnimatedGif(byte[] screenshot) {
        if (Boolean.TRUE.equals(gifOptions) && screenshot != null) {
            try {
                testCaseName = Helper.getTestMethodName();
                String gifFileName = FileSystems.getDefault().getSeparator() + System.currentTimeMillis() + "_"
                        + testCaseName + ".gif";
                gifRelativePathWithFileName = "allure-result/screenshots/" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + gifFileName;

                // get the width and height of the current window of the browser
                var height = getTARGET_WINDOW_SIZE().getHeight();
                var width = getTARGET_WINDOW_SIZE().getWidth();

                // grab the output image type from the first image in the sequence
                BufferedImage firstImage = ImageIO.read(new ByteArrayInputStream(screenshot));

                //scaling it down
                firstImage = Scalr.resize(firstImage, Scalr.Method.BALANCED, GIF_SIZE);

                // create a new BufferedOutputStream
                FileActions.getInstance().createFile("allure-result/screenshots/" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()), gifFileName);
                gifOutputStream.set(new FileImageOutputStream(new File(gifRelativePathWithFileName)));

                // create a gif sequence with the type of the first image, 500 milliseconds
                // between frames, which loops infinitely
                gifWriter.set(new AnimatedGifManager(gifOutputStream.get(), firstImage.getType(), 500));

                // draw initial blank image to set the size of the GIF...
                BufferedImage initialImage = new BufferedImage(width, height, firstImage.getType());
                Graphics2D initialImageGraphics = initialImage.createGraphics();
                initialImageGraphics.setBackground(Color.WHITE);
                initialImageGraphics.setColor(Color.WHITE);
                initialImageGraphics.clearRect(0, 0, width, height);

                // write out initialImage to the sequence...
                gifWriter.get().writeToSequence(initialImage);
                initialImageGraphics.dispose();

                // write out first image to the sequence...
                //  gifWriter.get().writeToSequence(overlayShaftEngineLogo(toBufferedImage(firstImage)));
            } catch (NullPointerException | NoSuchSessionException e) {
                // this happens in case the start animated Gif is triggered in a none-test
                // method
                // or this happens when the window is already closed
            } catch (IOException | WebDriverException e) {
                CustomReporter.logError(e.getMessage());
            }
        }
    }

    private static void startOrAppendToAnimatedGif(byte[] screenshot) {
        // ensure that animatedGif is started, else force start it
        if (Boolean.TRUE.equals(gifOptions)) {
            if (gifRelativePathWithFileName.isEmpty()) {
                startAnimatedGif(screenshot);
            } else {
                appendToAnimatedGif(screenshot);
            }
        }
    }

    private static void appendToAnimatedGif(byte[] screenshot) {
        try {
            BufferedImage image;
            if (screenshot != null && gifWriter.get() != null) {
                image = ImageIO.read(new ByteArrayInputStream(screenshot));
                //scaling it down
                image = Scalr.resize(image, Scalr.Method.BALANCED, GIF_SIZE);
                // gifWriter.get().writeToSequence(overlayShaftEngineLogo(image));
            }
        } catch (NoSuchSessionException e) {
            // this happens when attempting to append to a non-existing gif, expected
            // solution is to recreate the gif
            // removed the old solution, the new fix is to ignore this exception, this will
            // leave the gif intact and will attach it even after failing to append to it
        } catch (WebDriverException | IOException | IllegalStateException | IllegalArgumentException |
                 NullPointerException e) {
            CustomReporter.logError(e.getMessage());
            e.printStackTrace();
        }
    }

    private ScreenshotManager() {
        throw new IllegalStateException("Utility class");
    }
}

