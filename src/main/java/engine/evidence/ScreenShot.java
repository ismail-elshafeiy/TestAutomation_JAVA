package engine.evidence;

import engine.FileActions;
import engine.Helper;
import engine.listeners.Logger;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;

public class ScreenShot {
	private static String gifRelativePathWithFileName = "";
	private static final Boolean CREATE_GIF = Boolean.valueOf(System.getProperty("createAnimatedGif").trim());
	private static final int GIF_FRAME_DELAY = Integer.parseInt(System.getProperty("animatedGif_frameDelay").trim());
	private static ThreadLocal<ImageOutputStream> gifOutputStream = new ThreadLocal<>();
	private static ThreadLocal<AnimatedGifManager> gifWriter = new ThreadLocal<>();

	/**
	 * Take the screen shot and save it in the ScreenShot folder
	 *
	 * @param driver - WebDriver instance
	 */
	public static void takeScreenShotToFile (WebDriver driver) {
		try {
			Logger.logStep("Screenshot taken for Test Case ..... [" + Helper.getTestMethodName() + "]");
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File("./ScreenShot/" + Helper.getTestMethodName() + ".png"));
		} catch ( Exception e ) {
			e.printStackTrace();
			Logger.logMessage("Screenshot not taken: " + e.getMessage());
		}
	}

	/**
	 * Take the screen shot and save it in the ScreenShot folder
	 * Checks if the Test Result is Fail
	 * If yes, then take the screen shot and save it in the ScreenShot folder
	 *
	 * @param driver - WebDriver instance
	 * @param result - Test Result
	 */
	public static void takeScreenShotToFile (WebDriver driver, ITestResult result) {
		try {
			if ( ITestResult.FAILURE == result.getStatus() ) {
				Logger.logStep("Screenshot taken for Test Case Failed ..... [" + result.getName() + "]");
				takeScreenShotToFile(driver);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			Logger.logMessage("Screenshot not taken: " + e.getMessage());
		}
	}

	/**
	 * Take Screenshot of the Element using the WebDriver class and the getScreenshotAs method
	 * Save the Screenshot in the ScreenShot folder with the name of the Element Locator Name + .png extension
	 *
	 * @param driver  - WebDriver Instance of the Browser
	 * @param locator - Locator of the Element
	 */
	public static void takeElementScreenShot (WebDriver driver, By locator) {
		String locatorName = driver.findElement(locator).getText();
		try {
			Logger.logStep("Screenshot taken for Element ..... [" + locatorName + "]");
			File srcFile = driver.findElement(locator).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File("./ScreenShot/" + locatorName + ".png"));
		} catch ( Exception e ) {
			e.printStackTrace();
			Logger.logMessage("Element Screenshot not taken: " + e.getMessage());
		}
	}

	/**
	 * Take Full Page Screenshot using the FirefoxDriver class and the getFullPageScreenshotAs method
	 * Save the Screenshot in the ScreenShot folder with the name of the Image Name + _FullPage.png extension
	 *
	 * @param driver    - WebDriver Instance of the Browser
	 * @param imageName - Name of the Image to be saved in the ScreenShot folder
	 */
	public static void takeFullPageScreenshot (WebDriver driver, String imageName) {
		try {
			File source = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("./ScreenShot/" + imageName + "_FullPage.png"));
		} catch ( Exception e ) {
			e.printStackTrace();
			Logger.logMessage("Full Page Screenshot not taken: " + e.getMessage());
		}
	}

}
