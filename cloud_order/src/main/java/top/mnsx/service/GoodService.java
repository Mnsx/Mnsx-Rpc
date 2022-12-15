package top.mnsx.service;

import org.springframework.stereotype.Component;
import top.mnsx.annotation.XClients;
import top.mnsx.domain.entity.Good;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Component
@XClients("127.0.0.1:6681")
public interface GoodService {
    Good getInfoById(Long goodId);
}
