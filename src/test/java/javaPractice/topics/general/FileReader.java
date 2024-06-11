package javaPractice.topics.general;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileReader {
    public static void main(String[] arrgs) {
//        File file = new File("fileCreation.txt");
        File file = new File("C:\\fileCreation.txt");
        try {

            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            writer.write("Hello from created file");
            writer.flush();
            writer.close();

            BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);

            }
            reader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
