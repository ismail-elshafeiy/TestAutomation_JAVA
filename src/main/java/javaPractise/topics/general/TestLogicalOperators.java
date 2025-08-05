package javaPractise.topics.general;

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


		int a = 25, b = 50;

		// == Equal To Operator
		System.out.println("Is 25 equal to 50 ? " + (a == b));

		// != Not Equal To Operator
		System.out.println("Is 25 not equal to 50 ? " + (a != b));

		// > Greater Than Operator
		System.out.println("Is 25 greater than 50 ? " + (a > b));

		// >= Greater Than or equal Operator
		System.out.println("Is 25 greater than or equal 50 ? " + (a >= b));

		// < Less Than Operator
		System.out.println("Is 25 Less than 50 ? " + (a < b));

		// <= Less Than or equal Operator
		System.out.println("Is 25 less than or equal 50 ? " + (a <= b));

	}

}
