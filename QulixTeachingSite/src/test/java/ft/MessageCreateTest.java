package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageCreateTest extends TestBase {


    @Test
    public void messageCreationTest() {

        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("MessageCreateTest").setText("Test Text")).setAuthor(loginPage.getCurrentUser());
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        messageList.goToMessageList();
        Assert.assertTrue(messageList.findMessageInMessageList(newMessageData).isDisplayed());

        messageList.deleteSelectedMessage(newMessageData);


    }


}
