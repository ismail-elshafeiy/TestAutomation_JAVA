package com.engine.dataDriven;

import com.engine.actions.FileActions;
import com.engine.reports.CustomReporter;
import org.openqa.selenium.MutableCapabilities;

import java.io.*;
import java.util.LinkedList;
import java.util.Properties;

public class PropertiesManager {


    //    @Getter
    public static final String CUSTOM_PROPERTIES_FOLDER_PATH = "src/main/resources/properties";

	private static FileReader reader = null;
	public static final String propertiesFileName = "config.properties";
	private static Properties properties;
	private static String linkFile;
	private static FileInputStream file;
	private static FileOutputStream out;

	public static Properties loadAllFiles() {
		LinkedList<String> files = new LinkedList<>();
		files.add("src/main/resources/properties/config.properties");
		files.add("src/main/resources/properties/allure.properties");
		try {
			properties = new Properties();
			for (String f : files) {
				Properties tempProp = new Properties();
				linkFile = FileActions.getDir() + f;
				file = new FileInputStream(linkFile);
				tempProp.load(file);
				properties.putAll(tempProp);
			}
			file.close();
            CustomReporter.logInfoStep("Loaded all properties files.");
			return properties;
		} catch (IOException e) {
            CustomReporter.logError("Warning !! Can not Load All File.");
			return new Properties();
		}
	}

	public static String getPropertyValue(String propertyFileName, String propertyName) {
        String propPath = CUSTOM_PROPERTIES_FOLDER_PATH + "/" + propertyFileName;
		properties = new Properties();
		try {
			reader = new FileReader(propPath);
		} catch ( FileNotFoundException e ) {
            CustomReporter.logError(e.getMessage() + " No file found in the given path: " + propPath);
			e.printStackTrace();
		}
		try {
			properties.load(reader);
		} catch ( IOException e ) {
            CustomReporter.logError(e.getMessage() + " Couldn't find any properties with the given property name: " + propertyName);
			e.printStackTrace();
		}
        CustomReporter.logInfoStep("Property value for [ " + propertyName + " ] is: [" + properties.getProperty(propertyName) + "] from file: [ " + propertyFileName + " ]");
		return properties.getProperty(propertyName);
	}

	public static void setPropertyValue(String propertiesFileName, String key, String value) {
        String propPath = CUSTOM_PROPERTIES_FOLDER_PATH + "/" + propertiesFileName;
		properties = new Properties();
		try {
			out = new FileOutputStream(propPath);
		} catch (FileNotFoundException e) {
            CustomReporter.logError(e.getMessage() + " No file found in the given path: " + propPath);
			e.printStackTrace();
		}
		try {
			properties.setProperty(key, value);
			properties.store(out, "Set value for key: " + key + " to: " + value);
		} catch (Exception e) {
            CustomReporter.logError(e.getMessage() + " Couldn't find any properties with the given property name: " + key);
			e.printStackTrace();
		}
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

}