package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigFileReader;


public class PageBase {

    private static final Logger logger = Logger.getLogger(PageBase.class);
    protected ConfigFileReader config = ConfigFileReader.getInstance();
    protected WebDriver driver;
    protected static final String HELLO = "Hello ";

    public PageBase(WebDriver driver) {
        this.driver = driver;
    }


    public String getCurrentUser() {
        String authorText = driver.findElement(By.xpath(".//span[contains(.,\"" + HELLO + "\")]")).getText();
        String author = authorText.substring((authorText.indexOf(" ")), authorText.indexOf("[")).trim();
        return author;
    }

    public void enterValue(WebElement field, String value) {
        field.clear();
        field.sendKeys(value);
    }


    public boolean isElementPresent(WebElement element) {
        try {
            new WebDriverWait(driver, (config.getExplicitWaitTimeout()))
                    .until(ExpectedConditions.visibilityOf(element)).isDisplayed();
            return true;
        } catch (TimeoutException ex) {
            logger.info(element + " is not found on page");
            return false;
        }
    }
}
