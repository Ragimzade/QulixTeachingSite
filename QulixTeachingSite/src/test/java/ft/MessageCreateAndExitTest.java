package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageCreateAndExitTest extends TestBase {

    @Test
    public void createAndExitTest() {
        messagesPage.initMessageCreation();
        Object newMessageData = messagesPage.fillMessageForm(new MessageData().withName("Test").withText("Test Text"));
        messagesPage.goToMessageList();
        messagesPage.getMessageList();
        Assert.assertFalse(messagesPage.getMessageList().contains(newMessageData));
    }
}
