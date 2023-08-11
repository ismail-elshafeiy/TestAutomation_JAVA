package java.classesAndObjects;

import java.util.Scanner;

/*
 * Write a class that creates instances of the Rectangle class to find the
 * total area of two rooms in a house.
 */

/* Static Non-Access Modifier
 *  used for members of a class which can be accessed without using an instance of the class to do
 *  Because it’s not associated with an instantiation, these members have no state.
 * */
public class B_HomeAreaCalculator {

    public static void main(String args[]) {

        /*******************
         * RECTANGLE 1
         ********************/

        //Create instance of Rectangle class
        A_Rectangle room1 = new A_Rectangle();
        Scanner scanner = new Scanner(System.in);

        System.out.println("please enter the width of room");
        room1.setWidth(scanner.nextDouble());

        System.out.println("please enter the length of room");
        room1.setLength(scanner.nextDouble());

        double areaOfRoom1 = room1.calculateArea();
        System.out.println("the first room: " + areaOfRoom1);

        /*******************
         * RECTANGLE 2
         ********************/

        //Create instance of Rectangle class by using the default constructor
        A_Rectangle room2 = new A_Rectangle(30, 75);

        double areaOfRoom2 = room2.calculateArea();
        System.out.println("the second room: " + areaOfRoom2);

        double totalArea = areaOfRoom1 + areaOfRoom2;

        System.out.println("Area of both rooms: " + totalArea);
    }
}