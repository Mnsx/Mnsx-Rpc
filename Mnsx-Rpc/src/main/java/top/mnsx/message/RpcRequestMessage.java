package top.mnsx.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;
import top.mnsx.config.ClassArrayDeserializer;
import top.mnsx.config.ClassDeserializer;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequestMessage extends Message {
    // 接口名称
    private String interfaceName;
    // 方法名
    private String methodName;
    // 返回类型
    @JSONField(deserializeUsing = ClassDeserializer.class)
    private Class<?> returnType;
    // 参数类型数组
    @JSONField(deserializeUsing = ClassArrayDeserializer.class)
    private Class<?>[] parameterTypes;
    // 方法参数数组
    private Object[] parameterValues;

    public RpcRequestMessage(int sequenceId, String interfaceName,
                             String methodName, Class<?> returnType,
                             Class<?>[] parameterTypes, Object[] parameterValues) {
        super.setSequenceId(sequenceId);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.parameterValues = parameterValues;
    }

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_REQUEST;
    }
}
