package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageCreateAndDeleteTest extends TestBase {


    @Test
    public void createAndDeleteTest() {
        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("dmkgndflsg[ds[").setText("sfdbgb234234234").setAuthor(loginPage.getCurrentUser()));
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getShowMessagePageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.findMessageInMessageList(newMessageData).isDisplayed());


        messageList.viewSelectedMessage(newMessageData);


    }
}
