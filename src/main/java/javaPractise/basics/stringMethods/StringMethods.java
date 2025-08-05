package javaPractise.basics.stringMethods;

import java.util.Arrays;

public class StringMethods {
    public static class CharAtExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            char result = myStr.charAt(2);
            System.out.println(result);
        }
    }

    public static class codePointAtExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            int result = myStr.codePointAt(0);
            System.out.println(result);
        }
    }

    public static class codePointBeforeExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            int result = myStr.codePointBefore(1);
            System.out.println(result);
        }
    }

    public static class codePointCountExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            int result = myStr.codePointCount(0, 3);
            System.out.println(result);
        }
    }

    public static class compareToExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            int result = myStr.compareTo("Hello"); // 0 if equal , 1 if not equal

            System.out.println(result);
        }
    }

    public static class compareToIgnoreCaseExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            int result = myStr.compareToIgnoreCase("Hello"); // 0 if equal , 1 if not equal
            System.out.println(result);
        }
    }

    public static class concatExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            String result = myStr.concat(" World"); // 	Appends a string to the end of another string
            System.out.println(result);
        }
    }

    public static class containsExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            boolean result = myStr.contains("Hello"); // Checks whether a string contains a sequence of characters
            System.out.println(result);
        }
    }

    public static class contentEqualsExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            System.out.println(myStr.contentEquals("Hello"));
            System.out.println(myStr.contentEquals("e"));
            System.out.println(myStr.contentEquals("Hi"));
        }
    }

    public static class copyValueOfExample {
        public static void main(String[] args) {
            char[] myStr1 = {'H', 'e', 'l', 'l', 'o'};
            String myStr2 = "";
            myStr2 = myStr2.copyValueOf(myStr1, 0, 4); // 	Returns a String that represents the characters of the character array
            System.out.println("Returned String: " + myStr2);
        }
    }

    public static class endsWithExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            System.out.println(myStr.endsWith("Hel")); // Checks whether a string ends with the specified character(s)
            System.out.println(myStr.endsWith("llo"));
            System.out.println(myStr.endsWith("o"));
        }
    }

    public static class equalsExample {
        public static void main(String[] args) {
            String myStr1 = "Hello";
            String myStr2 = "Hello";
            String myStr3 = "Another String";
            System.out.println(myStr1.equals(myStr2));
            System.out.println(myStr1.equals(myStr3));
        }
    }

    public static class equalsIgnoreCaseExample {
        public static void main(String[] args) {
            String myStr1 = "Hello";
            String myStr2 = "HELLO";
            String myStr3 = "Another String";
            System.out.println(myStr1.equalsIgnoreCase(myStr2));
            System.out.println(myStr1.equalsIgnoreCase(myStr3));
        }
    }

    public static class formatExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail";
            System.out.println(myStr.format("My name is %s", "John")); // Returns a formatted string using the specified locale, format string, and arguments
        }
    }

    public static class getBytesExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail";
            byte[] result = myStr.getBytes(); // Encodes this String into a sequence of bytes using the platform's default charset, storing the result into a new byte array
            System.out.println(Arrays.toString(result));
        }
    }

    public static class getCharsExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail";
            char[] result = new char[10];
            myStr.getChars(0, 10, result, 2); // Copies characters from this string into the destination character array
            System.out.println(Arrays.toString(result));
        }
    }

    public static class hashCodeExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail";
            int result = myStr.hashCode(); // Returns a hash code for this string
            System.out.println(result);
        }
    }

    public static class indexOfExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail";
            int result = myStr.indexOf("i"); // Returns the position of the first found occurrence of specified characters in a string
            System.out.println(result);
        }
    }

    public static class internExample {
        public static void main(String[] args) {
            String myStr = "Hello    ismail";
            String result = myStr.intern(); // Returns the index within this string of the first occurrence of the specified substring
            System.out.println(result);
        }
    }

    public static class isEmptyExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail";
            boolean result = myStr.isEmpty(); // Checks whether a string is empty or not
            System.out.println(result);
            myStr = "";
            result = myStr.isEmpty();
            System.out.println(result);
        }
    }

    public static class lastIndexOfExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail ismail ismail";
            int result = myStr.lastIndexOf("i"); // Returns the position of the last found occurrence of specified characters in a string
            System.out.println(result);
        }
    }

    public static class lengthExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail";
            int result = myStr.length(); // Returns the length of a specified string
            System.out.println(result);
        }
    }

    public static class matchesExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail";
            boolean result = myStr.matches("(.*)ismail"); // Searches a string for a match against a regular expression, and returns the matches
            System.out.println(result);
            myStr = "Hello";
            result = myStr.matches("(.*)ismail");
            System.out.println(result);
        }
    }

    public static class offsetByCodePointsExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail";
            int result = myStr.offsetByCodePoints(0, 2); // Returns the index within this String that is offset from the given index by codePointOffset code points
            System.out.println(result);
        }
    }

    public static class regionMatchesExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail";
            boolean result = myStr.regionMatches(0, "Hello", 0, 5); // Tests if two string regions are equal
            System.out.println(result);
            myStr = "Hello";
            result = myStr.regionMatches(0, "ismail", 0, 5);
            System.out.println(result);

        }
    }

    public static class replaceExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail ismail ismail";
            String result = myStr.replace("ismail", "John"); // Searches a string for a specified value, or a regular expression, and returns a new string where the specified values are replaced
            System.out.println(result);
        }
    }

    public static class replaceAllExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail ismail ismail ismail";
            String result = myStr.replaceAll("i", "I");    // Replaces each substring of this string that matches the given regular expression with the given replacement
            System.out.println(result);
        }
    }

    public static class replaceFirstExample {
        public static void main(String[] args) {
            String myStr = "Hello ismail ismail ismail ismail";
            String result = myStr.replaceFirst("ismail", "hamada");    // Replaces the first substring of this string that matches the given regular expression with the given replacement
            System.out.println(result);
        }
    }

    public static class splitExample {
        public static void main(String[] args) {
            String myStr = "Hello_ismail_ismail_ismail_ismail";
            String[] result = myStr.split("_");// Splits a string into an array of substrings
            System.out.println(Arrays.toString(result));
            result = myStr.split("_", 2);
            System.out.println(Arrays.toString(result));
        }
    }

    public static class startsWithExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            System.out.println(myStr.startsWith("Hel"));
            System.out.println(myStr.startsWith("llo"));
            System.out.println(myStr.startsWith("o"));
        }
    }

    public static class subSequenceExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            CharSequence result = myStr.subSequence(2, 5); // Returns a new character sequence that is a subsequence of this sequence
            System.out.println(result);
        }
    }

    public static class substringExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            String result = myStr.substring(2, 5); // Extracts the characters from a string, beginning at a specified start position, and through the specified number of character
            System.out.println(result);
        }
    }

    public static class toCharArrayExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            char[] result = myStr.toCharArray(); // Converts this string to a new character array
            System.out.println(Arrays.toString(result));
        }
    }

    public static class toLowerCaseExample {
        public static void main(String[] args) {
            String myStr = "HELLO WORLD";
            String result = myStr.toLowerCase(); // Converts a string to lower case letters
            System.out.println(result);
        }
    }

    public static class toStringExample {
        public static void main(String[] args) {
            String myStr = "Hello";
            String result = myStr.toString(); // Returns the value of a String object
            System.out.println(result);
        }
    }

    public static class toUpperCaseExample {
        public static void main(String[] args) {
            String myStr = "Hello World";
            String result = myStr.toUpperCase(); // Converts a string to upper case letters
            System.out.println(result);
        }
    }

    public static class trimExample {
        public static void main(String[] args) {
            String myStr = "   Hello World   ";
            String result = myStr.trim(); // Removes whitespace from both ends of a string
            System.out.println(result);
        }
    }

    public static class valueOfExample {
        public static void main(String[] args) {
            int myInt = 5;
            String result = String.valueOf(myInt); // Converts a data type to a string
            System.out.println(result);
        }
    }


}
