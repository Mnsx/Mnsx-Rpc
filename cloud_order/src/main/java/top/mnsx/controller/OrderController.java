package top.mnsx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mnsx.service.OrderService;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/submit/{userId}/{goodId}")
    public String submit(@PathVariable("userId") Long userId, @PathVariable("goodId") Long goodId) {
        return orderService.submit(userId, goodId);
    }
}
