package top.mnsx.service;

import org.springframework.stereotype.Component;
import top.mnsx.annotation.XClients;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@XClients(host = "127.0.0.1", port = 6679)
@Component
public interface UserService {
    String sayHello(String name);
}
