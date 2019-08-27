package pages;

import model.MessageData;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShowMessage extends PageBase {


    private static final Logger logger = Logger.getLogger(MessageList.class);


    public ShowMessage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = ".//h1[contains(.,'Show Message')]")
    private WebElement showMessagePage;


    public boolean isShowMessageFormDisplayed() {
        try {
            new WebDriverWait(driver, Long.parseLong(configFileReader.getExplicitWait())).until(ExpectedConditions
                    .visibilityOf(showMessagePage));
            logger.info("Message page is displayed");
            return true;
        } catch (TimeoutException ex) {
            logger.error("Message page is not displayed" + ex);
            return false;
        }
    }

    public MessageData getEditFormData() {
        String name = driver.findElement(By.xpath(".//input[@name=\"headline\"]")).getAttribute("value");
        String text = driver.findElement(By.xpath(".//input[@name=\"text\"]")).getAttribute("value");
        String author = getCurrentUser();
        // String author = getAuthorOfMessage(messageData);//todo откуда информация,
        //что на странице редактирования открыто сообщение, созданное этим пользователем?
        //если чек-бокс "Show messages of all users" отжат, то показываются сообщения текущего пользователя
        //todo Так а откуда ты знаешь, что чекбокс не нажат??
        //todo А если завтра логика отображения поменяется или дефект?
        //Не можешь ты здесь определить автора - смирись и не пытайся

        //todo имена переменных нормальные должны быть
        MessageData editFormData = new MessageData().setHeadline(name).setText(text).setAuthor(author);
        return editFormData;

    }


    public MessageData getMessageData() {
        String headline = driver.findElement(By.xpath("(.//td[@class=\"value\"])[1]")).getText();
        String text = driver.findElement(By.xpath("(.//td[@class=\"value\"])[3]")).getText();
        String author = driver.findElement(By.xpath("(.//td[@class=\"value\"])[2]")).getText();

        MessageData showMessageData = new MessageData().setHeadline(headline).setText(text).setAuthor(author);
        return showMessageData;
    }


}
