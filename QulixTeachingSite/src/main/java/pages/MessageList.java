package pages;

import model.MessageData;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MessageList {
    private Properties properties;
    private WebDriver driver;
    private static final Logger logger = Logger.getLogger(MessageList.class);
    private MessageData messageData;
    private MainPage mainPage;
    private LoginPage loginPage;


    public MessageList(WebDriver driver) throws IOException {
        this.driver = driver;
        PageFactory.initElements(driver, this);

        properties = new Properties();
        properties.load(new FileReader(new File((String.format("src/main/resources/config.properties")))));
        //todo properties загружать как ресурс, а не через путь. MessagePage.class.getResource()
        //todo этого здесь быть не должно. Отдельный класс для работы с конфигом
    }

    @FindBy(linkText = "Message List")
    private WebElement messageList;

    @FindBy(xpath = ".//body[contains(.,\"Message List\")]")
    private WebElement messageListTable;

    @FindBy(xpath = ".//a[contains(.,'View')]")
    private WebElement viewSelectedMessage;

    @FindBy(name = "allUsers")
    private WebElement allUsersCheckBox;

    @FindBy(xpath = ".//a[1]")
    private WebElement viewButton;

    @FindBy(xpath = ".//table")
    private WebElement table;

    @FindBy(xpath = ".//a[@class=\"nextLink\"]")
    private WebElement nextPage;

    @FindBy(xpath = ".//a[@class=\"prevLink\"]")
    private WebElement previousPage;


    public void goToMessageList() {
        messageList.click();
    }

    public boolean isMessageListTablePresent() {
        return (new WebDriverWait(driver, Long.parseLong(properties.getProperty("explicitWaits")))).
                until(ExpectedConditions.visibilityOf(messageListTable)).isDisplayed();
    }

    public void showMessagesOfAllUsers() {
        allUsersCheckBox.click();
    }


    public void deleteFoundMessage(MessageData messageData) {
        //todo так findMessageInMessageList может вернуть null? А потом ищи откуда эти чертовы NPE лезут
        findMessageInMessageList(messageData).findElement(By.xpath(".//a[3]")).click();
    }

    public void viewFoundMessage(MessageData messageData) {
        findMessageInMessageList(messageData).findElement(By.xpath(".//a[1]")).click();
    }

    public void modifyFoundMessage(MessageData messageData) {
        findMessageInMessageList(messageData).findElement(By.xpath(".//a[2]")).click();
    }

    private WebElement findMessageOnPage(MessageData messageData) {

        try {
            return driver.findElement(By.xpath("//tbody/tr" + createXpathForList(messageData)));
            //todo почему createXpathForList не формирует полный xpath? Зачем эти дописывания //tbody/tr?
        } catch (NoSuchElementException e) {
            return null;  
        }

    }


    public WebElement findMessageInMessageList(MessageData messageData) {//Возвращать WebElement в public - плохо

        WebElement message = findMessageOnPage(messageData);

        if (message != null) {
            return message;
        }

        if (isElementPresent(nextPage)) {
            //todo что будет в твоей логике если я сейчас на 3ей странице из 5ти?
            message = findMessageWithPaginator(messageData, nextPage);
        } else if (isElementPresent(previousPage)) {
            message = findMessageWithPaginator(messageData, previousPage);
        }

        return message;//todo Возвращение null - это плохая практика. Для публичных методов лучше Exception
    }


    private WebElement findMessageWithPaginator(MessageData messageData, WebElement paginator) {
        WebElement message = null;
        //ты этот метод проверял на количестве страниц >2? Это не должно работать
        while (message == null && isElementPresent(paginator)) {
            paginator.click();
            message = findMessageOnPage(messageData);
        }
        return message;
    }


    public String createXpathForList(MessageData messageData) {

        return "[contains(.,'" + messageData.getHeadline() + "')" + "and contains(.,'" + messageData.getText() + "')"
                + "and contains(.,'" + messageData.getAuthor() + "')]";
    }

    public boolean isElementPresent(WebElement element) {
        boolean exists = false;
        try {
            element.getTagName();
            exists = true;
        } catch (NoSuchElementException e) {//todo работает? не должно

        }
        return exists;
    }

}



