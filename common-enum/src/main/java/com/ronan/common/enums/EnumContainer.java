package com.ronan.common.enums;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @program: common
 * @description:
 * @author: L.J.Ran
 * @create: 2025/12/9
 */
public class EnumContainer {

    private EnumContainer() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Enum 常量 -> Bean(code,desc)
     */
    private static final Map<IBaseEnum<?>, EnumBean<?>> ENUM_BEAN_MAP = new ConcurrentHashMap<>();


    public static <T> void put(IBaseEnum<T> baseEnum, T code, String desc) {
        ENUM_BEAN_MAP.put(baseEnum, new EnumBean<>(code, desc));
    }


    @SuppressWarnings("unchecked")
    static <K extends IBaseEnum<T>, T> EnumBean<T> getEnum(K key) {
        return (EnumBean<T>) ENUM_BEAN_MAP.get(key);
    }


    /**
     * 获取 code
     */
    public static <T> T code(IBaseEnum<T> e) {
        return getEnum(e).code();
    }

    /**
     * 获取 msg
     */
    public static String desc(IBaseEnum<?> e) {
        return getEnum(e).desc();
    }

    /**
     * 枚举根据 code 反查枚举
     */
    public static <C, R extends IBaseEnum<C>> R getByCode(Class<? extends IBaseEnum<C>> clazz, C code) {
        return Stream.of(clazz.getEnumConstants())
                .filter(e -> Objects.equals(code(e), code))
                .map(v -> (R) v)
                .findFirst()
                .orElse(null);
    }

    /**
     * 枚举转 List<Map>（常用于前端字典）
     */
    public static <E extends Enum<E> & IBaseEnum<C>, C> List<Map<String, Object>> toList(Class<E> clazz) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (E e : clazz.getEnumConstants()) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("code", code(e));
            m.put("desc", desc(e));
            list.add(m);
        }
        return list;
    }

    /**
     * 枚举转 map(code -> msg)
     */
    public static <E extends Enum<E> & IBaseEnum<C>, C> Map<C, String> toMap(Class<E> clazz) {
        Map<C, String> map = new LinkedHashMap<>();
        for (E e : clazz.getEnumConstants()) {
            map.put(code(e), desc(e));
        }
        return map;
    }

    static class EnumBean<T> implements Serializable {
        private final T code;
        private final String desc;

        EnumBean(T code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public T code() {
            return code;
        }

        public String desc() {
            return desc;
        }
    }
}
