package web.practice.browserInteractions;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class NetworkConditions {
    ChromeDriver driver;
    DevTools devTools;

    @BeforeMethod
    public void setUp() {
        // WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        devTools = driver.getDevTools();
    }
//
//    @Test
//    public void enableSlowRexJonesII() {
//        devTools.createSession();
//        devTools.send(Network.enable(
//                Optional.empty(),
//                Optional.empty(),
//                Optional.empty()));
//        devTools.send(Network.emulateNetworkConditions(
//                false,
//                150,
//                2500,
//                2000,
//                Optional.of(ConnectionType.CELLULAR3G)));
//        driver.get("https://linkedin.com");
//        System.out.println("Enable Slow Network: " + driver.getTitle());
//    }

    @Test
    public void doNotEnableRexJonesII() {
        driver.get("https://linkedin.com");
        System.out.println("Do Not Enable Network: " + driver.getTitle());
    }
}
