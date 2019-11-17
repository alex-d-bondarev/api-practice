import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {
    private static final String CONFIG_PROPERTY_FILE_NAME = System.getProperty("configFile");

    private static ConfigProperties instance = null;
    private static Properties properties = null;

    public static String getProperty(String key){
        getInstance();
        return properties.getProperty(key);
    }

    private static void getInstance(){
        if(instance == null)
            instance = new ConfigProperties();
    }

    private ConfigProperties(){
        InputStream input = getInputStreamWithConfigFile();

        try {
            loadProperties(input);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private InputStream getInputStreamWithConfigFile() {
        InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_PROPERTY_FILE_NAME);
        if(input == null)
            throw new NoConfigFile();

        return input;
    }

    private void loadProperties(InputStream input) throws IOException {
        properties = new Properties();
        properties.load(input);
        input.close();
    }

    public static class NoConfigFile extends RuntimeException{}
}
