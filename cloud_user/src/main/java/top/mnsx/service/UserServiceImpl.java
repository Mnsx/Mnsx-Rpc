package top.mnsx.service;

import org.springframework.stereotype.Service;
import top.mnsx.service.UserService;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
