package com.ronan.common.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author L.J.Ran
 */
@SuppressWarnings("all")
public class JsonUtil {


    public static void init(ObjectMapper objectMapper) {
        JacksonHolder.INSTANCE = objectMapper;
    }


    private JsonUtil() {
    }

    @SneakyThrows
    public static String toJson(Object value) {
        return getInstance().writeValueAsString(value);
    }


    @SneakyThrows
    public static byte[] toJsonAsBytes(Object object) {
        return getInstance().writeValueAsBytes(object);
    }

    @SneakyThrows
    public static String toJsonPretty(Object value) {
        return getInstance().writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }


    public static <T> T parse(String content, Class<T> valueType) {
        if (content.isEmpty()) {
            return null;
        }
        try {
            return getInstance().readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parse(String content, TypeReference<T> typeReference) {
        if (content.isEmpty()) {
            return null;
        }
        try {
            return getInstance().readValue(content, typeReference);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T parse(String content, JavaType valueType) {
        if (content.isEmpty()) {
            return null;
        }
        try {
            return getInstance().readValue(content, valueType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T parse(byte[] bytes, Class<T> valueType) {
        try {
            return getInstance().readValue(bytes, valueType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T parse(byte[] bytes, TypeReference<T> typeReference) {
        try {
            return getInstance().readValue(bytes, typeReference);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T parse(InputStream in, Class<T> valueType) {
        try {
            return getInstance().readValue(in, valueType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T parse(InputStream in, TypeReference<T> typeReference) {
        try {
            return getInstance().readValue(in, typeReference);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 将json字符串转换为List
     *
     * @param content      json字符串
     * @param valueTypeRef 类型引用
     * @param <T>          泛型类型
     */
    public static <T> List<T> parseList(String content, Class<T> valueTypeRef) {
        if (content.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return getInstance().readValue(content, getInstance().getTypeFactory().constructCollectionType(List.class, valueTypeRef));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Deprecated
    public static <T> List<T> parseArray(String content, Class<T> valueTypeRef) {
        if (content.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            if (!content.startsWith("[")) {
                content = "[" + content;
            }
            if (!content.endsWith("]")) {
                content += "]";
            }
            List<Map<String, Object>> list = getInstance().readValue(content, new TypeReference<List<Map<String, Object>>>() {
            });

            return list.stream().map(e -> toPojo(e, valueTypeRef)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static Map<String, Object> toMap(String content) {
        try {
            return getInstance().readValue(content, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    public static <T> Map<String, T> toMap(String content, Class<T> valueTypeRef) {
        try {
            Map<String, Map<String, Object>> map = getInstance().readValue(content, new TypeReference<Map<String, Map<String, Object>>>() {
            });
            Map<String, T> result = new HashMap<>((int) (map.size() / 0.75F + 1));
            map.forEach((key, value) -> result.put(key, toPojo(value, valueTypeRef)));

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    public static <T> T toPojo(Map fromValue, Class<T> toValueType) {
        return getInstance().convertValue(fromValue, toValueType);
    }

    public static <T> T toPojo(JsonNode resultNode, Class<T> toValueType) {
        return getInstance().convertValue(resultNode, toValueType);
    }

    public static JsonNode readTree(String jsonString) {
        try {
            return getInstance().readTree(jsonString);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static JsonNode readTree(InputStream in) {
        try {
            return getInstance().readTree(in);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static JsonNode readTree(byte[] content) {
        try {
            return getInstance().readTree(content);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static JsonNode readTree(JsonParser jsonParser) {
        try {
            return getInstance().readTree(jsonParser);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static ObjectMapper getInstance() {
        return JacksonHolder.INSTANCE;
    }


    private static class JacksonHolder {
        private static ObjectMapper INSTANCE = new JacksonObjectMapper();
    }
}
