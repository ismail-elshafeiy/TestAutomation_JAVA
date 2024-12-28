package com.engine.config;

import com.engine.actions.FileActions;
import com.engine.reports.Logger;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.MutableCapabilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;
public final class PropertyFileManager {

    @Getter
    private static final String CUSTOM_PROPERTIES_FOLDER_PATH = "src/main/resources/properties";

    private PropertyFileManager() {
        throw new IllegalStateException("Utility class");
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

    private static void readPropertyFiles(String propertiesFolderPath) {
        if (propertiesFolderPath != null) {
            Logger.logInfoStep("Reading properties directory: " + propertiesFolderPath);
            try {
                java.util.Properties properties = new java.util.Properties();
                if (propertiesFolderPath.contains(".jar")) {
                    // unpacks default properties to target folder
                    URL url = URI.create(propertiesFolderPath.substring(0, propertiesFolderPath.indexOf("!"))).toURL();
                    FileActions.getInstance().unpackArchive(url, "target/");
                    propertiesFolderPath = "target/resources/properties/default/";
                }
                // reading regular files
                Collection<File> propertiesFilesList;
                if (FileActions.getInstance().doesFileExist(propertiesFolderPath)) {
                    propertiesFilesList = FileUtils.listFiles(new File(propertiesFolderPath), new String[]{"properties"},
                            false);
                    File propertyFile;
                    for (int i = 0; i < propertiesFilesList.size(); i++) {
                        propertyFile = (File) (propertiesFilesList.toArray())[i];
                        Logger.logInfoStep("Loading properties file: " + propertyFile);
                        loadPropertiesFileIntoSystemProperties(properties, propertyFile);
                    }
                } else {
                    Logger.logError(
                            "The desired propertiesFolderPath directory doesn't exist. ["
                                    + propertiesFolderPath + "]");
                }
            } catch (Exception e) {
                Logger.logError(e.getMessage());
            }
        }
    }

    private static void loadPropertiesFileIntoSystemProperties(java.util.Properties properties, File propertyFile) {
        try {
            properties.load(new FileInputStream(propertyFile));
            // load properties from the properties file
            properties.putAll(System.getProperties());
            // override properties file with system properties
            System.getProperties().putAll(properties);
            // reset system properties
        } catch (IOException e) {
            Logger.logError(e.getMessage());
        }
    }

    public static void readCustomPropertyFiles() {
        readPropertyFiles(Objects.requireNonNullElse(Properties.paths.properties(), CUSTOM_PROPERTIES_FOLDER_PATH));
    }
}