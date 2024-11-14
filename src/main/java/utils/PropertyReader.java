package utils;

import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log
public class PropertyReader {
    private final Properties properties = new Properties();

    public PropertyReader() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Could not find config.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            log.info("Failed to load config.properties - " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
