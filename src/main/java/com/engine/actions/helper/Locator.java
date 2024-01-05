package com.engine.actions.helper;

public class Locator {

    public static LocatorBuilder hasTagName(String tagName) {
        return LocatorBuilder.hasTagName(tagName);
    }

    public static LocatorBuilder hasAnyTagName() {
        return LocatorBuilder.hasTagName("*");
    }
}
