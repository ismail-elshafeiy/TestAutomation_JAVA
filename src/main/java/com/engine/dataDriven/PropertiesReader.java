package com.engine.dataDriven;

import com.engine.listeners.CustomReporter;
import lombok.Getter;
import org.openqa.selenium.MutableCapabilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    @Getter
    private static final String CUSTOM_PROPERTIES_FOLDER_PATH = "src/main/resources/properties";
	private static FileReader reader = null;
	public static final String propertiesFileName = "project.properties";
	private static Properties p = new Properties();


	public static String getProperty (String propertyFileName, String propertyName) {
        String propPath = getCUSTOM_PROPERTIES_FOLDER_PATH() + "/" + propertyFileName;
		try {
			reader = new FileReader(propPath);
		} catch ( FileNotFoundException e ) {
            CustomReporter.logMessage("No file found in the given path: " + propPath);
			e.printStackTrace();
		}
		try {
			p.load(reader);
		} catch ( IOException e ) {
            CustomReporter.logMessage("Couldn't find any properties with the given property name: " + propertyName);
			e.printStackTrace();
		}
        CustomReporter.logStep("Property value for [ " + propertyName + " ] is: [" + p.getProperty(propertyName) + "] from file: [ " + propertyFileName + " ]");
		return p.getProperty(propertyName);
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