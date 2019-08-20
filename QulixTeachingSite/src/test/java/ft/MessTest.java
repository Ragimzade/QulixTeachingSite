//package ft;
//
//import org.testng.annotations.Test;
//import model.MessageData;
//
//public class MessTest extends TestBase {
//
//    @Test
//    public void createAndDeleteTest() {
//        messagesPage.initMessageCreation();
//        MessageData newMessageData = messagesPage.fillMessageForm(new MessageData()
//                .setHeadline("cvbxcvb").setText("cvbxcvbefdsf").setAuthor(mainPage.getCurrentUser()));
//
//
//        messagesPage.submitMessageCreation();
//        messagesPage.goToMessageList();
//     messagesPage.viewFoundMessage(newMessageData);
//
//
//        //   messagesPage.deleteFoundMessage(newMessageData);
//
//    }
//}