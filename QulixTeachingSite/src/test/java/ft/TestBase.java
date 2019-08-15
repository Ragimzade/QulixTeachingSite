package ft;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.MainPage;
import pages.MessageData;
import pages.MessagesPage;
import pages.WebDriverSingleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    private static final Logger logger = Logger.getLogger(TestBase.class);
    MainPage mainPage;
    MessagesPage messagesPage;
    MessageData messageData;
    private WebDriverSingleton webDriverSingleton;
    private Properties properties;


    @BeforeClass
    public void init() throws IOException {
        properties = new Properties();
        properties.load(new FileReader(new File((String.format("src/main/resources/config.properties")))));//todo в отдельый класс
        WebDriver driver = webDriverSingleton.getInstance();
        mainPage = new MainPage(driver);
        messagesPage = new MessagesPage(driver);
        //todo Я разве не говорил убрать implicitlyWait?
        driver.manage().timeouts().implicitlyWait(Long.parseLong(properties.getProperty("implicitlyWait")), TimeUnit.SECONDS);
        mainPage.goToMainPage();
        mainPage.goToLoginPage();
        mainPage.login(properties.getProperty("creds.Login"), properties.getProperty("creds.Password"));

    }

    @AfterClass
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
