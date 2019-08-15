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
    private WebDriver driver;
    private static final Logger logger = Logger.getLogger(MessagesPage.class);
    private MessageData messageData;
    private MainPage mainPage;
    private String author;//todo зачем нам это хранить?
    // для метода fillMessageForm
    // todo Вот я тебе прозрачно намекнул, что этого быть не должно. Если тебе это надо в том методе, значит что-то не то с методом


    public MessagesPage(WebDriver driver) throws IOException {
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
        this.headline.sendKeys(messageData.getHeadline()); //todo enterValue
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
        } catch (NoSuchElementException ex) {//todo почитай гайд, какое исключение приходит из WebDriverWait
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

        for (int i = 0; i < elements.size(); i++) {//todo for(element:elements)

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
        //todo Я все равно не понимаю. Что этот метод делает здесь
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
        //todo Я все равно не понимаю. Что этот метод делает здесь
        String name = driver.findElement(By.xpath(".//input[@name=\"headline\"]")).getAttribute("value");
        String text = driver.findElement(By.xpath(".//input[@name=\"text\"]")).getAttribute("value");

        MessageData editMessageForm = new MessageData().headline(name).text(text).author(author);
        return editMessageForm;

    }

    public void submitMessageModification() {
        submitMessageModification.click();
    }


    public boolean isMessageListTablePresent() {
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
        //todo НЕТ!!!!!!!!!!!!! это бред:
        //  1. Факт, что getText() можно напрямую сравнивать с messageData - не очевиден и ломает мозг, любому кто читает метод
        //  2. Даже если ты хочешь метод сравнения текста с messageData, то у тебя должен быть метод,
        //     явно приводящий messageData к требуемой! строке, а не просто toString(), 
        //     и уж точно здесь метод toString() должен был бы вызываться явно
        //  3. Я не понял, почему нет конверсии messageData в xpath и поиска по xpath?
    }

    public void modifySelectedMessage(MessageData messageData) {
        selectMessage(messageData, ".//a[2]");
        Assert.assertTrue(editMessageForm.isDisplayed()); //todo зачем здесь Assert и почему его нет во view?
        //по тест кейсу - "проверить отображение формы", добавил Assert в viewCreatedMessage
        //todo ну так и делай проверку в тесте, чего ты сюда это сунешь
    }

    public void viewSelectedMessage(MessageData messageData) {
        //todo вот у всех у вас. Вы все пытаетесь исходить из того, что созданное сообщение будет в конце списка. Но никто вам этого не обещал
        //todo во-вторых список там с paging-ом, т.е. страниц может быть более одной. Метод должен принимать MessageData, который хотим найти
        //поправил
        selectMessage(messageData, ".//a[1]");
        isMessageListTablePresent();
    }


    private void findMessage(MessageData messageData, String xpathExpression) {
        //todo ну вот у тебя есть метод поиска messageData строки по xpath. Почему там выше ты делаешь какой-то text.contains()???
        WebElement tr = driver.findElement(By.xpath("//tbody/tr" + messageData.findByXpath()));
        //todo ну вот посмотри сам. У тебя метод называется findMessage. Какого собственно черта тут делается еще какой-то click????
        //либо имя метода меняй, либо удаляй отсюда клик
        tr.findElement(By.xpath(xpathExpression)).click();
    }


    private void selectMessage(MessageData messageData, String xpathExpression) {
        //todo Ты понимаешь, что кроме тебя в твоих методах никто не разберется? findMessage, selectMessage. 
        //Эти методы делают не пойми что. Точнее понять можно, но только посмотрев исходный код. Меняй
        
        //todo ну вот тут первое уловие if - это же явный дубликат кода. Ровно это же есть в findMessage
        if (isElementPresent(By.xpath("//tbody/tr" + messageData.findByXpath())) && !isWebElementPresent(nextPage)) {
            findMessage(messageData, xpathExpression);
        } else {
            do {//todo а если я на последней странице сейчас, то первые страницы проверять не надо?
                nextPage.click();
            }//todo Что за форматирование????
            while (!isElementPresent(By.xpath("//tbody/tr" + messageData.findByXpath())));//Copy Paste - наше всё?
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
        return isElementPresent(By.xpath(String.valueOf(webElement)));//todo Это еще что за хак такой? Завязывай
    }
}



