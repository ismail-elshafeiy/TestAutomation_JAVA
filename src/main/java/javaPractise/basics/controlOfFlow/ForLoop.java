package javaPractise.basics.controlOfFlow;

public class ForLoop {
    /**
     * For loop is used to iterate a part of the program several times.
     * Logic:
     * 1. Define a counter variable.
     * 2. Initialize the counter variable.
     * 3. Check the condition for the loop to continue.
     * 4. Increment or decrement the counter variable.
     * 5. Repeat the steps until the condition is false.
     * ------------------------------------------------------------------------------------------
     * Types of for loops:
     * 1. for loop
     * 2. Nested for loop
     * 3. Enhanced for loop (for-each)
     * ------------------------------------------------------------------------------------------
     * Notes (Tips and Tricks):
     * - Use for loop when you know the iteration count, Prefer for Over while When count Is Known
     * - Use enhanced for loop (for-each) for iterating over arrays and collections.
     * - Avoid infinite loops by ensuring the loop control conditions are properly defined.
     * - Use break and continue to control loop execution flow.
     * - Minimize Logic Inside Loops, Keep the loop body simple
     */
    public static void main(String[] args) {
        ForLoop forLoop = new ForLoop();
        forLoop.forLoopExample();
        forLoop.forWithMultipleVariables();
        forLoop.nestedForLoopExample();
        forLoop.forLoopWithNoBody();
        forLoop.enhancedForLoopExample();
        forLoop.forLoopWithBreak();
        forLoop.forLoopWithContinue();
    }

    public void forLoopExample() {
        // 1- Define counter variable
        // 2- Initialize variable
        // 3- Check the condition
        // 4- Increment the counter variable
        for (int i = 0; i < 5; i++) {
            System.out.println("The loop control variable value is : " + i);
        }
    }

    public void forWithMultipleVariables() {
        for (int i = 0, j = 10; i <= 5 || j <= 20; i++, j++) {
            System.out.println("i: " + i + ", j: " + j);
        }
    }

    public void nestedForLoopExample() {
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j < 3; j++) {
                System.out.println("i: [ " + i + "] , j: [ " + j + " ]");
                for (int k = 1; k < 4; k++) {
                    System.out.println("i: [ " + i + "] , j: [ " + j + " ], k: [ " + k + " ]");
                }
            }
        }
    }

    public void forLoopWithNoBody() {
        for (int i = 0; i < 5; System.out.println(i++)) ;
    }

    public void enhancedForLoopExample() {
        // Enhanced for loop (for-each) is used to iterate over arrays and collections
        String[] names = {"Alice", "Bob", "Charlie"};
        for (String name : names) {
            System.out.println("Name: " + name);
        }
    }

    public void forLoopWithBreak() {
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                System.out.println("Breaking the loop at i = " + i);
                break; // Exit the loop when i equals 5
            }
            System.out.println("i: " + i);
        }
    }

    public void forLoopWithContinue() {
        String[] names = {"Alice", "Bob", "Charlie", "", "  ", null};
        for (String name : names) {
            if (name == null || name.isBlank()) {
                System.out.println("Skipping invalid name: " + name);
                continue;
            }
            System.out.println("Valid name: " + name);
        }
    }

}
