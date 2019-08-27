package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageCreateAndExitTest extends TestBase {


    @Test
    public void createAndExitTest() {
        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("!createAndExitTest").setText("Test Text").setAuthor(showMessage.getCurrentUser()));
        messageList.goToMessageList();
        Assert.assertFalse(messageList.assertMessageInList(newMessageData));
    }
}
