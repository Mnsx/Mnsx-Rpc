package top.mnsx.serializer;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public interface Serializer {
    /**
     * 反序列化算法
     * @param clazz 反序列化目标类
     * @param bytes 反序列化的字节数组
     * @param <T> 反序列化类型泛型参数
     * @return 返回对应对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**
     * 序列化算法
     * @param object 需要序列化的对象
     * @param <T> 目标对象的泛型参数
     * @return 返回序列化侯的字节数组
     */
    <T> byte[] serialize(T object);
}
