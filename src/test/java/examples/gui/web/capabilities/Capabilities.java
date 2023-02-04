package examples.gui.web.capabilities;


import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.Test;

public class Capabilities {

    /*
     *
     * Using Chrome Options to handle the Chrome capabilities such as SSL certificate issues
     * And the not secured websites
     *
     */

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Handling Chrome Browser Options Test Case")
    @Epic("Selenium Actions on Elements")
    @Story("Capabilities Class")
    void chromeOptions(){
        // https://peter.sh/experiments/chromium-command-line-switches/

        // Chrome Options
        ChromeOptions options = new ChromeOptions();
//        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

//        Specifies if the browser should start in fullscreen mode,
//        like if the user had pressed F11 right after startup.
        options.addArguments("--start-fullscreen");
//        Starts the shell with the profile in incognito mode.
        options.addArguments("--incognito");
        options.addArguments("--start-in-incognito");
//        Mutes audio sent to the audio device so it is not audible during automated testing.
        options.addArguments("--mute-audio");
//        Run in headless mode, i.e., without a UI or display server dependencies.
        options.addArguments("--headless ");

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
//        DriverContext.driver.get("http://google.com");
    }
}
