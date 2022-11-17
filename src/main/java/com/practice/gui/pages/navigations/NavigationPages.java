package com.practice.gui.pages.navigations;

import org.openqa.selenium.WebDriver;
import engine.broswer.BrowserActions;

public class NavigationPages {


    // driver
    private static WebDriver driver;

    // Constructor
    public NavigationPages(WebDriver driver) {
        this.driver = driver;
    }
    //////////////////////////// Elements Locators ////////////////////////////


    //////////////////////////// Business Actions ////////////////////////////

    /**
     * Navigate to Home Page
     *
     * @return self reference
     */

    public NavigationPages navigateTo_homePage(String baseUrl) {
        BrowserActions.navigateToUrl(driver, baseUrl);
        return this;
    }


    public WindowManager getWindowManager() {
        return new WindowManager(driver);
    }


}
