package testCases.gui.mobile;

import com.practice.gui.screens.Account_Native;
import com.practice.gui.screens.CountryScreen_Native;
import engine.MobileActions;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class TestAppiumNative {
	private MobileDriver<MobileElement> mobile;

	private String englishLanguage = "English";
	private String email = "testA7QA@tes.com";
	private String password = "Aa12345#";
	private int countryIndex = 2;
	private String emaulatorUDID = "emulator-5554";

	@Test
	public void checkSuccessRegistration () {
		new CountryScreen_Native(mobile)
				.clickOnCountry(countryIndex)
				.clickOnLanguage(englishLanguage)
				.dismissAlert()
				.clickOnAccount()
				.clickOnLogin()
				.clickOnCreateNewAccount()
				.fillRegistrationForm("Test1", "Test1", email, password);
		Assert.assertEquals(new Account_Native(mobile).getAccountEmail(), email);
	}

	@Test(dependsOnMethods = {"checkSuccessfulRegistration"})
	public void checkSuccessfulLogin () {
		new CountryScreen_Native(mobile)
				.clickOnCountry(countryIndex)
				.clickOnLanguage(englishLanguage)
				.dismissAlert()
				.clickOnAccount()
				.clickOnLogin()
				.loginUser(email, password);
		Assert.assertEquals(new Account_Native(mobile).getAccountEmail(), email);
	}

	@BeforeMethod
	public void beforeMethod () throws MalformedURLException {
		mobile = MobileActions.InitiateMobileDriver(mobile, MobileActions.MobileDriverType.ANDROID);
		MobileActions.setCapabilities(emaulatorUDID,
				"Test Mobile",
				"7.1.1",
				"Android",
				"com.jumia.android",
				"com.mobile.view.SplashScreenActivity",
				"src/test/resources/jumia-7-5-1.apk");
	}

	@AfterMethod
	public void afterMethod () {
		mobile.quit();
	}
}