package top.mnsx.service;

import org.springframework.stereotype.Component;
import top.mnsx.annotation.XClients;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Component
@XClients("127.0.0.1:6681")
public interface PayService {
    String pay(String name);
}
