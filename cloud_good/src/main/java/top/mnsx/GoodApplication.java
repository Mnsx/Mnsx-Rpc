package top.mnsx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@SpringBootApplication
@MapperScan("top.mnsx.mapper")
public class GoodApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodApplication.class, args);
    }
}
