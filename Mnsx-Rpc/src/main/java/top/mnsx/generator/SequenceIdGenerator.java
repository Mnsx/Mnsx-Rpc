package top.mnsx.generator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public class SequenceIdGenerator {
    private static final AtomicInteger CUR_ID = new AtomicInteger();

    public static int nextId() {
        return CUR_ID.getAndIncrement();
    }
}
