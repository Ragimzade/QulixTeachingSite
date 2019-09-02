package pages;

import model.MessageData;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MessageList extends PageBase {

    private static final Logger logger = Logger.getLogger(MessageList.class);

    public MessageList(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "Message List")
    private WebElement messageList;

    @FindBy(xpath = ".//body[contains(.,\"Message List\")]")
    private WebElement messageListTable;

    @FindBy(xpath = ".//a[contains(.,'View')]")
    private WebElement viewSelectedMessage;

    @FindBy(name = "allUsers")
    private WebElement allUsersCheckBox;

    @FindBy(xpath = ".//a[1]")
    private WebElement viewButton;

    @FindBy(xpath = ".//table")
    private WebElement table;

    @FindBy(xpath = ".//a[@class=\"nextLink\"]")
    private WebElement nextPage;

    @FindBy(xpath = ".//a[@class=\"prevLink\"]")
    private WebElement previousPage;

    @FindBy(xpath = ".//div[@class=\"paginateButtons\"]")
    private WebElement paginator;

    @FindBy(xpath = ".//a[contains(.,'1')]")
    private WebElement firstPage;


    public void goToMessageList() {
        messageList.click();
    }

    public boolean isMessageListTablePresent() {
        return (new WebDriverWait(driver, (instance.getExplicitWaitTimeout())).
                until(ExpectedConditions.visibilityOf(messageListTable)).isDisplayed());
    }

    public void showMessagesOfAllUsers() {
        if (!allUsersCheckBox.isSelected()) {
            allUsersCheckBox.click();
        }
    }

    public void showMessagesOfCurrentUser() {
        if (allUsersCheckBox.isSelected()) {
            allUsersCheckBox.click();
        }
    }

    public void deleteFoundMessage(MessageData messageData) {
        findMessageInMessageList(messageData).findElement(By.xpath(".//a[3]")).click();
    }

    public void viewFoundMessage(MessageData messageData) {
        findMessageInMessageList(messageData).findElement(By.xpath(".//a[1]")).click();
    }

    public void editFoundMessage(MessageData messageData) {
        findMessageInMessageList(messageData).findElement(By.xpath(".//a[2]")).click();
    }


    private WebElement findMessageInMessageList(MessageData messageData) {
        WebElement message = findMessageOnPage(messageData);
        if (message != null) {
            return message;
        }
        if (isWrappedElementPresent(paginator) && getCurrentPage() != 1) {
            goToFirstPage();
            message = findMessageWithPaginator(messageData, nextPage);
        } else if (isWrappedElementPresent(nextPage) && getCurrentPage() == 1) {
            message = findMessageWithPaginator(messageData, nextPage);
        }
        if (message != null) {
            return message;
        }
        throw new NoSuchElementException("Message is not found");
    }


    private WebElement findMessageWithPaginator(MessageData messageData, WebElement paginator) {
        WebElement message = null;
        while (message == null && isWrappedElementPresent(paginator)) {
            paginator.click();
            message = findMessageOnPage(messageData);
        }
        return message;
    }

    private WebElement findMessageOnPage(MessageData messageData) {

        try {
            return driver.findElement(By.xpath(createXpathForList(messageData)));

        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public String createXpathForList(MessageData messageData) {

        return "//tbody/tr" + "[contains(.,'" + messageData.getHeadline() + "')" + "and contains(.,'" + messageData.getText() + "')]";
    }


    public boolean assertMessageInList(MessageData messageData) {
        try {
            findMessageInMessageList(messageData);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public int getCurrentPage() {
        return Integer.parseInt(driver.findElement(By.xpath(".//span[@class=\"currentStep\"]")).getText());
    }

    public void goToFirstPage() {
        firstPage.click();
    }

}



