package web.practice.waitLoading;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DynamicLoadingExample2Page {

    private WebDriver driver;
    private By startButton = By.cssSelector("#start button");
    private By loadedText = By.id("finish");

    public DynamicLoadingExample2Page(WebDriver driver){
        this.driver = driver;
    }

    public void clickStart() {
        driver.findElement(startButton).click();
//        WebDriverWait wait = new WebDriverWait(driver, 5.2);
//        wait.until(ExpectedConditions.presenceOfElementLocated(loadedText));
    }

    public boolean isStartButtonDisplayed(){
        return driver.findElement(startButton).isDisplayed();
    }

    public String getLoadedText(){
        return driver.findElement(loadedText).getText();
    }
}
