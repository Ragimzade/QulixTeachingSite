package pages;

import model.MessageData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CreateMessage extends PageBase {



    public CreateMessage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
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

    public void submitMessageCreation() {
        sumbitButton.click();
    }

    public void submitMessageModification() {
        submitMessageModification.click();
    }

}
