package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Random;

import static org.testng.Assert.fail;

public class Helper {

    public static String getCurrentTime(String dateFormat) {
        String currentTime = "";
        try {
            currentTime = new SimpleDateFormat(dateFormat).format(new Date());
        } catch (IllegalArgumentException e) {
            Logger.logStep(e.getMessage());
            fail(e.getMessage());
        }
        return currentTime;
    }

    public static String getCurrentTime() {
        return getCurrentTime("ddMMyyyyHHmmssSSS");
    }

    public int randomNumbers(int min, int max) {
        Random random = new Random();
        int Low = min;
        int High = max;
        int Result = random.nextInt(High - Low) + Low;
        return Result;

    }

    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for ( int i = 0; i < length; i++ ) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String generateUuid() {
        return java.util.UUID.randomUUID().toString();
    }

    public static int getRandomNumberBetweenTwoValues(int lowValue, int highValue) {
        return new Random().nextInt(highValue - lowValue) + lowValue;
    }

    public static String getRandomNumberBetweenTwoValuesAsString(int lowValue, int highValue) {
        return Integer.toString(getRandomNumberBetweenTwoValues(lowValue, highValue));
    }

}
