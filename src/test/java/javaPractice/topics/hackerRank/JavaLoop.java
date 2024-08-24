package javaPractice.topics.hackerRank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class JavaLoop {

    public static void main(String[] args) {
        JavaLoop javaLoop = new JavaLoop();
        // javaLoop.task1_solution1();
        javaLoop.task2_solution1();
    }

    // Task 1:  https://www.hackerrank.com/challenges/java-loops-i/problem
    //Given an integer, N,
    // print its first 10  multiples.
    // Each multiple N * i  (where 1 =< i =< 10 ) should be printed on a new line in the form: N x i = result.
    public void task1_solution1() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter a number: ");
        int N = Integer.parseInt(bufferedReader.readLine().trim());
        bufferedReader.close();

        for (int i = 1; i <= 10; i++) {
            System.out.println(N + " x " + i + " = " + N * i);
        }
    }

    // Task 2:  https://www.hackerrank.com/challenges/java-loops-ii/problem
    // We use the integers a, b, and n to create the following series:
    // (a + 2^0 * b), (a + 2^0 * b + 2^1 * b), ... (a + 2^0 * b + 2^1 * b + ... + 2^n-1 * b)
    // You are given q queries in the form of a, b, and n. For each query,
    // print the series corresponding to the given a, b, and n values as a single line of n space-separated integers.
    public void task2_solution1() {
        // Create a scanner object to read input
        Scanner scanner = new Scanner(System.in);
        // Read the number of queries
        int q = scanner.nextInt();
        // Process each query
        for (int i = 0; i < q; i++) {
            // Read the values of a, b, and n
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int n = scanner.nextInt();
            // Initialize the current value with a
            int currentValue = a;
            // Generate the series
            for (int j = 0; j < n; j++) {
                // Update the current value
                currentValue += (Math.pow(2, j) * b);
                // Print the current value followed by a space
                System.out.print(currentValue + " ");
            }
            // Print a newline after each series
            System.out.println();
        }

        // Close the scanner
        scanner.close();
    }
}

