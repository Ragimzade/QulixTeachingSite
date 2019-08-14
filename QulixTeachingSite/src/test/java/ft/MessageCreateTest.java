package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageCreateTest extends TestBase {


    @Test
    public void messageCreationTest() {

        messagesPage.initMessageCreation();
        MessageData newMessageData = messagesPage.fillMessageForm(new MessageData()
                .headline("MessageCreateTest").text("Test Text")).author(mainPage.getCurrentUser());
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));

        messagesPage.deleteSelectedMessage(newMessageData);


    }


}
