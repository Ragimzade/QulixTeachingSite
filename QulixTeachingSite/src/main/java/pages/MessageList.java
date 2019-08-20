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
//todo зачем нам это хранить?
    // для метода fillMessageForm
    // todo Вот я тебе прозрачно намекнул, что этого быть не должно. Если тебе это надо в том методе, значит что-то не то с методом


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
        return (new WebDriverWait(driver, Long.parseLong(properties.getProperty("explicitWaits")))).until(ExpectedConditions.visibilityOf(messageListTable)).isDisplayed();
    }

    public void showMessagesOfAllUsers() {
        allUsersCheckBox.click();
    }


    public void deleteSelectedMessage(MessageData messageData) {
        findMessageInMessageList(messageData).findElement(By.xpath(".//a[3]")).click();

        //  Assert.assertFalse(table.getText().contains(messageData));//todo такая проверка очень дорогая по времени. getMessagesList отнимает солидно времени
        //заменил на такую проверку
        //todo НЕТ!!!!!!!!!!!!! это бред:
        //  1. Факт, что getText() можно напрямую сравнивать с messageData - не очевиден и ломает мозг, любому кто читает метод
        //  2. Даже если ты хочешь метод сравнения текста с messageData, то у тебя должен быть метод,
        //     явно приводящий messageData к требуемой! строке, а не просто toString(),
        //     и уж точно здесь метод toString() должен был бы вызываться явно
        //  3. Я не понял, почему нет конверсии messageData в xpath и поиска по xpath?
        //убрал проверку из метода, добавил в тестовые классы ассерты вида:
        //  "Assert.assertTrue(messagesPage.findMessageInMessageList(newMessageData) == null);"
    }

    public void viewSelectedMessage(MessageData messageData) {
        findMessageInMessageList(messageData).findElement(By.xpath(".//a[1]")).click();
    }

    public void modifySelectedMessage(MessageData messageData) {
        findMessageInMessageList(messageData).findElement(By.xpath(".//a[2]")).click();

    }

    private WebElement findMessageOnPage(MessageData messageData) {

        try {
            return driver.findElement(By.xpath("//tbody/tr" + createXpathForList(messageData)));

        } catch (NoSuchElementException e) {
            return null;
        }

    }


    public WebElement findMessageInMessageList(MessageData messageData) {

        WebElement message = findMessageOnPage(messageData);

        if (message != null) {
            return message;
        }

        if (isElementPresent(nextPage)) {
            message = findMessageWithPaginator(messageData, nextPage);
        } else if (isElementPresent( previousPage)) {
            message = findMessageWithPaginator(messageData, previousPage);
        }

        return message;
    }


    private WebElement findMessageWithPaginator(MessageData messageData, WebElement paginator) {
        WebElement message = null;
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
        } catch (NoSuchElementException e) {

        }
        return exists;
    }


}



