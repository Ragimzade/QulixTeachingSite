package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageCreateAndDeleteTest extends TestBase {


    @Test
    public void createAndDeleteTest() {
        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("createAnddelete").setText("createAndDelete").setAuthor(showMessage.getCurrentUser()));
        createMessage.submitMessageCreation();

        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData(true));
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageInList(newMessageData));


        messageList.deleteFoundMessage(newMessageData);
        Assert.assertFalse(messageList.assertMessageInList(newMessageData));

    }
}
