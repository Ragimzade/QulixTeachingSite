package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;

public class LoginPage extends PageBase {

    private static final Logger logger = Logger.getLogger(MessageList.class);
    public static final String HELLO = "Hello";


    public LoginPage(WebDriver driver) throws IOException {
        super(driver);
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = ".//input[@value='Login']")
    private WebElement loginButton;


    @FindBy(id = "login")
    private WebElement loginField;

    @FindBy(id = "password")
    private WebElement passwordField;


    @FindBy(xpath = ".//a[contains(.,\"" + HELLO + "\")]")
    private WebElement helloMessage;

    @FindBy(xpath = ".//a[@href=\"/QulixTeachingSite/user/logout\"]")
    private WebElement logoutButton;

    public void isLoginButtonPresent() {
        try {
            new WebDriverWait(driver, Long.parseLong(configFileReader.getExplicitWait()))
                    .until(ExpectedConditions.visibilityOf(loginButton));
            loginButton.isDisplayed();
            logger.info("Login page is opened");
        } catch (TimeoutException e) {
            logger.fatal(e + "Login page is not opened");
            driver.quit();
            throw new TimeoutException("Test ended with critical error");
        }
    }


    public void login(String login, String password) {
        enterValue(loginField, login);
        enterValue(passwordField, password);
        loginButton.click();


    }


    public boolean isHelloMessagePresent(String userName) {

        //todo ну вот тут снова...
        //1. Что это и зачем
        //2. если элемент не будет найден, получим снова Exception непонятного происхождения:
        // я попросил assertHelloMessage, а в ответ получаю Web driver exceptioт причем после асерта.
        // если у меня не будет stacktrace-а, то этот метод будет последним куда я полезу искать причину ошибки
        try {
            new WebDriverWait(driver, Long.parseLong(configFileReader.getExplicitWait()))
                    .until(ExpectedConditions.visibilityOf(helloMessage)).isDisplayed();
            Assert.assertTrue(helloMessage.getText().contains(HELLO + userName));
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    public void logout() {
        logoutButton.click();
    }

}
