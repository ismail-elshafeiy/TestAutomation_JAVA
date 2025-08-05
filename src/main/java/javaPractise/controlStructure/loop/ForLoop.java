package javaPractise.controlStructure.loop;

import java.util.Scanner;

public class ForLoop {

    /*
     * LOOP BREAK
     * Search a String to determine if it contains the letter ‘A’.
     */

    public static void main(String args[]) {

        //Get text
        System.out.println("Enter some text:");
        Scanner scanner = new Scanner(System.in);
        String text = scanner.next();
        scanner.close();

        boolean letterFound = false;

        //Search text for letter A
        for (int i = 0; i < text.length(); i++) {
            char currentLetter = text.charAt(i);
            if (currentLetter == 'A' || currentLetter == 'a') {
                letterFound = true;
                break;
            }
        }

        if (letterFound) {
            System.out.println("This text contains the letter 'A'");
        } else {
            System.out.println("This text does not contain the letter 'A'");
        }
    }

    public static class ForLoop2 {

        public static void main(String[] args) {
            for (int i = 0; i < 5; i++) {
                System.out.println("The loop control variable value is : " + i);
            }
        }

    }
    public static class DoWhileLoop {

        public static void main(String[] args) {
            int i = 0;
            do {
                System.out.println("The Variable value is : " + i);
                i++;

            } while (i < 5);
        }

    }
}
