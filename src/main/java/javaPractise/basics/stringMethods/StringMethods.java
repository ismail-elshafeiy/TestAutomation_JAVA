package javaPractise.basics.stringMethods;

import java.util.Arrays;

public class StringMethods {
    public static void main(String[] args) {
        StringMethods stringMethods = new StringMethods();
        stringMethods.charAtExample();
        stringMethods.codePointAtExample();
        stringMethods.codePointBeforeExample();
        stringMethods.codePointCountExample();
        stringMethods.compareToExample();
        stringMethods.compareToIgnoreCaseExample();
        stringMethods.concatExample();
        stringMethods.containsExample();
        stringMethods.contentEqualsExample();
        stringMethods.copyValueOfExample();
        stringMethods.endsWithExample();
        stringMethods.equalsExample();
        stringMethods.equalsIgnoreCaseExample();
        stringMethods.formatExample();
        stringMethods.getBytesExample();
        stringMethods.getCharsExample();
        stringMethods.hashCodeExample();
        stringMethods.indexOfExample();
        stringMethods.internExample();
        stringMethods.isEmptyExample();
        stringMethods.lastIndexOfExample();
        stringMethods.lengthExample();
        stringMethods.matchesExample();
        stringMethods.offsetByCodePointsExample();
        stringMethods.regionMatchesExample();
        stringMethods.replaceExample();
        stringMethods.replaceAllExample();
        stringMethods.replaceFirstExample();
        stringMethods.splitExample();
        stringMethods.startsWithExample();
        stringMethods.subSequenceExample();
        stringMethods.substringExample();
        stringMethods.toCharArrayExample();
        stringMethods.toLowerCaseExample();
        stringMethods.toStringExample();
        stringMethods.toUpperCaseExample();
        stringMethods.trimExample();
        stringMethods.valueOfExample();
    }

    public void charAtExample() {
        String myStr = "Hello";
        char result = myStr.charAt(2);
        System.out.println(result); // Output: l
    }

    public void codePointAtExample() {
        String myStr = "Hello";
        int result = myStr.codePointAt(0);
        System.out.println(result); // Output: 72 (Unicode code point for 'H')
    }

    public void codePointBeforeExample() {
        String myStr = "Hello";
        int result = myStr.codePointBefore(1);
        System.out.println(result); // Output: 72 (Unicode code point for 'H')
    }

    public void codePointCountExample() {
        String myStr = "Hello";
        int result = myStr.codePointCount(0, 3);
        System.out.println(result); // Output: 3 (counts 'H', 'e', 'l')
    }

    public void compareToExample() {
        String myStr = "Hello";
        int result = myStr.compareTo("Hello");
        System.out.println(result); // Output: 0 (strings are equal)
    }

    public void compareToIgnoreCaseExample() {
        String myStr = "Hello";
        int result = myStr.compareToIgnoreCase("Hello");
        System.out.println(result); // Output: 0 (strings are equal ignoring case)
    }

    public void concatExample() {
        String myStr = "Hello";
        String result = myStr.concat(" World");
        System.out.println(result); // Output: Hello World
    }

    public void containsExample() {
        String myStr = "Hello";
        boolean result = myStr.contains("Hello");
        System.out.println(result); // Output: true
    }

    public void contentEqualsExample() {
        String myStr = "Hello";
        System.out.println(myStr.contentEquals("Hello")); // Output: true
        System.out.println(myStr.contentEquals("e")); // Output: false
        System.out.println(myStr.contentEquals("Hi")); // Output: false
    }

    public void copyValueOfExample() {
        char[] myStr1 = {'H', 'e', 'l', 'l', 'o'};
        String myStr2 = "";
        myStr2 = myStr2.copyValueOf(myStr1, 0, 4);
        System.out.println("Returned String: " + myStr2); // Output: Hello
    }

    public void endsWithExample() {
        String myStr = "Hello";
        System.out.println(myStr.endsWith("Hel")); // Output: false
        System.out.println(myStr.endsWith("llo")); // Output: true
        System.out.println(myStr.endsWith("o")); // Output: true
    }

    public void equalsExample() {
        String myStr1 = "Hello";
        String myStr2 = "Hello";
        String myStr3 = "Another String";
        System.out.println(myStr1.equals(myStr2)); // Output: true
        System.out.println(myStr1.equals(myStr3)); // Output: false
    }

    public void equalsIgnoreCaseExample() {
        String myStr1 = "Hello";
        String myStr2 = "HELLO";
        String myStr3 = "Another String";
        System.out.println(myStr1.equalsIgnoreCase(myStr2)); // Output: true
        System.out.println(myStr1.equalsIgnoreCase(myStr3)); // Output: false
    }

    public void formatExample() {
        String myStr = "Hello ismail";
        System.out.println(String.format("My name is %s", "John")); // Output: My name is John
    }

    public void getBytesExample() {
        String myStr = "Hello ismail";
        byte[] result = myStr.getBytes();
        System.out.println(Arrays.toString(result)); // Output: [72, 101, 108, 108, 111, 32, 105, 115, 109, 97, 105, 108]
    }

    public void getCharsExample() {
        String myStr = "Hello ismail";
        char[] result = new char[10];
        myStr.getChars(0, 10, result, 0);
        System.out.println(Arrays.toString(result)); // Output: [H, e, l, l, o,  , i, s, m, a]
    }

    public void hashCodeExample() {
        String myStr = "Hello ismail";
        int result = myStr.hashCode();
        System.out.println(result);
    }

    public void indexOfExample() {
        String myStr = "Hello ismail";
        int result = myStr.indexOf("i");
        System.out.println(result); // Output: 6
    }

    public void internExample() {
        String myStr = "Hello    ismail";
        String result = myStr.intern();
        System.out.println(result); // Output: Hello    ismail
    }

    public void isEmptyExample() {
        String myStr = "Hello ismail";
        boolean result = myStr.isEmpty();
        System.out.println(result);
        myStr = "";
        result = myStr.isEmpty();
        System.out.println(result); // Output: true
    }

    public void lastIndexOfExample() {
        String myStr = "Hello ismail ismail ismail";
        int result = myStr.lastIndexOf("i");
        System.out.println(result); // Output: 20
    }

    public void lengthExample() {
        String myStr = "Hello ismail";
        int result = myStr.length();
        System.out.println(result); // Output: 12
    }

    public void matchesExample() {
        String myStr = "Hello ismail";
        boolean result = myStr.matches("(.*)ismail");
        System.out.println(result); // Output: true
        myStr = "Hello";
        result = myStr.matches("(.*)ismail");
        System.out.println(result); // Output: false
    }

    public void offsetByCodePointsExample() {
        String myStr = "Hello ismail";
        int result = myStr.offsetByCodePoints(0, 2);
        System.out.println(result); // Output: 2 (offset by 2 code points from index 0)
    }

    public void regionMatchesExample() {
        String myStr = "Hello ismail";
        boolean result = myStr.regionMatches(0, "Hello", 0, 5);
        System.out.println(result);
        myStr = "Hello";
        result = myStr.regionMatches(0, "ismail", 0, 5);
        System.out.println(result);
    }

    public void replaceExample() {
        String myStr = "Hello ismail ismail ismail";
        String result = myStr.replace("ismail", "John");
        System.out.println(result);
    }

    public void replaceAllExample() {
        String myStr = "Hello ismail ismail ismail ismail";
        String result = myStr.replaceAll("i", "I");
        System.out.println(result);
    }

    public void replaceFirstExample() {
        String myStr = "Hello ismail ismail ismail ismail";
        String result = myStr.replaceFirst("ismail", "hamada");
        System.out.println(result);
    }

    public void splitExample() {
        String myStr = "Hello_ismail_ismail_ismail_ismail";
        String[] result = myStr.split("_");
        System.out.println(Arrays.toString(result));
        result = myStr.split("_", 2);
        System.out.println(Arrays.toString(result));
    }

    public void startsWithExample() {
        String myStr = "Hello";
        System.out.println(myStr.startsWith("Hel"));
        System.out.println(myStr.startsWith("llo"));
        System.out.println(myStr.startsWith("o"));
    }

    public void subSequenceExample() {
        String myStr = "Hello";
        CharSequence result = myStr.subSequence(2, 5);
        System.out.println(result);
    }

    public void substringExample() {
        String myStr = "Hello";
        String result = myStr.substring(2, 5);
        System.out.println(result);
    }

    public void toCharArrayExample() {
        String myStr = "Hello";
        char[] result = myStr.toCharArray();
        System.out.println(Arrays.toString(result));
    }

    public void toLowerCaseExample() {
        String myStr = "HELLO WORLD";
        String result = myStr.toLowerCase();
        System.out.println(result);
    }

    public void toStringExample() {
        String myStr = "Hello";
        String result = myStr.toString();
        System.out.println(result);
    }

    public void toUpperCaseExample() {
        String myStr = "Hello World";
        String result = myStr.toUpperCase();
        System.out.println(result);
    }

    public void trimExample() {
        String myStr = "   Hello World   ";
        String result = myStr.trim();
        System.out.println(result);
    }

    public void valueOfExample() {
        int myInt = 5;
        String result = String.valueOf(myInt);
        System.out.println(result);
    }
}