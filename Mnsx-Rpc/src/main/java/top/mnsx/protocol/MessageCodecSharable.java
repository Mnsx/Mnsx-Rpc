package top.mnsx.protocol;

import top.mnsx.config.Config;
import top.mnsx.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import top.mnsx.serializer.SerializerAlgorithm;

import java.util.List;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        // 魔数
        buf.writeBytes(new byte[]{'m', 'n', 's', 'x'});
        // 版本号
        buf.writeByte(1);
        // 序列化算法 —— json 0 jdk 1
        buf.writeByte(Config.getSerializerAlgorithm().ordinal());
        // 指令类型
        buf.writeByte(msg.getMessageType());
        // 请求序号
        buf.writeInt(msg.getSequenceId());
        // 填充
        buf.writeByte(0xff);

        byte[] content = Config.getSerializerAlgorithm().serialize(msg);

        // 长度
        buf.writeInt(content.length);
        // 内容
        buf.writeBytes(content);

        out.add(buf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int magicNum = msg.readInt();
        byte version = msg.readByte();
        byte serializerAlgorithm = msg.readByte();
        byte messageType = msg.readByte();
        int sequenceId = msg.readInt();
        msg.readByte();
        int length = msg.readInt();
        byte[] bytes = new byte[length];
        msg.readBytes(bytes, 0, length);

        SerializerAlgorithm algorithm = SerializerAlgorithm.getAlgorithm(serializerAlgorithm);
        Class<? extends Message> messageClass = Message.getMessageClass(messageType);
        Message message = algorithm.deserialize(messageClass, bytes);
        out.add(message);
    }
}
