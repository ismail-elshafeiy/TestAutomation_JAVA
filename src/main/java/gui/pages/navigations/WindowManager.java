package gui.pages.navigations;

import org.openqa.selenium.WebDriver;

public class WindowManager {

    private WebDriver driver;
    private WebDriver.Navigation navigate;

    public WindowManager(WebDriver driver) {
        this.driver = driver;
        navigate = driver.navigate();
    }

    public WindowManager goBack() {
        navigate.back();
        return this;
    }

    public WindowManager goForward() {
        navigate.forward();
        return this;
    }

    public WindowManager refreshPage() {
        navigate.refresh();
        return this;
    }

    public WindowManager goTo(String url) {
        navigate.to(url);
        return this;
    }

    public WindowManager switchToTab(String tabTitle) {
        var windows = driver.getWindowHandles();

        System.out.println("Number of tabs: " + windows.size());

        System.out.println("Window handles:");
        windows.forEach(System.out::println);

        for (String window : windows) {
            System.out.println("Switching to window: " + window);
            driver.switchTo().window(window);

            System.out.println("Current window title: " + driver.getTitle());

            if (tabTitle.equals(driver.getTitle())) {
                break;
            }
        }
        return this;
    }

    public WindowManager switchToNewTab() {
        var windows = driver.getWindowHandles();
        windows.forEach(driver.switchTo()::window);
        return this;
    }
}
