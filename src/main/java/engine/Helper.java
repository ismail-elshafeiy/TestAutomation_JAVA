package engine;

import engine.tools.Logger;
import org.testng.Reporter;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.testng.Assert.fail;

public class Helper {
	/**
	 * This method is used to get test method name
	 * @return String
	 */
	public static String getTestMethodName () {
		Reporter.getCurrentTestResult();
		return Reporter.getCurrentTestResult().getMethod().getMethodName();
	}
   /**
	* This method is used to check if the test is passed or failed
	* @return boolean
	*/
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

	public static String getAlphaNumericString (int length) {
		byte[] array = new byte[256];
		new Random().nextBytes(array);
		String randomString = new String(array, Charset.forName("UTF-8"));
		StringBuffer r = new StringBuffer();
		for (int k = 0; k < randomString.length(); k++) {
			char ch = randomString.charAt(k);
			if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (length > 0)) {
				r.append(ch);
				length--;
			}
		}
		return r.toString();
	}
}
