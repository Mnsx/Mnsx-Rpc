package top.mnsx.service;

import org.springframework.stereotype.Service;
import top.mnsx.service.PayService;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Service
public class PayServiceImpl implements PayService {
    @Override
    public String pay(String name) {
        return name + "已经支付了该订单";
    }
}
