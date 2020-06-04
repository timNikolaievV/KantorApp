package pl.edu.pwr.KantorApp.utils;

import java.io.*;
import java.util.Properties;

//https://4programmers.net/Java/Properties_-_pliki_tekstowe

public class ConfigurationLoader {
    private Properties properties = new Properties();
    private String configuration = "src\\main\\resources\\Configuration.properties";

    public void loadProperties() throws FileNotFoundException {
        InputStream configurationStream = new FileInputStream(configuration);

        try {
            properties.load(configurationStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.getProperties().putAll(properties);
    }
}