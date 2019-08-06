package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageCreateAndExitTest extends TestBase {

    @Test
    public void createAndExitTest() {
        messagesPage.initMessageCreation();
        Object newMessageData = messagesPage.fillMessageForm(new MessageData()
                .withName("Test").withText("Test Text").withAuthor(mainPage.getCurrentUser()));
        messagesPage.goToMessageList();
        Assert.assertFalse(messagesPage.getMessageList().contains(newMessageData));
    }
}
