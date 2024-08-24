package javaPractice.topics.hackerRank;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;


//Task: You are given a date. You just need to write the method, findDay, which returns the day on that date.
// The method takes three arguments: month, day, and year. You can use the following code to create a date:
// Calendar calendar = new GregorianCalendar(year, month-1, day);
// Date date = calendar.getTime();
// Finally, you just need to write the following line of code:
// String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
// Here is the method signature:
// public static String findDay(int month, int day, int year)
// Input Format: A single line of input containing the space-separated month, day, and year, respectively, in MM DD YYYY format.
// Output Format: Output the correct day in capital letters.
// Sample Input: 08 05 2015
// Sample Output: WEDNESDAY
// link: https://www.hackerrank.com/challenges/java-date-and-time/problem
public class DateAndTime {
    public static void main(String[] args) throws IOException {
        // System.out.println(findDay(8, 5, 2015));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the month: ");
        int month = Integer.parseInt(bufferedReader.readLine().trim());
        System.out.println("Please enter the day: ");
        int day = Integer.parseInt(bufferedReader.readLine().trim());
        System.out.println("Please enter the year: ");
        int year = Integer.parseInt(bufferedReader.readLine().trim());
        bufferedReader.close();

        String dayOfWeek = findDay(month, day, year);
        if (dayOfWeek != null) {
            System.out.println(dayOfWeek);
        }
    }

    public static String findDay(int month, int day, int year) {
        // Create a Calendar instance and set the date
        Calendar calendar = new GregorianCalendar(year, month - 1, day);
        // Get the day of the week as an integer
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Array to hold the days of the week in capital letters
        String[] days = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};

        // Return the corresponding day of the week
        return days[dayOfWeek - 1];
    }
}

