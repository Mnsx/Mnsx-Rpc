package top.mnsx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@SpringBootApplication
@MapperScan("top.mnsx.mapper")
public class OrderApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(OrderApplication.class, args);
    }
}
