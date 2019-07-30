package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageDoubleCreate extends TestBase {
    @Test
    public void doubleCreate() {
        messagesPage.initMessageCreation();
        Object firstMessage = messagesPage.fillMessageForm(new MessageData().withName("First message").withText("there not there"));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(firstMessage, messagesPage.showMessage());
        messagesPage.initMessageCreation();
        Object secondMessage = messagesPage.fillMessageForm(new MessageData().withName("Second message").withText("there not there"));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessage, messagesPage.showMessage());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(firstMessage));
        Assert.assertTrue(messagesPage.getMessageList().contains(secondMessage));

    }
}
