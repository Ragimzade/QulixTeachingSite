package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageCreateAndDeleteTest extends TestBase {


    @Test
    public void createAndDeleteTest() throws NoSuchFieldException {
        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("fghdfghdgfh dfgsdfgsdfg").setText("sfdbgb234234234").setAuthor(showMessage.getCurrentUser()));
        createMessage.submitMessageCreation();

        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageIsPresent(newMessageData));


        messageList.deleteFoundMessage(newMessageData);
        Assert.assertFalse(messageList.assertMessageIsPresent(newMessageData));

    }
}
