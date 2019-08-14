package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageCreateAndModifyTest extends TestBase {


    @Test
    public void createAndModifyTest() {

        messagesPage.initMessageCreation();
        MessageData newMessageData = messagesPage.fillMessageForm(new MessageData()
                .headline("Edited message").text("edited text").author(mainPage.getCurrentUser()));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.getShowMessagePageData());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));
        messagesPage.modifySelectedMessage(newMessageData);
        Assert.assertEquals(newMessageData, messagesPage.getEditFormData());
        MessageData editedMessage = messagesPage.fillMessageForm(new MessageData()
                .headline("Modified message").text("modified text").author(mainPage.getCurrentUser()));
        messagesPage.submitMessageModification();
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageLists().contains(editedMessage));


        messagesPage.deleteSelectedMessage(editedMessage);
    }
}
