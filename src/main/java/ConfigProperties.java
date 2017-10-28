import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

    private static final String CONFIG_PROPERTY_FILE_NAME = System.getProperty("configFile");

    private static ConfigProperties instance = null;
    private static Properties prop = null;

    private ConfigProperties(){
        InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_PROPERTY_FILE_NAME);
        prop = new Properties();

        try {

            if (input != null) {
                prop.load(input);
            } else {
                throw new FileNotFoundException(CONFIG_PROPERTY_FILE_NAME + " was not found");
            }

            input.close();

        } catch (IOException ex) {
            System.out.println("Exception: " + ex);
        }
    }

    public static ConfigProperties getInstance(){
        if(instance == null) instance = new ConfigProperties();
        return instance;
    }

    public static String getProperty(String key){
        if(prop == null) getInstance();
        return prop.getProperty(key);
    }

}
