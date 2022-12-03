package top.mnsx.config;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public class ClassDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
        String content = null;
        try {
            if (type.getTypeName().contains("Class")) {
                content = defaultJSONParser.getLexer().getReader().readString();
            }
            return (T) Class.forName(content);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
