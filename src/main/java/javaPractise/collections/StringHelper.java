

package com.collections;

import dev.failsafe.internal.util.Lists;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class contains a bunch of static methods that help you manipulate
 * strings.
 */
@UtilityClass
public class StringHelper {

    // A regular expression that matches a lowercase letter followed by an
    // uppercase letter.
    private static final String SNAKE_CASE_REGEX = "([a-z])([A-Z]+)";
    // A regular expression that matches any character that is not a letter or a
    // number.
    private static final String CAMEL_CASE_REGEX = "[^a-zA-Z0-9]";
    // A regular expression that matches a dollar sign, followed by an open
    // curly brace,
    // followed by one or more characters, followed by a close curly brace.
    private static final String INTERPOLATE_REGEX = "\\$\\{(.+?)}";
    // A regular expression that matches any character that is not a number, an
    // underscore, or a period.
    private static final String VERSION_NUMBER_REGEX = "[^0-9_.]";

    /**
     * Determines if a string is null, empty, or contains only whitespace
     * characters.
     *
     * @param text the string to check for null or empty values
     * @return true if the string is null, empty, or contains only
     * whitespace characters, false otherwise
     */
    public boolean isEmpty(final String text) {
        return text == null || text.isBlank();
    }

    /**
     * Determines if a string is non-null and not empty or containing only
     * whitespace characters.
     *
     * @param text the string to check for non-null and non-empty values
     * @return true if the string is non-null and not empty or containing
     * only whitespace characters, false otherwise
     */
    public boolean isNonEmpty(final String text) {
        return !isEmpty(text);
    }

    /**
     * It takes a string, replaces all the camel case with underscores, and then
     * converts the string to lower case
     *
     * @param text The text to convert to snake case.
     * @return A string that is the text parameter with the first letter of
     * each word capitalized.
     */
    public String toSnakeCase(final String text) {
        final String replacement = "$1_$2";
        return text.replaceAll(SNAKE_CASE_REGEX, replacement).toLowerCase();
    }

    /**
     * It takes a string, replaces all non-alphanumeric characters with
     * underscores, splits the string on underscores, capitalizes the first
     * letter of each word, and joins the words back together
     *
     * @param text The text to convert to camel case.
     * @return A string that is in camel case.
     */
    public String toCamelCase(final String text) {
        return Arrays.stream(text.replaceAll(CAMEL_CASE_REGEX, "_").split("_"))
                .filter(word -> !word.isEmpty())
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining());
    }

    /**
     * It takes a string, removes all non-alphanumeric characters, and returns
     * the first character in lowercase
     *
     * @param text The text to be converted.
     * @return The first letter of the string is being returned in
     * lowercase.
     */
    public static String toFieldName(final String text) {
        final String fieldName = text.replaceAll(CAMEL_CASE_REGEX, "");
        return !fieldName.isEmpty() ? fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1) : null;
    }

    /**
     * Interpolates a string by replacing placeholders with values obtained from
     * a function.
     *
     * @param text     The string to interpolate.
     * @param replacer A function that takes a placeholder string and returns a
     *                 replacement string.
     * @return The interpolated string.
     */
    public String interpolate(final String text, final UnaryOperator<String> replacer) {
        return Pattern.compile(INTERPOLATE_REGEX)
                .matcher(text)
                .replaceAll(match -> replacer.apply(match.group(1)));
    }

    /**
     * It takes a string and returns a string with all the version numbers
     * removed
     *
     * @param text The text to be processed.
     * @return The version number is being returned.
     */
    public String extractVersionNumber(final String text) {
        return text.replaceAll(VERSION_NUMBER_REGEX, "");
    }

    /**
     * It replaces all non-breaking spaces, carriage returns, and line feeds
     * with a single space, and then trims the result
     *
     * @param text The text to be normalized.
     * @return The text is being returned with all the white spaces
     * removed.
     */
    public static String normalizeText(final String text) {
        return text != null ? text.replaceAll("\u00A0|\\r\\n|\\r|\\n", " ").trim() : null;
    }

    /**
     * Returns the first group of the matches if the text is not null and the
     * compiled pattern matches. Note: This function compiles the pattern only
     * once, so subsequent calls with the same pattern are faster.
     *
     * @param pattern The regular expression pattern to match.
     * @param text    The text to search for the pattern.
     * @return An Optional String containing the first group of the
     * matches, or empty if no match is found or the text is
     * null.
     */
    public static Optional<String> findPattern(String pattern, String text) {
        var compiledPattern = Pattern.compile(pattern);
        return Optional.ofNullable(text)
                .map(compiledPattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1));
    }

    /**
     * Checks if the given label contains any of the words in the specified
     * list, ignoring case.
     *
     * @param label the label to check for word matches
     * @param words the list of words to check against the label
     * @return true if the label contains any of the words, false
     * otherwise
     */
    public boolean containsWord(String label, List<String> words) {
        return words.stream()
                .anyMatch(word -> label.toLowerCase().contains(word.toLowerCase()));
    }

    /**
     * Converts the given string representation of an integer to its hexadecimal
     * string representation.
     *
     * @param decimalString the string representation of an integer to
     *                      convert
     * @return the hexadecimal string representation of
     * the given integer
     * @throws NumberFormatException if the input string is not a valid integer
     */
    public String toHex(String decimalString) {
        int decimal = Integer.parseInt(decimalString);
        return Integer.toHexString(decimal);
    }
}
