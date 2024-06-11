package javaPractice.topics.oop.inheritance_2;


public class E3_Employee extends E2_PersonBaseClass {

    double salary;
    String rank;
    String job;

    public E3_Employee() {

    }

    public E3_Employee(String n, double a, String ad, String nat, double sal, String r, String j) {
        super(n, a, ad, nat);
        salary = sal;
        rank = r;
        job = j;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double s) {
        salary = s;
    }

    @Override
    public void print_all_details() {
        super.print_all_details();
        System.out.println("job = " + job + "Rank = " + rank + "n/ Salary = " + salary);
    }
}

























