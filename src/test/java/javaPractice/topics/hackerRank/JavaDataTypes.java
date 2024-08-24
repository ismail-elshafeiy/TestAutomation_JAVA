package javaPractice.topics.hackerRank;

import java.util.Scanner;

public class JavaDataTypes {
    public static void main(String[] args) {
        JavaDataTypes javaDataTypes = new JavaDataTypes();
        javaDataTypes.task1_solution1();
    }

    // Task 1:  https://www.hackerrank.com/challenges/java-datatypes/problem
    // You are given an integer T, you have to declare three variables a, b, and c and assign them values.
    // Then, you have to print the appropriate data type of a, b, and c.
    public void task1_solution1() {
        // Declare and initialize the variables
        Scanner scanner = new Scanner(System.in);
        // Read the number of test cases
        System.out.println("Please enter the number of test cases: ");
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            try {
                // Read the next number
                System.out.println("Please enter a number: ");
                long n = scanner.nextLong();
                // Check which data types can fit the number
                System.out.println(n + " can be fitted in:");
                if (n >= Byte.MIN_VALUE && n <= Byte.MAX_VALUE) {
                    System.out.println("* byte");
                }
                if (n >= Short.MIN_VALUE && n <= Short.MAX_VALUE) {
                    System.out.println("* short");
                }
                if (n >= Integer.MIN_VALUE && n <= Integer.MAX_VALUE) {
                    System.out.println("* int");
                }
                if (n >= Long.MIN_VALUE && n <= Long.MAX_VALUE) {
                    System.out.println("* long");
                }
            } catch (Exception e) {
                // If the number is too large to be stored in a long
                System.out.println(scanner.next() + " can't be fitted anywhere.");
            }
        }
        scanner.close();
    }
}
