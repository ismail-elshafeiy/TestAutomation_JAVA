package utilities.broswer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.PropertiesReader;

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

}
