/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaPractise.topics.revision;

/**
 * @author admin
 */
public class Revision {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Department d1 = new Department(1, "Information Systems");

        SalariedEmployee se = new SalariedEmployee(2000, 500, 50, "Ahmed", "Riyadh", 1200, Gender.male);

        d1.add_employee(se);

        HourlyEmployee he = new HourlyEmployee(100, 6, "Mohamed", "Kharj", 1500, Gender.male);

        d1.add_employee(he);

        Commission_Employee ce = new Commission_Employee(15000, 0.25, "Fatema", "Riyadh", 1200, Gender.female);

        d1.add_employee(ce);

        System.out.println(d1.getEmployeeCount());

        //  d1.print_basic_data();

        d1.print_All_details();

    }

}
