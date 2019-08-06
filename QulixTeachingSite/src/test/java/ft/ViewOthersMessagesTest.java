package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class ViewOthersMessagesTest extends TestBase {

    @Test
    public void viewOthersMessagesTest() {
        messagesPage.initMessageCreation();
        Object newMessageData = messagesPage.fillMessageForm(new MessageData()
                .withName("Edited messagfe").withText("new message").withAuthor(mainPage.getCurrentUser()));
        System.out.println(newMessageData);
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));
        messagesPage.viewCreatedMessage();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));
        mainPage.logout();


        mainPage.login("jdoe", "password");
        mainPage.getHelloMessage("John Doe");
        messagesPage.isMessageListTablePresent();
        messagesPage.initMessageCreation();
        Object secondMessageData = messagesPage.fillMessageForm(new MessageData()
                .withName("Edited messagfe")
                .withText("edited text")
                .withAuthor(mainPage.getCurrentUser()));
        System.out.println(secondMessageData);
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(secondMessageData));
        messagesPage.viewCreatedMessage();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessageData, messagesPage.showMessage());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageList().contains(secondMessageData));
        mainPage.logout();

        mainPage.login("admin", "password");
        mainPage.getHelloMessage("Administrator");
        messagesPage.showMessagesOfAllUsers();
        Assert.assertTrue(messagesPage.getMessageList().contains(secondMessageData));
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));

        messagesPage.showMessagesOfAllUsers();
        Assert.assertFalse(messagesPage.getMessageList().contains(secondMessageData));
        Assert.assertTrue(messagesPage.getMessageList().contains(newMessageData));

        messagesPage.deleteTheLatestMessage(newMessageData);
        mainPage.logout();

        mainPage.login("jdoe", "password");
        messagesPage.deleteTheLatestMessage(secondMessageData);

    }
}
