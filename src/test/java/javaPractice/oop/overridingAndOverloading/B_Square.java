package javaPractice.oop.overridingAndOverloading;

public class B_Square extends A_Rectangle {
    @Override
    public double calculatePerimeter() {
        return sides * length;
    }

    public void print(String what) {
        System.out.println("I am a " + what);
    }
}