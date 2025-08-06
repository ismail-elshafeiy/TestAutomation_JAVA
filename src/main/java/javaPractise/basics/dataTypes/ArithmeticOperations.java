package javaPractise.basics.dataTypes;

public class ArithmeticOperations {
    /**
     * Notes (Tips & Tricks):
     * - Arithmetic operations in Java are performed using the standard operators: +, -, *, /, and %.
     * - Dividing two integers drops the decimal part, Use at least one float or double to get decimals
     * - Java follows the order of operations PEMDAS : [ Parentheses -> Exponents -> Multiplication and Division -> Addition and Subtraction ] Use parentheses to control order
     * - Increment and Decrement Operators: Java provides ++ (increment) and -- (decrement) operators, which can be used in both pre-increment/decrement (++x or --x) and post-increment/decrement (x++ or x--)
     * - Compound Assignment Operators: like +=, -=, *=, /=, and %= to simplify arithmetic operations to shorten code and improve readability.
     */
    public static void main(String[] args) {
        ArithmeticOperations operations = new ArithmeticOperations();
        operations.additionExample();
        operations.subtractionExample();
        operations.multiplicationExample();
        operations.divisionExample();
        operations.divisionAndTruncationExample();
        operations.modulusExample();
        operations.incrementDecrementBeforeExample();
        operations.incrementDecrementAfterExample();
        operations.compoundAssignmentExample();
    }

    public void additionExample() {
        int x = 15;
        int y = 25;
        int result = x + y;
        System.out.println("Addition Result: " + result);
    }

    public void subtractionExample() {
        int x = 30;
        int y = 10;
        int result = x - y;
        System.out.println("Subtraction Result: " + result);
    }

    public void multiplicationExample() {
        int x = 7;
        int y = 6;
        int result = x * y;
        System.out.println("Multiplication Result: " + result);
    }

    public void divisionExample() {
        int x = 20;
        int y = 4;
        int result = x / y;
        System.out.println("Division Result: " + result);
    }

    public void divisionAndTruncationExample() {
        int x = 20;
        int y = 6;
        int result = x / y; // This will truncate the decimal part
        System.out.println("Division Result with Truncation: " + result);
    }

    public void modulusExample() {
        int x = 10;
        int y = 3;
        int result = x % y;
        System.out.println("Modulus Result: " + result);
    }

    public void incrementDecrementBeforeExample() {
        int x = 5;
        int y = ++x; // Pre-increment: x is incremented before assignment (before assignment)
        System.out.println("Pre-increment Result: x = " + x + ", y = " + y);
    }

    public void incrementDecrementAfterExample() {
        int x = 5;
        int y = x++; // Post-increment: x is assigned to y before incrementing (after assignment)
        System.out.println("Post-increment Result: x = " + x + ", y = " + y);
    }

    public void compoundAssignmentExample() {
        // Compound assignment operators: +=, -=, *=, /=, %=
        int a = 10;
        a += 5; // Equivalent to x = x + 5
        System.out.println("After += : " + a);

        int b = 20;
        b -= 3; // Equivalent to b = b - 3
        System.out.println("After -= : " + b);
        int c = 4;
        c *= 2; // Equivalent to c = c * 2
        System.out.println("After *= : " + c);
        int d = 15;
        d /= 3; // Equivalent to d = d / 3
        System.out.println("After /= : " + d);
        int e = 10;
        e %= 3; // Equivalent to e = e % 3
        System.out.println("After %= : " + e);
    }
}
