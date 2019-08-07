package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverSingleton {

    public static WebDriver driver; //todo почему это public?

    private WebDriverSingleton() {
    }

    public static WebDriver getInstance() {
        if (driver == null) {
            driver = new ChromeDriver();
        }
        return driver;
    }

    public static void quit() { 
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }
}

