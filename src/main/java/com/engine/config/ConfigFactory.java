package com.engine.config;


import org.aeonbits.owner.ConfigCache;


public class ConfigFactory {

    private ConfigFactory() {
    }

    public static Configurations getConfigs() {
        return ConfigCache.getOrCreate(Configurations.class);

    }

}
