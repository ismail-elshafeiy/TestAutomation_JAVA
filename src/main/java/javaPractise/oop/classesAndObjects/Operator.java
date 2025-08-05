package javaPractise.oop.classesAndObjects;


import java.util.Scanner;

/*
 * LOGICAL OPERATORS:
 * To qualify for a loan, a person must make at least $30,000
 * and have been working at their current job for at least 2 years.
 */
public class Operator {

    public static void main(String args[]) {

        //Initialize what we know
        int requiredSalary = 30000;
        int requiredYearsEmployed = 2;

        //Get what we don't know
        System.out.println("Enter your salary:");
        Scanner scanner = new Scanner(System.in);
        double salary = scanner.nextDouble();

        System.out.println("Enter the number of years with your current employer:");
        double years = scanner.nextDouble();


        //Make decision:  1 decision + 2 decision = S.O.P
        if (salary >= requiredSalary && years >= requiredYearsEmployed) {
            System.out.println("Congrats! You qualify for the loan");
        } else {
            System.out.println("Sorry, you must earn at least $"
                    + requiredSalary + " to qualify for the loan");
        }


        double penny = .01;
        double nickel = .05;
        double dime = .10;
        double quarter = .25;
        int dollar = 1;

        System.out.println("Welcome to 'Change for a Dollar'! " +
                "Your goal is to enter enough change to make exactly $1.00");

        System.out.println("Enter your number of pennies:");
        int numOfPennies = scanner.nextInt();

        System.out.println("Enter your number of nickels:");
        int numOfNickels = scanner.nextInt();

        System.out.println("Enter your number of dimes:");
        int numOfDimes = scanner.nextInt();

        System.out.println("Enter your number of quarters:");
        int numOfQuarters = scanner.nextInt();

        scanner.close();

        double total = numOfPennies * penny + numOfNickels * nickel + numOfDimes * dime + numOfQuarters * quarter;

        if (total < dollar) {
            double amountShort = dollar - total;

            //Way to make the decimal print with only 2 decimal places
            System.out.println("Sorry, you lose! You were short " + String.format("%.2f", amountShort) + " cents.");
        } else if (total > dollar) {
            double amountOver = total - dollar;

            //Way to make the decimal print with only 2 decimal places
            System.out.println("Sorry, you lose! You were over " + String.format("%.2f", amountOver) + " cents.");
        } else {
            System.out.println("Yay! That's exactly $1.00! You win!");
        }
    }
}


