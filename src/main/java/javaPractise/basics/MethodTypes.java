package javaPractise.basics;

import javaPractise.basics.dataTypes.ArithmeticOperations;

public class MethodTypes {

    /**
     * Methods is a block of code that performs a specific task, it is executed when it is called.
     * Basic Types of methods in Java:
     * 1. Void Method: Does not return any value.
     * 2. Method with Return Type: Returns a value of a specific type.
     * 3. Method with Parameters: Accepts parameters to perform operations.
     * 4. Overloaded Method: Multiple methods with the same name but different parameters.
     * 5. Static Method: Belongs to the class rather than
     * ---------------------------------------------------------------------------------------
     * Tips & Tricks / Best Practices:
     * - Method names should clearly describe what the method do.
     * - Use camelCase for method names
     * - Keep methods short and focused on a single task.
     * - Use meaningful parameter names and Avoid Too Many Parameters maximum 3-4 parameters.
     * - Use Parameters Instead of Global Variables to make methods more reusable and testable.
     * - Use Method Overloading to provide multiple ways to call a method with different parameters.
     * - Use Static Methods for utility functions that do not require an instance of the class.
     * - Use Javadoc comments to document methods, especially public methods.
     * - Use access modifiers:
     * - public -> Accessible from anywhere
     * - private -> Accessible only within the class
     * - protected -> Accessible within the package and subclasses
     * - default (no modifier) -> Accessible only within the package
     */
    public static void main(String[] args) {
        MethodTypes methodTypes = new MethodTypes();
        methodTypes.voidMethod();
        System.out.println(methodTypes.methodWithReturnType());
        methodTypes.methodWithParameters("Ismail", 32);
        methodTypes.overloadedMethod(10);
        methodTypes.overloadedMethod(20, "Hello");
        staticMethod();
    }

    public void voidMethod() {
        System.out.println("This is a simple method that prints a message.");
    }

    public String methodWithReturnType() {
        String name = "Ismail";
        return "Hello, " + name + "!";
    }

    public void methodWithParameters(String name, int age) {
        System.out.println("Name: " + name + ", Age: " + age);
    }

    public void overloadedMethod(int a) {
        System.out.println("Overloaded method with one int parameter: " + a);
    }

    public void overloadedMethod(int a, String b) {
        System.out.println("Overloaded method with int and String parameters: " + a + ", " + b);
    }

    public static void staticMethod() {
        System.out.println("This is a static method that can be called without creating an instance of the class.");
    }
}
