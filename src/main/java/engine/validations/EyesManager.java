package engine.validations;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.images.Eyes;
import com.applitools.eyes.*;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class EyesManager {

	private static Eyes eyes;
	private static String appName;
	private static WebDriver driver;

	public EyesManager (WebDriver driver, String appName) {
		this.driver = driver;
		this.appName = appName;
		eyes = new Eyes();
		eyes.setApiKey(System.getProperty("applitools.api.key"));
	}

	public static void setBatchName (String batchName) {
		eyes.setBatch(new BatchInfo(batchName));
	}

	public static void setTestGroup (String group) {
		eyes.addProperty("Test Group", group);
	}
//
//	public static void validateWindow () {
//		eyes.open(driver, appName, Thread.currentThread().getStackTrace()[2].getMethodName());
//		eyes.checkWindow();
//		eyes.close();
//	}
//
//	public static void validateElement (By locator) {
//		eyes.open(driver, appName, Thread.currentThread().getStackTrace()[2].getMethodName());
//		eyes.checkElement(locator);
//		eyes.close();
//	}
//
//	public static void validateFrame (String locator) {
//		eyes.open(driver, appName, Thread.currentThread().getStackTrace()[2].getMethodName());
//		eyes.checkFrame(locator);
//		eyes.close();
//	}

	public static void abort () {
		eyes.abortIfNotClosed();
	}

	public Eyes getEyes () {
		return eyes;
	}

	public static boolean validatePDF (String filepath) throws IOException, InterruptedException {
		String command = String.format(
				"java -jar resources/ImageTester.jar -k %s -f %s",
				System.getProperty("applitools.api.key"),
				filepath);

		Process process = Runtime.getRuntime().exec(command);
		process.waitFor();
		String stream = IOUtils.toString(process.getInputStream(), "UTF-8");
		System.out.println(stream);

		if (stream != null && stream.contains("Mismatch")) {
			return false;
		}

		return true;
	}
}
