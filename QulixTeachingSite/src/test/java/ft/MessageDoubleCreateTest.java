package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageDoubleCreateTest extends TestBase {
    @Test
    public void doubleCreateTest() {
        createMessage.initMessageCreation();
        MessageData firstMessage = createMessage.fillMessageForm(new MessageData()
                .setHeadline("First message").setText("there not there").setAuthor(showMessage.getCurrentUser()));
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(firstMessage, showMessage.getShowMessagePageData());

        createMessage.initMessageCreation();
        MessageData secondMessage = createMessage.fillMessageForm(new MessageData().setHeadline("Second message")
                .setText("there not there")
                .setAuthor(showMessage.getCurrentUser()));
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessage, showMessage.getShowMessagePageData());
        messageList.goToMessageList();

        Assert.assertTrue(messageList.findMessageInMessageList(firstMessage).isDisplayed());
        Assert.assertTrue(messageList.findMessageInMessageList(secondMessage).isDisplayed());


        messageList.deleteFoundMessage(secondMessage);
        Assert.assertTrue(messageList.findMessageInMessageList(secondMessage) == null);

        messageList.deleteFoundMessage(firstMessage);
        Assert.assertTrue(messageList.findMessageInMessageList(firstMessage) == null);

    }
}
