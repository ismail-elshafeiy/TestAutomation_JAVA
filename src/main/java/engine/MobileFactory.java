/*
package engine;

import engine.tools.Logger;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MobileFactory {
	static DesiredCapabilities cap = new DesiredCapabilities();
	static MobileDriver<MobileElement> mobile;

	public static void setCapabilities (String UDID, String DEVICE_NAME, String PLATFORM_VERSION, String PLATFORM_NAME, String APP_PACKAGE, String APP_ACTIVITY, String File_PATH) {
		cap.setCapability(MobileCapabilityType.UDID, UDID);
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
		cap.setCapability("appPackage", APP_PACKAGE);
		cap.setCapability("appActivity", APP_ACTIVITY);
		File file = new File(File_PATH);
		cap.setCapability("app", file.getAbsolutePath());
	}

	public enum MobileDriverType {
		ANDROID, IOS
	}

	public static MobileDriver<MobileElement> InitiateMobileDriver (MobileDriver<MobileElement> mobile,
																	MobileDriverType mobileDriverType) {
		switch (mobileDriverType) {
			case ANDROID:
				try {
					mobile = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), cap);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			case IOS:
				try {
					mobile = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), cap);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
		}
		return mobile;
	}

	public enum AlertActionType {
		ACCEPT, DISMISS
	}

	public static void AlertAction (MobileDriver<MobileElement> mobile, AlertActionType alertActionType) {
		Alert alert = mobile.switchTo().alert();
		switch (alertActionType) {
			case ACCEPT -> alert.accept();
			case DISMISS -> alert.dismiss();
		}
	}

	public static void closeMobile (MobileDriver<MobileElement> mobile) {
		Logger.logStep("[Browser Action] Close all Opened Browser Windows");
		if (mobile != null) {
			try {
				mobile.quit();
			} catch (WebDriverException rootCauseException) {
				Logger.logMessage(rootCauseException.getMessage());
			}
		} else {
			Logger.logMessage("Windows are already closed and the driver object is null.");
		}
	}
}
*/
