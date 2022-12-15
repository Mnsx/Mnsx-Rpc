package top.mnsx.message;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public class PingMessage extends Message {
    @Override
    public int getMessageType() {
        return PING_MESSAGE_REQUEST;
    }
}
