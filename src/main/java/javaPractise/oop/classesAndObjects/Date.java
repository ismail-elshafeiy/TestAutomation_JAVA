package javaPractise.oop.classesAndObjects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date {
    public static void main(String[] args) {
        dateFormat_dd_MM_yyyy_HH_mm_ss();
        dateFormat_2_dd_MM_yyyy_HH_mm_ss();
        dateFormat_3_dd_MMM_yyyy_HH_mm_ss();
        dateFormat_4_E_MMM_dd_yyyy();
    }

    public static void dateFormat_dd_MM_yyyy_HH_mm_ss() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println(" Formatting [ " + myDateObj + "] to be [ " + formattedDate + " ]");
    }

    public static void dateFormat_2_dd_MM_yyyy_HH_mm_ss() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println(" Formatting [ " + myDateObj + "] to be [ " + formattedDate + " ]");
    }

    public static void dateFormat_3_dd_MMM_yyyy_HH_mm_ss() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println(" Formatting [ " + myDateObj + "] to be [ " + formattedDate + " ]");
    }

    public static void dateFormat_4_E_MMM_dd_yyyy() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println(" Formatting [ " + myDateObj + "] to be [ " + formattedDate + " ]");
    }

}
