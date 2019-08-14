package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageCreateAndViewTest extends TestBase {

    @Test
    public void messageCreationAndViewTest() {
        messagesPage.initMessageCreation();
        MessageData newMessageData = messagesPage.fillMessageForm(new MessageData()
                .headline("Here not here").text("there not there").author(mainPage.getCurrentUser()));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.getShowMessagePageData());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));
        messagesPage.viewSelectedMessage(newMessageData);
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.getShowMessagePageData());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));

        messagesPage.deleteSelectedMessage(newMessageData);
    }
}
