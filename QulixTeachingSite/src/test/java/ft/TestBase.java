package ft;

import utils.ConfigFileReader;
import model.WebDriverSingleton;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.*;

import java.lang.reflect.Method;

public class TestBase {
    private static final Logger logger = Logger.getLogger(TestBase.class);
    MainPage mainPage;
    CreateMessage createMessage;
    LoginPage loginPage;
    MessageList messageList;
    ShowMessage showMessage;
    private WebDriverSingleton webDriverSingleton;
    private ConfigFileReader configFileReader;


    @BeforeClass
    public void init() {

        ConfigFileReader config = configFileReader.getInstance();
        WebDriver driver = webDriverSingleton.getInstance();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        messageList = new MessageList(driver);
        createMessage = new CreateMessage(driver);
        showMessage = new ShowMessage(driver);

        mainPage.goToMainPage();
        mainPage.goToLoginPage();
        loginPage.isLoginButtonPresent();
        loginPage.login(config.getLogin(), config.getPassword());
        messageList.isMessageListTablePresent();

    }

    @AfterSuite
    public void tearDown() {
        WebDriverSingleton.quit();
    }

    @BeforeMethod(alwaysRun = true)
    public void logTestStart(Method m) {
        logger.info("Start test " + m.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        logger.info("Stop test " + m.getName());
    }

}
