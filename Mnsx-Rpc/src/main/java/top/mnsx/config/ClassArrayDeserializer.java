package top.mnsx.config;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public class ClassArrayDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
        String content = "";
        try {
            content = defaultJSONParser.getLexer().getReader().readString();
            content = content.substring(1, content.length() - 1);
            String[] strings = content.split(",");
            Class<?>[] classes = new Class[strings.length];
            int index = 0;
            for (String string : strings) {
                string = string.substring(1, string.length() - 1);
                classes[index++] = Class.forName(string);
            }
            return (T) classes;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
