package javaPractice.general;

// Encapsulation
public class RunEncap {
    public static void main(String[] arg) {
        Encaptest encap = new Encaptest();
        encap.setAge(30);
        encap.setIdNum("is122");
        encap.setName("ismail");

        System.out.println("Name:" + encap.getName() + "Age:" + encap.getAge() + "idNum:" + encap.getIdNum());

    }
}
