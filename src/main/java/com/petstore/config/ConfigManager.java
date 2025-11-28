package com.petstore.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration manager for loading and accessing application properties
 */
public class ConfigManager {
    private static final Properties properties = new Properties();
    private static ConfigManager instance;

    private ConfigManager() {
        loadProperties();
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                System.err.println("config.properties file not found");
            }
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }

    public String getBaseUrl() {
        return getProperty("base.url", "https://petstore.swagger.io/v2");
    }

    public int getRequestTimeout() {
        return Integer.parseInt(getProperty("request.timeout", "5000"));
    }

    public int getResponseTimeout() {
        return Integer.parseInt(getProperty("response.timeout", "5000"));
    }

    public String getLogLevel() {
        return getProperty("log.level", "INFO");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
