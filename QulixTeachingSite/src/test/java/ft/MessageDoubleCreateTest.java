package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageDoubleCreateTest extends TestBase {

    @Test
    public void doubleCreate() {
        messagesPage.initMessageCreation();
        MessageData firstMessage = messagesPage.fillMessageForm(new MessageData()
                .headline("First message").text("MessageDoubleCreateTest").author(mainPage.getCurrentUser()));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(firstMessage, messagesPage.getShowMessagePageData());
        messagesPage.initMessageCreation();
        MessageData secondMessage = messagesPage.fillMessageForm(new MessageData().headline("Second message").text("MessageDoubleCreateTest").author(mainPage.getCurrentUser()));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessage, messagesPage.getShowMessagePageData());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageLists().contains(firstMessage));
        Assert.assertTrue(messagesPage.getMessageLists().contains(secondMessage));


        messagesPage.deleteSelectedMessage(secondMessage);
        messagesPage.deleteSelectedMessage(firstMessage);


    }
}
