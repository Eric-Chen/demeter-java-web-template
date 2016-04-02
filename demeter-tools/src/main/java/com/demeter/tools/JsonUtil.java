package com.demeter.tools;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * Created by eric on 4/2/16.
 */
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonUtil() {
    }

    public static String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> T fromJson(String json, TypeReference type) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    static {
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(DeserializationConfig.Feature.AUTO_DETECT_FIELDS, true);
    }

}
