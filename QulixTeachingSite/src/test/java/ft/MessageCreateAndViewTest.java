package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageCreateAndViewTest extends TestBase {
    @Test
    public void messageCreationAndViewTest() {
        messagesPage.initMessageCreation();
        Object newMessageData = messagesPage.fillMessageForm(new MessageData()
                .withName("Here not here").withText("there not there").withAuthor(mainPage.getCurrentUser()));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));
        messagesPage.viewCreatedMessage();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));

        messagesPage.deleteTheLatestMessage(newMessageData);
    }
}
