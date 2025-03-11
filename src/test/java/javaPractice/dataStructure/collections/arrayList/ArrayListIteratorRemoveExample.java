package javaPractice.dataStructure.collections.arrayList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayListIteratorRemoveExample {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(13);
        numbers.add(18);
        numbers.add(25);
        numbers.add(40);

        Iterator<Integer> numbersIterator = numbers.iterator();
        System.out.println("List before removing odd numbers: " + numbers);
        while (numbersIterator.hasNext()) {
            Integer num = numbersIterator.next();
            System.out.println("Checking number: " + num);
            if (num % 2 != 0) {
                numbersIterator.remove();
            }
        }

        System.out.println(numbers);
    }
}
