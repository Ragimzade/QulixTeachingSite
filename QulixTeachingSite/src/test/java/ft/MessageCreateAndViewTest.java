package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageCreateAndViewTest extends TestBase {

    @Test
    public void messageCreationAndViewTest() throws NoSuchFieldException {
        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Here not here").setText("there not there").setAuthor(showMessage.getCurrentUser()));
        createMessage.submitMessageCreation();

        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageIsPresent(newMessageData));
        messageList.viewFoundMessage(newMessageData);
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageIsPresent(newMessageData));

        messageList.deleteFoundMessage(newMessageData);
        Assert.assertFalse(messageList.assertMessageIsPresent(newMessageData));
    }
}
