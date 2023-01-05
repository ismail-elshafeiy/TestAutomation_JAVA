package com.practice.gui.screens;

import engine.broswer.Waits;
import engine.gui.actions.MobileElementActions;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Login_Native {

	private MobileDriver<MobileElement> mobile;

	private By create_new_account = MobileBy.id("login_email_create_account");
	private By email_field = MobileBy.AccessibilityId("email");
	private By password_field = MobileBy.AccessibilityId("password");
	private By login_button = MobileBy.id("login_button_continue");

	public Login_Native (MobileDriver<MobileElement> mobile) {
		this.mobile = mobile;
	}

	public SignUp_Native clickOnCreateNewAccount () {
		MobileElementActions.click(mobile, create_new_account);
		return new SignUp_Native(mobile);
	}

	public Account_Native loginUser (String email, String password) {
		Waits.getExplicitWait(mobile).until(ExpectedConditions.presenceOfElementLocated(email_field));
		MobileElementActions.type(mobile, password_field, password);
		MobileElementActions.type(mobile, email_field, email);
		MobileElementActions.click(mobile, login_button);
		return new Account_Native(mobile);
	}
}
