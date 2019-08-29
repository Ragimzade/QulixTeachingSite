package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Properties;

public class LoginPage extends PageBase {

    private static final Logger logger = Logger.getLogger(MessageList.class);


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

    public boolean isLoginButtonPresent() {

        try {
            new WebDriverWait(driver, Long.parseLong(config.getExplicitWait()))
                    .until(ExpectedConditions.visibilityOf(loginButton)).isDisplayed();
            return true;
        } catch (TimeoutException ex) {
            logger.error(loginButton + " is not found on page");
            return false;
        }

        //todo Почему это здесь?
        //todo Зачем создавать новый, если у тебя уже есть TimeoutException e

    }


    public void login(String login, String password) {
        enterValue(loginField, login);
        enterValue(passwordField, password);
        loginButton.click();


    }

    public boolean isHelloMessagePresent() {

        //todo ну вот тут снова...
        //1. Что это и зачем
        //2. если элемент не будет найден, получим снова Exception непонятного происхождения:
        // я попросил assertHelloMessage, а в ответ получаю Web driver exceptioт причем после асерта.
        // если у меня не будет stacktrace-а, то этот метод будет последним куда я полезу искать причину ошибки
        //todo Простой вобщем-то метод, но мы по нему скоро три тома коментов составим
        //todo ну у тебя метод булевый, ну верни ты просто
        //helloMessage.getText().contains(HELLO + userName).
        //Я не понимаю, зачем ты делаешь этот чертов асерт, чем он тебе помогает????
        // да что-то намудрил. Разбил на 2 метода, проверка отображения и проверка на корректность
        return new WebDriverWait(driver, Long.parseLong(config.getExplicitWait()))
                .until(ExpectedConditions.visibilityOf(helloMessage)).isDisplayed();
    }

    public boolean isHelloMessageCorrect(String userName) {

        if (helloMessage.getText().equals(HELLO + userName)) {
            logger.info("Hello message is correct");
            return true;
        } else logger.error("Hello message is not correct");
        return false;
    }

    public void logout() {
        logoutButton.click();
    }

}
