package java.coursesJava;

public class MyCalcuation extends Calculation {
	public void muliplication(int fNum, int secNum) {
		total = fNum * secNum;
		System.out.println("The prodcut of the given numbers:" + total);
	}

	public static void main(String[] args) {
		MyCalcuation myCalc = new MyCalcuation();
		myCalc.addition(5, 6);
		myCalc.Subtraction(10, 5);
		myCalc.muliplication(5, 5);
	}

}
