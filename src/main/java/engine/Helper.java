package engine;

import engine.tools.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.testng.Assert.fail;

public class Helper {
	public static String getScreenshot (WebDriver driver, String screenshotName) throws IOException {
		String currentTime = getCurrentTime("dd-MM-yyyy HH-mm-ss");
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/src/test/resources/TestsScreenshots/TestFailed/" + screenshotName
				+ currentTime + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static String getDate () {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String todayDate = dateFormat.format(date).toString();
		return todayDate;
	}

	public static String getCurrentTime () {
		return getCurrentTime("ddMMyyyyHHmmssSSS");
	}

	public static String getCurrentTime (String dateFormat) {
		String currentTime = "";
		try {
			currentTime = new SimpleDateFormat(dateFormat).format(new Date());
		} catch (IllegalArgumentException e) {
			Logger.logStep(e.getMessage());
			fail(e.getMessage());
		}
		return currentTime;
	}


	public static int randomNumbers (int min, int max) {
		Random random = new Random();
		int Low = min;
		int High = max;
		int Result = random.nextInt(High - Low) + Low;
		return Result;
	}

	public static String getRandomString (int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	public static String generateUuid () {
		return java.util.UUID.randomUUID().toString();
	}

	public static int getRandomNumberBetweenTwoValues (int lowValue, int highValue) {
		return new Random().nextInt(highValue - lowValue) + lowValue;
	}

	public static String getRandomNumberBetweenTwoValuesAsString (int lowValue, int highValue) {
		return Integer.toString(getRandomNumberBetweenTwoValues(lowValue, highValue));
	}

}
