package top.mnsx.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;
import top.mnsx.annotation.XClients;
import top.mnsx.properties.RpcProperties;
import top.mnsx.utils.ClassUtils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Component
public class RegistryBean implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // TODO: 2022/12/2  动态获取package
        List<Class<?>> classesByPackage = ClassUtils.getClassesByPackage("top.mnsx");
        if (classesByPackage != null) {
            classesByPackage = classesByPackage.stream()
                    .filter(c -> c.isAnnotationPresent(XClients.class))
                    .collect(Collectors.toList());
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            List<Class<?>> finalClassesByPackage = classesByPackage;
            // TODO: 2022/12/3 优化，浪费cpu 
            executor.scheduleWithFixedDelay(() -> {
                for (Class<?> cls : finalClassesByPackage) {
                    XClients annotation = cls.getAnnotation(XClients.class);
                    String value = annotation.value();
                    String[] url = value.split(":");
                    String host = url[0];
                    int port = Integer.parseInt(url[1]);
                    BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
                    GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
                    definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
                    definition.getPropertyValues().add("host", host);
                    definition.getPropertyValues().add("port", port);
                    definition.setBeanClass(ProxyFactory.class);
                    definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
                    StringBuilder sb = new StringBuilder();
                    String name = cls.getName();
                    int index = name.lastIndexOf(".");
                    sb.append(name.substring(index + 1, index + 2).toLowerCase()).append(name.substring(index + 2));
                    registry.registerBeanDefinition(sb.toString(), definition);
                }
            }, 0, 10, TimeUnit.SECONDS);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}
