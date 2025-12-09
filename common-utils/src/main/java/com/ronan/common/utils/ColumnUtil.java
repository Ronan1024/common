package com.ronan.common.utils;



import com.ronan.common.function.SFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author L.J.Ran
 * @version 1.0
 */
public class ColumnUtil {
    private static final Map<Class<?>, SerializedLambda> CLASS_LAMBDA_CACHE = new ConcurrentHashMap<>();

    public static <T> String column(SFunction<T, ?> func) {
        SerializedLambda serialized = getSerialized(func);
        return methodToProperty(serialized);
    }

    public static <T> SerializedLambda getSerialized(SFunction<T, ?> func) {
        try {
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(func);
            if (CLASS_LAMBDA_CACHE.containsKey(func.getClass())) {
                return CLASS_LAMBDA_CACHE.get(func.getClass());
            }
            return serializedLambda;
        } catch (Exception var2) {
            throw new IllegalArgumentException("获取方法异常");
        }
    }

    public static String methodToProperty(SerializedLambda lambda) {
        String name = lambda.getImplMethodName();
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else {
            if (!name.startsWith("get") && !name.startsWith("set")) {
                throw new IllegalArgumentException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
            }
            name = name.substring(3);
        }

        if (name.length() == 1 || name.length() > 1 && !Character.isUpperCase(name.charAt(1))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    public static String convert(String column, String character) {
        Assert.notNull(column, "column must not be null");
        Assert.notNull(character, "character must not be null");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < column.length(); i++) {
            char c = column.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append(character);
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * 转小驼峰
     *
     * @param column column
     * @return string
     */
    public static String convert(String column) {
        //转小驼峰
        Assert.notNull(column, "column must not be null");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < column.length(); i++) {
            char c = column.charAt(i);
            if (i == 0) {
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
