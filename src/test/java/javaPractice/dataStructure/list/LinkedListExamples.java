package javaPractice.dataStructure.list;


import java.util.*;

/*
 * ArrayList vs. LinkedList
 * The LinkedList class has all of the same methods as the ArrayList class because they both implement the List interface.
 * This means that you can add items, change items, remove items and clear the list in the same way.
 *
 * The ArrayList class has a regular array inside it. When an element is added, it is placed into the array.
 * If the array is not big enough, a new, larger array is created to replace the old one and the old one is removed.
 *
 * The LinkedList stores its items in "containers."
 * The list has a link to the first container and each container has a link to the next container in the list.
 * To add an element to the list, the element is placed into a new container
 * and that container is linked to one of the other containers in the list.
 *
 * When To Use
 * Use an ArrayList for storing and accessing data, and LinkedList to manipulate data.
 *
 * For many cases, the ArrayList is more efficient as it is common to need access to random items in the list,
 * but the LinkedList provides several methods to do certain operations more efficiently:
 */
public class LinkedListExamples {
    private static LinkedListExamples instance;

    public LinkedListExamples() {
    }

    public static LinkedListExamples getInstance() {
        if (instance == null) {
            instance = new LinkedListExamples();
        }
        return instance;
    }

    public static void main(String[] args) {
        LinkedListExamples.getInstance()
                .basic()
                .arrayListIteration()
                .arrayListActions()
                .sortAndIsContainsLinkedList()
                .nestedArrayListExample()
                .customObjectArrayListExample()
                .streamArrayListExample()
                .sortCustomObjectsExample()
        ;
    }

    public LinkedListExamples basic() {
        System.out.println("\nLinkedListExamples basic");
        LinkedList<String> list = new LinkedList<>();
        // Adding elements to the ArrayList
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        // Accessing elements
        System.out.println("First element: " + list.get(0));  // Output: Apple
        System.out.println("Second element: " + list.get(1));  // Output: Banana
        System.out.println("Third element: " + list.get(2));  // Output: Cherry
        return this;
    }

    public LinkedListExamples arrayListIteration() {
        System.out.println("\nLinkedListExamples arrayListIteration");
        LinkedList<String> list = new LinkedList<>();
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

    public LinkedListExamples arrayListActions() {
        LinkedList<String> list = new LinkedList<>();
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

    public LinkedListExamples sortAndIsContainsLinkedList() {
        System.out.println("\nLinkedListExamples sortAndIsContainsLinkedList");
        LinkedList<String> list = new LinkedList<>();
        list.add("b.Cat");
        list.add("a.Dog");
        list.add("c.Cow");
        // Sorting the list
        Collections.sort(list);
        System.out.println("Sorted list: " + list);
        list.sort(Collections.reverseOrder());
        System.out.println("Reverse Order: " + list);
        LinkedList<String> list2 = new LinkedList<>();
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
    public LinkedListExamples nestedArrayListExample() {
        System.out.println("\nLinkedListExamples nestedArrayListExample");
        // Creating a nested ArrayList (ArrayList of ArrayLists)
        LinkedList<LinkedList<Integer>> matrix = new LinkedList<>();
        // Initializing the matrix
        for (int i = 0; i < 3; i++) {
            LinkedList<Integer> row = new LinkedList<>();
            for (int j = 0; j < 3; j++) {
                row.add(i * 3 + j + 1);
            }
            matrix.add(row);
        }
        System.out.println("Matrix: " + matrix);
        // Displaying the matrix
        for (LinkedList<Integer> row : matrix) {
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
    public LinkedListExamples customObjectArrayListExample() {
        System.out.println("\nLinkedListExamples customObjectArrayListExample");
        LinkedList<Person> people = new LinkedList<>();
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

    public LinkedListExamples streamArrayListExample() {
        System.out.println("\nLinkedListExamples streamArrayListExample");
        LinkedList<String> names = new LinkedList<>();
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

    public LinkedListExamples sortCustomObjectsExample() {
        System.out.println("\nLinkedListExamples sortCustomObjectsExample");
        LinkedList<Employee> employees = new LinkedList<>();

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
