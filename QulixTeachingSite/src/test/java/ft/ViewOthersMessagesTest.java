package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class ViewOthersMessagesTest extends TestBase {


    @Test
    public void viewOthersMessagesTest() {
        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Edited messagfe").setText("new message").setAuthor(showMessage.getCurrentUser()));
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageInList(newMessageData));
        messageList.viewFoundMessage(newMessageData);
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData());

        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageInList(newMessageData));
        loginPage.logout();


        loginPage.login("jdoe", "password");
        loginPage.isHelloMessagePresent();
        loginPage.isHelloMessageCorrect("John Doe");
        messageList.isMessageListTablePresent();

        createMessage.initMessageCreation();
        MessageData secondMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Edited messagfe")
                .setText("edited setText")
                .setAuthor(showMessage.getCurrentUser()));
        createMessage.submitMessageCreation();

        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessageData, showMessage.getMessageData());

        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageInList(secondMessageData));
        messageList.viewFoundMessage(secondMessageData);
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessageData, showMessage.getMessageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageInList(secondMessageData));
        loginPage.logout();

        loginPage.login("admin", "password");
        loginPage.isHelloMessagePresent();
        loginPage.isHelloMessageCorrect("Administrator");

        messageList.showMessagesOfAllUsers();
        Assert.assertTrue(messageList.assertMessageInList(secondMessageData));
        Assert.assertTrue(messageList.assertMessageInList(newMessageData));


        messageList.showMessagesOfCurrentUser();
        Assert.assertTrue(messageList.assertMessageInList(newMessageData));
        Assert.assertFalse(messageList.assertMessageInList(secondMessageData));


        messageList.deleteFoundMessage(newMessageData);
        Assert.assertFalse(messageList.assertMessageInList(newMessageData));
        loginPage.logout();

        loginPage.login("jdoe", "password");
        messageList.deleteFoundMessage(secondMessageData);
        Assert.assertFalse(messageList.assertMessageInList(secondMessageData));

    }
}
