package javaPractice.oop.constructor;

public class MyFirstOOPProject {
    public static void main(String[] args) {

        Employee el = new Employee(100, "Ahmed", true);
        el.print_emp_data();

        Employee e2 = new Employee(300, "ismail", 5000, 300, "Quality", true);
        e2.print_emp_data();
        System.out.println("Before set salary ");
        e2.set_salary(7000, 500);
        e2.print_emp_data();
    }
}
