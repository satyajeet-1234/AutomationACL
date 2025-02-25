package com.workforceScheduler.dataProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	// Static instance of Properties to store configuration values
    private static Properties properties;
   static FileInputStream input;

    // Static block to load the configuration file once when the class is loaded
    static {
        properties = new Properties();
        try {
            // Path to the config file (relative to the project root)
            String configFilePath ="F:\\Selenium Java BDD Training Workplace\\AutomationOptimumFramework\\src\\test\\resources\\config.properties";
            input = new FileInputStream(configFilePath);
            properties.load(input); // Load the properties from the file
           
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties file");
        }
        finally {
        	try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    /**
     * Retrieves the value of a property from the configuration file.
     *
     * @param key The key of the property to retrieve.
     * @return The value of the property, or null if the key is not found.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Retrieves the value of a property from the configuration file with a default value.
     *
     * @param key          The key of the property to retrieve.
     * @param defaultValue The default value to return if the key is not found.
     * @return The value of the property, or the default value if the key is not found.
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
	
}
