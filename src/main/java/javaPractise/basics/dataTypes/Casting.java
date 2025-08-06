package javaPractise.basics.dataTypes;

/**
 * Notes (Tips & Tricks):
 * - Casting in Java is the process of converting a variable from one data type to another.
 * - There are two types of casting:
 * 1. Implicit Casting : Small → Large (auto done by compiler)
 * - compiler when converting a smaller data type to a larger data type.
 * - Examples: byte -> short -> int -> long -> float -> double.
 * - Implicit casting is safe and does not lose information, while explicit casting can lead to data loss if the value being cast is outside the range of the target type.
 * -------------------------------------------------------------------------------------------------------------------
 * 2. Explicit Casting : Large → Small (manual done by developer)
 * - using parentheses to convert a larger data type to a smaller data type.
 * - Be careful with explicit casting, converting from a larger to a smaller type may cause data loss or overflow.
 * - Examples: double -> float -> long -> int -> short -> byte.
 * - Use explicit casting when you are sure that the value fits within the range of the target type.
 * - Always check the range of the target type before performing explicit casting to avoid unexpected results.
 */

public class Casting {
    public static void main(String[] args) {
        Casting casting = new Casting();
        casting.implicitCastingExample();
        casting.explicitCastingExample();

    }

    public void implicitCastingExample() {
        // Implicit Casting (Widening Conversion)
        int intValue = 100;
        long longValue = intValue; // int to long
        float floatValue = longValue; // long to float
        double doubleValue = floatValue; // float to double

        System.out.println("Implicit Casting:");
        System.out.println("int to long: " + longValue);
        System.out.println("long to float: " + floatValue);
        System.out.println("float to double: " + doubleValue);
    }

    public void explicitCastingExample() {
        double anotherDoubleValue = 123.45;
        float anotherFloatValue = (float) anotherDoubleValue; // double to float
        long anotherLongValue = (long) anotherFloatValue; // float to long
        int anotherIntValue = (int) anotherLongValue; // long to int

        System.out.println("Explicit Casting:");
        System.out.println("double to float: " + anotherFloatValue);
        System.out.println("float to long: " + anotherLongValue);
        System.out.println("long to int: " + anotherIntValue);
    }
}
