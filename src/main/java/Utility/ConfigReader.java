package Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private Properties properties;

    public ConfigReader(String filePath) throws IOException {
        properties = new Properties();
        FileInputStream file = new FileInputStream(filePath);
        properties.load(file);
        file.close();
    }

    // Get XPath from properties file
    public String getXPath(String key) {
        return properties.getProperty(key);
    }

    // Get Application URL (hardcoded in Utility class)
    public String getAppUrl() {
        return properties.getProperty("app.URL");
    }
}
