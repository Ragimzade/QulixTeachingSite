package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MessagesPage {
    private Properties properties;
    private WebDriver driver; //todo ну ты понял
    private static final Logger logger = Logger.getLogger(MessagesPage.class);
    private MessageData messageData;
    private MainPage mainPage;
    private String author;//todo зачем нам это хранить?
    // для метода fillMessageForm


    public MessagesPage(WebDriver driver) throws IOException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        properties = new Properties();
        properties.load(new FileReader(new File((String.format("src/main/resources/config.properties")))));
    }

    @FindBy(linkText = "Message List")
    private WebElement messageList;

    @FindBy(xpath = ".//body[contains(.,\"Message List\")]")
    private WebElement messageListTable;

    @FindBy(linkText = "New Message")
    private WebElement newMessageButton;

    @FindBy(xpath = ".//form[@action='/QulixTeachingSite/message/save']")
    private WebElement createMessageForm;

    @FindBy(name = "headline")
    private WebElement headline;

    @FindBy(name = "text")
    private WebElement text;

    @FindBy(name = "create")
    private WebElement sumbitButton;

    @FindBy(xpath = ".//h1[contains(text(),'Show Message')]")
    private WebElement showMessagePage;

    @FindBy(xpath = ".//a[contains(.,'View')]")
    private WebElement viewSelectedMessage;

    @FindBy(xpath = ".//h1[contains(.,'Edit Message')]")
    private WebElement editMessageForm;

    @FindBy(name = "_action_save")
    private WebElement submitMessageModification;

    @FindBy(name = "allUsers")
    private WebElement allUsersCheckBox;

    @FindBy(xpath = ".//a[1]")
    private WebElement viewButton;

    @FindBy(xpath = ".//table")
    private WebElement table;

    @FindBy(xpath = ".//a[@class=\"nextLink\"]")
    private WebElement nextPage;


    public void initMessageCreation() {
        newMessageButton.click();
        Assert.assertTrue(createMessageForm.isDisplayed());
    }


    public MessageData fillMessageForm(MessageData messageData) {
        headline.clear();
        this.headline.sendKeys(messageData.getHeadline());
        Assert.assertFalse(headline.getAttribute("value").isEmpty());
        text.clear();
        this.text.sendKeys(messageData.getText());
        Assert.assertFalse(text.getAttribute("value").isEmpty());
        this.author = messageData.getAuthor();

        return messageData;

    }

    public void submitMessageCreation() {
        sumbitButton.click();
    }

    public boolean isShowMessageFormDisplayed() {
        //todo boolean метод предполагает, что вернется true/false. У тебя если элемент будет не найден/не виден вылетит Exception
        //поправил
        try {
            new WebDriverWait(driver, Long.parseLong(properties.getProperty("explicitWaits"))).until(ExpectedConditions.visibilityOf(showMessagePage));
            logger.info("Message page is displayed");
            return true;
        } catch (NoSuchElementException ex) {
            logger.error("Message page is not displayed");
            return false;

        }
    }

    public void goToMessageList() {
        messageList.click();
    }


    public List<MessageData> getMessageLists() {
        List<MessageData> messages = new ArrayList<>();
        //todo несмотря на то что это работает - так коллектить неправильно. Собирай все tr. А уже у них собирай td по индексу в цикле
        //поправил

        List<WebElement> elements = driver.findElements(By.xpath("//tbody//tr"));

        for (int i = 0; i < elements.size(); i++) {

            String name = elements.get(i).findElement(By.xpath(".//td[2]")).getText();
            String text = elements.get(i).findElement(By.xpath(".//td[3]")).getText();
            String author = elements.get(i).findElement(By.xpath(".//td[4]")).getText();

            MessageData messageData = new MessageData().headline(name).text(text).author(author);
            messages.add(messageData);
        }

        return messages;
    }


    public MessageData getShowMessagePageData() {
        //todo что-то я не понимаю, что метод делает
        //Достает данные из страницы "Show Message", переименовал метод
        String headline = driver.findElement(By.xpath("(.//td[@class=\"value\"])[1]")).getText();
        String text = driver.findElement(By.xpath("(.//td[@class=\"value\"])[3]")).getText();
        String author = driver.findElement(By.xpath("(.//td[@class=\"value\"])[2]")).getText();

        MessageData showMessageData = new MessageData().headline(headline).text(text).author(author);
        return showMessageData;
    }


    public MessageData getEditFormData() {
        //todo а почему этот метод у списка?
        //todo и снова, что он делает то?
        //достает введенные данные из формы "Edit Message", переименовал метод
        String name = driver.findElement(By.xpath(".//input[@name=\"headline\"]")).getAttribute("value");
        String text = driver.findElement(By.xpath(".//input[@name=\"text\"]")).getAttribute("value");

        MessageData editMessageForm = new MessageData().headline(name).text(text).author(author);
        return editMessageForm;

    }

    public void submitMessageModification() {
        submitMessageModification.click();
    }


    public boolean isMessageListTablePresent() {
        //todo раньше было 5, теперь 10.
        //поправил
        return (new WebDriverWait(driver, Long.parseLong(properties.getProperty("explicitWaits")))).until(ExpectedConditions.visibilityOf(messageListTable)).isDisplayed();
    }

    public void showMessagesOfAllUsers() {
        allUsersCheckBox.click();
    }


    public void deleteSelectedMessage(MessageData messageData) {
        selectMessage(messageData, ".//a[3]");//todo снова про позиционирование
        //поправил
        Assert.assertFalse(table.getText().contains(messageData));//todo такая проверка очень дорогая по времени. getMessagesList отнимает солидно времени
        //заменил на такую проверку
    }

    public void modifySelectedMessage(MessageData messageData) {
        selectMessage(messageData, ".//a[2]");//todo смотри viewCreatedMessage
        //поправил
        Assert.assertTrue(editMessageForm.isDisplayed()); //todo зачем здесь Assert и почему его нет во view?
        //по тест кейсу - "проверить отображение формы", добавил Assert в viewCreatedMessage
    }

    public void viewSelectedMessage(MessageData messageData) {
        //todo вот у всех у вас. Вы все пытаетесь исходить из того, что созданное сообщение будет в конце списка. Но никто вам этого не обещал
        //todo во-вторых список там с paging-ом, т.е. страниц может быть более одной. Метод должен принимать MessageData, который хотим найти
        //поправил
        selectMessage(messageData, ".//a[1]");
        isMessageListTablePresent();
    }


    private void findMessage(MessageData messageData, String xpathExpression) {
        WebElement tr = driver.findElement(By.xpath("//tbody/tr" + messageData.findByXpath()));
        tr.findElement(By.xpath(xpathExpression)).click();
    }


    private void selectMessage(MessageData messageData, String xpathExpression) {
        if (isElementPresent(By.xpath("//tbody/tr" + messageData.findByXpath())) && !isWebElementPresent(nextPage)) {
            findMessage(messageData, xpathExpression);
        } else {
            do {
                nextPage.click();
            }
            while (!isElementPresent(By.xpath("//tbody/tr" + messageData.findByXpath())));
            findMessage(messageData, xpathExpression);
        }
    }


    public boolean isElementPresent(By xpath) {
        try {
            driver.findElement(xpath);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public boolean isWebElementPresent(WebElement webElement) {
        return isElementPresent(By.xpath(String.valueOf(webElement)));
    }
}



