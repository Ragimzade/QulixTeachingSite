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
        Assert.assertEquals(newMessageData, showMessage.getShowMessagePageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.findMessageInMessageList(newMessageData).isDisplayed());
        messageList.viewFoundMessage(newMessageData);
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(newMessageData, showMessage.getShowMessagePageData());

        messageList.goToMessageList();
        Assert.assertTrue(messageList.findMessageInMessageList(newMessageData).isDisplayed());
        loginPage.logout();


        loginPage.login("jdoe", "password");
        loginPage.assertHelloMessagePresent("John Doe");
        messageList.isMessageListTablePresent();
        createMessage.initMessageCreation();
        MessageData secondMessageData = createMessage.fillMessageForm(new MessageData()
                .setHeadline("Edited messagfe")
                .setText("edited setText")
                .setAuthor(showMessage.getCurrentUser()));
        createMessage.submitMessageCreation();

        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessageData, showMessage.getShowMessagePageData());

        messageList.goToMessageList();
        Assert.assertTrue(messageList.findMessageInMessageList(secondMessageData).isDisplayed());
        messageList.viewFoundMessage(secondMessageData);
        showMessage.isShowMessageFormDisplayed();
        Assert.assertEquals(secondMessageData, showMessage.getShowMessagePageData());
        messageList.goToMessageList();
        Assert.assertTrue(messageList.findMessageInMessageList(secondMessageData).isDisplayed());
        loginPage.logout();

        loginPage.login("admin", "password");
        loginPage.assertHelloMessagePresent("Administrator");

        messageList.showMessagesOfAllUsers();
        Assert.assertTrue(messageList.findMessageInMessageList(secondMessageData).isDisplayed());
        Assert.assertTrue(messageList.findMessageInMessageList(newMessageData).isDisplayed());


        messageList.showMessagesOfAllUsers();
        Assert.assertTrue(messageList.findMessageInMessageList(secondMessageData) == null);
        Assert.assertTrue(messageList.findMessageInMessageList(newMessageData).isDisplayed());


        messageList.deleteFoundMessage(newMessageData);
        Assert.assertTrue(messageList.findMessageInMessageList(newMessageData) == null);
        loginPage.logout();

        loginPage.login("jdoe", "password");
        Assert.assertTrue(messageList.findMessageInMessageList(newMessageData) == null);

    }
}
