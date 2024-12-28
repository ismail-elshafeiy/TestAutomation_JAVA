package dataDriven;

import com.engine.dataDriven.FakerData;
import com.engine.dataDriven.PropertiesManager;
import com.engine.reports.Logger;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropertiesManager_Tests {
    @Test(description = "Load all properties from the properties files")
    public void loadAllFiles() {
        List<String> propertiesFiles = new ArrayList<>();
        propertiesFiles.add("src/main/resources/properties/allure.properties");
        propertiesFiles.add("src/main/resources/properties/config.properties");
        propertiesFiles.add("src/main/resources/properties/paths.properties");
        Properties properties = PropertiesManager.loadAllFiles(propertiesFiles);
        Logger.logInfoStep("From [ config ] Key: projectName, Value: [ " + properties.getProperty("projectName") + " ]");
        Logger.logInfoStep("From [ allure ] Key: username, Value: [ " + properties.getProperty("allure.link.issue.pattern") + " ]");
        Logger.logInfoStep("From [ paths ] Key: password, Value: [ " + properties.getProperty("TAU.homeUrl") + " ]");
    }

    @Test(description = "Load all properties from the properties folder path end with the .properties ")
    public void loadAllProperties() {
        Properties properties = PropertiesManager.loadAllProperties();
        Logger.logInfoStep("From [ config ] Key: projectName, Value: [ " + properties.getProperty("projectName") + " ]");
        Logger.logInfoStep("From [ allure ] Key: username, Value: [ " + properties.getProperty("allure.link.issue.pattern") + " ]");
        Logger.logInfoStep("From [ paths ] Key: password, Value: [ " + properties.getProperty("TAU.homeUrl") + " ]");
    }

    @Test(description = "Get all properties as a map")
    public void getPropertiesAsMap() {
        Map<String, String> properties = PropertiesManager.getPropertiesAsMap("config");
        Logger.logInfoStep("From [ config ] Key: projectName, Value: [ " + properties.get("projectName") + " ]");
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            Logger.logInfoStep("Key: [ " + entry.getKey() + "] , Value: [ " + entry.getValue() + " ]");
        }
    }

    @Test(description = "Add properties from a Map")
    public void addPropertiesFromMap() {
        Map<String, String> properties = Map.of(
                "projectName", "Test Project",
                "projectVersion", "1.0.0",
                "projectDescription", "Test Description"
        );
        PropertiesManager.addNewPropertiesFromMap("testProperties", properties);
        Logger.logInfoStep(PropertiesManager.getPropertyValue("testProperties", "projectName"));
    }
    @Test(description = "Update multiple properties at once")
    public void updateMultipleProperties() {
        Map<String, String> properties = Map.of(
                "projectName", "Updated Project",
                "projectDescription", "Updated Description",
                "newProperty", "New Property"
        );
        PropertiesManager.updatePropertiesFromMap("testProperties", properties);
        Logger.logInfoStep(PropertiesManager.getPropertyValue("testProperties", "projectName"));
    }

    @Test(description = "Get the value of a property")
    public void getPropertyValue() {
        Logger.logInfoStep(PropertiesManager.getPropertyValue("config", "projectName"));
    }

    @Test(description = "Add new property key and value")
    public void addNewPropertyKeyAndValue() {
        String newKey = FakerData.getFirstName();
        PropertiesManager.setPropertyValue("testProperties", newKey, "Test New key & value");
        Logger.logInfoStep(PropertiesManager.getPropertyValue("testProperties", newKey));
    }

    @Test(description = "Update the value of a property")
    public void updatePropertyValue() {
        PropertiesManager.setPropertyValue("testProperties", "projectName", "Test New key & value 3");
        Logger.logInfoStep(PropertiesManager.getPropertyValue("testProperties", "projectName"));
    }
    @Test(description = "Remove a property")
    public void removeProperty() {
        PropertiesManager.isPropertyKeyExists("testProperties", "test1");
        PropertiesManager.isPropertyKeyExists("testProperties", "test10");
        PropertiesManager.removeKeyFromProperties("testProperties", "test1");
    }
}
