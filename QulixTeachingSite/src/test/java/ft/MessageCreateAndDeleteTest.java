package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageCreateAndDeleteTest extends TestBase {


    @Test
    public void createAndDeleteTest() {
        messagesPage.initMessageCreation();
        MessageData newMessageData = messagesPage.fillMessageForm(new MessageData() //todo а чего Object?
                //бесы попутали
                .headline("New Message").text("MessageCreateAndDelete").author(mainPage.getCurrentUser()));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.getShowMessagePageData());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));

        messagesPage.deleteSelectedMessage(newMessageData);


    }
}
