package pages;

import model.MessageData;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static pages.LoginPage.HELLO;

public class ShowMessage {

    private WebDriver driver;
    private Properties properties;
    private static final Logger logger = Logger.getLogger(MessageList.class);
    private MessageData messageData; //todo нет
    private MainPage mainPage;
    private LoginPage loginPage;

    public ShowMessage(WebDriver driver) throws IOException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        properties = new Properties();
        properties.load(new FileReader(new File((String.format("src/main/resources/config.properties")))));

    }


    @FindBy(xpath = ".//h1[contains(.,'Show Message')]")
    private WebElement showMessagePage;


    public boolean isShowMessageFormDisplayed() {
        try {
            new WebDriverWait(driver, Long.parseLong(properties.getProperty("explicitWaits"))).until(ExpectedConditions
                    .visibilityOf(showMessagePage));
            logger.info("Message page is displayed");
            return true;
        } catch (RuntimeException ex) {//todo почитай гайд, какое исключение приходит из WebDriverWait
            //без гайдов очевидно
            //todo не очевидно, как видим. Почитай
            logger.error("Message page is not displayed");
            return false;
        }
    }

    public MessageData getEditFormData() throws IOException {
        //достает введенные данные из формы "Edit Message", переименовал метод
        //todo Я все равно не понимаю. Что этот метод делает здесь
        //todo Я все равно не понимаю. Что этот метод делает здесь
        String name = driver.findElement(By.xpath(".//input[@name=\"headline\"]")).getAttribute("value");
        String text = driver.findElement(By.xpath(".//input[@name=\"text\"]")).getAttribute("value");
        String author = getCurrentUser();//todo откуда информация, 
        //что на странице редактирования открыто сообщение, созданное этим пользователем?

        //todo имена переменных нормальные должны быть
        MessageData editMessageForm = new MessageData().setHeadline(name).setText(text).setAuthor(author);
        return editMessageForm;

    }

    public MessageData getShowMessagePageData() {//todo просто getMessageData()
        String headline = driver.findElement(By.xpath("(.//td[@class=\"value\"])[1]")).getText();
        String text = driver.findElement(By.xpath("(.//td[@class=\"value\"])[3]")).getText();
        String author = driver.findElement(By.xpath("(.//td[@class=\"value\"])[2]")).getText();

        MessageData showMessageData = new MessageData().setHeadline(headline).setText(text).setAuthor(author);
        return showMessageData;
    }


    public  String getCurrentUser() {//todo Не сюда. Должен быть base class для этой страницы и страницы списка
        String authorText = driver.findElement(By.xpath(".//span[contains(.,\"" + HELLO + "\")]")).getText();
        String author = authorText.substring((authorText.indexOf(" ")), authorText.indexOf("[")).trim();
        return author;
    }
}
