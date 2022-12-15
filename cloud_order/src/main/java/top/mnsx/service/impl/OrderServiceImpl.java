package top.mnsx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mnsx.domain.entity.Good;
import top.mnsx.domain.entity.User;
import top.mnsx.service.GoodService;
import top.mnsx.service.OrderService;
import top.mnsx.service.UserService;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserService userService;
    @Autowired
    private GoodService goodService;

    @Override
    public String submit(Long userId, Long goodId) {
        User user = userService.getInfoById(userId);
        Good good = goodService.getInfoById(goodId);
        return "客户-" + user.getUsername() + "购买了商品" + good.getName() + "花费了"
                + good.getPrice() + "需要配送到" + user.getAddress();
    }
}
