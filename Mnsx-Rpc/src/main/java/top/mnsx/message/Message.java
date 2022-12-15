package top.mnsx.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Message implements Serializable {
    // 序列号
    private int sequenceId;
    // 消息类型
    private int messageType;

    // 消息集合
    private static final Map<Integer, Class<? extends Message>> ALL_MESSAGE = new HashMap<>();

    // 消息
    public static final int RPC_MESSAGE_REQUEST = 101;
    public static final int RPC_MESSAGE_RESPONSE = 102;
    // 心跳数据包
    public static final int PING_MESSAGE_REQUEST = 103;

    static {
        ALL_MESSAGE.put(RPC_MESSAGE_REQUEST, RpcRequestMessage.class);
        ALL_MESSAGE.put(RPC_MESSAGE_RESPONSE, RpcResponseMessage.class);
        ALL_MESSAGE.put(PING_MESSAGE_REQUEST, PingMessage.class);
    }

    public abstract int getMessageType();

    public static Class<? extends Message> getMessageClass(int messageType) {
        return ALL_MESSAGE.get(messageType);
    }
}
