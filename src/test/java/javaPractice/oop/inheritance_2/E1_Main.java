package javaPractice.oop.inheritance_2;

public class E1_Main {
    public static void main(String[] args) {
      /*  E4_Student std1 = new E4_Student ("ismail",18,"math","Egyption",5,"us",4.5);
        System.out.println(std1.getName());
        System.out.println(std1.getNationality());*/


   /*     // When a subClass is instantiated superClass default Constructor is executed first
        E4_Student std2 = new E4_Student();*/

        E5_Salaried_Employee sel = new E5_Salaried_Employee("ismail", 10, "cairo", "egypt",
                3000, "QC", "engineer", 500, 100);

        System.out.println(sel.getSalary());
        E3_Employee e1 = new E3_Employee("ismail", 10, "cairo", "egypt", 3000, "QC", "engineer");
        e1.print_all_details();
    }
}
