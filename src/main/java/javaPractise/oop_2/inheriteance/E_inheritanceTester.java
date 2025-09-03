package javaPractise.oop_2.inheriteance;


import javaPractise.oop_2.overridingAndOverloading.A_Rectangle;
import javaPractise.oop_2.overridingAndOverloading.B_Square;

public class E_inheritanceTester {


    public static void main(String[] args) {

        D_Mother mom = new D_Mother();
        mom.setName("Glenda");

        System.out.println(mom.getName() + " is a " + mom.getGender());
    }

    public static void testSquareOverride() {
        A_Rectangle rectangle = new A_Rectangle();
        rectangle.setLength(4);
        rectangle.setWidth(8);
        System.out.println(rectangle.calculatePerimeter());

        B_Square square = new B_Square();
        square.setLength(4);
        square.setWidth(8);
        System.out.println(square.calculatePerimeter());
    }

    public static void testInheritance() {
        B_Employee employee = new B_Employee();
        employee.setName("Angie");
    }

    public static void testOverload() {
        A_Rectangle rectangle = new A_Rectangle();
        rectangle.print();

        B_Square square = new B_Square();
        square.print("square");
    }
}
