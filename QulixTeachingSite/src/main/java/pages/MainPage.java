package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainPage {

    public static String baseUrl;
    private WebDriver driver;
    private static final Logger logger = Logger.getLogger(MessageList.class);
    private Properties properties;
    private LoginPage loginPage;

    @FindBy(xpath = ".//a[@href=\"/QulixTeachingSite/user/index\"]")
    private WebElement loginLink;



    public MainPage(WebDriver driver) throws IOException {
        this.driver = driver; 
        PageFactory.initElements(driver, this);
        properties = new Properties();
        properties.load(new FileReader(new File((String.format("src/main/resources/config.properties")))));

    }

    public void goToMainPage(){

        driver.get(properties.getProperty("baseUrl")); 
        //done
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"pageBody\"]/h1")).isDisplayed());
    }

    public void goToLoginPage() throws IOException {
        loginLink.click();

    }

}
