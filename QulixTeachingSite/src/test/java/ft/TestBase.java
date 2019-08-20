package ft;

import model.WebDriverSingleton;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.*;
import model.MessageData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class TestBase {
    private static final Logger logger = Logger.getLogger(TestBase.class);
    MainPage mainPage;
    MessageData messageData;
    CreateMessage createMessage;
    LoginPage loginPage;
    MessageList messageList;
    ShowMessage showMessage;
    private WebDriverSingleton webDriverSingleton;
    private Properties properties;


    @BeforeClass
    public void init() throws IOException {
        properties = new Properties();
        properties.load(new FileReader(new File((String.format("src/main/resources/config.properties")))));//todo в отдельый класс
        WebDriver driver = webDriverSingleton.getInstance();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        messageList = new MessageList(driver);
        createMessage = new CreateMessage(driver);
        showMessage = new ShowMessage(driver);
        //todo Я разве не говорил убрать implicitlyWait?
        //не говорил, только явные ожидания использовать?
        mainPage.goToMainPage();
        mainPage.goToLoginPage();
        loginPage.isLoginButtonPresent();
        loginPage.login(properties.getProperty("creds.Login"), properties.getProperty("creds.Password"));

    }

//    @AfterClass
//    public void tearDown() {
//        WebDriverSingleton.quit();
//    }

    @BeforeMethod(alwaysRun = true)
    public void logTestStart(Method m) {
        logger.info("Start test " + m.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        logger.info("Stop test " + m.getName());
    }

}
