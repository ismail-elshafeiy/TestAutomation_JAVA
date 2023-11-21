package javaPractice.classesAndObjects;

import java.util.Scanner;

/*
 * methods are subtasks within a class.
 *
 * OUR FIRST METHOD
 * Write a method that asks a user for their name, then greets them by name.
 */
public class A_Method {

    public static void main(String args[]) {

        System.out.println("Enter your name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        scanner.close();
        greetUser(name);
    }

    public static void greetUser(String name) {

        System.out.println("Hi there, " + name);
    }
}