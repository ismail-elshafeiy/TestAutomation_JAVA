/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java.revision;

/**
 * @author admin
 */
public class Commission_Employee extends Employee implements Displayable {

    private double gross_sales;
    private double commission_rate;

    public Commission_Employee() {
    }

    public Commission_Employee(double gross_sales, double commission_rate, String name, String address, int ssn, Gender sex) {
        super(name, address, ssn, sex);
        this.gross_sales = gross_sales;
        this.commission_rate = commission_rate;
    }


    public double getGross_sales() {
        return gross_sales;
    }

    public void setGross_sales(double gross_sales) {
        this.gross_sales = gross_sales;
    }


    public double getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(double commission_rate) {
        this.commission_rate = commission_rate;
    }

    @Override
    public double earnings() {
        return gross_sales * commission_rate;
    }

    @Override
    public String toString() {
        return "Commission_Employee{" + "gross_sales=" + gross_sales + ", commission_rate=" + commission_rate + '}';
    }

    public void DisplayEarnings() {
        System.out.println(earnings());
    }

    public void DisplayAllDetails() {
        System.out.println(super.toString());
        System.out.println(toString());
    }


}
