package javaPractise.controlStructure.branch;

import java.util.Scanner;

public class SwitchStatements {
    private static SwitchStatements switchStatements;

    public SwitchStatements() {
    }

    public static SwitchStatements getInstance() {
        if (switchStatements == null) {
            switchStatements = new SwitchStatements();
        }
        return switchStatements;
    }

    /*
     *
     * " The Switch Statement " is interchangeable with " if-Else-if_Statement "
     * If situation A occurs, do something.
     * Else if situation B occurs, do something else.
     * Else if situation C occurs, do something else.
     *
     * type of expression does the condition : Equality
     *
     *  SWITCH
     * Have a user enter their letter grade, and using a switch statement,
     * print out a message letting them know how they did.
     *
     * just make sure to put the [break Or default] to stop the Check and End a case block within a switch statement, Because The next case will also be executed
     *
     * To choose the true and do what the user needs
     *
     */
    public static void main(String args[]) {
        String season = SwitchStatements.getInstance()
                .basicSwitchStatement()
                .SwitchEnumExample()
                .switchFallThroughExampleAndSwitchEnhanced()
                .switchAndYield()
                .ReturnSwitchExample(3);
        System.out.println("The season is: [ " + season + " ]");

    }

    Scanner scanner;

    public SwitchStatements basicSwitchStatement() {
        System.out.println("Switch Statement ======================");
        scanner = new Scanner(System.in);
        System.out.println("Enter your letter grade example: (A, B, C, D, F): ");
        String grade = scanner.next();
        switch (grade.toLowerCase().trim()) {
            case "a":
                System.out.println("Excellent job!");
                break;
            case "b":
                System.out.println("Great job!");
                break;
            case "c":
                System.out.println("Good job!");
                break;
            case "d":
                System.out.println("You passed!");
                break;
            case "f":
                System.out.println("Better luck next time!");
                break;
            default:
                System.out.println("Invalid grade, please try again! ");
        }
        return this;
    }

    enum Day {
        SUNDAY("Sunday"),
        MONDAY("Monday"),
        TUESDAY("Tuesday"),
        WEDNESDAY("Wednesday"),
        THURSDAY("Thursday"),
        FRIDAY("Friday"),
        SATURDAY("Saturday");

        Day(String day) {
        }
    }

    public SwitchStatements SwitchEnumExample() {
        System.out.println("Switch Statement with enum ======================");
        Day today = Day.MONDAY;
        switch (today) {
            case SUNDAY:
                System.out.println("Today is Sunday");
                break;
            case MONDAY:
                System.out.println("Today is Monday");
                break;
            case TUESDAY:
                System.out.println("Today is Tuesday");
                break;
            case WEDNESDAY:
                System.out.println("Today is Wednesday");
                break;
            case THURSDAY:
                System.out.println("Today is Thursday");
                break;
            case FRIDAY:
                System.out.println("Today is Friday");
                break;
            case SATURDAY:
                System.out.println("Today is Saturday");
                break;
            default:
                System.out.println("Invalid day of the week");
        }
        return this;
    }

    public SwitchStatements switchFallThroughExampleAndSwitchEnhanced() {
        System.out.println("Switch Statement with Fall Through ======================");
        System.out.println("Enter a month number: ");
        scanner = new Scanner(System.in);
        int month = scanner.nextInt();
        String season = switch (month) {
            case 12, 1, 2 -> "Winter";
            case 3, 4, 5 -> "Spring";
            case 6, 7, 8 -> "Summer";
            case 9, 10, 11 -> "Fall";
            default -> "Invalid month";
        };
        System.out.println("The season is: " + season);
        return this;
    }

    /**
     * Detailed Explanation
     * Purpose: yield is used to return a value from a block within a switch expression. This is particularly useful when the logic for determining the return value is complex and cannot be expressed in a single line.
     * Syntax: Within a case block, use yield followed by the value or expression to be returned.
     */
    public SwitchStatements switchAndYield() {
        System.out.println("Switch Statement with Yield ======================");
        System.out.println("Enter a month number: ");
        scanner = new Scanner(System.in);
        int month = scanner.nextInt();
        String season = switch (month) {
            case 12, 1, 2 -> {
                yield "Winter";
            }
            case 3, 4, 5 -> {
                yield "Spring";
            }
            case 6, 7, 8 -> {
                yield "Summer";
            }
            case 9, 10, 11 -> {
                yield "Fall";
            }
            default -> {
                yield "Invalid month";
            }
        };

        System.out.println("The season is: " + season);
        return this;
    }

    public String ReturnSwitchExample(int month) {
        System.out.println("Switch Statement with Return ======================");
        return switch (month) {
            case 12, 1, 2 -> {
                yield "Winter";
            }
            case 3, 4, 5 -> {
                yield "Spring";
            }
            case 6, 7, 8 -> {
                yield "Summer";
            }
            case 9, 10, 11 -> {
                yield "Fall";
            }
            default -> {
                yield "Invalid month";
            }
        };
    }

    public void closeScanner() {
        scanner.close();
    }
}
