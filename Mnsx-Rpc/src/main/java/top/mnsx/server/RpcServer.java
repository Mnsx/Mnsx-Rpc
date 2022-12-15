package top.mnsx.server;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import top.mnsx.handler.RpcRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import top.mnsx.properties.RpcProperties;
import top.mnsx.protocol.MessageCodecSharable;
import top.mnsx.protocol.ProtocolFrameDecoder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Slf4j
@Component
// 实现CommandLineRunner，再容器启动时启动Netty的Server
public class RpcServer implements CommandLineRunner {

    @Autowired
    private RpcProperties properties;
    @Autowired
    // RPC请求处理器
    RpcRequestMessageHandler RPC_REQUEST_HANDLER;

    /**
     * Netty Server启动类
     */
    public void bootstrap() {
        // EventLoop组
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        // 日志处理器
        LoggingHandler LOGGING_HANDLER = new LoggingHandler();
        // 消息处理器
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();

        try {
            log.info("Starting service [Netty]");
            Channel channel = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            // 添加协议解析处理器
                            ch.pipeline().addLast(new ProtocolFrameDecoder());
                            // 添加日志处理器
                            ch.pipeline().addLast(LOGGING_HANDLER);
                            // 添加消息编解码处理器
                            ch.pipeline().addLast(MESSAGE_CODEC);
                            // 添加RPC请求处理器
                            ch.pipeline().addLast(RPC_REQUEST_HANDLER);
                            // 添加心跳功能，五秒没有收到客户端的消息，将会关闭该通道
                            ch.pipeline().addLast(new IdleStateHandler(5, 0, 0));
                            ch.pipeline().addLast(new ChannelDuplexHandler() {
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    IdleStateEvent event = (IdleStateEvent) evt;
                                    if (event.state() == IdleState.READER_IDLE) {
                                        ctx.channel().close();
                                    }
                                }
                            });
                        }
                    })
                    .bind(properties.getPort())
                    .sync()
                    .channel();
            log.info("Netty started on port(s): {}", properties.getPort());
            channel.closeFuture()
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        bootstrap();
    }
}
