package javaPractice.topics.hackerRank;

import java.util.Scanner;

public class JavaString {
    public static void main(String[] args) {
        JavaString javaString = new JavaString();
        //  javaString.task1_solution1();
        javaString.task2_solution1();
    }

    // Task 1:  https://www.hackerrank.com/challenges/java-output-formatting/problem
    // Java's System.out.printf function can be used to print formatted output.
    // The purpose of this exercise is to test your understanding of formatting output using printf.
    // To get you started, a portion of the solution is provided for you in the editor; you must format and print the input to complete the solution.
    public void task1_solution1() {
        Scanner scanner = new Scanner(System.in);
        // Print the header
        System.out.println("================================");
        // Process each line of input
        for (int i = 0; i < 3; i++) {  // Since there are three lines in the input
            System.out.println("Please enter a string and a number: ");
            String str = scanner.next();
            int num = scanner.nextInt();
            // Format the output
            System.out.printf("%-15s%03d%n", str, num);
        }
        // Print the footer
        System.out.println("================================");
        scanner.close();
    }

    public void task2_solution1() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.close();
        // Convert integer to string
        String s = Integer.toString(n);
        // Check if the conversion is correct
        if (n == Integer.parseInt(s)) {
            System.out.println("Good job");
        } else {
            System.out.println("Wrong answer");
        }
    }
}
