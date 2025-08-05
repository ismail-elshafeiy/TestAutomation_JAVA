package javaPractise.topics.hackerRank;

import java.util.Scanner;

public class JavaEndOfFile {
    public static void main(String[] args) {
        JavaEndOfFile javaEndOfFile = new JavaEndOfFile();
        javaEndOfFile.task1_solution1();
    }

    public void task1_solution1() {
        Scanner scanner = new Scanner(System.in);
        int lineNumber = 1;
        // Loop until EOF is reached
        while (scanner.hasNext()) {
            // Read the next line
            String line = scanner.nextLine();
            // Print the line number followed by the content
            System.out.println(lineNumber + " " + line);
            // Increment the line number
            lineNumber++;
        }
        scanner.close();
    }

}
