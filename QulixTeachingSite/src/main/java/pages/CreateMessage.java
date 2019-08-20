package pages;

import model.MessageData;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CreateMessage {
    private WebDriver driver;
    private static final Logger logger = Logger.getLogger(MessageList.class);
    private Properties properties;

    public CreateMessage(WebDriver driver) throws IOException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        properties = new Properties();
        properties.load(new FileReader(new File((String.format("src/main/resources/config.properties")))));

    }


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

    @FindBy(xpath = ".//h1[contains(.,'Edit Message')]")
    private WebElement editMessageForm;

    @FindBy(name = "_action_save")
    private WebElement submitMessageModification;

    public void initMessageCreation() {
        newMessageButton.click();
        Assert.assertTrue(createMessageForm.isDisplayed());
    }


    public MessageData fillMessageForm(MessageData messageData) {
        enterValue(headline, messageData.getHeadline());
        enterValue(text, messageData.getText());

        return messageData;
    }

    private void enterValue(WebElement field, String value) {
        field.clear();
        field.sendKeys(value); //todo enterValue
    }

    public void submitMessageCreation() {
        sumbitButton.click();
    }

    public void submitMessageModification() {
        submitMessageModification.click();
    }
}
