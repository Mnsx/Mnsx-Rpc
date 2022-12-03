package top.mnsx.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import top.mnsx.generator.SequenceIdGenerator;
import top.mnsx.handler.RpcResponseMessageHandler;
import top.mnsx.message.RpcRequestMessage;
import top.mnsx.protocol.MessageCodecSharable;
import top.mnsx.protocol.ProtocolFrameDecoder;

import java.lang.reflect.Proxy;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
// TODO: 2022/12/3 添加心跳，心跳不调动后，删除channel 
public class RpcClient {
    // TODO: 2022/12/2 支持多个Channel
    private static volatile Channel channel;
    private static final Object LOCK = new Object();

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
            getChannel(host, port).writeAndFlush(msg);

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
        if (channel == null) {
            synchronized (LOCK) {
                if (channel == null) {
                    initChannel(host, port);
                }
            }
        }
        return channel;
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
            channel = new Bootstrap()
                    .channel(NioSocketChannel.class)
                    .group(group)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtocolFrameDecoder());
                            ch.pipeline().addLast(LOGGING_HANDLER);
                            ch.pipeline().addLast(MESSAGE_CODEC);
                            ch.pipeline().addLast(RPC_RESPONSE_HANDLER);
                        }
                    })
                    .connect(host, port)
                    .sync()
                    .channel();

            channel.closeFuture()
                    .addListener(future -> group.shutdownGracefully());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
