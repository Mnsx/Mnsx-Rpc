package top.mnsx.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {
    // 协议最大字节数
    private static final int MAX_FRAME_LENGTH = 10240;
    // 长度字段偏移量
    private static final int LENGTH_FIELD_OFFSET = 12;
    // 长度字段长度
    private static final int LENGTH_FIELD_LENGTH = 4;
    // 长度后间隔多少字节为正文
    private static final int LENGTH_ADJUSTMENT = 0;
    // 截取多少字节，作为正文
    private static final int INITIAL_BYTES_TO_STRIP = 0;

    public ProtocolFrameDecoder() {
        this(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
                LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP);
    }

    public ProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
                LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP);
    }
}
