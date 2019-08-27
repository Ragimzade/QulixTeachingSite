package pages;

import model.MessageData;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MessageList extends PageBase {
    private Properties properties;
    private static final Logger logger = Logger.getLogger(MessageList.class);
    private MessageData messageData;
    private MainPage mainPage;
    private LoginPage loginPage;


    public MessageList(WebDriver driver) throws IOException {
        super(driver);
        PageFactory.initElements(driver, this);

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

    @FindBy(xpath = ".//div[@class=\"paginateButtons\"]")
    private WebElement paginator;

    @FindBy(xpath = ".//a[contains(.,'1')]")
    private WebElement firstPage;


    public void goToMessageList() {
        messageList.click();
    }

    public boolean isMessageListTablePresent() {
        return (new WebDriverWait(driver, Long.parseLong((configFileReader.getExplicitWait())))).
                until(ExpectedConditions.visibilityOf(messageListTable)).isDisplayed();
    }

    public void showMessagesOfAllUsers() {
        allUsersCheckBox.click();
    }


    public void deleteFoundMessage(MessageData messageData) {
        //todo так findMessageInMessageList может вернуть null? А потом ищи откуда эти чертовы NPE лезут
        //теперь NoSuchElementEx
        findMessageInMessageList(messageData).findElement(By.xpath(".//a[3]")).click();
    }

    public void viewFoundMessage(MessageData messageData) {
        findMessageInMessageList(messageData).findElement(By.xpath(".//a[1]")).click();
    }

    public void modifyFoundMessage(MessageData messageData) {
        findMessageInMessageList(messageData).findElement(By.xpath(".//a[2]")).click();
    }


    private WebElement findMessageInMessageList(MessageData messageData) {//Возвращать WebElement в public - плохо
        //поправил
        WebElement message = findMessageOnPage(messageData);
        if (message != null) {
            return message;
        }
        if (isElementPresent(paginator) && getCurrentPage() != 1) {
            goToFirstPage();
            message = findMessageWithPaginator(messageData, nextPage);
            //todo что будет в твоей логике если я сейчас на 3ей странице из 5ти?
            //поправил
        } else if (isElementPresent(nextPage) && getCurrentPage() == 1) {
            message = findMessageWithPaginator(messageData, nextPage);
        }
        if (message != null) {
            return message; //todo Возвращение null - это плохая практика. Для публичных методов лучше Exception
            //поправил
        }
        throw new NoSuchElementException("Message is not found");
    }


    private WebElement findMessageWithPaginator(MessageData messageData, WebElement paginator) {
        WebElement message = null;
        //ты этот метод проверял на количестве страниц >2? Это не должно работать
        //проверял на 1-5 страницах. Почему не должно работать, из-за @FindBy?
        while (message == null && isElementPresent(paginator)) {
            paginator.click();
            message = findMessageOnPage(messageData);
        }
        return message;
    }

    private WebElement findMessageOnPage(MessageData messageData) {

        try {
            return driver.findElement(By.xpath(createXpathForList(messageData)));

        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public String createXpathForList(MessageData messageData) {

        return "//tbody/tr" + "[contains(.,'" + messageData.getHeadline() + "')" + "and contains(.,'" + messageData.getText() + "')"
                + "and contains(.,'" + messageData.getAuthor() + "')]";
    }


    public boolean assertMessageIsPresent(MessageData messageData) {
        try {
            findMessageInMessageList(messageData);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public int getCurrentPage() {
        return Integer.parseInt(driver.findElement(By.xpath(".//span[@class=\"currentStep\"]")).getText());
    }

    public void goToFirstPage() {
        firstPage.click();
    }

}



