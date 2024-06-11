package javaPractice.hash;

import javaPractice.list.ArrayListExamples;

public class HashSetExamples {
    private static HashSetExamples instance;

    public HashSetExamples() {
    }

    public static HashSetExamples getInstance() {
        if (instance == null) {
            instance = new HashSetExamples();
        }
        return instance;
    }

    public static void main(String[] args) {
        HashSetExamples.getInstance();
    }
}
