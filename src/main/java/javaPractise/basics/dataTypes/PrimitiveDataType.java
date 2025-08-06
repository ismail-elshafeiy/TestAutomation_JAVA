package javaPractise.basics.dataTypes;

public class PrimitiveDataType {
    /**
     * In Java, actions on a variable typically refer to the operations you can perform on variables,
     * - Declaration: This is where you define a variable and its type.
     * - Initialization: This is where you assign a value to the variable for the first time.
     * - Modification / Assignment / Reassignment : This is where you change the value of an already initialized variable.
     * - Accessing / Using : This is where you retrieve the value of variable in expressions, methods, print statements, etc.
     * - Passing as Argument : This is where you pass a variable to a method or function as an argument.
     * - Returning : This is where you return a variable from a method or function.
     * - Casting / Conversion : This is where you convert a variable from one type to another, such as from an integer to a double.
     * ----------------------------------------------------------------------------------------------------------------------------
     * <p>
     * Data types in Java are divided into two categories:
     * 1. Primitive Data Types: Java has 8 primitive data types grouped by type (integer, floating point, character, and boolean):
     * integer to store numbers.
     * - byte: 1 byte(8 bits), range from -128 to 127 -> used for smaller numbers like age, count, etc.
     * - short: 2 bytes(16 bits), range from -32,768 to 32,767 -> used for smaller numbers, like temperature.
     * - int: 4 bytes(32 bits), range from -2,147,483,648 to 2,147,483,647 -> used for general-purpose integer values.
     * - long: 8 bytes(64 bits), range from -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 = [-2^63 to 2^63] -> used for larger integer values like IDs, distance, timestamps, etc.
     * ----------------------------------------------------------------------------------------------------------------------------
     * Decimal numbers
     * - float: 4 bytes(32 bits), up to 6 decimal digit -> used for floating-point numbers, like measurements, calculations.
     * - double: 8 bytes(64 bits), up to 15 decimal digit -> used for more precise floating-point numbers, like scientific calculations, financial data.
     * ----------------------------------------------------------------------------------------------------------------------------
     * - char: 2 bytes(16 bits), represents a single character (e.g., 'A', '1', '$') -> used for characters, like letters, symbols.
     * - boolean: 1 bit, represents true or false values -> used for logical conditions like flags, checks, etc.
     * ----------------------------------------------------------------------------------------------------------------------------
     * Notes (Tips & Tricks):
     * - Choose the right data type for memory and performance
     * - User better naming conventions for variables (e.g., camelCase for variables, UPPER_CASE for constants) makes code more readable and self-documenting.
     * - Don't use java keywords as variable names (e.g., int, class, public, etc.) to avoid compilation errors. https://www.w3schools.com/java/java_ref_keywords.asp
     * - For long type variables, you can use the suffix 'L' or 'l' to indicate that the number is a long literal. It's recommended to use 'L' to avoid confusion with the digit '1'.
     * - For float type variables, you can use the suffix 'f' or 'F to indicate that the number is a float literal. It's recommended to use 'f' to avoid confusion with double literals.
     * - For integer, you can use underscores (_) to improve readability, e.g., 1_000_000 for one million.
     * - For char type variables, you must use single quotes (' ') to define a character, e.g., 'A'. Double quotes (" ") are used for strings.
     * - Use final for constants (unchangeable values) and follow naming conventions (e.g., CONSTANT_NAME) to indicate that the value should not change.
     * - var keyword allowing the compiler to infer/determines the data type of the variable based on the assigned value. but make sure to use it only when the type is clear and unambiguous, as it can make code less readable.
     */


    public static void main(String[] args) {
        PrimitiveDataType primitiveDataType;
        primitiveDataType = new PrimitiveDataType();
        primitiveDataType.byteExample();
        primitiveDataType.shortExample();
        primitiveDataType.intExample();
        primitiveDataType.longExample();
        primitiveDataType.floatExample();
        primitiveDataType.doubleExample();
        primitiveDataType.charExample();
        primitiveDataType.booleanExample();
        primitiveDataType.finalExample();
        primitiveDataType.varExample();
    }

    public void byteExample() {
        // Declare variable
        byte smallNumber;
        // Initialize variable
        smallNumber = 100;
        // Modify variable
        smallNumber = 120;
        // Access variable
        System.out.println("Byte: [ " + smallNumber + " ]");
    }

    public void shortExample() {
        // Declare variable
        short mediumNumber;
        // Initialize variable
        mediumNumber = 10000;
        // Modify variable
        mediumNumber = 12000;
        // Access variable
        System.out.println("Short: [ " + mediumNumber + " ]");
    }

    public void intExample() {
        // Declare variable
        int largeNumber;
        // Initialize variable
        largeNumber = 1000000;
        // Modify variable
        largeNumber = 1200000;
        // Access variable
        System.out.println("Int: [ " + largeNumber + " ]");
    }

    public void longExample() {
        // Declare variable
        long veryLargeNumber;
        // Initialize variable
        veryLargeNumber = 10000000000L; // 'L' suffix indicates it's a long literal can be small letter 'l' or capital letter 'L' but it's recommended to use capital 'L' to avoid confusion with digit '1'
        // Modify variable
        veryLargeNumber = 12_123_123_000L;
        // Access variable
        System.out.println("Long: [ " + veryLargeNumber + " ]");
    }

    public void floatExample() {
        // Declare variable
        float decimalNumber;
        // Initialize variable
        decimalNumber = 3.14f; // 'f' suffix indicates it's a float
        // Modify variable
        decimalNumber = 2.718f;
        // Access variable
        System.out.println("Float: [ " + decimalNumber + " ]");
    }

    public void doubleExample() {
        // Declare variable
        double preciseDecimalNumber;
        // Initialize variable
        preciseDecimalNumber = 3.141592653589793; // No suffix needed for double
        // Modify variable
        preciseDecimalNumber = 2.718281828459045;
        // Access variable
        System.out.println("Double: [ " + preciseDecimalNumber + " ]");
    }

    public void charExample() {
        // Declare variable
        char singleCharacter;
        // Initialize variable
        singleCharacter = 'A'; // Single quotes for char
        // Modify variable
        singleCharacter = 'B';
        // Access variable
        System.out.println("Char: [ " + singleCharacter + " ]");
    }

    public void booleanExample() {
        // Declare variable
        boolean isTrue;
        // Initialize variable
        isTrue = true; // Boolean values are either true or false
        // Modify variable
        isTrue = false;
        // Access variable
        System.out.println("Boolean: [ " + isTrue + " ]");
    }

    public void finalExample() {
        // Declare a constant
        final int CONSTANT_VALUE = 100; // 'final' keyword makes it unchangeable
        // Access constant
        System.out.println("Final Constant: [ " + CONSTANT_VALUE + " ]");
        // Uncommenting the next line will cause a compilation error because CONSTANT_VALUE cannot be changed
        // CONSTANT_VALUE = 200;
    }

    public void varExample() {
        var inferredInt = 42; // Inferred as int
        var inferredDouble = 3.14; // Inferred as double
        var isInferredBoolean = true; // Inferred as boolean
        var inferredChar = 'C'; // Inferred as char
        var inferredString = "Hello, World!"; // Inferred as String

        System.out.println("Inferred Int: [ " + inferredInt + " ]");
        System.out.println("Inferred Double: [ " + inferredDouble + " ]");
        System.out.println("Inferred Boolean: [ " + isInferredBoolean + " ]");
        System.out.println("Inferred Char: [ " + inferredChar + " ]");
        System.out.println("Inferred String: [ " + inferredString + " ]");
    }

}
