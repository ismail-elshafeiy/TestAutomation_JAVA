package com.engine.config;

import com.engine.reports.Logger;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "system:env",
        "file:./src/main/resources/properties/config.properties",
        "file:./src/test/resources/config/data.properties"})
public interface Configurations extends Config {

    @Key("browserType")
    @DefaultValue("Google Chrome")
    String browserType();

    @Key("executionType")
    @DefaultValue("local")
    String executionType();

    @Key("maximize")
    @DefaultValue("true")
    Boolean maximize();

    @Key("width")
    @DefaultValue("1024")
    int width();

    @Key("height")
    @DefaultValue("768")
    int height();

    @Key("implicitWaitTimeout")
    @DefaultValue("60")
    int timeoutImplicitDefault();

    @Key("explicitWaitTimeout")
    @DefaultValue("60")
    int timeoutExplicitDefault();



    class SetProperty implements EngineProperties.SetProperty {
        public SetProperty browserType(String value) {
            setProperty("browserType", value);
            return this;
        }
        public SetProperty executionType(String value) {
            setProperty("executionType", value);
            return this;
        }
        public SetProperty maximize(Boolean value) {
            setProperty("maximize", value.toString());
            return this;
        }
        public SetProperty width(int value) {
            setProperty("width", String.valueOf(value));
            return this;
        }
        public SetProperty height(int value) {
            setProperty("height", String.valueOf(value));
            return this;
        }
        public SetProperty timeoutImplicitDefault(int value) {
            setProperty("implicitWaitTimeout", String.valueOf(value));
            return this;
        }
        public SetProperty timeoutExplicitDefault(int value) {
            setProperty("explicitWaitTimeout", String.valueOf(value));
            return this;
        }
    }
    private static void setProperty(String key, String value) {
        var updatedProps = new java.util.Properties();
        updatedProps.setProperty(key, value);
        Properties.configurations = ConfigFactory.create(Configurations.class, updatedProps);
        // temporarily set the system property to support hybrid read/write mode
        System.setProperty(key, value);
        Logger.logInfoStep("Setting \"" + key + "\" property with \"" + value + "\".");
    }

    default SetProperty set() {
        return new SetProperty();
    }
}