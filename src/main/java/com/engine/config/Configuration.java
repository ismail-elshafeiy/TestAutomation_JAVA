package com.engine.config;


import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "system:env",
        "file:./src/main/resources/properties/config.properties",
        "file:./src/test/resources/config/data.properties"})
public interface Configuration extends Config {

    @Key("browserType")
    String browserType();

    @Key("executionType")
    String executionType();

    @Key("maximize")
    Boolean maximize();


}
