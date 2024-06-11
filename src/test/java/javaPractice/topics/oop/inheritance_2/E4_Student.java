package javaPractice.topics.oop.inheritance_2;

public class E4_Student extends E2_PersonBaseClass {
    int study_level;
    String specialization;
    double GPA;

    public E4_Student() {
        System.out.println("I'm the Derived Class Constructor");

    }

    public E4_Student(String n, double a, String ad, String nat, int lvl, String special, double gpa) {
        // super is called the constructor in the base Class
        super(n, a, ad, nat);
        study_level = lvl;
        specialization = special;
        GPA = gpa;

    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double gpa) {
        GPA = gpa;
    }


    public int getStudy_level() {
        return study_level;
    }

    public void setStudy_level(int lvl) {
        study_level = lvl;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String special) {
        specialization = special;
    }
}
