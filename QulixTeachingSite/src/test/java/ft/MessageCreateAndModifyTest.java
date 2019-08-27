package ft;

import model.MessageData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MessageCreateAndModifyTest extends TestBase {


    @Test
    public void createAndModifyTest() {

        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Edited message").setText("edited setText").setAuthor(loginPage.getCurrentUser()));
        System.out.println(newMessageData);
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageInList(newMessageData));
        messageList.modifyFoundMessage(newMessageData);
        System.out.println(showMessage.getEditFormData());
        Assert.assertEquals(newMessageData, showMessage.getEditFormData());

        MessageData editedMessage = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Modified message").setText("createAndModifyTest").setAuthor(loginPage.getCurrentUser()));
        createMessage.submitMessageModification();
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageInList(editedMessage));


        messageList.deleteFoundMessage(editedMessage);
        Assert.assertFalse(messageList.assertMessageInList(editedMessage));
    }
}
