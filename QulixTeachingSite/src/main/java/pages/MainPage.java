package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class MainPage {
    private static WebDriver driver;
    private static final Logger logger = Logger.getLogger(MessagesPage.class);

    @FindBy(xpath = ".//a[@href=\"/QulixTeachingSite/user/index\"]")
    private WebElement loginLink;

    @FindBy(id = "login")
    private WebElement loginField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(xpath = ".//input[@value='Login']")
    private WebElement loginButton;

    @FindBy(xpath = ".//div[@class=\"body\" and contains(.,'Message List')]")
    private WebElement messageList;

    @FindBy(xpath = ".//a[@href=\"/QulixTeachingSite/user/logout\"]")
    private WebElement logoutButton;

    @FindBy(xpath = ".//div[@class=\"message\" and contains(.,'Hello John Doe')]")
    private WebElement helloMessage;

    public void goToMainPage() {
        driver.get("http://localhost:8080/QulixTeachingSite/");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"pageBody\"]/h1")).isDisplayed());
    }

    public void goToLoginPage() {
        loginLink.click();
        isLoginButtonPresent();

    }

    public void isLoginButtonPresent() {
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(loginButton));
            loginButton.isDisplayed();
            logger.info("Login page is opened");
        } catch (TimeoutException e) {
            logger.fatal(e + "Login page is not opened");
            driver.quit();
            throw new RuntimeException("Test ended with critical error");
        }
    }

    public void login(String login, String password) {
        loginField.clear();
        loginField.sendKeys(login);
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton.click();
        Assert.assertTrue(messageList.isDisplayed());

    }

    public void logout() {
        logoutButton.click();
    }

    public boolean isHelloMessagePresent() {
        return (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(helloMessage)).isDisplayed();
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


}
