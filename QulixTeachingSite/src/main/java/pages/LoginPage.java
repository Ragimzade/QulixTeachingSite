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

public class LoginPage extends PageBase {

    private static final Logger logger = Logger.getLogger(MessageList.class);
    public static final String HELLO = "Hello ";


    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = ".//input[@value='Login']")
    private WebElement loginButton;


    @FindBy(id = "login")
    private WebElement loginField;

    @FindBy(id = "password")
    private WebElement passwordField;


    @FindBy(xpath = ".//span[contains(.,\"" + HELLO + "\")]")
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
            driver.quit(); //todo Почему это здесь? 
            //todo Зачем создавать новый, если у тебя уже есть TimeoutException e
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
        //todo Простой вобщем-то метод, но мы по нему скоро три тома коментов составим
        try {
            new WebDriverWait(driver, Long.parseLong(configFileReader.getExplicitWait()))
                    .until(ExpectedConditions.visibilityOf(helloMessage)).isDisplayed();
            Assert.assertTrue(helloMessage.getText().contains(HELLO + userName)); //todo ну у тебя метод булевый, ну верни ты просто 
            //helloMessage.getText().contains(HELLO + userName). 
            //Я не понимаю, зачем ты делаешь этот чертов асерт, чем он тебе помогает????
            return true;
        } catch (Exception e) { //todo это не споймает ошибку Assert и не надо делать таких широких catch. 
            //Лови то, что считаешь допустимым
            logger.error(e);
            return false;
        }
    }

    public void logout() {
        logoutButton.click();
    }

}
