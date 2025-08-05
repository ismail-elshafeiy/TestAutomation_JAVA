package javaPractise.basics.stringMethods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {
    public static void main(String[] args) {
        // Pattern Class - Defines a pattern (to be used in a search)
        // Flags - Used to change the search behavior of the pattern
        // FlagPattern.CASE_INSENSITIVE - The case of letters will be ignored when performing a search.
        // Pattern.LITERAL - Special characters in the pattern will not have any special meaning and will be treated as ordinary characters when performing a search.
        // Pattern.UNICODE_CASE - Use it together with the CASE_INSENSITIVE flag to also ignore the case of letters outside of the English alphabet
        Pattern pattern = Pattern.compile("w3schools", Pattern.CASE_INSENSITIVE);
        // Matcher Class - Used to search for the pattern
        // |	Find a match for any one of the patterns separated by | as in: cat|dog|fish
        // .	Find just one instance of any character
        // ^	Finds a match as the beginning of a string as in: ^Hello
        // $	Finds a match at the end of the string as in: World$
        // \d	Find a digit
        // \s	Find a whitespace character
        // \b	Find a match at the beginning of a word like this: \bWORD, or at the end of a word like this: WORD\b
        //	Find the Unicode character specified by the hexadecimal number xxxx
        Matcher matcher = pattern.matcher("Visit W3Schools!");
        boolean matchFound = matcher.find();
        if (matchFound) {
            System.out.println("Match found");
        } else {
            System.out.println("Match not found");
        }
    }
}