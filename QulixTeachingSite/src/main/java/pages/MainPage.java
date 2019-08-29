package pages;

import model.ConfigFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class MainPage extends PageBase {

    @FindBy(xpath = ".//a[@href=\"/QulixTeachingSite/user/index\"]")
    private WebElement loginLink;


    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void goToMainPage() {

        driver.get(config.getBaseUrl());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"pageBody\"]/h1")).isDisplayed());
    }

    public void goToLoginPage() {
        loginLink.click();
    }

}
