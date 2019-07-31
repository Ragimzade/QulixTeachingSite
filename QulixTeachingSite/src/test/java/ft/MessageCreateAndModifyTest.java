package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageCreateAndModifyTest extends TestBase {

    @Test
    public void createAndModifyTest() {

        messagesPage.initMessageCreation();
        Object newMessageData = messagesPage.fillMessageForm(new MessageData().withName("Edited messagfe").withText("edited text"));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));
        messagesPage.modifyCreatedMessage();
        System.out.println(messagesPage.editMessageForm());
        Assert.assertEquals(newMessageData, messagesPage.editMessageForm());
        Object editedMessage = messagesPage.fillMessageForm(new MessageData().withName("Modified message").withText("modified text"));
        messagesPage.submitMessageModification();
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(editedMessage));

        messagesPage.deleteCreatedMessage();
        Assert.assertFalse(messagesPage.getMessageList().contains(editedMessage));


    }
}
