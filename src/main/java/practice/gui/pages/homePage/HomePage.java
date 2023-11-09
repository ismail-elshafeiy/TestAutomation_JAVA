package practice.gui.pages.homePage;

import practice.gui.pages.inputs.ForgotPasswordPage;
import practice.gui.pages.inputs.LoginPage;
import practice.gui.pages.select.DropdownPage;
import practice.gui.pages.select.CheckBoxPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.engine.actions.BrowserActions;
import com.engine.actions.ElementActions;
import com.engine.dataDriven.PropertiesReader;

import java.util.List;

public class HomePage {

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Ho
    private final String homeUrlTau = PropertiesReader.getProperty("project.properties", "TAU.homeUrl");

    private final By getAll_LinksInHome = By.xpath("//ul/li/a");

    public List<WebElement> allList() {
        return driver.findElements(getAll_LinksInHome);
    }

    public int getLinksSize() {
        return allList().size();
    }

    private final By formAuthentication_textlink = By.linkText("Form Authentication");
    private By dropdown_textlink = By.linkText("Dropdown");
    private By hovers_textlink = By.linkText("Hovers");
    private By keyPresses_textlink = By.linkText("Key Presses");
    private By javaScriptAlerts_textlink = By.linkText("JavaScript Alerts");
    private By fileUpload_textlink = By.linkText("File Upload");
    private By WYSIWYGEditor_textlink = By.linkText("WYSIWYG Editor");
    private By largeDeepDOM_textlink = By.linkText("Large & Deep DOM");
    private By scroll_textlink = By.linkText("Infinite Scroll");
    private By DynamicLoading_textlink = By.linkText("Dynamic Loading");
    private By MultipleWindows_textlink = By.linkText("Multiple Windows");
    private By ForgotPassword_textlink = By.linkText("Forgot Password");
    private By HorizontalSlider_textlink = By.linkText("Horizontal Slider");
    private By ContextMenu_textlink = By.linkText("Context Menu");
    private By Frames_textlink = By.linkText("Frames");
    private By checkbox_textLink = By.linkText("Checkboxes");

    /**
     * Navigate to Home Page
     *
     * @return self reference
     */

    public HomePage navigateToHomePage() {
        BrowserActions.navigateToUrl(driver, homeUrlTau);
        return this;
    }

    /**
     * Navigate to Home Page
     *
     * @param baseUrl
     * @return self reference
     */
    public HomePage navigateToHomePage(String baseUrl) {
        BrowserActions.navigateToUrl(driver, baseUrl);
        return this;
    }

    public LoginPage clickFormAuthentication() {
        ElementActions.click(driver, formAuthentication_textlink);
        return new LoginPage(driver);
    }

    public HomePage getAll_links_homePage() {
        System.out.println("Number of links in Home Page: " + getLinksSize());
        return this;
    }

    @Step("Get All text of links")
    public HomePage printAll_Links_homePage_1() {
        System.out.println("Number of links in Home Page: " + getLinksSize());

        for (int i = 0; i < allList().size(); i++) {
            WebElement linkText = allList().get(i);
            String link_text = linkText.getText();
            String link_href = linkText.getAttribute("href");

            System.out.println(i + 1 + ":" + link_text + " /" + getLinksSize() + " Links " + "--> " + link_href);
        }
        return this;
    }

    @Step("Get All text of Links and href link")
    public HomePage printAll_Links_homePage_2() {
        System.out.println("Number of links in Home Page: " + getLinksSize());

        for (WebElement links : allList()) {
            String linkText = links.getText();
            String linkAttribute = links.getAttribute("href");

            System.out.println(linkText + "-" + linkAttribute);
        }
        return this;
    }

    @Step("Get text of link and click on it")
    public HomePage clickOn_Link_homePage(int index) {
        printAll_Links_homePage_1();

        WebElement element = allList().get(index - 1);
        String elementText = element.getText();
        String link_href = element.getAttribute("href");
        element.isDisplayed();
        element.click();

        System.out.println("Click on the link --> " + index + " = Text --> " + elementText + " of the home page " + " --> " + link_href);

        return this;
    }

    public DropdownPage clickDropDown() {
        ElementActions.click(driver, dropdown_textlink);
        return new DropdownPage(driver);
    }


    public ForgotPasswordPage clickForgotPassword() {
        ElementActions.click(driver, ForgotPassword_textlink);
        return new ForgotPasswordPage(driver);
    }


    public CheckBoxPage clickCheckBoxesPage() {
        ElementActions.click(driver,checkbox_textLink);
        return new CheckBoxPage(driver);
    }


}