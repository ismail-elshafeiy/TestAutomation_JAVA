package javaPractice.topics.oop.constructor;

public class Employee {
    int emp_id;
    String ename;
    String depart;
    double salary;
    double bonus;
    boolean resident;

    public Employee() {
        emp_id = 100;
        ename = "No Name";
        salary = 3000;
        bonus = 500;
        depart = "No assigned yet";
        resident = true;
    }

    public Employee(int idno, String n) {
        emp_id = idno;
        ename = n;
    }

    public Employee(int indo, String n, boolean r) {
//        emp_id = indo;
//        ename = n;
        // constructor chain --> this = work on current object
        this(indo, n);
        resident = r;

    }

    public Employee(int indo, String n, double s, double b, String d, boolean r) {
//
        this(indo, n, r);
        depart = d;
        salary = s;
        bonus = b;
    }

    public void set_salary(double s) {
        salary = s;
    }

    public void set_salary(double s, double b) {

        this.set_salary(s); // Or set_salary(s);
        bonus = b;
    }


    public void print_emp_data() {
        System.out.println("ID " + emp_id);
        System.out.println("Name " + ename);
        System.out.println("Depart " + depart);
        System.out.println("Salary " + salary);
        System.out.println("Bonus " + bonus);
        System.out.println("Resident " + resident);
    }
}