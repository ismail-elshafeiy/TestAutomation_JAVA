package javaPractise.topics.general;

public class ClassStaticVar {

	static int age = 25;

	public static void testStatic() {
		System.out.println("This is a static Method");
	}

	public static void main(String[] args) {
		System.out.println("Most people gradute from school at " + ClassStaticVar.age + " Years old");
		ClassStaticVar.testStatic();
	}

}
