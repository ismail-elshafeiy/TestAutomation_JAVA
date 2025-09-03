package javaPractise.oop_2.phone;

public class B_PhoneBillCalculator {
    public static void main(String args[]) {
        A_PhoneBill bill = new A_PhoneBill(123456);
        bill.setMinutesUsed(1000);
        bill.printItemizedBill();
    }
}
