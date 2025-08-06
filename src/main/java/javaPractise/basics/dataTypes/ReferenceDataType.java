package javaPractise.basics.dataTypes;

public class ReferenceDataType {
    /**
     * Reference data types in Java are used to store references to objects rather than the actual data itself.
     * These types include classes, interfaces, arrays, and enums.
     * - Classes for creating objects, encapsulating data and methods.
     * - Interfaces define a contract for classes to implement, allowing for polymorphism.
     * - Arrays are used to store multiple values of the same type in a single variable.
     * - Enums are special classes that represent a fixed set of constants.
     */
    public static void main(String[] args) {
        ReferenceDataType referenceDataType = new ReferenceDataType();
        referenceDataType.arrayExample();
        referenceDataType.enumExample();
        referenceDataType.enumExampleWithDescription();
    }

    public void arrayExample() {
        // Example of an array
        int[] numbers = {1, 2, 3, 4, 5};
        for (int number : numbers) {
            System.out.println("Number: " + number);
        }
    }

    public void enumExample() {
        enum days {
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
        }
        days today = days.MONDAY;
        System.out.println("Today is: " + today);
    }

    public void enumExampleWithDescription() {
        enum Days {
            MONDAY("Monday is the first day of the week"),
            TUESDAY("Tuesday is the second day of the week"),
            WEDNESDAY("Wednesday is the middle of the week"),
            THURSDAY("Thursday is the fourth day of the week"),
            FRIDAY("Friday is the last working day of the week"),
            SATURDAY("Saturday is a weekend day"),
            SUNDAY("Sunday is a weekend day");

            private String description;

            Days(String description) {
                this.description = description;
            }

            public String getDescription() {
                return description;
            }
        }

        for (Days day : Days.values()) {
            System.out.println(day + ": " + day.getDescription());
        }
    }
}
