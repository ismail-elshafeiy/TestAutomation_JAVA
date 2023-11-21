package javaPractice.arrays;

public class test {
    public static void main(String[] args) {

    }

    public void ConvertDecimalTo() {
        double[] numbers = {1.5, 1.6, 1.7, 1.8, 1.9};


        for (Double number : numbers) {
            //will convert that decimal number into a whole number.
            System.out.println(number.intValue() + number);
        }
    }
}
