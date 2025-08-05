package javaPractise.advancedTopics.dataStructure.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * The ArrayList class is a resizable array, which can be found in the java.util package.
 * The difference between a built-in array and an ArrayList in Java, is that the size of an array cannot be modified
 * Unfortunately, (if you want to add or remove elements to/from an array, you have to create a new one)
 *  All ArrayList methods https://www.w3schools.com/java/java_ref_arraylist.asp
 */
public class ArrayListExamples {
    private static ArrayListExamples instance;

    public ArrayListExamples() {
    }

    public static ArrayListExamples getInstance() {
        if (instance == null) {
            instance = new ArrayListExamples();
        }
        return instance;
    }

    public static void main(String[] args) {
        ArrayListExamples.getInstance()
                //  .basic()
                // .arrayListIteration()
                .arrayListActions()
                .sortAndIsContainsArrayList()
        //  .nestedArrayListExample()
        //.customObjectArrayListExample()
        // .streamArrayListExample()
        //  .sortCustomObjectsExample()

        ;
    }

    public ArrayListExamples basic() {
        // Creating an ArrayList of String type
        ArrayList<String> arraylist = new ArrayList<>();
        // Adding elements to the ArrayList
        arraylist.add("Apple");
        arraylist.add("Banana");
        arraylist.add("Cherry");
        // Accessing elements
        System.out.println("First element: " + arraylist.get(0));  // Output: Apple
        System.out.println("Second element: " + arraylist.get(1));  // Output: Banana
        System.out.println("Third element: " + arraylist.get(2));  // Output: Cherry
        return this;
    }

    public ArrayListExamples arrayListIteration() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Dog");
        list.add("Cat");
        list.add("Cow");
        // Iterating using a for loop
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Element at index " + i + ": " + list.get(i));
        }
        // Iterating using an enhanced for loop
        int a = 0;
        for (String animal : list) {
            System.out.println(a++ + " Animal: " + animal);
        }
        return this;
    }

    public ArrayListExamples arrayListActions() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        // Adding an element at a specific index
        list.add(1, "Orange");
        System.out.println("Updated list after adding " + list);
        // Getting an element at a specific index
        System.out.println("Updated list: " + list.get(1));
        // Modifying an element at a specific index
        list.set(1, "Grapes");
        System.out.println("Updated list after Modifying : " + list);
        // Removing an element at a specific index
        list.remove(1);
        System.out.println("Updated list after removing : " + list);
        return this;
    }

    public ArrayListExamples sortAndIsContainsArrayList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("b.Cat");
        list.add("a.Dog");
        list.add("c.Cow");
        // Sorting the list
        Collections.sort(list);
        System.out.println("Sorted list: " + list);
        list.sort(Collections.reverseOrder());
        System.out.println("Reverse Order: " + list);
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("Paris");
        list2.add("London");
        list2.add("New York");
        // Check if the list contains "London"
        if (list2.contains("London")) {
            System.out.println("London is in the list.");
        } else {
            System.out.println("London is not in the list.");
        }
        // Check if the list contains "Tokyo"
        if (list2.contains("Tokyo")) {
            System.out.println("Tokyo is in the list.");
        } else {
            System.out.println("Tokyo is not in the list.");
        }
        return this;
    }

    /**
     * This example demonstrates creating an ArrayList of ArrayLists,
     * which can be useful for representing a matrix or a list of lists.
     */
    public ArrayListExamples nestedArrayListExample() {
        // Creating a nested ArrayList (ArrayList of ArrayLists)
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        // Initializing the matrix
        for (int i = 0; i < 3; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(i * 3 + j + 1);
            }
            matrix.add(row);
        }
        System.out.println("Matrix: " + matrix);
        // Displaying the matrix
        for (ArrayList<Integer> row : matrix) {
            System.out.println(row);
        }
        return this;
    }

    public class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name + " (" + age + ")";
        }
    }

    /**
     * This example shows how to use an ArrayList to store custom objects.
     */
    public ArrayListExamples customObjectArrayListExample() {
        ArrayList<Person> people = new ArrayList<>();
        // Adding custom objects to the ArrayList
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 25));
        people.add(new Person("Charlie", 35));
        // Iterating over the custom objects
        for (Person person : people) {
            System.out.println(person);
        }
        return this;
    }

    public ArrayListExamples streamArrayListExample() {
        ArrayList<String> names = new ArrayList<>();
        names.add("John");
        names.add("Jane");
        names.add("Jack");
        names.add("Jill");
        names.add("James");
        names.add("ismail");
        names.add("Ali");
        // Filtering names that start with "J" and have 4 letters
        List<String> filteredNames = names.stream()
                .filter(name -> name.startsWith("J") && name.length() == 4)
                .toList();
        System.out.println("Filtered names: " + filteredNames);  // Output: [John, Jack]
        // Converting names to uppercase
        List<String> upperCaseNames = names.stream()
                .map(String::toUpperCase)
                .toList();
        System.out.println("Uppercase names: " + upperCaseNames);  // Output: [JOHN, JANE, JACK, JILL, JAMES]
        // Sorting names in alphabetical order
        List<String> sortedNames = names.stream()
                .sorted()
                .toList();
        System.out.println("Sorted names: " + sortedNames);  // Output: [Jack, James, Jane, Jill, John]
        return this;
    }

    public class Employee {
        String name;
        double salary;

        Employee(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return name + ": $" + salary;
        }
    }

    public ArrayListExamples sortCustomObjectsExample() {
        ArrayList<Employee> employees = new ArrayList<>();

        employees.add(new Employee("Alice", 75000));
        employees.add(new Employee("Bob", 50000));
        employees.add(new Employee("Charlie", 60000));

        // Sorting employees by salary in ascending order
        Collections.sort(employees, Comparator.comparingDouble(emp -> emp.salary));
        System.out.println("\nEmployees sorted by salary:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        // Sorting employees by name in alphabetical order
        Collections.sort(employees, Comparator.comparing(emp -> emp.name));
        System.out.println("\nEmployees sorted by name:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
        return this;
    }


}
