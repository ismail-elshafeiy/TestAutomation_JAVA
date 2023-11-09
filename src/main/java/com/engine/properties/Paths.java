package com.engine.properties;

import com.engine.listeners.CustomReporter;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"system:properties",
        "file:src/main/resources/properties/project.properties",
        "classpath:path.properties",
})
public interface Paths extends EngineProperties {
    @Key("servicesFolderPath")
    @DefaultValue("src/test/resources/META-INF/services/")
    String services();

    private static void setProperty(String key, String value) {
        var updatedProps = new java.util.Properties();
        updatedProps.setProperty(key, value);
        //  Properties.paths = ConfigFactory.create(Paths.class, updatedProps);
        // temporarily set the system property to support hybrid read/write mode
        System.setProperty(key, value);
        CustomReporter.logConsole("Setting \"" + key + "\" property with \"" + value + "\".");
    }
//    public SetProperty services(String value) {
//        setProperty("servicesFolderPath", value);
//        return this;
//    }
}
