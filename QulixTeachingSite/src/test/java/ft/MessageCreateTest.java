package ft;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class MessageCreateTest extends TestBase {
    private static final Logger logger = Logger.getLogger(MessageCreateTest.class);


    @Test
    public void messageCreationTest() {

        messagesPage.initMessageCreation();
        Object newMessageData = messagesPage.fillMessageForm(new MessageData()
                .withName("Test").withText("Test Text")).withAuthor(mainPage.getCurrentUser());
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));

        messagesPage.deleteTheLatestMessage(newMessageData);


    }


}
