package javaPractise.advancedTopics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JavaExceptions {

    public static void main(String args[]) {
        // printIndexOfArray();
        // printIndexOfArrayAndUseFinally();
        arithmeticException(15);
        // createNewFile();
        //numbersExceptionHandling();
        //tryWithResources();
    }

    /**
     * If an error occurs, we can use try...catch to catch the error and execute some code to handle it:
     */
    public static void printIndexOfArray() {
        try {
            int[] myNumbers = {1, 2, 3};
            System.out.println(myNumbers[10]);
        } catch (Exception e) {
            System.out.println("Something went wrong.[ " + e.getMessage() + " ]");
            e.printStackTrace();
        }
    }

    /**
     * The finally statement lets you execute code, after try...catch, regardless of the result:
     */
    public static void printIndexOfArrayAndUseFinally() {
        try {
            int[] myNumbers = {1, 2, 3};
            System.out.println(myNumbers[10]);
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        } finally {
            System.out.println("The 'try catch' is finished.");
        }
    }

    /**
     * The throw statement allows you to create a custom error.
     * There are many exception types available in Java: ArithmeticException, FileNotFoundException, ArrayIndexOutOfBoundsException, SecurityException, etc:
     */
    static void arithmeticException(int age) {
        if (age < 18) {
            throw new ArithmeticException("Access denied - You must be at least 18 years old.");
        } else {
            System.out.println("Access granted - You are old enough!");
        }
    }

    public static void createNewFile() {
        File file = new File("resources/nonexistent.txt");
        try {
            file.createNewFile();
        } catch (Exception e) {
            System.out.println("Directory does not exist.");
            e.printStackTrace();
        }
    }

    public static void createNewFileRethrow() throws IOException {
        File file = new File("resources/nonexistent.txt");
        file.createNewFile();
    }

    public static void numbersExceptionHandling() {
        File file = new File("resources/numbers.txt");
        Scanner fileReader = null;
        try {
            fileReader = new Scanner(file);
            while (fileReader.hasNext()) {
                double num = fileReader.nextDouble();
                System.out.println(num);
            }
        } catch (FileNotFoundException | InputMismatchException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
//        } finally {
//            fileReader.close();
//        }
    }


    public static void tryWithResources() {
        File file = new File("resources/numbers.txt");
        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNext()) {
                double num = fileReader.nextDouble();
                System.out.println(num);
            }
        } catch (FileNotFoundException | InputMismatchException e) {
            e.printStackTrace();
        }
    }
}
