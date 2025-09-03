package com.engine.config;

import com.engine.reports.CustomReporter;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"system:properties",
        "file:src/main/resources/properties/paths.properties",
        "classpath:path.properties",})
public interface Paths extends EngineProperties {
    //    @Key("servicesFolderPath")
//    @DefaultValue("src/test/resources/META-INF/services/")
//    String services();
//    private static void setProperty(String key, String value) {
//        var updatedProps = new java.util.Properties();
//        updatedProps.setProperty(key, value);
//        Properties.paths = ConfigFactory.create(Paths.class, updatedProps);
//        // temporarily set the system property to support hybrid read/write mode
//        System.setProperty(key, value);
//        CustomReporter.logConsole("Setting \"" + key + "\" property with \"" + value + "\".");
//    }
////    public SetProperty services(String value) {
////        setProperty("servicesFolderPath", value);
////        return this;
////    }
//}
    @Key("propertiesFolderPath")
    @DefaultValue("src/main/resources/properties/")
    String properties();

    private static void setProperty(String key, String value) {
        var updatedProps = new java.util.Properties();
        updatedProps.setProperty(key, value);
        Properties.paths = ConfigFactory.create(Paths.class, updatedProps);
        // temporarily set the system property to support hybrid read/write mode
        System.setProperty(key, value);
        CustomReporter.logInfoStep("Setting \"" + key + "\" property with \"" + value + "\".");
    }

    default SetProperty set() {
        return new SetProperty();
    }

    class SetProperty implements EngineProperties.SetProperty {
        public SetProperty properties(String value) {
            setProperty("propertiesFolderPath", value);
            return this;
        }
    }
}