package testCases.gui.web.takeScreenShot;


import com.practice.gui.pages.homePage.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import engine.broswer.BrowserFactory;


import java.util.List;

public class CheckAllBrokenImage {

    public WebDriver driver;
    private int invalidImageCount;

    @BeforeMethod
    public void setUp_BeforeMethod() {
        driver = BrowserFactory.getBrowser();
        new HomePage(driver).navigateToHomePage("http://the-internet.herokuapp.com/broken_images");
    }


    @Test
    public void testBrokenImages() {
        invalidImageCount = 0;
        List<WebElement> imageList = (List<WebElement>) driver.findElement(By.tagName("img"));
//        for (WebElement imageList)

    }
/*
    public void verifyImageActive(WebElement imgElement) {

        HttpClient client = (HttpClient) HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(imgElement.getAttribute("src"));
        try {
            HttpResponse response = (HttpResponse) client.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                invalidImageCount++;

            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/




}
