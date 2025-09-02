package com.ronan.common.array;

/**
 * @program: common
 * @description:
 * @author: L.J.Ran
 * @create: 2025/9/2
 */
public class ArrayUtil extends PrimitiveArrayUtil {
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }


    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

}
