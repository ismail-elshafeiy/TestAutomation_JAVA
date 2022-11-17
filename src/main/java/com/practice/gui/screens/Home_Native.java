package com.practice.gui.screens;

import engine.gui.actions.MobileElementActions;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class Home_Native {
	private By dismiss_alert = By.id("android:id/button3");
	private String navigation_link = "com.jumia.android:id/navigation_%s";

	private String account = "account";
	private String categories = "categories";
	private String home = "home";

	private MobileDriver<MobileElement> mobile;

	public Home_Native (MobileDriver<MobileElement> mobile) {
		this.mobile = mobile;
	}

	public Home_Native dismissAlert () {
		MobileElementActions.click(mobile, dismiss_alert);
		return this;
	}

	// Generic method to click on navigation menu icons in home page
	public void clickOnNavigationMenuLinks (String navigationLink) {
		MobileElementActions.click(mobile, MobileBy.id(String.format(navigation_link, navigationLink)));
	}

	public Account_Native clickOnAccount () {
		clickOnNavigationMenuLinks(account);
		return new Account_Native(mobile);
	}
}