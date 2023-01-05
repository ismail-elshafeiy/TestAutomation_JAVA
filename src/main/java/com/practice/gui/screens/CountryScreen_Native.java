package com.practice.gui.screens;

import engine.gui.actions.MobileElementActions;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class CountryScreen_Native {
	private MobileDriver<MobileElement> mobile;

	private String profileIconExperssion = "(//*[@index='%d'])";
	private String languageSelection = "//*[contains(@text, '%s')]//parent::*";

	public CountryScreen_Native (MobileDriver<MobileElement> mobile) {
		this.mobile = mobile;
	}

	public CountryScreen_Native clickOnCountry (int index) {
		MobileElementActions.click(mobile, By.xpath(String.format(profileIconExperssion, index)));
		return this;
	}

	public Home_Native clickOnLanguage (String language) {
		MobileElementActions.click(mobile, By.xpath(String.format(languageSelection, language)));
		return new Home_Native(mobile);
	}
}
