package ft;

import model.MessageData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MessageCreateAndModifyTest extends TestBase {


    @Test
    public void createAndModifyTest() {

        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Edited message").setText("edited setText")).setAuthor(showMessage.getCurrentUser());
        System.out.println(newMessageData);
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData());
        messageList.goToMessageList();
        System.out.println(messageList.createXpathForList(newMessageData));
        Assert.assertTrue(messageList.assertMessageInList(newMessageData));
        messageList.editFoundMessage(newMessageData);

        Assert.assertEquals(showMessage.getEditFormData().getHeadline(), newMessageData.getHeadline());
        Assert.assertEquals(showMessage.getEditFormData().getText(), newMessageData.getText());

        MessageData editedMessage = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Modified message").setText("createAndModifyTest"));
        createMessage.submitMessageModification();
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageInList(editedMessage));


        messageList.deleteFoundMessage(editedMessage);
        Assert.assertFalse(messageList.assertMessageInList(editedMessage));
    }
}
