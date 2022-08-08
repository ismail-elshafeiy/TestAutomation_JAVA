package engine.broswer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import engine.PropertiesReader;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import java.time.Duration;


public class Waits {
    private static final int TIMEOUT = Integer
            .parseInt(PropertiesReader.getProperty("project.properties", "webdriver.wait"));

    public static WebDriverWait getExplicitWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    public static void implicitWait(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
    }

    public static WebDriverWait getExplicitWait(MobileDriver<MobileElement> mobile) {
        return new WebDriverWait(mobile, Duration.ofSeconds(TIMEOUT));
    }

    public static void implicitWait(MobileDriver<MobileElement> mobile) {
        mobile.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
    }

}
