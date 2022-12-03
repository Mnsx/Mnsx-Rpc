package top.mnsx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Component
@Data
@ConfigurationProperties(prefix = "mnsx.rpc")
public class RpcProperties {
    private int port;
}
