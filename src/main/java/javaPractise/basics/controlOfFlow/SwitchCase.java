package javaPractise.basics.controlOfFlow;

/**
 * Switch statement is a control flow statement that allows you to execute one block of code among many based on the value of an expression.
 * We have 7 types of switch cases:
 * 1. Basic switch case -> executes a block of code based on the value of an expression.
 * 2. Switch case with String -> allows you to use String values in the switch expression.
 * 3. Switch case with return -> allows you to return a value from the switch expression.
 * 4. Switch case with return value -> allows you to return a value based on the case matched.
 * 5. Switch case with multiple cases -> allows you to group multiple cases together.
 * 6. Enhanced switch case -> introduced in Java 12, allows you to use the arrow syntax (->) for cleaner code.
 * 7. Switch case with Enum -> allows you to use Enum values in the switch expression.
 * -------------------------------------------------------------------------------------------------
 * Notes (Tips and Tricks) / Best Practices:
 * - Use switch statements for multiple constant value checks.
 * - Use with one variable and equality checks.
 * - Use the enhanced switch case for cleaner code.
 * - Use enums for better type safety and readability.
 * - Always include a default case to handle unexpected values.
 * - Use break statements to prevent fall-through behavior.
 * - Use default case to handle unexpected values.
 */

public class SwitchCase {
    public static void main(String[] args) {
        SwitchCase switchCase = new SwitchCase();
        switchCase.switchCaseExample();
        switchCase.switchCaseWithStringExample();
        switchCase.switchCaseWithReturnExample();
        System.out.println(switchCase.switchCaseWithReturnValueExample(1));
        switchCase.switchCaseWithMultipleCasesExample(3);
        switchCase.switchCaseEnhancedExample(5);
        switchCase.switchCaseWithEnumExample(Day.MONDAY);
    }

    public void switchCaseExample() {
        int day = 3;
        String dayName;
        switch (day) {
            case 1:
                dayName = "Monday";
                break;
            case 2:
                dayName = "Tuesday";
                break;
            case 3:
                dayName = "Wednesday";
                break;
            case 4:
                dayName = "Thursday";
                break;
            case 5:
                dayName = "Friday";
                break;
            case 6:
                dayName = "Saturday";
                break;
            case 7:
                dayName = "Sunday";
                break;
            default:
                dayName = "Invalid day";
        }
        System.out.println("Day " + day + " is: " + dayName);
    }

    public void switchCaseWithStringExample() {
        String fruit = "Apple";
        switch (fruit) {
            case "Apple":
                System.out.println("It's an apple.");
                break;
            case "Banana":
                System.out.println("It's a banana.");
                break;
            case "Orange":
                System.out.println("It's an orange.");
                break;
            default:
                System.out.println("Unknown fruit.");
        }
    }

    public void switchCaseWithReturnExample() {
        int number = 2;
        String result = switch (number) {
            case 1 -> "One";
            case 2 -> "Two";
            case 3 -> "Three";
            default -> "Unknown number";
        };
        System.out.println("The number is: " + result);
    }

    public String switchCaseWithReturnValueExample(int d) {
        switch (d) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            default:
                return "Unknown";
        }
    }

    public void switchCaseWithMultipleCasesExample(int month) {
        String season;
        switch (month) {
            case 12, 1, 2:
                season = "Winter";
                break;
            case 3, 4, 5:
                season = "Spring";
                break;
            case 6, 7, 8:
                season = "Summer";
                break;
            case 9, 10, 11:
                season = "Autumn";
                break;
            default:
                season = "Invalid month";
        }
        System.out.println("Month " + month + " is in: " + season);
    }

    public void switchCaseEnhancedExample(int day) {
        String dayName = switch (day) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6 -> "Saturday";
            case 7 -> "Sunday";
            default -> "Invalid day";
        };
        System.out.println("Day " + day + " is: " + dayName);
    }

    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public void switchCaseWithEnumExample(Day day) {
        String dayName = switch (day) {
            case MONDAY -> "Monday";
            case TUESDAY -> "Tuesday";
            case WEDNESDAY -> "Wednesday";
            case THURSDAY -> "Thursday";
            case FRIDAY -> "Friday";
            case SATURDAY -> "Saturday";
            case SUNDAY -> "Sunday";
        };
        System.out.println("Day is: " + dayName);
    }

}
