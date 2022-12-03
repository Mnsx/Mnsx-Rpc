package top.mnsx.message;

import lombok.*;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponseMessage extends Message {
    // 返回值
    private Object returnValue;
    // 异常返回
    private Exception exceptionValue;

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_RESPONSE;
    }
}
