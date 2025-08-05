package javaPractise.advancedTopics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class JavaAdvancedSorting {


    public static class Main {
        public static void main(String[] args) {
            // Create a list of cars
            ArrayList<Car> myCars = new ArrayList<Car>();
            myCars.add(new Car("BMW", "X5", 1999));
            myCars.add(new Car("Honda", "Accord", 2006));
            myCars.add(new Car("Ford", "Mustang", 1970));
            // Use a comparator to sort the cars
            //   Comparator myComparator = new SortByYear();
            //Collections.sort(myCars, myComparator);
            // Use a lambda expression to sort the cars
            Collections.sort(myCars, (obj1, obj2) -> {
                Car a = (Car) obj1;
                Car b = (Car) obj2;
                if (a.year < b.year) return -1;
                if (a.year > b.year) return 1;
                return 0;
            });

            // Display the cars
            for (Car car : myCars) {
                System.out.println("Brand: [ " + car.brand + " ], Model: [ " + car.model + " ], Year: [ " + car.year + " ]");
            }
        }

        static class Car {
            public String brand;
            public String model;
            public int year;

            public Car(String b, String m, int y) {
                brand = b;
                model = m;
                year = y;
            }
        }

        // Create a comparator
        static class SortByYear implements Comparator {
            public int compare(Object obj1, Object obj2) {
                // Make sure that the objects are Car objects
                Car a = (Car) obj1;
                Car b = (Car) obj2;
                // Compare the year of both objects
                if (a.year < b.year) return -1; // The first car has a smaller year
                if (a.year > b.year) return 1;  // The first car has a larger year
                return 0; // Both cars have the same year
            }
        }
    }
}
