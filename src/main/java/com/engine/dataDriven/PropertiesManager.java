package com.engine.dataDriven;

import com.engine.actions.FileActions;
import com.engine.reports.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.MutableCapabilities;

import java.io.*;
import java.util.*;

public class PropertiesManager {
    //@Getter
    public static final String CUSTOM_PROPERTIES_FOLDER_PATH = "src/main/resources/properties";
    private static Properties properties;
    private static FileOutputStream out;

    /**
     * Loads properties from a list of properties files.
     *
     * @param propertiesFiles A list of file paths to properties files.
     * @return A Properties object containing all loaded properties.
     */
    public static Properties loadAllFiles(List<String> propertiesFiles) {
        properties = new Properties();
        try {
            for (String filePath : propertiesFiles) {
                try (FileInputStream fileInput = new FileInputStream(FileActions.getDir() + filePath)) {
                    Properties tempProp = new Properties();
                    tempProp.load(fileInput);
                    properties.putAll(tempProp);
                }
            }
            Logger.logConsole("Loaded all properties files.");
        } catch (IOException e) {
            Logger.logError("Warning !! Can not Load All File.");
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Loads all properties files from the properties folder path.
     *
     * <p> - This method retrieves all `.properties` files from the specified folder path. </p>
     * <p> - loads them into the `properties` object, and merges them with the system properties object. </p>
     *
     * @return A `Properties` object containing all loaded properties.
     */
    public static Properties loadAllProperties() {
        properties = new Properties();
        try {
            Collection<File> propertiesFilesList = FileUtils.listFiles(new File(CUSTOM_PROPERTIES_FOLDER_PATH), new String[]{"properties"}, true);
            for (File propertyFile : propertiesFilesList) {
                try (FileInputStream fileInput = new FileInputStream(propertyFile)) {
                    Logger.logInfoStep("Loading properties file: [ " + propertyFile.getName() + " ]");
                    properties.load(fileInput);
                } catch (IOException ioe) {
                    Logger.logWarning("Error loading properties file: [ " + propertyFile.getName() + " ]");
                    Logger.logError(ioe.getMessage());
                }
            }
            properties.putAll(System.getProperties());
            System.getProperties().putAll(properties);
            Logger.logConsole("Loaded all properties files.");
        } catch (Exception e) {
            Logger.logError("Warning !! Can not Load All File.");
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Retrieves properties from a specified properties file and returns them as a Map.
     *
     * @param propertyFileName The name of the properties file (without the .properties extension).
     * @return A Map containing the properties from the specified file.
     */
    public static Map<String, String> getPropertiesAsMap(String propertyFileName) {
        properties = new Properties();
        String propPath = getPropertyFilePath(propertyFileName);
        Map<String, String> map = new HashMap<>();
        try (FileInputStream input = new FileInputStream(propPath)) {
            properties.load(input); // Load properties file
            properties.forEach((key, value) -> map.put(key.toString(), value.toString())); // Convert to Map
            Logger.logConsole("Successfully retrieved properties as Map from file: [ " + propPath + " ]");
        } catch (IOException e) {
            Logger.logError("Failed to retrieve properties as Map from file: [ " + propPath + " ]");
            e.printStackTrace();
        }
        return map;
    }

    public static void addNewPropertiesFromMap(String propertyFileName, Map<String, String> newProperties) {
        properties = new Properties();
        String propPath = getPropertyFilePath(propertyFileName);
        try (FileInputStream input = new FileInputStream(propPath);
             FileOutputStream output = new FileOutputStream(propPath)) {
            properties.load(input); // Load existing properties
            newProperties.forEach((key, value) -> {
                if (!properties.containsKey(key)) {
                    properties.setProperty(key, value); // Add only if the key does not exist
                }
            });
            properties.store(output, null); // Save updated properties
            Logger.logInfoStep("Successfully added new properties from Map in file: [ " + propPath + " ]");
        } catch (IOException e) {
            Logger.logError("Failed to add new properties from Map in file: [ " + propPath + " ]");
            e.printStackTrace();
        }
    }

    public static void updatePropertiesFromMap(String propertyFileName, Map<String, String> updates) {
        properties = new Properties();
        String propPath = getPropertyFilePath(propertyFileName);
        try (FileInputStream input = new FileInputStream(propPath);
             FileOutputStream output = new FileOutputStream(propPath)) {
            properties.load(input); // Load existing properties
            updates.forEach((key, value) -> {
                Logger.logConsole("Key: [ " + key + " ] , Value: [ " + value + " ]");
                if (properties.containsKey(key)) {
                    Logger.logConsole("Updating key: [ " + key + " ] with value: [ " + value + " ]");
                    properties.setProperty(key, value); // Update only if the key exists
                }
            });
            properties.store(output, null); // Save updated properties
            Logger.logInfoStep("Successfully updated properties from Map in file: [ " + propPath + " ]");
        } catch (IOException e) {
            Logger.logError("Failed to update properties from Map in file: [ " + propPath + " ]");
            e.printStackTrace();
        }
    }


    /**
     * Retrieves the value of a specified property from a given properties file.
     *
     * @param propertyFileName The name of the properties file (without the .properties extension).
     * @param key              The key of the property to retrieve.
     * @return The value of the specified property, or null if an error occurs.
     */
    public static String getPropertyValue(String propertyFileName, String key) {
        properties = new Properties();
        String propPath = getPropertyFilePath(propertyFileName);
        try (FileReader reader = new FileReader(propPath)) {
            properties.load(reader);
            Logger.logConsole("Property value for [ " + key + " ] is: [" + properties.getProperty(key) + "] from file: [ " + propertyFileName + " ]");
            return properties.getProperty(key);
        } catch (IOException e) {
            Logger.logError(e.getMessage() + " Error accessing file: " + propPath);
            e.printStackTrace();
        }
        return null;
    }

    public static String getPropertyValue(String key) {
        return System.getProperty(key);
    }

    /**
     * Sets the value of a specified property in a given properties file.
     *
     * @param propertyFileName The name of the properties file (without the .properties extension).
     * @param key              The key of the property to set.
     * @param value            The value to set for the specified key.
     */
    public static void setPropertyValue(String propertyFileName, String key, String value) {
        properties = new Properties();
        String propPath = getPropertyFilePath(propertyFileName);
        try (FileInputStream input = new FileInputStream(propPath);
             FileOutputStream output = new FileOutputStream(propPath)) {
            properties.load(input); // Load existing properties
            properties.setProperty(key, value); // Set the new value for the key
            properties.store(output, null); // Save updated properties
        } catch (IOException e) {
            Logger.logError(e.getMessage() + " Error accessing file: " + propPath);
            e.printStackTrace();
        }
    }

    public static void removeKeyFromProperties(String propertyFileName, String key) {
        properties = new Properties();
        String propPath = getPropertyFilePath(propertyFileName);
        try (FileInputStream input = new FileInputStream(propPath);
             FileOutputStream output = new FileOutputStream(propPath)) {
            properties.load(input);
            if (properties.containsKey(key)) {
                properties.remove(key);
                // properties.store(output, null);
                Logger.logInfoStep("Successfully removed key: [ " + key + " ] from properties file: [ " + propPath + " ]");
            } else {
                Logger.logWarning("Key not found in properties file: [ " + key + " ]");
            }

        } catch (IOException e) {
            Logger.logError("Failed to remove key from properties file: [ " + propPath + " ]");
            e.printStackTrace();
        }
    }

    public static boolean isPropertyKeyExists(String propertyFileName, String key) {
        properties = new Properties();
        String propPath = getPropertyFilePath(propertyFileName);
        try (FileInputStream input = new FileInputStream(propPath)) {
            properties.load(input);
            boolean exists = properties.containsKey(key);
            Logger.logInfoStep("Key exists [ " + exists + " ] in properties file: [ " + key + " ]");
            return exists;
        } catch (IOException e) {
            Logger.logError("Failed to check if key exists in properties file: [ " + propPath + " ]");
            e.printStackTrace();
        }
        return false;
    }

    public static MutableCapabilities getCustomWebDriverDesiredCapabilities() {
        MutableCapabilities customDriverOptions = new MutableCapabilities();
        java.util.Properties props = System.getProperties();
        props.forEach((key, value) -> {
            if (String.valueOf(key).toLowerCase().startsWith("capabilities.") && !String.valueOf(value).isBlank()) {
                customDriverOptions.setCapability(String.valueOf(key).split("capabilities.")[1], String.valueOf(value));
            }
        });
        return customDriverOptions;
    }

    private static String getPropertyFilePath(String fileName) {
        String filePath = CUSTOM_PROPERTIES_FOLDER_PATH + "/" + fileName + ".properties";
        Logger.logConsole("Property file path: [ " + filePath + " ]");
        return filePath;
    }

}