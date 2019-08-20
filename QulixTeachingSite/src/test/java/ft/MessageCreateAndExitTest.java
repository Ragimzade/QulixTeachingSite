package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class MessageCreateAndExitTest extends TestBase {


    @Test
    public void createAndExitTest() {
        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("!2ваыпп").setText("Test Text").setAuthor(showMessage.getCurrentUser()));
        messageList.goToMessageList();
        Assert.assertTrue(messageList.findMessageInMessageList(newMessageData)==null);//todo не трогай messageList. Лучше вообще удали оттуда метод
                                                                                  //удалил метод
    }
}
