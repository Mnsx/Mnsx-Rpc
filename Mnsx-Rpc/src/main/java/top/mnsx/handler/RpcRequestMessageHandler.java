package top.mnsx.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import top.mnsx.message.RpcRequestMessage;
import top.mnsx.message.RpcResponseMessage;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Component
@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> implements ApplicationContextAware {
    private ApplicationContext ac;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage msg) throws Exception {
        RpcResponseMessage response = new RpcResponseMessage();
        response.setSequenceId(msg.getSequenceId());
        try {
            // 获取真正的实现对象
            Iterator<?> iterator = ac.getBeansOfType(Class.forName(msg.getInterfaceName())).values().iterator();
            Object service = msg.getInterfaceName();
            if (iterator.hasNext()) {
                service = iterator.next();
            }
            // 获取要调用方法
            // TODO: 2022/12/15 空参没有办法解析 
            Method method = service.getClass().getMethod(msg.getMethodName(), msg.getParameterTypes());
            // 调用方法
            Object result = method.invoke(service, msg.getParameterValues());
            // 调用成功
            response.setReturnValue(result);
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getCause().getMessage();
            // 调用失败
            response.setExceptionValue(new Exception("远程调用出错: " + message));
        }
        ctx.writeAndFlush(response);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac = applicationContext;
    }
}
