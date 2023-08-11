package java.controlStructure.loop;

import java.util.Random;
import java.util.Scanner;

/*
 * NESTED LOOPS:
 * Find the average of each student's test scores
 */
public class D_NestedLoop {

    public static void main(String args[]) {

        //Initialize what we know
        int numberOfStudents = 5;
        int numberOfTests = 4;

        Scanner scanner = new Scanner(System.in);

        /*
         * Process all students
         * i need to know the first test of the first student
         * */
        for (int i = 0; i < numberOfStudents; i++) {

            double total = 0;
            for (int j = 0; j < numberOfTests; j++) {
                System.out.println("Enter the score for Test #" + (j + 1));
                double score = scanner.nextDouble();
                total = total + score;
            }

            double average = total / numberOfTests;
            System.out.println("The test average for student #" + (i + 1) + " is " + average);
        }

        int lastSpace = 20;
        int currentSpace = 0;
        int maxRolls = 5;
        Random random = new Random();

        System.out.println("Welcome to Roll the Die! Let's begin...");

        for (int i = 1; i <= maxRolls; i++) {

            int die = random.nextInt(6) + 1;
            currentSpace = currentSpace + die;

            System.out.print(String.format("Roll #%d: You've rolled a %d. ", i, die));

            if (currentSpace == lastSpace) {
                System.out.print("You're on space " + currentSpace + ". Congrats, you win!");
                break;
            } else if (currentSpace > lastSpace) {
                System.out.print("Unfortunately, that takes you past " + lastSpace + " spaces. You lose!");
                break;
            } else if (i == maxRolls && currentSpace < lastSpace) {
                System.out.println("You're on space " + currentSpace + ".");
                System.out.println("Unfortunately, you didn't make it to all " +
                        lastSpace + " spaces. You lose!");
            } else {
                int spacesToGo = lastSpace - currentSpace;
                System.out.print("You are now on space " + currentSpace +
                        " and have " + spacesToGo + " more to go.");
            }

            System.out.println();
        }
    }

    public static class LoopsConcept {

        public static void main(String[] args) {


            //1. while:
            int i = 1; //Initialization
            while (i <= 10) { //conditional
                System.out.println(i);
                i++; //incremental/decremental
            }


            //2. for:
            for (int j = 1; j <= 10; j++) {
                System.out.println(j);
            }

        }

    }
}
