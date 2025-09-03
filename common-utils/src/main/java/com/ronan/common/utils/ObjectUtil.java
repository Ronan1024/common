package com.ronan.common.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author L.J.Ran
 */
public class ObjectUtil {

    private ObjectUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Determine whether the given object is empty.
     *
     * @param obj the object to
     * @return true if the object is empty
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof CharSequence) {
            CharSequence charSequence = (CharSequence) obj;
            return charSequence.length() == 0;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>) obj;
            return collection.isEmpty();
        }
        if (obj instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) obj;
            return map.isEmpty();
        }

        // else
        return false;
    }

    public static boolean notEmpty(Object obj) {
        return !isEmpty(obj);
    }
}
