package javaPractice.topics.oop.inheritance_2;

public class E2_PersonBaseClass {
    String name;
    Double age;
    String address;
    String nationality;

    public E2_PersonBaseClass() {
        System.out.println("I'm the base Class Constructor");
    }

    public E2_PersonBaseClass(String n, Double a, String ad, String nat) {
        name = n;
        age = a;
        address = ad;
        nationality = nat;
    }


    public void setName(String n) {
        name = n;
    }

    public void setAge(Double a) {
        age = a;
    }

    public void setAddress(String ad) {
        address = ad;
    }

    public void setNationality(String nat) {
        nationality = nat;
    }

    public String getName() {
        return name;
    }

    public Double getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getNationality() {
        return nationality;
    }

    public void print_all_details() {
        System.out.println("Name = " + name + "/n Age = " + age + "/n Nationality = " + nationality + "/n Address = " + address);
    }
}
