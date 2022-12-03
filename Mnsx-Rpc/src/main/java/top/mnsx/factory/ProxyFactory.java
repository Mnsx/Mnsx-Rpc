package top.mnsx.factory;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import top.mnsx.client.RpcClient;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Data
public class ProxyFactory<T> implements FactoryBean<T> {
    private Class<T> interfaceClass;
    private String host;
    private int port;

    @Override
    public T getObject() throws Exception {
        return RpcClient.getProxyService(interfaceClass, host, port);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        // 单例模式
        return true;
    }

}
