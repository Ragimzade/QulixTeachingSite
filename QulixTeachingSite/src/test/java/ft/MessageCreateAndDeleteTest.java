package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageCreateAndDeleteTest extends TestBase {

    @Test
    public void createAndDeleteTest() {
        messagesPage.initMessageCreation();
        Object newMessageData = messagesPage.fillMessageForm(new MessageData()
                .withName("New Message").withText("MessageCreateAndDelete").withAuthor(mainPage.getCurrentUser()));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));


        messagesPage.deleteTheLatestMessage(newMessageData);


    }
}
