package javaPractice.controlStructure.branch;

import java.util.Scanner;

public class SwitchStatements {
    /*
     *
     * " The Switch Statement " is interchangeable with " if-Else-if_Statement "
     * If situation A occurs, do something.
     * Else if situation B occurs, do something else.
     * Else if situation C occurs, do something else.
     *
     * type of expression does the condition : Equality
     *
     *  SWITCH
     * Have a user enter their letter grade, and using a switch statement,
     * print out a message letting them know how they did.
     *
     * just make sure to put the [break Or default] to stop the Check and End a case block within a switch statement, Because The next case will also be executed
     *
     * To choose the true and do what the user needs
     *
     */
    public static void main(String args[]) {

        System.out.println("Enter your letter grade:");
        Scanner scanner = new Scanner(System.in);
        String grade = scanner.next();
        scanner.close();

        String message;

        switch (grade) {
            case "A":
                message = "Excellent job!";
                break;
            case "B":
                message = "Great job!";
                break;
            case "C":
                message = "Good job!";
                break;
            case "D":
                message = "You need to work a bit harder";
                break;
            case "F":
                message = "Uh oh!";
                break;
            default:
                message = "Error. Invalid grade";
                break;
        }

        System.out.println(message);

    }
}

