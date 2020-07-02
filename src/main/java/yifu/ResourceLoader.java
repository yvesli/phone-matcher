package yifu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton class for loading resources and configurations
 */
public class ResourceLoader {

    private static ResourceLoader loader;
    private static final Properties properties = new Properties();

    private ResourceLoader() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config/config.properties");
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file config.properties not found");
            }
        } catch (IOException e) {
            System.err.println("Cannot load config.properties");
            e.printStackTrace();
        }
    }

    public static ResourceLoader getInstance() {
        if (loader == null) {
            loader = new ResourceLoader();
            return loader;
        }
        return loader;
    }

    public File getFile(String fileName) {
        return new File(getClass().getResource(fileName).getFile());
    }

    /**
     * Get configuration value of name
     * @param configName config name
     * @return config value
     */
    public String getConfig(String configName) {
        return properties.getProperty(configName);
    }

}
