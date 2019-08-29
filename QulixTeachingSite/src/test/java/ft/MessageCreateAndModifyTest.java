package ft;

import model.MessageData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MessageCreateAndModifyTest extends TestBase {


    @Test
    public void createAndModifyTest() {

        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Edited message").setText("edited setText"));
        System.out.println(newMessageData);
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData(false));
        messageList.goToMessageList();
        System.out.println(messageList.createXpathForList(newMessageData));
        Assert.assertTrue(messageList.assertMessageInList(newMessageData));
        messageList.editFoundMessage(newMessageData);
        System.out.println(showMessage.getEditFormData());
        Assert.assertEquals(newMessageData, showMessage.getEditFormData());

        MessageData editedMessage = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Modified message").setText("createAndModifyTest"));
        createMessage.submitMessageModification();
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageInList(editedMessage));


        messageList.deleteFoundMessage(editedMessage);
        Assert.assertFalse(messageList.assertMessageInList(editedMessage));
    }
}
