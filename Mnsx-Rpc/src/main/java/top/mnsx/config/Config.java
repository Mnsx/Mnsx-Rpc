package top.mnsx.config;

import top.mnsx.serializer.SerializerAlgorithm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public class Config {
    static Properties properties;

    static {
        try (
            InputStream in = Config.class.getResourceAsStream("/application.properties")
        ) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SerializerAlgorithm getSerializerAlgorithm() {
        String value = properties.getProperty("serializer.algorithm");
        if (value == null) {
            return SerializerAlgorithm.Jdk;
        } else {
            return SerializerAlgorithm.valueOf(value);
        }
    }
}
