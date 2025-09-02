package com.ronan.common.string;

/**
 * @program: common
 * @description:
 * @author: L.J.Ran
 * @create: 2025/8/5
 */
public class StringUtil {

    private StringUtil() {
        throw new IllegalArgumentException("This is util class.");
    }

    public static boolean isEmpty(String str) {
        return (str != null && !str.isEmpty());
    }

    public static boolean isEmpty(CharSequence str) {
        int strLen = str.length();
        if (strLen == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }


    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean notEmpty(CharSequence str) {
        return !isEmpty(str);
    }
}
