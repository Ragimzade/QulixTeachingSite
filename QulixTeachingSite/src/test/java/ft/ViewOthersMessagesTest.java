package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MessageData;

public class ViewOthersMessagesTest extends TestBase {


    @Test
    public void viewOthersMessagesTest() {
        messagesPage.initMessageCreation();
        MessageData newMessageData = messagesPage.fillMessageForm(new MessageData()
                .headline("Edited messagfe").text("new message").author(mainPage.getCurrentUser()));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.getShowMessagePageData());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));
        messagesPage.viewSelectedMessage(newMessageData);
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, messagesPage.getShowMessagePageData());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));
        mainPage.logout();


        mainPage.login("jdoe", "password");
        mainPage.isHelloMessagePresent("John Doe");
        messagesPage.isMessageListTablePresent();
        messagesPage.initMessageCreation();
        MessageData secondMessageData = messagesPage.fillMessageForm(new MessageData()
                .headline("Edited messagfe")
                .text("edited text")
                .author(mainPage.getCurrentUser()));
        messagesPage.submitMessageCreation();
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessageData, messagesPage.getShowMessagePageData());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageLists().contains(secondMessageData));
        messagesPage.viewSelectedMessage(secondMessageData);
        messagesPage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessageData, messagesPage.getShowMessagePageData());
        messagesPage.goToMessageList();
        Assert.assertTrue(messagesPage.getMessageLists().contains(secondMessageData));
        mainPage.logout();

        mainPage.login("admin", "password");
        mainPage.isHelloMessagePresent("Administrator");
        messagesPage.showMessagesOfAllUsers();
        Assert.assertTrue(messagesPage.getMessageLists().contains(secondMessageData));
        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));

        messagesPage.showMessagesOfAllUsers();
        Assert.assertFalse(messagesPage.getMessageLists().contains(secondMessageData));
        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));

        messagesPage.deleteSelectedMessage(newMessageData);
        mainPage.logout();

        mainPage.login("jdoe", "password");
        messagesPage.deleteSelectedMessage(secondMessageData);

    }
}
