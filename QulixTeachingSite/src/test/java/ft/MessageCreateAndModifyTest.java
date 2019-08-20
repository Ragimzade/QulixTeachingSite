package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageCreateAndModifyTest extends TestBase {


    @Test
    public void createAndModifyTest() {

        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Edited message").setText("edited setText").setAuthor(loginPage.getCurrentUser()));
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getShowMessagePageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.findMessageInMessageList(newMessageData).isDisplayed());
        messageList.modifySelectedMessage(newMessageData);
        Assert.assertEquals(newMessageData, showMessage.getEditFormData());
        MessageData editedMessage = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Modified message").setText("modified setText").setAuthor(loginPage.getCurrentUser()));
        createMessage.submitMessageModification();
        messageList.goToMessageList();
        Assert.assertTrue(messageList.findMessageInMessageList(editedMessage).isDisplayed());


        messageList.deleteSelectedMessage(editedMessage);
    }
}
