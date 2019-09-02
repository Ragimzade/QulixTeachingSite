package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageCreateAndViewTest extends TestBase {

    @Test
    public void messageCreationAndViewTest() {
        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Here not here").setText("there not there").setAuthor(showMessage.getCurrentUser()));
        createMessage.submitMessageCreation();

        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageInList(newMessageData));
        messageList.viewFoundMessage(newMessageData);
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageInList(newMessageData));

        messageList.deleteFoundMessage(newMessageData);
        Assert.assertFalse(messageList.assertMessageInList(newMessageData));
    }
}
