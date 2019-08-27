package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.Properties;

public class MainPage extends PageBase {

    public static String baseUrl;

    private static final Logger logger = Logger.getLogger(MessageList.class);
    private Properties properties;
    private LoginPage loginPage;

    @FindBy(xpath = ".//a[@href=\"/QulixTeachingSite/user/index\"]")
    private WebElement loginLink;


    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void goToMainPage() {
        driver.get(configFileReader.getApplicationUrl());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"pageBody\"]/h1")).isDisplayed());
    }

    public void goToLoginPage() {
        loginLink.click();
    }

}
