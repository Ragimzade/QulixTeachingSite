//package ft;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import model.MessageData;
//
//public class ViewOthersMessagesTest extends TestBase {
//
//
//    @Test
//    public void viewOthersMessagesTest() {
//        createMessage.initMessageCreation();
//        MessageData newMessageData = createMessage.fillMessageForm(new MessageData()
//                .setHeadline("Edited messagfe").setText("new message").setAuthor(mainPage.getCurrentUser()));
//        createMessage.submitMessageCreation();
//        messagesPage.isShowMessageFormDisplayed();
//        Assert.assertEquals(newMessageData, messagesPage.getShowMessagePageData());
//        messagesPage.goToMessageList();
//        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));
//        messagesPage.viewSelectedMessage(newMessageData);
//        messagesPage.isShowMessageFormDisplayed();
//        Assert.assertEquals(newMessageData, messagesPage.getShowMessagePageData());
//        messagesPage.goToMessageList();
//        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));
//        mainPage.logout();
//
//
//        mainPage.login("jdoe", "password");
//        mainPage.isHelloMessagePresent("John Doe");
//        messagesPage.isMessageListTablePresent();
//        messagesPage.initMessageCreation();
//        MessageData secondMessageData = messagesPage.fillMessageForm(new MessageData()
//                .setHeadline("Edited messagfe")
//                .setText("edited setText")
//                .setAuthor(mainPage.getCurrentUser()));
//        messagesPage.submitMessageCreation();
//        messagesPage.isShowMessageFormDisplayed();
//        Assert.assertEquals(secondMessageData, messagesPage.getShowMessagePageData());
//        messagesPage.goToMessageList();
//        Assert.assertTrue(messagesPage.getMessageLists().contains(secondMessageData));
//        messagesPage.viewSelectedMessage(secondMessageData);
//        messagesPage.isShowMessageFormDisplayed();
//        Assert.assertEquals(secondMessageData, messagesPage.getShowMessagePageData());
//        messagesPage.goToMessageList();
//        Assert.assertTrue(messagesPage.getMessageLists().contains(secondMessageData));
//        mainPage.logout();
//
//        mainPage.login("admin", "password");
//        mainPage.isHelloMessagePresent("Administrator");
//        messagesPage.showMessagesOfAllUsers();
//        Assert.assertTrue(messagesPage.getMessageLists().contains(secondMessageData));
//        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));
//
//        messagesPage.showMessagesOfAllUsers();
//        Assert.assertFalse(messagesPage.getMessageLists().contains(secondMessageData));
//        Assert.assertTrue(messagesPage.getMessageLists().contains(newMessageData));
//
//        messagesPage.deleteSelectedMessage(newMessageData);
//        mainPage.logout();
//
//        mainPage.login("jdoe", "password");
//        messagesPage.deleteSelectedMessage(secondMessageData);
//
//    }
//}
