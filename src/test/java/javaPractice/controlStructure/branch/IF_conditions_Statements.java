package javaPractice.controlStructure.branch;

import java.util.Scanner;

public class IF_conditions_Statements {
    /**
     * "The if-Else-if_Statement"  is interchangeable with  " Switch Statement "
     * If situation A occurs, do something.
     * Else if situation B occurs, do something else.
     * Else if situation C occurs, do something else.
     * <p>
     * IF-ELSE-IF
     * Display the letter grade for a student based on their test score.
     * <p>
     * That's mean: A decision structure that executes statement(s) given some condition is met
     * <p>
     * Magic Number: A number that is hard-coded as opposed to being represented by a variable
     * <p>
     * we check the condition to do into {} and while can't do the Condition in IF
     * will  go to [else if]
     */
    public static void main(String args[]) {

        //Get the test score
        System.out.println("Enter your test score:");
        Scanner scanner = new Scanner(System.in);
        double score = scanner.nextDouble();
        scanner.close();

        //Determine the letter grade
        char grade;

        if (score < 60) {
            grade = 'F';
        } else if (score < 70) {
            grade = 'D';
        } else if (score < 80) {
            grade = 'C';
        } else if (score < 90) {
            grade = 'B';
        } else {
            grade = 'A';
        }

        System.out.println("Your grade is " + grade);
    }

    public static class I1_IfBranch {

        public static void main(String[] args) {
            int extraCustomer = 2;

            if (extraCustomer >= 3) {
                System.out.println("Customer receives a discount");
            } else if (extraCustomer <= 3) {
                System.out.println("No Discount !");
            } else {
                System.out.println("Error : Not a valid customer count");
            }

        }

    }

    public static class I2_testSwitchCase {

        public static void main(String[] args) {
            int day = 5;

            switch (day) {
                case 1:
                    System.out.println("Sunday is the first day of the week");
                    break;
                case 2:
                    System.out.println("Monday is the 2nd day of the week");
                    break;
                case 3:
                    System.out.println("Tuesday is the 3rd day of the week");
                    break;
                case 4:
                    System.out.println("Wednesday is the 4th day of the week");
                    break;
                case 5:
                    System.out.println("Thursday is the 5th day of the week");
                    break;
                case 6:
                    System.out.println("Friday is the 6th day of the week");
                    break;
                case 7:
                    System.out.println("Saturday is the 7th day of the week");
                    break;
                default:
                    System.out.println("No valid value , there are only 7 dsys in a week");
                    break;
            }

        }
    }

    public class IF_else_Statements {

        /*
         * IF ELSE
         * All salespeople are expected to make at least 10 sales each week.
         * For those who do, they receive a congratulatory message.
         * For those who don’t, they are informed of how many sales they were short.
         */


        public static void main(String args[]) {


            //Initialize values we know
            int quota = 10;

            //Get unknown values
            System.out.println("Enter the number of sales you made this week:");
            Scanner scanner = new Scanner(System.in);
            int sales = scanner.nextInt();
            scanner.close();

            //Make a decision on the path to take - Output
            if (sales >= quota) {
                System.out.println("Congrats! You've met your quota");
            } else {
                int salesShort = quota - sales;
                System.out.println("You did not make your quota. You were " + salesShort + " sales short");
            }
        }
    }
}

