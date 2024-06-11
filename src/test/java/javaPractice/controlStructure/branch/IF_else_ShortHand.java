package javaPractice.controlStructure.branch;

public class IF_else_ShortHand {

    private static IF_else_ShortHand ifElseShortHand;

    public IF_else_ShortHand() {
    }

    public static IF_else_ShortHand getInstance() {
        if (ifElseShortHand == null) {
            ifElseShortHand = new IF_else_ShortHand();
        }
        return ifElseShortHand;
    }

    /**
     * This is an example of a shorthand if...else statement.
     * If the variable time is less than 18, the value of the variable result is "Good day." Otherwise, the value of result is "Good evening."
     * variable = (condition) ? expressionTrue :  expressionFalse;
     */
    public static void main(String[] args) {
        IF_else_ShortHand.getInstance()
                .basicShorthandIfElse()
                .nestedTernaryOperator()
                .usingTernaryOperatorWithStrings()
                .ternaryOperatorWithBooleanExample()
                .ternaryOperatorWithMethodCalls();
    }

    public IF_else_ShortHand basicShorthandIfElse() {
        System.out.println("Basic Shorthand If Else ======================");
        int a = 10;
        int b = 20;
        // Using ternary operator to find the maximum value
        int max = (a > b) ? a : b;
        System.out.println("Maximum value: " + max);
        // Using ternary operator to find the minimum value
        int min = (a < b) ? a : b;
        System.out.println("Minimum value: " + min);
        return this;
    }

    public IF_else_ShortHand nestedTernaryOperator() {
        System.out.println("Nested Ternary Operator ======================");
        int a = 10;
        int b = 20;
        int c = 15;
        // Using nested ternary operators to find the maximum value
        int max = (a > b) ? (a > c ? a : c) : (b > c ? b : c);
        System.out.println("Maximum value: " + max);
        return this;
    }

    public IF_else_ShortHand usingTernaryOperatorWithStrings() {
        System.out.println("Using Ternary Operator With Strings ======================");
        int age = 18;
        // Using ternary operator to determine if a person is an adult
        String result = (age >= 18) ? "Adult" : "Minor";
        System.out.println("The person is an: " + result);
        return this;
    }

    public IF_else_ShortHand ternaryOperatorWithBooleanExample() {
        boolean isRaining = true;
        // Using ternary operator to decide an action based on a boolean expression
        String action = isRaining ? "Take an umbrella" : "Enjoy the sunshine";
        System.out.println("Action: " + action);
        return this;
    }

    public IF_else_ShortHand ternaryOperatorWithMethodCalls() {
        System.out.println("Ternary Operator With Method Calls ======================");
        int number = 5;

        // Using ternary operator to decide which method to call
        String result = (number % 2 == 0) ? evenMethod() : oddMethod();
        System.out.println("The number is: " + result);
        return this;
    }

    public static String evenMethod() {
        return "The number is even.";
    }

    public static String oddMethod() {
        return "The number is odd.";
    }

}

