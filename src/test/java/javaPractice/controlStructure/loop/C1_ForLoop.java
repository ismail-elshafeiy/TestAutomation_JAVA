package javaPractice.controlStructure.loop;

import java.util.Scanner;

/*
 * FOR LOOP:
 * Write a cashier program that will scan a given number of items and tally the cost.
 */
public class C1_ForLoop {

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        //Get number of items to scan
        System.out.println("Enter the number of items you would you like to scan:");
        int quantity = scanner.nextInt();
        //I stored that into this variable quantity.

        double total = 0;

        //Create loop to iterate through all of the items and accumulate the costs
        for (int i = 0; i < quantity; i++) {

            System.out.println("Enter the cost of the item:");
            double price = scanner.nextDouble();

            total = total + price;
        }
        System.out.println("Your total is $" + total);

    }

    public static class L2_DoWhileLoop {

        public static void main(String[] args) {
            int i = 0;
            do {
                System.out.println("The Variable value is : " + i);
                i++;

            } while (i < 5);
        }

    }
}