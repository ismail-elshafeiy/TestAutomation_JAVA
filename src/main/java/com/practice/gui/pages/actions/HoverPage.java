package com.practice.gui.pages.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import engine.gui.actions.ElementActions;

public class HoverPage {

    // driver
    private static WebDriver driver;

    // Constructor
    public HoverPage(WebDriver driver) {
        this.driver = driver;
    }

    //////////////////////////// Elements Locators ////////////////////////////
    private By figureBox = By.className("figure");
    private By boxCaption = By.className("figcaption");
    private By product_hover = By.linkText("Printed Chiffon Dress");
    // TODO check findElement "Quick View"= hover then Click on "Quick View" btn with [ Mohab M ]
    private By quickView_Btn = By.xpath("//a[@class='quick-view'][contains(.,'Quick view')]");
    private By addToCart_btn = By.xpath("(//a[@data-id-product='7'])[1]//span");


    //////////////////////////// Business Actions ////////////////////////////

    /**
     * @param index starts at 1
     */
    public FigureCaption hoverOverFigure(int index) {
        WebElement figure = driver.findElements(figureBox).get(index - 1);

        Actions actions = new Actions(driver);
        actions.moveToElement(figure).perform();

        return new FigureCaption(figure.findElement(boxCaption));
    }

    public static class FigureCaption {

        private static WebElement caption;
        private static By header = By.tagName("h5");
        private static By link = By.tagName("a");

        public FigureCaption(WebElement caption) {
            this.caption = caption;
        }

        public static boolean isCaptionDisplayed() {
            return caption.isDisplayed();
        }

        public static String getTitle() {
            return caption.findElement(header).getText();
        }

        public String getLink() {
            return caption.findElement(link).getAttribute("href");
        }

        public static String getLinkText() {
            return caption.findElement(link).getText();
        }
    }

    public HoverPage hoverOfElement() {
        Actions a = new Actions(driver);
        a.moveToElement(driver.findElement(product_hover)).perform();
        ElementActions.click(driver,quickView_Btn);
//        ElementActions.click(driver, addToCart_btn);

        a.contextClick(driver.findElement(quickView_Btn));
//        a.contextClick(driver.findElement(product_hover));
        return this;
    }

    public HoverPage openQuickView() {
        ElementActions.click(driver, quickView_Btn);
        return this;
    }


}
