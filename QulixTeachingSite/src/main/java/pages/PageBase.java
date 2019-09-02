package pages;

import model.ConfigFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class PageBase {

    private ConfigFileReader configFileReader;
    ConfigFileReader instance = ConfigFileReader.getInstance();
    protected WebDriver driver;
    protected static final String HELLO = "Hello ";

    public PageBase(WebDriver driver) {
        this.driver = driver;

    }


    public String getCurrentUser() {
        String authorText = driver.findElement(By.xpath(".//span[contains(.,\"" + HELLO + "\")]")).getText();
        String author = authorText.substring((authorText.indexOf(" ")), authorText.indexOf("[")).trim();
        return author;

    }

    public void enterValue(WebElement field, String value) {
        field.clear();
        field.sendKeys(value);
    }


    public boolean isElementPresent(WebElement wrappedElement) {
        boolean exists = false;
        try {
            wrappedElement.getTagName();
            exists = true;
        } catch (NoSuchElementException e) {//todo работает? не должно
            //а почему не должно, работает
            //todo Это будет работать только с элементами, аннотированными @FindBy. Если сделать просто driver.findElement() и передать сюда 
            //работать не будет, будет другое исключение
            //так я этот метод сделал специально для элементов с @FindBy
        }
        return exists;
    }
}
