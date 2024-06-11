package javaPractice.list;

import java.util.ArrayList;
import java.util.Scanner;

public class ArraysDemo {
    public class DemoSimpleArray {

        public static void main(String[] args) {
            int[] arr;
            arr = new int[10];

            int[] arr2 = new int[5];

            int[] arr3 = {1, 2, 3, 4, 5, 6, 7, 8};
            System.out.println(arr.length);
            System.out.println(arr2.length);
            System.out.println(arr3.length);
        }
    }

    public class DemoArrayList {
        public static void main(String[] args) {

            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("ismail");
            arrayList.add("ismail_1");
            arrayList.add("ismail_2");
            arrayList.add("ismail_3");
            arrayList.add("ismail_4");

            System.out.println("Currently the array list has the following items:" + arrayList);

            arrayList.add(0, "change to Mohamed");
            arrayList.add(1, "change to Saied");

            System.out.println("Currently the array list has the following items after adding new items" + arrayList);

            arrayList.remove(3);
            arrayList.remove("ismail_4");

            System.out.println("Currently the array list has the following items after deleting items" + arrayList);
        }
    }

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

    public class DemoArrayListConcept {

        public static void main(String[] args) {


            //ArrayList is a class in Java

            ArrayList ar = new ArrayList();

            ar.add(100);//0
            ar.add(200);//1
            ar.add(300);//2

            System.out.println(ar.size()); //size of arraylist

            ar.add(400);//3
            ar.add(500);//4

            System.out.println(ar.size());

            ar.remove(3);

            System.out.println(ar.size());

            ar.add("Tom");
            ar.add(12.33);
            ar.add('M');
            ar.add(25);

            System.out.println(ar.get(0));
            System.out.println(ar.get(6));

            //System.out.println(ar.get(10)); //IndexOutOfBoundsException

            //to print all the values: use for loop:

            for (int i = 0; i < ar.size(); i++) {
                System.out.println(ar.get(i));
            }


        }

    }

    public class DayOfTheWeek {
        public static void main(String args[]) {
            String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

            Scanner input = new Scanner(System.in);
            System.out.println("Enter a number for the day of the week");
            int index = input.nextInt();
            input.close();

            System.out.println("Corresponding day: " + week[index - 1]);
        }
    }

}

