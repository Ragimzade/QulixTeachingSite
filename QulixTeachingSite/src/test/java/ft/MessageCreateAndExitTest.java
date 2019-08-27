package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageCreateAndExitTest extends TestBase {


    @Test
    public void createAndExitTest() throws NoSuchFieldException {
        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("!2ваыпп").setText("Test Text").setAuthor(showMessage.getCurrentUser()));
        messageList.goToMessageList();
        Assert.assertFalse(messageList.assertMessageIsPresent(newMessageData));
    }
}
