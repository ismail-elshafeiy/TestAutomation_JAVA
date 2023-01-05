package engine;

import engine.tools.Logger;
import org.testng.Reporter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.testng.Assert.fail;

public class Helper {
	public static String getTestMethodName () {
		Reporter.getCurrentTestResult();
		return Reporter.getCurrentTestResult().getMethod().getMethodName();
	}

	public static Boolean isCurrentTestPassed () {
		Reporter.getCurrentTestResult();
		return Reporter.getCurrentTestResult().isSuccess();
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


	public static int getRandomNumbers (int min, int max) {
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


	public static String generateId () {
		int i;
		for (i = 0; i < 100; i++) {
			System.out.println("#" + i);
		}
		return "#" + i;
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
