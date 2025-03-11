package javaPractice.dataStructure.arrays;

import java.util.Arrays;

public class ArraysAndMultiDimensional {
    /**
     * Arrays are used to store multiple values in a single variable, instead of declaring separate variables for each value.
     * To declare an array, define the variable type with square brackets []
     */
    public static void main(String[] args) {
        ArraysAndMultiDimensional arraysAndMultiDimensional = new ArraysAndMultiDimensional();
        arraysAndMultiDimensional.array();
        arraysAndMultiDimensional.multiDimensional2Array();
        arraysAndMultiDimensional.multiDimensional3Array();
    }

    public void array() {
        System.out.println(" 1 D Array        =====================================");
        // 1 D Array
        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
        System.out.println(cars[0]);
        System.out.println(cars[1]);
        System.out.println("Length of cars array: " + cars.length);
        //Change an Array Element
        cars[1] = "Toyota";
        for (String car : cars) {
            System.out.println(car);
        }
    }

    public void multiDimensional2Array() {
        System.out.println(" 2 D Array        =====================================");
        // Multi-Dimensional Arrays - 2 D Array
        String[][] cars2 = {
                {"Volvo", "BMW", "Ford", "Mazda"},
                {"Volvo1", "BMW1", "Ford1", "Mazda1"},
                {"Volvo2", "BMW2", "Ford2", "Mazda2"},
                {"Volvo3", "BMW3", "Ford3", "Mazda3"}
        };
        System.out.println("cars2[0][1] = " + cars2[0][1]); // "BMW
        System.out.println("cars2[1][0] = " + cars2[1][0]); // "Volvo1
        System.out.println("cars2[2][2] = " + cars2[2][2]); // "Ford2
        System.out.println("cars2[3][3] = " + cars2[3][3]); // "Mazda3
        System.out.println("Length of cars2 array: " + cars2.length);
        for (String[] car : cars2) {
            System.out.println("=====================================");
            System.out.println("Array of cars: [ " + Arrays.toString(car) + " ]");
            System.out.println("Length of car array: [ " + car.length + " ]");

            for (String c : car) {
                System.out.println("The car is: [ " + c + " ]");
            }
        }
    }

    public void multiDimensional3Array() {
        System.out.println(" 3 D Array        =====================================");
        // Multi-Dimensional Arrays - 3 D Array
        String[][][] cars3 = {
                {
                        {"Volvo", "BMW", "Ford", "Mazda"},
                        {"Volvo1", "BMW1", "Ford1", "Mazda1"},
                        {"Volvo2", "BMW2", "Ford2", "Mazda2"},
                        {"Volvo3", "BMW3", "Ford3", "Mazda3"}
                },
                {
                        {"Volvo4", "BMW4", "Ford4", "Mazda4"},
                        {"Volvo5", "BMW5", "Ford5", "Mazda5"},
                        {"Volvo6", "BMW6", "Ford6", "Mazda6"},
                        {"Volvo7", "BMW7", "Ford7", "Mazda7"}
                },
                {
                        {"Volvo8", "BMW8", "Ford8", "Mazda8"},
                        {"Volvo9", "BMW9", "Ford9", "Mazda9"},
                        {"Volvo10", "BMW10", "Ford10", "Mazda10"},
                        {"Volvo11", "BMW11", "Ford11", "Mazda11"}
                }
        };
        System.out.println("cars3[0][0][1] = " + cars3[0][0][1]); // "BMW
        System.out.println("cars3[1][0][0] = " + cars3[1][0][0]); // "Volvo4
        System.out.println("cars3[2][2][2] = " + cars3[2][2][2]); // "Ford10
        for (String[][] car : cars3) {
            System.out.println("=====================================");
            for (String[] c : car) {
                System.out.println("Array of cars: [ " + Arrays.toString(c) + " ]");
                System.out.println("Length of car array: [ " + c.length + " ]");
                for (String cc : c) {
                    System.out.println("The car is: [ " + cc + " ]");
                }
            }
        }
    }

    /**
     * Jagged Array is an array of arrays with different number of columns in each row.
     */
    public void JaggedArray() {
        System.out.println(" Jagged Array        =====================================");
        // Declare a jagged array
        int[][] jaggedArray = new int[3][];
        jaggedArray[0] = new int[3];
        jaggedArray[1] = new int[4];
        jaggedArray[2] = new int[2];

        // Accessing elements in a jagged array
        System.out.println("Element at [0][1]: " + jaggedArray[0][1]);
        System.out.println("Element at [2][3]: " + jaggedArray[2][3]);
        // Iterating over a jagged array
        int count = 0;
        for (int i = 0; i < jaggedArray.length; i++) {
            for (int j = 0; j < jaggedArray[i].length; j++) {
                jaggedArray[i][j] = count++;
            }
        }
        for (int i = 0; i < jaggedArray.length; i++) {
            for (int j = 0; j < jaggedArray[i].length; j++) {
                System.out.print(jaggedArray[i][j] + " ");
            }
            System.out.println();
        }
    }
}