package ft;

import org.testng.Assert;
import org.testng.annotations.Test;
import model.MessageData;

public class ViewOthersMessagesTest extends TestBase {


    @Test
    public void viewOthersMessagesTest() throws NoSuchFieldException {
        createMessage.initMessageCreation();
        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Edited messagfe").setText("new message").setAuthor(showMessage.getCurrentUser()));
        createMessage.submitMessageCreation();
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageIsPresent(newMessageData));
        messageList.viewFoundMessage(newMessageData);
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getMessageData());

        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageIsPresent(newMessageData));
        loginPage.logout();


        loginPage.login("jdoe", "password");
        loginPage.isHelloMessagePresent("John Doeer");
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
        Assert.assertTrue(messageList.assertMessageIsPresent(secondMessageData));
        messageList.viewFoundMessage(secondMessageData);
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessageData, showMessage.getMessageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.assertMessageIsPresent(secondMessageData));
        loginPage.logout();

        loginPage.login("admin", "password");
        loginPage.isHelloMessagePresent("Administrator");

        messageList.showMessagesOfAllUsers();
        Assert.assertTrue(messageList.assertMessageIsPresent(secondMessageData));
        Assert.assertTrue(messageList.assertMessageIsPresent(newMessageData));


        messageList.showMessagesOfAllUsers();
        Assert.assertTrue(messageList.assertMessageIsPresent(newMessageData));
        Assert.assertFalse(messageList.assertMessageIsPresent(secondMessageData));


        messageList.deleteFoundMessage(newMessageData);
        Assert.assertTrue(messageList.assertMessageIsPresent(newMessageData));
        loginPage.logout();

        loginPage.login("jdoe", "password");
        Assert.assertTrue(messageList.assertMessageIsPresent(secondMessageData));

    }
}
