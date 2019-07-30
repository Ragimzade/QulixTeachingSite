package ft;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.MainPage;
import pages.MessagesPage;
import pages.WebDriverSingleton;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class TestBase {
    private static final Logger logger = Logger.getLogger(TestBase.class);
    MainPage mainPage;
    MessagesPage messagesPage;
    private WebDriverSingleton webDriverSingleton;



    @BeforeClass
    public void init() {
        WebDriver driver = webDriverSingleton.getInstance();
        mainPage = new MainPage(driver);
        messagesPage = new MessagesPage(driver);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        mainPage.goToMainPage();
        mainPage.goToLoginPage();
        mainPage.login("admin", "password");
    }

 //   @AfterClass
  //  public void tearDown() {
   //     WebDriverSingleton.quit();
  //  }

    @BeforeMethod(alwaysRun = true)
    public void logTestStart(Method m) {
        logger.info("Start test " + m.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        logger.info("Stop test " + m.getName());
    }

}
