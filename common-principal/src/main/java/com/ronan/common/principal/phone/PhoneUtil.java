package com.ronan.common.principal.phone;


import java.util.regex.Pattern;


/**
 * 电话号码工具类，包括：
 * <ul>
 *     <li>手机号码</li>
 *     <li>400、800号码</li>
 *     <li>座机号码</li>
 * </ul>
 * @author R.L.Ran
 */
public class PhoneUtil {

    private PhoneUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 验证是否为手机号码（中国大陆）
     *
     * @param value 值
     * @return 是否为手机号码（中国大陆）
     */
    public static boolean isMobile(CharSequence value) {
        return isMobile(value, MobileCountry.CHINA_MAINLAND);
    }

    /**
     * 验证是否为座机号码（中国大陆）
     *
     * @param value 值
     * @return 是否为座机号码（中国大陆）
     */
    public static boolean isTel(CharSequence value) {
        if (value == null || value.length() == 0) {
            // 提供null的字符串为不匹配
            return false;
        }
        return PhonePattern.TEL_PATTERN.matcher(value).matches();
    }

    /**
     * 验证是否为手机号码（中国大陆）
     *
     * @param value 值
     * @return 是否为手机号码（中国大陆）
     */
    public static boolean isMobile(CharSequence value, MobileCountry country) {
        if (value == null || value.length() == 0) {
            // 提供null的字符串为不匹配
            return false;
        }
        return Pattern.compile(country.getPattern()).matcher(value).matches();
    }


    /**
     * 验证是否为座机号码（中国大陆）+ 400 + 800
     *
     * @param value 值
     * @return 是否为座机号码（中国大陆）
     * @author dazer, ourslook
     */
    public static boolean isTel400800(CharSequence value) {
        return PhonePattern.TEL_400_800_PATTERN.matcher(value).matches();
    }

    /**
     * 验证是否为座机号码+手机号码（CharUtil中国）+ 400 + 800电话 + 手机号号码（中国香港）
     *
     * @param value 值
     * @return 是否为座机号码+手机号码（中国大陆）+手机号码（中国香港）+手机号码（中国台湾）+手机号码（中国澳门）
     */
    public static boolean isPhone(CharSequence value) {
        return isMobile(value) || isTel400800(value);
    }

}
