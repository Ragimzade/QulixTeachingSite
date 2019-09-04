package utils;

import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
    private static ConfigFileReader config;
    private Properties prop;


    private ConfigFileReader() {
        prop = new Properties();
        try {
            prop.load(ConfigFileReader.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigFileReader getInstance() {
        if (config == null) {
            config = new ConfigFileReader();
        }
        return config;
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

    public int getExplicitWaitTimeout() {
        return Integer.parseInt(prop.getProperty("timeout"));
    }


}



