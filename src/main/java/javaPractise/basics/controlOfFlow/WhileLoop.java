package javaPractise.basics.controlOfFlow;

public class WhileLoop {
    /**
     * While loop is used to iterate a part of the program several times.
     * Types of while loops:
     * 1. while loop -> check condition before executing the loop body.
     * 2. do-while loop -> check condition after executing the loop body.
     * -------------------------------------------------------------------------------------------
     * Notes:
     * - Use while loop when the number of iterations is not known beforehand.
     * - Use break and continue to control loop execution flow.
     * - Use do-while when you want the loop body to execute at least once.
     * - Always ensure the loop condition can eventually become false.
     */
    public static void main(String[] args) {
        WhileLoop whileLoop = new WhileLoop();
        whileLoop.whileLoopExample();
        //    whileLoop.doWhileLoopExample();
    }

    public void whileLoopExample() {
        boolean isAlarm = true;
        while (isAlarm) {
            System.out.println("Alarm is ringing");
            isAlarm = false; //
        }
    }

//    public void doWhileLoopExample() {
//        boolean isAlarm = true;
//        do {
//            System.out.println("The clock is 7:00 AM");
//        } while (isAlarm);
//
//    }
}
