package alAssistance;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private static Properties properties;

    static {
        try (FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\alAssistance\\configAI.properties")) {
            properties = new Properties();
            properties.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
