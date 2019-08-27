package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

import java.io.IOException;

public class MessageCreateAndModifyTest extends TestBase {


    @Test
    public void createAndModifyTest() throws IOException {

        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Edited message").setText("edited setText").setAuthor(loginPage.getCurrentUser()));
        System.out.println(newMessageData);
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageIsPresent(newMessageData));
        messageList.modifyFoundMessage(newMessageData);
        Assert.assertEquals(newMessageData, showMessage.getEditFormData());

        MessageData editedMessage = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Modified message").setText("modified setText"));
        createMessage.submitMessageModification();
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageIsPresent(newMessageData));


        messageList.deleteFoundMessage(editedMessage);
    }
}
