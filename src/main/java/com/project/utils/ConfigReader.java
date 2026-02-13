package com.project.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static ConfigReader instance;

    // Private constructor → Singleton
    private ConfigReader() {
        loadProperties();
    }

    // Get single instance
    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    // Load properties file
    private void loadProperties() {
        properties = new Properties();

        String filePath = System.getProperty("user.dir")
                + FilePath.getConfigPropertyFilePath();

        File file = new File(filePath);

        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
            logger.info("Config properties loaded successfully from: " + filePath);
        } catch (IOException e) {
            logger.error("Failed to load config properties file", e);
            throw new RuntimeException("Config file not found: " + filePath);
        }
    }

    // Generic getter
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    // ================= COMMON SETTINGS =================

    public String getBrowser() {
        return getProperty("browser");
    }

    public String getBaseUrl() {
        return getProperty("url");
    }

    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("browser.headless"));
    }

    public String getEnvironment() {
        return getProperty("environment");
    }

    // ================= FRAMEWORK CONFIG =================

    // cucumber-testng | cucumber-junit | playwright-java
    public String getFrameworkType() {
        return getProperty("framework.type");
    }

    // testng | junit
    public String getTestRunner() {
        return getProperty("test.runner");
    }

    // ================= CUCUMBER CONFIG =================

    public String getCucumberTags() {
        return getProperty("cucumber.tags");
    }

    public String getFeaturePath() {
        return getProperty("cucumber.features.path");
    }

    public String getGluePath() {
        return getProperty("cucumber.glue.path");
    }

    // ================= REPORTING =================

    public String getReportPath() {
        return getProperty("report.path");
    }

    public boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure"));
    }

    // ================= FUTURE – PLAYWRIGHT =================

    public boolean isPlaywrightEnabled() {
        return Boolean.parseBoolean(getProperty("playwright.enabled"));
    }

    public boolean isVisualRegressionEnabled() {
        return Boolean.parseBoolean(getProperty("visual.regression.enabled"));
    }

    public String getPlaywrightBrowser() {
        return getProperty("playwright.browser");
    }

}