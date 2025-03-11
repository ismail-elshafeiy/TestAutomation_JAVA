package javaPractice.dataStructure.hash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * A HashMap however, store items in "key/value" pairs, and you can access them by an index of another type
 * One object is used as a key (index) to another object (value).
 * It can store different types: String keys and Integer values, or the same type, like: String keys and String values:
 */
public class HashMapExamples {
    private static HashMapExamples instance;

    public HashMapExamples() {
    }

    public static HashMapExamples getInstance() {
        if (instance == null) {
            instance = new HashMapExamples();
        }
        return instance;
    }

    public static void main(String[] args) {
        HashMapExamples.getInstance()
                //  .basic()
                .wordFrequencyCounter()
        ;
    }

    public HashMapExamples basic() {
        // Create a HashMap object called capitalCities
        HashMap<String, String> capitalCities = new HashMap<>();
        // Add keys and values (Country, City)
        capitalCities.put("England", "London");
        capitalCities.put("Germany", "Berlin");
        capitalCities.put("Norway", "Oslo");
        capitalCities.put("USA", "Washington DC");
        System.out.println(capitalCities);
        System.out.println("\nGet by key [ " + capitalCities.get("England") + " ]");
        System.out.println("\nRemove by key " + capitalCities.remove("England"));
        System.out.println(capitalCities);
        System.out.println("Size of the HashMap: [ " + capitalCities.size() + " ]");
        System.out.println("\nPrint all values ");
        for (String value : capitalCities.values()) {
            System.out.println(value);
        }
        System.out.println("\nPrint all keys and values");
        for (String key : capitalCities.keySet()) {
            System.out.println("key: [ " + key + " ],  value: [ " + capitalCities.get(key) + " ]");
        }
        return this;
    }

    public HashMapExamples wordFrequencyCounter() {
        System.out.println("\nWord Frequency Counter");
        String text = "This is a test. This test is only a test.";
        // Remove punctuation and convert to lowercase
        text = text.replaceAll("[^a-zA-Z ]", "").toLowerCase();
        System.out.println("Text after replace All: [ " + text + " ]");
        String[] words = text.split("\\s+");
        System.out.println("Words after split: [ " + Arrays.toString(words) + " ]");
        HashMap<String, Integer> wordCountMap = new HashMap<>();
        for (String word : words) {
            System.out.println("Word: [ " + word + " ]");
            if (wordCountMap.containsKey(word)) {
                System.out.println("Word already exists in the map and the count is: [ " + wordCountMap.get(word) + 1 + " ]");
                wordCountMap.put(word, wordCountMap.get(word) + 1);
            } else {
                wordCountMap.put(word, 1);
            }
        }
        // Print word counts
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        return this;
    }

}
