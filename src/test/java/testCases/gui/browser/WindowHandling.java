package SeleniumActions_tests;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WindowHandling extends TestBase {

    /*
     * Window Handling Method to handle all the opened Tabs or windows and applies code on each window if needed
     */

    @BeforeMethod
    public void goToMultipleWindowsPage() {
        driver.get("https://cookbook.seleniumacademy.com/Config.html");
    }

    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Handling Windows and Tabs using Title Test Case")
    @Epic("Selenium Actions on Elements")
    @Story("Window Handling Tutorial")
    public void handleMultipleWindowsUsingTitle() {
        // Store WindowHandle of Parent browser Window in a ParentWindowID String
        String ParentWindowId = driver.getWindowHandle();
        // Click on the opening new tab button
        driver.findElement(By.xpath("//button[@id='visitbutton']")).click();
        // Get Handles of all the open windows
        //Iterate the list and check if title of each window as expected title
        try {
            for (String windowId : driver.getWindowHandles()) {
                String title = driver.switchTo().window(windowId).getTitle();
                if (title.equals("Visit Us")) {
                    Assert.assertEquals("Visit Us",driver.getTitle(), "Page Title is incorrect");
                    //Write any code to handle elements in Visit us page
                    System.out.println(driver.getTitle());
                    //Close Visit us Page
                    driver.close();
                    break;
                }
            }
        } finally {
            //Switch to the Parent browser window
            driver.switchTo().window(ParentWindowId);
        }
    }
    @Test(priority = 2)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Handling Windows and Tabs using Name Test Case")
    @Epic("Selenium Actions on Elements")
    @Story("Window Handling Tutorial")
    public void HandleMultipleWindowsUsingName() {
        //Store WindowHandle of parent window
        String currentWindowId = driver.getWindowHandle();
        driver.findElement(By.id("helpbutton")).click();
        driver.switchTo().window("HelpWindow");
        Assert.assertEquals("Help", driver.getTitle(), "Page Title is incorrect");
        System.out.println(driver.getTitle());
        // code inside Help Window
        driver.close();
        driver.switchTo().window(currentWindowId);

    }
}
