package java.coursesJava;

public class TestLogicalOperators {

	public static void main(String[] args) {

		boolean x = 100 > 99, y = 99 > 100;

		// Logical operator "&&" AND
		System.out.println("What is the result of 100 > 99 && 99 > 100 ? " + (x && y));

		// Logical OR || operator
		System.out.println("What is the result of 100 > 99 || 99 > 100 ? " + (x || y));

		//Logical operator XOR ^ 
		System.out.println("What is the result of 100 > 99 ^ 99 > 100 ? " + (x ^ y));


		// Logical operator Not !
		System.out.println("What is the result of not 100 > 99 ? " + (!x));

		// Logical operator Not !
		System.out.println("What is the result of not 100 > 99 ? " + (!y));

	}

}
