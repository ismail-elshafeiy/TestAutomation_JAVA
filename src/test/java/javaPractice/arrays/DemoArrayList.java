package javaPractice.arrays;

import java.util.ArrayList;

public class DemoArrayList {
    public static void main(String[] args) {

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("ismail");
        arrayList.add("ismail_1");
        arrayList.add("ismail_2");
        arrayList.add("ismail_3");
        arrayList.add("ismail_4");

        System.out.println("Currently the array list has the following items:" + arrayList);

        arrayList.add(0, "change to Mohamed");
        arrayList.add(1, "change to Saied");

        System.out.println("Currently the array list has the following items after adding new items" + arrayList);

        arrayList.remove(3);
        arrayList.remove("ismail_4");

        System.out.println("Currently the array list has the following items after deleting items" + arrayList);
    }
}
