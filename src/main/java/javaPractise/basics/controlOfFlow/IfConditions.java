package javaPractise.basics.controlOfFlow;

/**
 * if conditions is control flow statements used to execute code based on whether a condition is true or false
 * We have 5 types of if conditions:
 * 1. Simple if statement -> executes a block of code if the specified condition is true.
 * 2. if-else statement -> executes one block of code if the condition is true, and another block if it is false.
 * 3. if-else-if -> allows you to check multiple conditions sequentially. if the first condition is false, it checks the next condition, and so on.
 * 4. nested if statement -> an if statement inside another if statement. This allows for more complex decision-making.
 * 5. shorthand if-else (also known as ternary operator) -> write an if-else statement in a single line.
 * -------------------------------------------------------------------------------------------------
 * Logical Operators for comparing conditions:
 * 1. && (AND) -> true if both operands are true.
 * 2. || (OR) -> true if at least one operand is true.
 * 3. ! (NOT) -> inverts the truth value of the operand.
 * 4. ^ (XOR) -> true if exactly one of the operands is true
 * 5. == (Equality) -> true if both operands are equal.
 * 6. != (Inequality) -> true if operands are not equal.
 * 7. > (Greater than) -> true if the left operand is greater than the right operand.
 * 8. < (Less than) -> true if the left operand is less than the right operand.
 * 9. >= (Greater than or equal to) -> true if the left operand is greater than or equal to the right operand.
 * 10. <= (Less than or equal to) -> true if the left operand is less than or equal to the right operand.
 * Order of logical operators --> () --> ! --> && --> || --> ^ --> == --> != --> > --> < --> >= --> <=
 * -------------------------------------------------------------------------------------------------
 * Notes (Tips and Tricks) / Best Practices:
 * Use boolean expressions directly (avoid == true or == false).
 * Always use curly braces {}, even for one-line blocks.
 * Avoid deep nesting — use else if, return, or separate methods..
 * Use the shorthand/ternary operator ?: for simple value assignments.
 * Avoid repeating conditions — store them in a variable if reused.
 * Use switch statements for multiple constant value checks and it's better for performance.
 * Name your conditions clearly (e.g., isUserLoggedIn, hasAccessRights).
 *
 */
public class IfConditions {
    public static void main(String[] args) {
        IfConditions ifConditions = new IfConditions();
        ifConditions.ifSimpleExample();
        ifConditions.ifElseIfExample();
        ifConditions.ifNestedExample();
        ifConditions.ifShorthandExample();
        ifConditions.iFShorthandNestedExample();
    }

    public void ifSimpleExample() {
        byte a = 10;
        byte b = 20;
        // Using if statement
        if (a < b) {
            System.out.println("a is less than b");
        }
    }

    public void ifElseIfExample() {
        int score = 85;
        if (score >= 90) {
            System.out.println("Grade: A");
        } else if (score >= 80) {
            System.out.println("Grade: B");
        } else if (score >= 70) {
            System.out.println("Grade: C");
        } else if (score >= 60) {
            System.out.println("Grade: D");
        } else {
            System.out.println("Grade: F");
        }
    }

    public void ifNestedExample() {
        int age = 20;
        if (age >= 18) {
            System.out.println("You are an adult.");
            if (age >= 65) {
                System.out.println("You are a senior citizen.");
            } else {
                System.out.println("You are not a senior citizen.");
            }
        } else {
            System.out.println("You are a minor.");
        }
    }

    public void ifShorthandExample() {
        int a = 5;
        int b = 10;
        String result = (a > b) ? "a is greater than b" : "a is not greater than b";
        System.out.println(result);
    }

    public void iFShorthandNestedExample() {
        int x = 10;
        int y = 20;
        String result = (x > y) ? "x is greater than y" : (x < y) ? "x is less than y" : "x is equal to y";
        System.out.println(result);
    }

}

