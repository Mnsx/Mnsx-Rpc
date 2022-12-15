package top.mnsx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mnsx.service.PayService;
import top.mnsx.service.UserService;

import javax.annotation.Resource;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private PayService payService;

    @GetMapping("/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return userService.sayHello(name) + "\n" + payService.pay(name);
    }
}
