package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageDoubleCreateTest extends TestBase {
    @Test
    public void doubleCreateTest()  {
        createMessage.initMessageCreation();
        MessageData firstMessage = createMessage.fillMessageForm(new MessageData()
                .setHeadline("First message").setText("there not there").setAuthor(showMessage.getCurrentUser()));
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(firstMessage, showMessage.getMessageData());

        createMessage.initMessageCreation();
        MessageData secondMessage = createMessage.fillMessageForm(new MessageData().setHeadline("Second message")
                .setText("there not there")
                .setAuthor(showMessage.getCurrentUser()));
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessage, showMessage.getMessageData());
        messageList.goToMessageList();

        Assert.assertTrue(messageList.assertMessageInList(firstMessage));
        Assert.assertTrue(messageList.assertMessageInList(secondMessage));


        messageList.deleteFoundMessage(secondMessage);
        Assert.assertFalse(messageList.assertMessageInList(secondMessage));

        messageList.deleteFoundMessage(firstMessage);
        Assert.assertFalse(messageList.assertMessageInList(firstMessage));

    }
}
