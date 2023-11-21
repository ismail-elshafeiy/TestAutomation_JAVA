package javaPractice.general;

public class MyCalcuation {
	public void muliplication(int fNum, int secNum) {
		total = fNum * secNum;
		System.out.println("The prodcut of the given numbers:" + total);
	}

	int total;

	public void addition(int firstNum, int secondNum) {
		total = firstNum + secondNum;
		System.out.println("The sum of the given numbers: " + total);
	}

	public void Subtraction(int firstNum, int secondNum) {
		total = firstNum - secondNum;
		System.out.println("The difference between the given numbers: " + total);
	}

	public static void main(String[] args) {
		MyCalcuation myCalc = new MyCalcuation();
		myCalc.addition(5, 6);
		myCalc.Subtraction(10, 5);
		myCalc.muliplication(5, 5);
	}

}
