package top.mnsx.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;
import top.mnsx.generator.SequenceIdGenerator;
import top.mnsx.handler.RpcResponseMessageHandler;
import top.mnsx.message.PingMessage;
import top.mnsx.message.RpcRequestMessage;
import top.mnsx.protocol.MessageCodecSharable;
import top.mnsx.protocol.ProtocolFrameDecoder;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Slf4j
public class RpcClient {
//    private static volatile Channel channel;
    private static final Object LOCK = new Object();
    private static final Map<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

    public static <T> T getProxyService(Class<T> serviceClass, String host, int port) {
        ClassLoader loader = serviceClass.getClassLoader();
        Class<?>[] interfaces = new Class[]{serviceClass};

        Object o = Proxy.newProxyInstance(loader, interfaces, (proxy, method, args) -> {
            int sequenceId = SequenceIdGenerator.nextId();
            RpcRequestMessage msg = new RpcRequestMessage(
                    sequenceId,
                    serviceClass.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    args
            );
            Channel channel = getChannel(host, port);
            channel.writeAndFlush(msg);

            // 创建promise
            DefaultPromise<Object> promise = new DefaultPromise<>(getChannel(host, port).eventLoop());
            RpcResponseMessageHandler.PROMISE_MAP.put(sequenceId, promise);

            // 等待结果
            promise.await();
            if (promise.isSuccess()) {
                return promise.getNow();
            } else {
                throw new RuntimeException(promise.cause());
            }
        });
        return (T) o;
    }

    public static Channel getChannel(String host, int port) {
        String key = host + ":" + port;
        if (!CHANNEL_MAP.containsKey(key)) {
            synchronized (LOCK) {
                if (!CHANNEL_MAP.containsKey(key)) {
                    initChannel(host, port);
                }
            }
        }
        return CHANNEL_MAP.get(key);
    }

    // 初始化Channel方法
    private static void initChannel(String host, int port) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 日志处理器
        LoggingHandler LOGGING_HANDLER = new LoggingHandler();
        // 消息处理器
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        // RPC响应处理器
        RpcResponseMessageHandler RPC_RESPONSE_HANDLER = new RpcResponseMessageHandler();

        try {
            Channel channel = new Bootstrap()
                    .channel(NioSocketChannel.class)
                    .group(group)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtocolFrameDecoder());
                            ch.pipeline().addLast(LOGGING_HANDLER);
                            ch.pipeline().addLast(MESSAGE_CODEC);
                            ch.pipeline().addLast(RPC_RESPONSE_HANDLER);
                            ch.pipeline().addLast(new IdleStateHandler(0, 3, 0));
                            ch.pipeline().addLast(new ChannelDuplexHandler() {
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    IdleStateEvent event = (IdleStateEvent) evt;
                                    if (event.state() == IdleState.WRITER_IDLE) {
                                        ctx.channel().writeAndFlush(new PingMessage());
                                    }
                                }
                            });
                        }
                    })
                    .connect(host, port)
                    .sync()
                    .channel();

            channel.closeFuture()
                    .addListener(future -> {
                        group.shutdownGracefully();
                        String key = host + ":" + port;
                        CHANNEL_MAP.remove(key);
                    });

            String key = host + ":" + port;
            CHANNEL_MAP.putIfAbsent(key, channel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
