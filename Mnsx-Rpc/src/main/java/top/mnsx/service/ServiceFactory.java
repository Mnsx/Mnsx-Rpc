package top.mnsx.service;

import top.mnsx.config.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public class ServiceFactory {
    static Properties properties;
    // 保存Service
    static Map<Class<?>, Object> serviceMap = new ConcurrentHashMap<>();

    static {
        try (
            InputStream in = Config.class.getResourceAsStream("/application.properties")
        ) {
            properties = new Properties();
            properties.load(in);
            Set<String> names = properties.stringPropertyNames();
            for (String name : names) {
                if (name.endsWith("Service")) {
                    Class<?> interfaceClass = Class.forName(name);
                    Class<?> instanceClass = Class.forName(properties.getProperty(name));
                    serviceMap.put(interfaceClass, instanceClass.newInstance());
                }
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("资源加载错误");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> interfaceClass) {
        return (T) serviceMap.get(interfaceClass);
    }
}
