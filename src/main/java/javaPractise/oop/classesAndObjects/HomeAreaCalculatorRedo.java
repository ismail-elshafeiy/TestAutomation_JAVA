package javaPractise.oop.classesAndObjects;

import java.util.Scanner;

/*  Why do we have to import some classes?
 *  - For classes that are not within the same package, you have to import them.
 * */
public class HomeAreaCalculatorRedo {

    private Scanner scanner = new Scanner(System.in);

    public Rectangle getRoom() {

        System.out.println("Enter the length of your room:");
        double length = scanner.nextDouble();

        System.out.println("Enter the width of your room:");
        double width = scanner.nextDouble();

        return new Rectangle(length, width);
    }

    public double calculateTotalArea(Rectangle rectangle1, Rectangle rectangle2) {
        return rectangle1.calculateArea() + rectangle2.calculateArea();
    }

    public static void main(String args[]) {

        HomeAreaCalculatorRedo calculator = new HomeAreaCalculatorRedo();
        Rectangle kitchen = calculator.getRoom();
        Rectangle bathroom = calculator.getRoom();

        double area = calculator.calculateTotalArea(kitchen, bathroom);

        System.out.println("The total area is: " + area);

        calculator.scanner.close();

    }
}