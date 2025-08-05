/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaPractise.topics.revision;

/**
 * @author admin
 */
public class SalariedEmployee extends Employee implements Displayable {
    double salary, bonus, deductions;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public SalariedEmployee() {
    }

    public SalariedEmployee(double salary, double bonus, double deductions, String name, String address, int ssn, Gender sex) {
        super(name, address, ssn, sex);
        this.salary = salary;
        this.bonus = bonus;
        this.deductions = deductions;
    }

    @Override
    public double earnings() {
        return (salary + bonus) - deductions;

    }

    public void DisplayAllDetails() {
        System.out.println(super.toString());
        System.out.println(toString());

    }

    public void DisplayEarnings() {
        System.out.println(earnings());
    }

    @Override
    public String toString() {
        return "SalariedEmployee{" + "salary=" + salary + ", bonus=" + bonus + ", deductions=" + deductions + '}';
    }


}
