package javaPractice.topics.general;

public class TestLocalVar {


	public void testLocalVariable() {
		int age = 34;
		System.out.println("How old are you ?" + age);
	}

	public void testSameLocalVariable() {
		int age = 38;
		System.out.println("How old are you ?" + age);
	}

	public void testParameterVariable(int age) {
		System.out.println("My age is : " + age + " Years old");
	}

	public static void main(String[] args) {
		TestLocalVar testClass = new TestLocalVar();
		//testClass.testLocalVariable();
		//testClass.testSameLocalVariable();
		testClass.testParameterVariable(34);

	}

}
