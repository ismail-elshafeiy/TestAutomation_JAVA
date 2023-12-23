package javaPractice.classesAndObjects;

import java.util.Scanner;

/*
 * VARIABLE SCOPE
 * Write an ‘instant credit check’ program that approves
 * anyone who makes more than $25,000 and has a credit score
 * of 700 or better. Reject all others.
 */
public class Variable {

    static int requiredSalary = 25000;
    static int requiredCreditScore = 700;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) {

        double salary = getSalary();
        int creditScore = getCreditScore();


        //Check if the user is qualified
        boolean qualified = isUserQualified(creditScore, salary);

        //Notify the user
        notifyUser(qualified);
        ////////////////////////////////////////////////////////
        System.out.println("Enter base cost of the plan:");
        double baseCost = scanner.nextDouble();

        System.out.println("Enter overage minutes:");
        double overageMinutes = scanner.nextDouble();

        double overageCharge = calculateOverages(overageMinutes);
        double tax = calculateTax(baseCost + overageCharge);

        calculateAndPrintBill(baseCost, overageCharge, tax);

        scanner.close();
    }

    public static double getSalary() {
        System.out.println("Enter your salary:");

        double salary = scanner.nextDouble();
        return salary;
    }

    public static int getCreditScore() {
        System.out.println("Enter your credit score:");
        int creditScore = scanner.nextInt();
        return creditScore;
    }

    public static boolean isUserQualified(int creditScore, double salary) {
        if (creditScore >= requiredCreditScore && salary <= requiredSalary) {
            return true;
        } else {
            return false;
        }
    }

    public static void notifyUser(boolean isQualified) {
        if (isQualified) {
            System.out.println("Congrats! You've been approved.");
        } else {
            System.out.println("Sorry. You've been declined");
        }
    }


    public static double calculateOverages(double extraMinutes) {
        double rate = 0.25;
        double overage = extraMinutes * rate;
        return overage;
    }

    public static double calculateTax(double subtotal) {
        double rate = 0.15;
        double tax = subtotal * rate;
        return tax;
    }

    public static void calculateAndPrintBill(double base, double overage, double tax) {
        double finalTotal = base + overage + tax;

        System.out.println("Phone Bill Statement");
        System.out.println("Plan: $" + String.format("%.2f", base));
        System.out.println("Overage: $" + String.format("%.2f", overage));
        System.out.println("Tax: $" + tax);
        System.out.println("Total: $" + String.format("%.2f", finalTotal));
    }
}
