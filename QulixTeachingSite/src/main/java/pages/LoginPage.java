package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
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
import java.util.Properties;

public class LoginPage {
    private WebDriver driver;
    private static final Logger logger = Logger.getLogger(MessageList.class);
    private Properties properties;
    public static final String HELLO = "Hello ";

    public LoginPage(WebDriver driver) throws IOException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        properties = new Properties();
        properties.load(new FileReader(new File((String.format("src/main/resources/config.properties")))));
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
            //todo у тебя IDE справа рисует линию - границу страницы. Оформляй код в пределах этой линии
            new WebDriverWait(driver, Long.parseLong(properties.getProperty("explicitWaits")))
                    .until(ExpectedConditions.visibilityOf(loginButton));
            loginButton.isDisplayed();
            logger.info("Login page is opened");
        } catch (TimeoutException e) {
            logger.fatal(e + "Login page is not opened");
            driver.quit();
            throw new RuntimeException("Test ended with critical error");
        }
    }


    public void login(String login, String password) {
        enterValue(loginField, login);
        //todo а чего не сделано? //сделал, не сразу понял, что имеется ввиду
        //Assert.assertFalse(loginField.getAttribute("value").isEmpty()); //todo зачем это делать, непонятно
        // так это в кейсах просят такую проверку
        enterValue(passwordField, password);
        //Assert.assertFalse(passwordField.getAttribute("value").isEmpty());
        loginButton.click();
        //Assert.assertTrue(messageList.isDisplayed());
    }

    private void enterValue(WebElement field, String value) {
        field.clear();
        field.sendKeys(value); //todo но вот тут прямо напрашивается метод а-ля enterValue(field, value){field.clear(); field.sendKeys(value);}
    }


    public void assertHelloMessagePresent(String userName) {
        //todo а что у тебя в get.. делает Assert??
        //Проверяет отображение сообщения, переименовал метод, если в этом проблема
        //todo Да, проблема в имени метода. Метод get - должен отдавать значение или выдавать исключение в случае ошибки
        //метод is... должен возвращать true/false, а если метод isPresent, то в случае отсутствия - просто false, а не исключение
        //если ты хочешь сделать assert так и называй метод
        //понял
        Assert.assertTrue(helloMessage.getText().contains(HELLO + userName)); //todo Hello - константа
        //Поправил
        new WebDriverWait(driver, Long.parseLong(properties.getProperty("explicitWaits")))
                .until(ExpectedConditions.visibilityOf(helloMessage)).isDisplayed();
    }


    public void logout() {
        logoutButton.click();
    }


    public String getCurrentUser() {
        String authorText = driver.findElement(By.xpath(".//span[contains(.,\"" + HELLO + "\")]")).getText();
        String author = authorText.substring((authorText.indexOf(" ")), authorText.indexOf("[")).trim();
        return author;
    }
}
