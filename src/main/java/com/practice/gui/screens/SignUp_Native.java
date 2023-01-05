package com.practice.gui.screens;

import engine.gui.actions.MobileElementActions;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class SignUp_Native {

	private MobileDriver<MobileElement> mobile;

	private By first_name = MobileBy.AccessibilityId("first_name");
	private By last_name = MobileBy.AccessibilityId("last_name");
	private By email_field = MobileBy.AccessibilityId("email");
	private By password_field = MobileBy.AccessibilityId("password");
	private By terms = MobileBy.AccessibilityId("terms");
	private By login_button = MobileBy.id("com.jumia.android:id/login_button_continue");
	private By male_checkbox = MobileBy.xpath("//*[@text='Male']");

	public static By dismissAdBtn = MobileBy.id("com_ad4screen_sdk_popup_secondary_button");

	public SignUp_Native (MobileDriver<MobileElement> mobile) {
		this.mobile = mobile;
	}

	public SignUp_Native fillRegistrationForm (String firstName, String lastName, String emailAddress, String password) {
		MobileElementActions.type(mobile, first_name, firstName);
		MobileElementActions.type(mobile, last_name, lastName);
		MobileElementActions.type(mobile, email_field, emailAddress);
		MobileElementActions.type(mobile, password_field, password);
		MobileElementActions.click(mobile, male_checkbox);
		MobileElementActions.click(mobile, terms);
		MobileElementActions.click(mobile, login_button);
		return this;
	}

	public void dismissAd () {
		new Home_Native(mobile).dismissAlert();
	}

}
