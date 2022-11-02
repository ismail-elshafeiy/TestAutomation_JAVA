package engine;

import com.github.javafaker.File;
import engine.tools.Logger;
import org.apache.commons.io.FileUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class PropertiesReader {
    private static FileReader reader = null;
    private static String propRoot = "src/main/resources/";
    private static Properties p = new Properties();

    public static String getProperty (String propertyFileName, String propertyName) {
        String propPath = propRoot + propertyFileName;

        try {
            reader = new FileReader(propPath);
        } catch (FileNotFoundException e) {
            Logger.logMessage("No file found in the given path: " + propPath);
            e.printStackTrace();
        }

        try {
            p.load(reader);
        } catch (IOException e) {
            Logger.logMessage("Couldn't find any properties with the given property name: " + propertyName);
            e.printStackTrace();
        }
        return p.getProperty(propertyName);
    }

/*    public static void loadProperties () {
        Properties properties = new Properties();
        Collection<File> propertiesFilesList;
        propertiesFilesList = FileUtils.listFiles(new File(propRoot), new String[]{"properties"}, true);
        propertiesFilesList.forEach(propertyFile -> {
            try {
                properties.load(new FileInputStream(propertyFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            properties.putAll(System.getProperties());
            System.getProperties().putAll(properties);
        });
    }*/
}