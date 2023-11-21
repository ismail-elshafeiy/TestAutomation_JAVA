/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaPractice.revision;

/**
 * @author admin
 */
public class BasePlusCommissionEmployee extends Commission_Employee {

    private double base;

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    @Override
    public double earnings() {
        return base + super.earnings();
    }

    @Override
    public void DisplayAllDetails() {
        super.DisplayAllDetails();
        this.DisplayEarnings();
    }

    @Override
    public void DisplayEarnings() {
        System.out.println("Earnings = " + earnings());

    }


}
