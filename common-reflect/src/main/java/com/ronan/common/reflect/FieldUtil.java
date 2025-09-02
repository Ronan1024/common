package com.ronan.common.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: common
 * @description:
 * @author: L.J.Ran
 * @create: 2025/8/29
 */
public class FieldUtil {
    private static final ConcurrentHashMap<Class<?>, Field[]> FIELDS_CACHE = new ConcurrentHashMap<>();


    public static Field getField(Class<?> clazz, String name) {

        return null;
    }


    public static Field[] getFields(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Class cannot be null");


        return FIELDS_CACHE.computeIfAbsent(clazz, e -> getFieldsDirectly(e, true));
    }


    /**
     * 获取当前class 中所有的字段列表
     *
     * @param clazz         要获取字段的类
     * @param withInherited 是否包含父类的字段
     */
    public static Field[] getFieldsDirectly(Class<?> clazz, boolean withInherited) {
        Objects.requireNonNull(clazz, "Class cannot be null");

        Class<?> searchType = clazz;
        List<Field> fieldList = new ArrayList<>();
        while (searchType != null) {
            Field[] declaredFields = searchType.getDeclaredFields();
            fieldList.addAll(Arrays.asList(declaredFields));
            searchType = withInherited ? searchType.getSuperclass() : null;
        }

        return fieldList.toArray(new Field[0]);
    }
}
