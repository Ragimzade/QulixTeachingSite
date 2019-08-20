package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageCreateAndExitTest extends TestBase {


    @Test
    public void createAndExitTest() {
        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Test").setText("Test Text").setAuthor(loginPage.getCurrentUser()));
        messageList.goToMessageList();
        Assert.assertFalse(messageList.getMessageLists().contains(newMessageData));//todo не трогай messageList. Лучше вообще удали оттуда метод
    }
}
