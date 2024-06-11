package javaPractice.list;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * Java Iterator
 * An Iterator is an object that can be used to loop through collections, like ArrayList and HashSet.
 * It is called an "iterator" because "iterating" is the technical term for looping.
 */
public class IteratorExamples {
    private static IteratorExamples instance;

    public IteratorExamples() {
    }

    public static IteratorExamples getInstance() {
        if (instance == null) {
            instance = new IteratorExamples();
        }
        return instance;
    }

    public static void main(String[] args) {
        IteratorExamples.getInstance()
                .basic()
        ;
    }

    public IteratorExamples basic() {
        // Creating an ArrayList of String type
        ArrayList<String> list = new ArrayList<>();

        // Adding elements to the ArrayList
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        // Get the iterator
        Iterator<String> it = list.iterator();
        // Print the first item
        // Loop through a collection
        if (it.hasNext()) {
            do {
                System.out.println(it.next());
            } while (it.hasNext());
        }
        return this;
    }
}
