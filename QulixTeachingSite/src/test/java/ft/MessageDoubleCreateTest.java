package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageDoubleCreateTest extends TestBase {
    @Test
    public void doubleCreateTest() throws NoSuchFieldException {
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

        Assert.assertTrue(messageList.assertMessageIsPresent(firstMessage));
        Assert.assertTrue(messageList.assertMessageIsPresent(secondMessage));


        messageList.deleteFoundMessage(secondMessage);
        Assert.assertTrue(messageList.assertMessageIsPresent(secondMessage));

        messageList.deleteFoundMessage(firstMessage);
        Assert.assertTrue(messageList.assertMessageIsPresent(firstMessage));

    }
}
