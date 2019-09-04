package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

        return isElementPresent(loginButton);

    }


    public void login(String login, String password) {
        enterValue(loginField, login);
        enterValue(passwordField, password);
        loginButton.click();
    }

    public boolean isHelloMessagePresent() {

        return isElementPresent(helloMessage);
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
