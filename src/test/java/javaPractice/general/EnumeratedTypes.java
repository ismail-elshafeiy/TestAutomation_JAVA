package javaPractice.general;


enum Gender {Male, Female};

enum Course {DataBase, Programming, Math};

public class EnumeratedTypes {
    String stName;
    Gender stGender;
    Course crs;

    public EnumeratedTypes() {
        stName = "ismail";
        stGender = Gender.Male;
        crs = Course.Math;
    }
}
