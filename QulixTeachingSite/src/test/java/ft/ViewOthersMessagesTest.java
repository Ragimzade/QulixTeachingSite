package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class ViewOthersMessagesTest extends TestBase {

    @Test
    public void viewOthersMessagesTest() {
        messagesPage.initMessageCreation();
        Object newMessageData = messagesPage.fillMessageForm(new MessageData().withName("Edited messagfe").withText("edited text"));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        messagesPage.getMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));
        messagesPage.viewCreatedMessage();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        messagesPage.getMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));
        mainPage.logout();
        mainPage.login("jdoe", "password");
        mainPage.isHelloMessagePresent();
        messagesPage.isMessageListTablePresent();


        messagesPage.initMessageCreation();
        Object secondMessageData = messagesPage.fillMessageForm(new MessageData().withName("Edited messagfe").withText("edited text"));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        messagesPage.getMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(secondMessageData));
        messagesPage.viewCreatedMessage();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        messagesPage.getMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(secondMessageData));
        mainPage.logout();

        mainPage.login("admin", "password");
        messagesPage.showMessagesOfAllUsers();
        messagesPage.getMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(secondMessageData));
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));


    }
}
