package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageCreateAndModifyTest extends TestBase {

    @Test
    public void createAndModifyTest() {

        messagesPage.initMessageCreation();
        Object newMessageData = messagesPage.fillMessageForm(new MessageData()
                .withName("Edited messagfe").withText("edited text").withAuthor(mainPage.getCurrentUser()));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));
        messagesPage.modifyCreatedMessage();
        Assert.assertEquals(newMessageData, messagesPage.editMessageForm());
        Object editedMessage = messagesPage.fillMessageForm(new MessageData()
                .withName("Modified message").withText("modified text").withAuthor(mainPage.getCurrentUser()));
        messagesPage.submitMessageModification();
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(editedMessage));


        messagesPage.deleteTheLatestMessage(editedMessage);
    }
}
