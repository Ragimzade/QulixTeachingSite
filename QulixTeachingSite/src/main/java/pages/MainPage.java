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

public class MainPage {
    public static final String HELLO = "Hello ";
    public static String baseUrl;
    private WebDriver driver;
    private static final Logger logger = Logger.getLogger(MessagesPage.class);
    private Properties properties;

    @FindBy(xpath = ".//a[@href=\"/QulixTeachingSite/user/index\"]")
    private WebElement loginLink;

    @FindBy(id = "login")
    private WebElement loginField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(xpath = ".//input[@value='Login']")
    private WebElement loginButton;

    @FindBy(xpath = ".//div[@class=\"body\" and contains(.,'Message List')]")
    private WebElement messageList;

    @FindBy(xpath = ".//a[@href=\"/QulixTeachingSite/user/logout\"]")
    private WebElement logoutButton;

    @FindBy(xpath = ".//span[contains(.,\"" + HELLO + "\")]")
    private WebElement helloMessage;



    public MainPage(WebDriver driver) throws IOException {
        this.driver = driver; 
        PageFactory.initElements(driver, this);
        properties = new Properties();
        properties.load(new FileReader(new File((String.format("src/main/resources/config.properties")))));

    }

    public void goToMainPage() throws IOException {

        driver.get(properties.getProperty("baseUrl")); 
        //done
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"pageBody\"]/h1")).isDisplayed());
    }

    public void goToLoginPage() {
        loginLink.click();
        isLoginButtonPresent();

    }

    public void isLoginButtonPresent() {
        try {
            //todo у тебя IDE справа рисует линию - границу страницы. Оформляй код в пределах этой линии
            new WebDriverWait(driver, Long.parseLong(properties.getProperty("explicitWaits"))).until(ExpectedConditions.visibilityOf(loginButton)); 
            loginButton.isDisplayed();
            logger.info("Login page is opened");
        } catch (TimeoutException e) {
            logger.fatal(e + "Login page is not opened");
            driver.quit();
            throw new RuntimeException("Test ended with critical error");
        }
    }

    public void isHelloMessagePresent(String userName) {
        //todo а что у тебя в get.. делает Assert??
        //Проверяет отображение сообщения, переименовал метод, если в этом проблема
        //todo Да, проблема в имени метода. Метод get - должен отдавать значение или выдавать исключение в случае ошибки
        //метод is... должен возвращать true/false, а если метод isPresent, то в случае отсутствия - просто false, а не исключение
        //если ты хочешь сделать assert так и называй метод
        Assert.assertTrue(helloMessage.getText().contains(HELLO + userName)); //todo Hello - константа
        //Поправил
        new WebDriverWait(driver, Long.parseLong(properties.getProperty("explicitWaits"))).until(ExpectedConditions.visibilityOf(helloMessage)).isDisplayed();

    }


    public void login(String login, String password) {
        loginField.clear();
        loginField.sendKeys(login); //todo но вот тут прямо напрашивается метод а-ля enterValue(field, value){field.clear(); field.sendKeys(value);}
        //todo а чего не сделано?
        Assert.assertFalse(loginField.getAttribute("value").isEmpty()); //todo зачем это делать, непонятно
        passwordField.clear();
        passwordField.sendKeys(password);
        Assert.assertFalse(passwordField.getAttribute("value").isEmpty());
        loginButton.click();
        Assert.assertTrue(messageList.isDisplayed());

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
