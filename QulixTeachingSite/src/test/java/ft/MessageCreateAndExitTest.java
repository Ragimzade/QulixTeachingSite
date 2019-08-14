package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageCreateAndExitTest extends TestBase {


    @Test
    public void createAndExitTest() {
        messagesPage.initMessageCreation();
        MessageData newMessageData = messagesPage.fillMessageForm(new MessageData()
                .headline("Test").text("Test Text").author(mainPage.getCurrentUser()));
        messagesPage.goToMessageList();
        Assert.assertFalse(messagesPage.getMessageLists().contains(newMessageData));
    }
}
