package javaPractice.arrays;

import java.util.Scanner;

/*
 * Create a program that allows a user to enter any
 * number of grades and provides them with their
 * average score, as well as the highest and lowest scores.
 */
public class Grades {

    private static int[] grades;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("How many grades would you like to enter?");
        grades = new int[scanner.nextInt()];
        //now we have the value.
        getArraysGrades();

        System.out.println("Average: " + String.format("%.2f", calculateAverage()));
        System.out.println("Highest: " + getHighest());
        System.out.println("Lowest: " + getLowest());
    }

    public static void getArraysGrades() {
        for (int i = 0; i < grades.length; i++) {
            System.out.println("Enter grade #" + (i + 1));
            grades[i] = scanner.nextInt();
        }
    }

    public static int calculateSum() {
        int sum = 0;
        for (int grade : grades) {
            System.out.println("grade: " + grade);
            System.out.println("sum: " + sum);
            System.out.println("sum + grade: " + (sum + grade));
            sum = sum + grade;
        }
        System.out.println("sum: " + sum);
        System.out.println("grades.length: " + grades.length);
        return sum;
    }

    public static double calculateAverage() {
        return (double) calculateSum() / grades.length;
    }

    public static int getHighest() {
        int highest = grades[0];
        for (int grade : grades) {
            if (grade > highest) {
                highest = grade;
            }
        }
        return highest;
    }

    public static int getLowest() {
        int lowest = grades[0];
        for (int grade : grades) {
            if (grade < lowest) {
                lowest = grade;
            }
        }
        return lowest;
    }

}