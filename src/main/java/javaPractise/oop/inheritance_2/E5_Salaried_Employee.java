package javaPractise.oop.inheritance_2;

public class E5_Salaried_Employee extends E3_Employee {
    double bonus;
    double deduction;

    public E5_Salaried_Employee() {

    }

    public E5_Salaried_Employee(String n, double a, String ad, String nat, double sal, String r, String j, double b, double d) {
        super(n, a, ad, nat, sal, r, j);
        bonus = b;
        deduction = d;
    }

    // @Override is not mandatory But just to notice about this method is override to method in supClass
    @Override
    public double getSalary() {

        return salary + bonus - deduction;
    }
}
