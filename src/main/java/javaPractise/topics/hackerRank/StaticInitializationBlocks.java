package javaPractise.topics.hackerRank;

import java.util.Scanner;

public class StaticInitializationBlocks {
    public static void main(String[] args) {
        // Calculate area if inputs are valid used to check if the inputs are valid.
        if (flag) {
            int area = B * H;
            System.out.println(area);
        }
    }

    // Declare static variables
    static int B;
    static int H;
    static boolean flag = true;

    // Static initialization block
    static {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the breadth and height of the rectangle: ");
        B = scanner.nextInt();
        System.out.println("Please enter the height of the rectangle: ");
        H = scanner.nextInt();
        scanner.close();

        // Validate if both breadth and height are positive
        if (B <= 0 || H <= 0) {
            flag = false;
            System.out.println("java.lang.Exception: Breadth and height must be positive");
        }
    }
}
