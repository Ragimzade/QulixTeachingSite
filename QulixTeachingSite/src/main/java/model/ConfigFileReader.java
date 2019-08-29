package model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileReader {

    protected Properties prop = null;
    protected InputStream input = ConfigFileReader.class.getClassLoader().getResourceAsStream("config.properties"); //todo я говорил, использовать class.getResourceAsStream

    public ConfigFileReader() {
        prop = new Properties();
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBaseUrl() {
        String url = prop.getProperty("baseUrl");
        if (url != null)
            return url;
        else throw new RuntimeException("url is null");
    }

    public String getLogin() {
        return prop.getProperty("creds.Login");
    }

    public String getPassword() {
        return prop.getProperty("creds.Password");
    }

    public String getExplicitWait() {
        return prop.getProperty("explicitWaits");
    }


}



