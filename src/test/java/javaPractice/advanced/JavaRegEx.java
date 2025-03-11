package javaPractice.advanced;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaRegEx {
    public static void main(String[] args) {
        findAnyOccurrence();
    }

    /*
     * Regular Expression Patterns
     * [abc] - a, b, or c (simple class) find any of the characters a, b, or c
     * [^abc] - Any character except a, b, or c (negation) find any character except a, b, or c
     * [a-z] - Any lowercase letter (range) find any lowercase letter
     * [A-Z] - Any uppercase letter (range) find any uppercase letter
     * [a-zA-Z] - Any letter (range) find any letter
     * [0-9] - Any digit (range) find any digit
     */

    /*
     * Metacharacters
     * |	Find a match for any one of the patterns separated by | as in: cat|dog|fish
     * .	Find just one instance of any character
     * ^	Finds a match as the beginning of a string as in: ^Hello
     * $	Finds a match at the end of the string as in: World$
     * \d	Find a digit
     * \s	Find a whitespace character
     * \b	Find a match at the beginning of a word like this: \bWORD, or at the end of a word like this: WORD\b
     * \\uxxxx	Find the Unicode character specified by the hexadecimal number xxxx
     *
     */

    /*
     * Quantifiers
     * n+	Matches any string that contains at least one n
     * n*	Matches any string that contains zero or more occurrences of n
     * n?	Matches any string that contains zero or one occurrences of n
     * n{X}	Matches any string that contains a sequence of X n's
     * n{X,Y}	Matches any string that contains a sequence of X to Y n's
     * n{X,}	Matches any string that contains a sequence of at least X n's
     * n$	Matches any string with n at the end of it
     *
     */
    public static void findAnyOccurrence() {
//        Pattern pattern = Pattern.compile("w3schools", Pattern.CASE_INSENSITIVE);
        //Pattern pattern = Pattern.compile("w3schools", Pattern.LITERAL);
        Pattern pattern = Pattern.compile("w3schools", Pattern.UNICODE_CASE);

        Matcher matcher = pattern.matcher("Visit W3Schools!");
        boolean matchFound = matcher.find();
        if (matchFound) {
            System.out.println("Match found");
        } else {
            System.out.println("Match not found");
        }
    }
}
