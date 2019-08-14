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
    private WebDriver driver; //todo убираем статик. В целом статик только для констант или при реализации каких-нибудь синглтонов
    //поправил
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
        this.driver = driver; //todo ну вот. Ты драйвер просишь в конструкторе. Зачем он статик тогда?
        //у меня как-то не работал синглтон драйвер, я везде где можно копал, забыл поменять
        PageFactory.initElements(driver, this);
        properties = new Properties();
        properties.load(new FileReader(new File((String.format("src/main/resources/config.properties")))));

    }

    public void goToMainPage() throws IOException {

        driver.get(properties.getProperty("baseUrl")); //todo url Должен браться из конфига
        //done
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"pageBody\"]/h1")).isDisplayed());
    }

    public void goToLoginPage() {
        loginLink.click();
        isLoginButtonPresent();

    }

    public void isLoginButtonPresent() {
        try {
            new WebDriverWait(driver, Long.parseLong(properties.getProperty("explicitWaits"))).until(ExpectedConditions.visibilityOf(loginButton)); //todo 5 - из конфига
            //done
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
        Assert.assertTrue(helloMessage.getText().contains(HELLO + userName)); //todo Hello - константа
        //Поправил
        new WebDriverWait(driver, Long.parseLong(properties.getProperty("explicitWaits"))).until(ExpectedConditions.visibilityOf(helloMessage)).isDisplayed();

    }


    public void login(String login, String password) {
        loginField.clear();
        loginField.sendKeys(login); //todo но вот тут прямо напрашивается метод а-ля enterValue(field, value){field.clear(); field.sendKeys(value);}
        Assert.assertFalse(loginField.getAttribute("value").isEmpty());
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
        String authorText = driver.findElement(By.xpath(".//span[contains(.,\"" + HELLO + "\")]")).getText();//todo Hello в константу
        //Done
        String author = authorText.substring((authorText.indexOf(" ")), authorText.indexOf("[")).trim(); //todo что-то не понятно. Почему lastIndexOf "lo", а не по пробелу? и имя переменной странной
        //Поправил
        return author;


    }

}
