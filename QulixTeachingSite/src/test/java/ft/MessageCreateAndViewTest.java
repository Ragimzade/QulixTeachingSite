package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageCreateAndViewTest extends TestBase {

    @Test
    public void messageCreationAndViewTest() {
        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Here not here").setText("there not there").setAuthor(loginPage.getCurrentUser()));
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getShowMessagePageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.getMessageLists().contains(newMessageData));
        messageList.viewSelectedMessage(newMessageData);
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getShowMessagePageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.getMessageLists().contains(newMessageData));

        messageList.deleteSelectedMessage(newMessageData);
    }
}
