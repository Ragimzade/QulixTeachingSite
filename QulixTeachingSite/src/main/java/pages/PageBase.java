package pages;

import model.ConfigFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static pages.LoginPage.HELLO;

public class PageBase {
    ConfigFileReader configFileReader;
    protected WebDriver driver;

    public PageBase(WebDriver driver) {
        this.driver = driver;
        configFileReader= new ConfigFileReader();
    }


    public String getCurrentUser() {
        String authorText = driver.findElement(By.xpath(".//span[contains(.,\"" + HELLO + "\")]")).getText();
        String author = authorText.substring((authorText.indexOf(" ")), authorText.indexOf("[")).trim();
        return author;
    }

    public void enterValue(WebElement field, String value) {
        //todo не смущает, что делать это надо не только здесь?
        //вынес сюда в общий класс
        field.clear();
        field.sendKeys(value);
    }


    public boolean isElementPresent(WebElement element) {
        boolean exists = false;
        try {
            element.getTagName();
            exists = true;
        } catch (NoSuchElementException e) {//todo работает? не должно
            //а почему не должно, работает
            //todo Это будет работать только с элементами, аннотированными @FindBy. Если сделать просто driver.findElement() и передать сюда 
            //работать не будет, будет другое исключение
        }
        return exists;
    }
}
