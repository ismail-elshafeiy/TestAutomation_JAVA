package javaPractice.topics.hackerRank;
//Task: https://www.hackerrank.com/challenges/java-currency-formatter/problem
//Given a double-precision number, payment, denoting an amount of money, use the NumberFormat class' getCurrencyInstance method to convert payment into the US, Indian, Chinese, and French currency formats. Then print the formatted values as follows:
//US: formattedPayment
//India: formattedPayment
//China: formattedPayment

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class JavaCurrencyFormatter {
    public static void main(String[] args) {
        // Write your code here.
        // Example input
//        double payment = 12324.134;
        Scanner scanner = new Scanner(System.in);
        double payment = scanner.nextDouble();
        scanner.close();
        // Create Locale for India
        Locale indiaLocale = new Locale("en", "IN");
        // Get currency instances for each locale
        NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat india = NumberFormat.getCurrencyInstance(indiaLocale);
        NumberFormat china = NumberFormat.getCurrencyInstance(Locale.CHINA);
        NumberFormat france = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        // Format the payment for each locale
        String usFormatted = us.format(payment);
        String indiaFormatted = india.format(payment);
        String chinaFormatted = china.format(payment);
        String franceFormatted = france.format(payment);
        // Print results
        System.out.println("US: " + usFormatted);
        System.out.println("India: " + indiaFormatted);
        System.out.println("China: " + chinaFormatted);
        System.out.println("France: " + franceFormatted);
    }
}
