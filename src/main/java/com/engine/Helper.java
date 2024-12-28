package com.engine;

import com.engine.actions.FileActions;
import com.engine.reports.Logger;
import org.testng.Reporter;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.fail;

public class Helper {
    /**
     * This method is used to get test method name
     *
     * @return String test method name
     */
    public static String getTestMethodName() {
        Reporter.getCurrentTestResult();
        return Reporter.getCurrentTestResult().getMethod().getMethodName();
    }

    /**
     * This method is used to check if the test is passed or failed
     *
     * @return boolean true if the test is passed and false if the test is failed
     */
    public static Boolean isCurrentTestPassed() {
        Reporter.getCurrentTestResult();
        return Reporter.getCurrentTestResult().isSuccess();
    }


    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String todayDate = dateFormat.format(date);
        return todayDate;
    }

    public static String getCurrentTime() {
        return getCurrentTime("ddMMyyyyHHmmssSSS");
    }

    /**
     * This method is used to get current time in a specific format (ddMMyyyyHHmmssSSS)
     *
     * @param dateFormat format of the date
     * @return String currentTime
     */
    public static String getCurrentTime(String dateFormat) {
        String currentTime = "";
        try {
            currentTime = new SimpleDateFormat(dateFormat).format(new Date());
        } catch (IllegalArgumentException e) {
            Logger.logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
        return currentTime;
    }

    /**
     * This method is used to get random number between two values
     *
     * @param min minimum value
     * @param max maximum value
     * @return int result
     */
    public static int getRandomNumbers(int min, int max) {
        Random random = new Random();
        int Low = min;
        int High = max;
        int Result = random.nextInt(High - Low) + Low;
        return Result;
    }

    /**
     * This method is used to get random string with specific length
     *
     * @param length
     * @return String
     */
    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }


    public static String generateId() {
        int i;
        for (i = 0; i < 100; i++) {
            System.out.println("#" + i);
        }
        return "#" + i;
    }

    /**
     * This method is used to generate UUID
     *
     * @return String
     */
    public static String generateUuid() {
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * This method is used to get random number between two values
     *
     * @param lowValue  int lowValue
     * @param highValue int highValue
     * @return int
     */
    public static int getRandomNumberBetweenTwoValues(int lowValue, int highValue) {
        return new Random().nextInt(highValue - lowValue) + lowValue;
    }

    /**
     * This method is used to get random number between two values as String
     *
     * @param lowValue  int lowValue
     * @param highValue int highValue
     * @return String
     */
    public static String getRandomNumberBetweenTwoValuesAsString(int lowValue, int highValue) {
        return Integer.toString(getRandomNumberBetweenTwoValues(lowValue, highValue));
    }

    /**
     * This method is used to get random string with specific length
     *
     * @param length the length of the string
     * @return String
     */
    public static String getAlphaNumericString(int length) {
        byte[] array = new byte[256];
        new Random().nextBytes(array);
        String randomString = new String(array, StandardCharsets.UTF_8);
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

    /**
     * Takes two parameters, one is the array of special characters that are needed
     * to be replaced, and the text needed to be updated It converts text with
     * %40%23%24%25%26 ..etc (special characters) to return it with
     * %5C%5C%40%5C%5C%23%5C%5C%24%5C%5C%25%5C%5C%26
     *
     * @param specialCharactersArray an array of the special characters that will be
     *                               escaped
     * @param text                   the string that will have its special
     *                               characters escaped
     * @return updated texts with escaped special characters
     */
    public static String replaceRegex(String[] specialCharactersArray, String text) {
        // @#$%&
        // \\@\\#\\$\\%\\&

        String oldChar;
        for (int i = 0; i < (specialCharactersArray.length); i++) {
            oldChar = specialCharactersArray[i];
            specialCharactersArray[i] = ("\\" + specialCharactersArray[i]);
            text = text.replace(oldChar, specialCharactersArray[i]);
        }
        return text;
    }

    public static String removeSpecialCharacters(String text) {
        StringBuilder cleanString = new StringBuilder();
        if (text != null) {
            for (int i = 0; i < text.length(); i++) {
                var character = String.valueOf(text.toCharArray()[i]);
                if (Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE).matcher(character).find()) {
                    cleanString.append("_");
                } else {
                    cleanString.append(character);
                }
            }
        }
        return cleanString.toString();
    }

    /**
     * Returns text after replaces its regular expressions which included in this
     * set []^$.|?*+(){}
     *
     * @param text the string that will have its special characters escaped
     * @return updated text after escaping its regular expressions
     */

    public static String replaceRegex(String text) {
        String[] specialCharactersArray = {"[", "]", "^", "$", ".", "|", "?", "*", "+", "(", ")", "{", "}"};
        return replaceRegex(specialCharactersArray, text);
    }

    public static String convertToSentenceCase(String text) {
        Pattern WORD_FINDER = Pattern.compile("(([A-Z]*[a-z]*)|([A-Z]))");
        Matcher matcher = WORD_FINDER.matcher(text);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        List<String> capitalized = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            String currentWord = words.get(i);
            if (i == 0) {
                capitalized.add(capitalizeFirst(currentWord));
            } else {
                capitalized.add(currentWord.toLowerCase());
            }
        }
        return String.join(" ", capitalized).trim();
    }

    private static String capitalizeFirst(String word) {
        return word.substring(0, 1).toUpperCase()
                + word.substring(1).toLowerCase();
    }

    public static String appendTestDataToRelativePath(String relativePath) {
        if (FileActions.getInstance().doesFileExist(relativePath)) {
            //file path is valid
            return relativePath;
        } else {
            if (relativePath.startsWith("/")) {
                //remove extra slash at the beginning if applicable
                relativePath = relativePath.substring(1);
            }
            var testDataFolderPath = "src/test/resources/TestData";
            if (relativePath.contains(testDataFolderPath)) {
                return relativePath;
            }
            return testDataFolderPath + relativePath;
        }
    }


}
