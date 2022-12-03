package top.mnsx.annotation;

import io.netty.channel.ChannelHandler;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface XClients {
    String host();
    int port();
}
