package javaPractice.arrays;

import java.util.ArrayList;

public class DemoArrayList2 {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        numbers.add(10);
        numbers.add(100);
        numbers.add(200);
        numbers.add(300);
        numbers.add(400);

//        System.out.println(numbers);
//        System.out.println(numbers.get(1));

        for (int i = 0; i < numbers.size(); i++) {

            System.out.println(numbers.get(i));
        }
        numbers.remove(numbers.size() - 1);
        numbers.remove(0);
    }
}
