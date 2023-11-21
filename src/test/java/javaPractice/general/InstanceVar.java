package javaPractice.general;

public class InstanceVar {

	int age = 25;


	public void testInstanceVar() {
		//int age = 4; 
		System.out.println("Most poeple graduate from school at " + age + " Years old");
	}


	public void testInstanceVarAgain() {
		System.out.println("Most poeple graduate from school at " + age + " Years old");
	}

	public static void main(String[] args) {
		InstanceVar instObj = new InstanceVar();
		instObj.testInstanceVar();
		instObj.testInstanceVarAgain();

	}

}
