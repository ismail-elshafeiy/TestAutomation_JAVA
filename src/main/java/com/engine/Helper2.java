package com.engine;

import engine.reports.CustomReporter;
import org.testng.Reporter;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.fail;

public class Helper2 {

    public static String getTestMethodName() {
        Reporter.getCurrentTestResult();
        return Reporter.getCurrentTestResult().getMethod().getMethodName();
    }

    public static Boolean isCurrentTestPassed() {
        return Reporter.getCurrentTestResult().isSuccess();
    }


    public static String getCurrentTime() {
        return getCurrentTime("HH:mm:ss dd-MM-yyyy");
    }

    public static String getCurrentDay() {
        return getCurrentTime("dd-MM-yyyy");
    }

    public static String getCurrentTime(String dateFormat) {
        String currentTime = "";
        try {
            currentTime = new SimpleDateFormat(dateFormat).format(new Date());
        } catch (IllegalArgumentException e) {
            CustomReporter.getInstance().logInfoStep(e.getMessage());
            fail(e.getMessage());
        }
        return currentTime;
    }

    public static int getRandomNumbers(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static int getRandomNumberBetweenTwoValues(int lowValue, int highValue) {
        return new Random().nextInt(highValue - lowValue) + lowValue;
    }

    public static String getRandomNumberBetweenTwoValuesAsString(int lowValue, int highValue) {
        return Integer.toString(getRandomNumberBetweenTwoValues(lowValue, highValue));
    }

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

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }


    public static String getAlphaNumericString(int length) {
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

    //****** Replace methods ******//
    public static String replace(String text, String textNeedsToBeRemoved) {
        try {
            String textAfterReplaced = text.replace(textNeedsToBeRemoved, "");
            CustomReporter.getInstance().logConsole("Replace: [ " + textNeedsToBeRemoved + " ] from [ " + text + "] to Be  [ " + textAfterReplaced + " ]");
            return textAfterReplaced;
        } catch (Exception e) {
            CustomReporter.getInstance().logError("Error while replacing text [ " + text + " ] : " + e.getMessage());
            return text;
        }
    }

    public static String replace(String fullText, String textTarget, String textNeedsToBeReplace) {
        try {
            String textAfterReplaced = fullText.replace(textTarget, textNeedsToBeReplace);
            CustomReporter.getInstance().logConsole("Replace: [ " + textTarget + " ] From [" + fullText + "] to be [" + textAfterReplaced + "]");
            return textAfterReplaced;
        } catch (Exception e) {
            CustomReporter.getInstance().logError("Error while replacing text [ " + fullText + " ] : " + e.getMessage());
            return fullText;
        }
    }

    //****** ReplaceAll methods ******//
    public static String replaceAll(String text, String textNeedsToBeRemoved, Boolean isPrint) {
        try {
            String textAfterReplaced = text.replaceAll(textNeedsToBeRemoved, "");
            if (isPrint) {
                CustomReporter.getInstance().logConsole("Replace: [ " + textNeedsToBeRemoved + " ] from [ " + text + "] to Be  [ " + textAfterReplaced + " ]");
            }
            return textAfterReplaced;
        } catch (Exception e) {
            CustomReporter.getInstance().logError("Error while replacing text [ " + text + " ] : " + e.getMessage());
            return text;
        }
    }

    public static String replaceAll(String text, String textNeedsToBeRemoved) {
        return replaceAll(text, textNeedsToBeRemoved, true);
    }


    public static String replaceAll(String fullText, String textTarget, String textNeedsToBeReplace) {
        try {
            String textAfterReplaced = fullText.replaceAll(textTarget, textNeedsToBeReplace);
            CustomReporter.getInstance().logConsole("Replace: [ " + textTarget + " ] from [ " + fullText + "] to Be [ " + textAfterReplaced + " ]");
            return textAfterReplaced.trim();
        } catch (Exception e) {
            CustomReporter.getInstance().logError("Error while replacing text [ " + fullText + " ] : " + e.getMessage());
            return fullText;
        }
    }

    /**
     * This method replaces all special characters and spaces in the given text.
     * It uses a regex to match digits, special characters, and whitespace
     * regex is "[0-9+*.^:;',!?\\n\\t \\u00a0\\s+]" which includes digits, special characters and spaces.
     * @param text the text to be processed
     * @param print if true, logs the replacement details to the console
     * @return text after removing all special characters and spaces
     */

    public static String removeRegex(String text, Boolean print) {
        try {
            if (text != null) {
                String regex = "[0-9+*.^:;',!?\\n\\t \\u00a0\\s+]";
                String textAfterReplaced = text.replaceAll(regex, "").replaceAll("\\s+", "").trim();
                if (print) {
                    CustomReporter.getInstance().logConsole("Replace All: [ " + regex + " ] from [ " + text + "] to Be [ " + textAfterReplaced + " ]");
                }
                return textAfterReplaced;
            } else {
                return null;
            }
        } catch (Exception e) {
            CustomReporter.getInstance().logError("Error while replacing text [ " + text + " ] : " + e.getMessage());
            return text;
        }
    }


    public static String removeRegex(String text) {
        return removeRegex(text, false);
    }

    public static String replaceAllSpaces(String text, Boolean print) {
        try {
            String regex = "[+*.^:;',!?\\n\\t \\u00a0\\s+]";
            String textAfterReplaced = text.replaceAll(regex, "");
            if (print) {
                CustomReporter.getInstance().logConsole("Replace All: [ " + regex + " ] from [ " + text + " ] to Be [ " + textAfterReplaced + " ]");
            }
            return textAfterReplaced.trim();
        } catch (Exception e) {
            CustomReporter.getInstance().logError("Error while replacing text [ " + text + " ] : " + e.getMessage());
            return text;
        }
    }

    public static String replaceAllSpaces(String text) {
        return replaceAllSpaces(text, true);
    }


    public static String replaceAllNumbers(String text) {
        try {
            String regex = "[0-9]";
            String textAfterReplaced = text.replaceAll(regex, "");
            CustomReporter.getInstance().logConsole("Replace All: [ " + regex + " ] from [ " + text + "] to Be [ " + textAfterReplaced + " ]");
            return textAfterReplaced.trim();
        } catch (Exception e) {
            CustomReporter.getInstance().logError("Error while replacing text [ " + text + " ] : " + e.getMessage());
            return text;
        }
    }

    public static String extractTextFromLemma(String text) {
        String str;
        str = text.substring(text.indexOf("*"), text.indexOf("*", 11));
        CustomReporter.getInstance().logConsole("Extracted text from lemma is : " + str);
        return str;
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
            for (int i = 0; i < text.toCharArray().length; i++) {
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
    public static String getTextAfterSplit(String text, String regex) {
        if (text.contains(regex)) {
            String tcIdAlm = text.split(regex)[0];
            CustomReporter.getInstance().logConsole("Test Case ID ALM is : [ " + tcIdAlm + " ]");
            return tcIdAlm;
        } else {
            return text;
        }
    }
    private Helper2() {
        throw new IllegalStateException("Utility class");
    }

}
