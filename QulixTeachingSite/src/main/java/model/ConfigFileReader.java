package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    private Properties properties;
    private final String propertyFilePath = "src/main/resources/config.properties";


    public ConfigFileReader() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }


    public String getApplicationUrl() {
        String url = properties.getProperty("baseUrl");
        if (url != null) return url;
        else throw new RuntimeException("url not specified in the Configuration.properties file.");
    }

    public String getAdminLogin() {
        String login = properties.getProperty("creds.Login");
        if (login != null) return login;
        else throw new RuntimeException("login is not specified in the Config.properties file.");
    }

    public String getAdminPassword() {
        String password = properties.getProperty("creds.Password");
        if (password != null) return password;
        else throw new RuntimeException("login is not specified in the Config.properties file.");
    }

    public String getExplicitWait() {
        String explicitWait = properties.getProperty("explicitWaits");
        if (explicitWait != null) return explicitWait;
        else throw new RuntimeException("explicitWait is not specified in the Config.properties file.");
    }

}