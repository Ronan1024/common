package com.ronan.common.string.chars;


import com.ronan.common.array.ArrayUtil;

/**
 * {@link CharSequence} 相关工具类封装
 */
public class CharSequenceUtil {

    /**
     * 字符串常量：{@code "null"} <br>
     * 注意：{@code "null" != null}
     */
    public static final String NULL = "null";

    /**
     * 字符串常量：空字符串 {@code ""}
     */
    public static final String EMPTY = "";

    /**
     * <p>字符串是否为空白，空白的定义如下：</p>
     * <ol>
     *     <li>{@code null}</li>
     *     <li>空字符串：{@code ""}</li>
     *     <li>空格、全角空格、制表符、换行符，等不可见字符</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code CharSequenceUtil.isBlank(null)     // true}</li>
     *     <li>{@code CharSequenceUtil.isBlank("")       // true}</li>
     *     <li>{@code CharSequenceUtil.isBlank(" \t\n")  // true}</li>
     *     <li>{@code CharSequenceUtil.isBlank("abc")    // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isEmpty(CharSequence)} 的区别是：
     * 该方法会校验空白字符，且性能相对于 {@link #isEmpty(CharSequence)} 略慢。</p>
     * <br>
     *
     * <p>建议：</p>
     * <ul>
     *     <li>该方法建议仅对于客户端（或第三方接口）传入的参数使用该方法。</li>
     *     <li>需要同时校验多个字符串时，建议采用 {@link #hasBlank(CharSequence...)} 或 {@link #isAllBlank(CharSequence...)}</li>
     * </ul>
     *
     * @param str 被检测的字符串
     * @return 若为空白，则返回 true
     * @see #isEmpty(CharSequence)
     */
    public static boolean isBlank(CharSequence str) {
        final int length;
        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (!CharUtil.isBlankChar(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>字符串是否为非空白，非空白的定义如下： </p>
     * <ol>
     *     <li>不为 {@code null}</li>
     *     <li>不为空字符串：{@code ""}</li>
     *     <li>不为空格、全角空格、制表符、换行符，等不可见字符</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code CharSequenceUtil.isNotBlank(null)     // false}</li>
     *     <li>{@code CharSequenceUtil.isNotBlank("")       // false}</li>
     *     <li>{@code CharSequenceUtil.isNotBlank(" \t\n")  // false}</li>
     *     <li>{@code CharSequenceUtil.isNotBlank("abc")    // true}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #notEmpty(CharSequence)} (CharSequence)} 的区别是：
     * 该方法会校验空白字符，且性能相对于 {@link #notEmpty(CharSequence)} (CharSequence)} 略慢。</p>
     * <p>建议：仅对于客户端（或第三方接口）传入的参数使用该方法。</p>
     *
     * @param str 被检测的字符串
     * @return 是否为非空
     * @see #isBlank(CharSequence)
     */
    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    /**
     * <p>指定字符串数组中，是否包含空字符串。</p>
     * <p>如果指定的字符串数组的长度为 0，或者其中的任意一个元素是空字符串，则返回 true。</p>
     * <br>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code CharSequenceUtil.hasBlank()                  // true}</li>
     *     <li>{@code CharSequenceUtil.hasBlank("", null, " ")     // true}</li>
     *     <li>{@code CharSequenceUtil.hasBlank("123", " ")        // true}</li>
     *     <li>{@code CharSequenceUtil.hasBlank("123", "abc")      // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isAllBlank(CharSequence...)} 的区别在于：</p>
     * <ul>
     *     <li>hasBlank(CharSequence...)            等价于 {@code isBlank(...) || isBlank(...) || ...}</li>
     *     <li>{@link #isAllBlank(CharSequence...)} 等价于 {@code isBlank(...) && isBlank(...) && ...}</li>
     * </ul>
     *
     * @param strs 字符串列表
     * @return 是否包含空字符串
     */
    public static boolean hasBlank(CharSequence... strs) {
        if (ArrayUtil.isEmpty(strs)) {
            return true;
        }

        for (CharSequence str : strs) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>指定字符串数组中的元素，是否全部为空字符串。</p>
     * <p>如果指定的字符串数组的长度为 0，或者所有元素都是空字符串，则返回 true。</p>
     * <br>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code CharSequenceUtil.isAllBlank()                  // true}</li>
     *     <li>{@code CharSequenceUtil.isAllBlank("", null, " ")     // true}</li>
     *     <li>{@code CharSequenceUtil.isAllBlank("123", " ")        // false}</li>
     *     <li>{@code CharSequenceUtil.isAllBlank("123", "abc")      // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #hasBlank(CharSequence...)} 的区别在于：</p>
     * <ul>
     *     <li>{@link #hasBlank(CharSequence...)}   等价于 {@code isBlank(...) || isBlank(...) || ...}</li>
     *     <li>isAllBlank(CharSequence...)          等价于 {@code isBlank(...) && isBlank(...) && ...}</li>
     * </ul>
     *
     * @param strs 字符串列表
     * @return 所有字符串是否为空白
     */
    public static boolean isAllBlank(CharSequence... strs) {
        if (ArrayUtil.isEmpty(strs)) {
            return true;
        }

        for (CharSequence str : strs) {
            if (isNotBlank(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>字符串是否为空，空的定义如下：</p>
     * <ol>
     *     <li>{@code null}</li>
     *     <li>空字符串：{@code ""}</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code CharSequenceUtil.isEmpty(null)     // true}</li>
     *     <li>{@code CharSequenceUtil.isEmpty("")       // true}</li>
     *     <li>{@code CharSequenceUtil.isEmpty(" \t\n")  // false}</li>
     *     <li>{@code CharSequenceUtil.isEmpty("abc")    // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isBlank(CharSequence)} 的区别是：该方法不校验空白字符。</p>
     * <p>建议：</p>
     * <ul>
     *     <li>该方法建议用于工具类或任何可以预期的方法参数的校验中。</li>
     *     <li>需要同时校验多个字符串时，建议采用 {@link #hasEmpty(CharSequence...)} 或 {@link #isAllEmpty(CharSequence...)}</li>
     * </ul>
     *
     * @param str 被检测的字符串
     * @return 是否为空
     * @see #isBlank(CharSequence)
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>字符串是否为非空白，非空白的定义如下： </p>
     * <ol>
     *     <li>不为 {@code null}</li>
     *     <li>不为空字符串：{@code ""}</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code CharSequenceUtil.isNotEmpty(null)     // false}</li>
     *     <li>{@code CharSequenceUtil.isNotEmpty("")       // false}</li>
     *     <li>{@code CharSequenceUtil.isNotEmpty(" \t\n")  // true}</li>
     *     <li>{@code CharSequenceUtil.isNotEmpty("abc")    // true}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isNotBlank(CharSequence)} 的区别是：该方法不校验空白字符。</p>
     * <p>建议：该方法建议用于工具类或任何可以预期的方法参数的校验中。</p>
     *
     * @param str 被检测的字符串
     * @return 是否为非空
     * @see #isEmpty(CharSequence)
     */
    public static boolean notEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 当给定字符串为null时，转换为Empty
     *
     * @param str 被检查的字符串
     * @return 原字符串或者空串
     * @see #nullToEmpty(CharSequence)
     */
    public static String emptyIfNull(CharSequence str) {
        return nullToEmpty(str);
    }

    /**
     * 当给定字符串为null时，转换为Empty
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String nullToEmpty(CharSequence str) {
        return nullToDefault(str, EMPTY);
    }

    /**
     * 如果字符串是 {@code null}，则返回指定默认字符串，否则返回字符串本身。
     *
     * <pre>
     * nullToDefault(null, &quot;default&quot;)  = &quot;default&quot;
     * nullToDefault(&quot;&quot;, &quot;default&quot;)    = &quot;&quot;
     * nullToDefault(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot;
     * nullToDefault(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
     * </pre>
     *
     * @param str        要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String nullToDefault(CharSequence str, String defaultStr) {
        return (str == null) ? defaultStr : str.toString();
    }

    /**
     * 如果字符串是{@code null}或者&quot;&quot;，则返回指定默认字符串，否则返回字符串本身。
     *
     * <pre>
     * emptyToDefault(null, &quot;default&quot;)  = &quot;default&quot;
     * emptyToDefault(&quot;&quot;, &quot;default&quot;)    = &quot;default&quot;
     * emptyToDefault(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot;
     * emptyToDefault(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
     * </pre>
     *
     * @param str        要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String emptyToDefault(CharSequence str, String defaultStr) {
        return isEmpty(str) ? defaultStr : str.toString();
    }

    /**
     * 如果字符串是{@code null}或者&quot;&quot;或者空白，则返回指定默认字符串，否则返回字符串本身。
     *
     * <pre>
     * blankToDefault(null, &quot;default&quot;)  = &quot;default&quot;
     * blankToDefault(&quot;&quot;, &quot;default&quot;)    = &quot;default&quot;
     * blankToDefault(&quot;  &quot;, &quot;default&quot;)  = &quot;default&quot;
     * blankToDefault(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
     * </pre>
     *
     * @param str        要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     * 
     */
    public static String blankToDefault(CharSequence str, String defaultStr) {
        return isBlank(str) ? defaultStr : str.toString();
    }

    /**
     * 当给定字符串为空字符串时，转换为{@code null}
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String emptyToNull(CharSequence str) {
        return isEmpty(str) ? null : str.toString();
    }

    /**
     * <p>是否包含空字符串。</p>
     * <p>如果指定的字符串数组的长度为 0，或者其中的任意一个元素是空字符串，则返回 true。</p>
     * <br>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code CharSequenceUtil.hasEmpty()                  // true}</li>
     *     <li>{@code CharSequenceUtil.hasEmpty("", null)          // true}</li>
     *     <li>{@code CharSequenceUtil.hasEmpty("123", "")         // true}</li>
     *     <li>{@code CharSequenceUtil.hasEmpty("123", "abc")      // false}</li>
     *     <li>{@code CharSequenceUtil.hasEmpty(" ", "\t", "\n")   // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isAllEmpty(CharSequence...)} 的区别在于：</p>
     * <ul>
     *     <li>hasEmpty(CharSequence...)            等价于 {@code isEmpty(...) || isEmpty(...) || ...}</li>
     *     <li>{@link #isAllEmpty(CharSequence...)} 等价于 {@code isEmpty(...) && isEmpty(...) && ...}</li>
     * </ul>
     *
     * @param strs 字符串列表
     * @return 是否包含空字符串
     */
    public static boolean hasEmpty(CharSequence... strs) {
        if (ArrayUtil.isEmpty(strs)) {
            return true;
        }

        for (CharSequence str : strs) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>指定字符串数组中的元素，是否全部为空字符串。</p>
     * <p>如果指定的字符串数组的长度为 0，或者所有元素都是空字符串，则返回 true。</p>
     * <br>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code CharSequenceUtil.isAllEmpty()                  // true}</li>
     *     <li>{@code CharSequenceUtil.isAllEmpty("", null)          // true}</li>
     *     <li>{@code CharSequenceUtil.isAllEmpty("123", "")         // false}</li>
     *     <li>{@code CharSequenceUtil.isAllEmpty("123", "abc")      // false}</li>
     *     <li>{@code CharSequenceUtil.isAllEmpty(" ", "\t", "\n")   // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #hasEmpty(CharSequence...)} 的区别在于：</p>
     * <ul>
     *     <li>{@link #hasEmpty(CharSequence...)}   等价于 {@code isEmpty(...) || isEmpty(...) || ...}</li>
     *     <li>isAllEmpty(CharSequence...)          等价于 {@code isEmpty(...) && isEmpty(...) && ...}</li>
     * </ul>
     *
     * @param strs 字符串列表
     * @return 所有字符串是否为空白
     */
    public static boolean isAllEmpty(CharSequence... strs) {
        if (ArrayUtil.isEmpty(strs)) {
            return true;
        }

        for (CharSequence str : strs) {
            if (notEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>指定字符串数组中的元素，是否都不为空字符串。</p>
     * <p>如果指定的字符串数组的长度不为 0，或者所有元素都不是空字符串，则返回 true。</p>
     * <br>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code CharSequenceUtil.isAllNotEmpty()                  // false}</li>
     *     <li>{@code CharSequenceUtil.isAllNotEmpty("", null)          // false}</li>
     *     <li>{@code CharSequenceUtil.isAllNotEmpty("123", "")         // false}</li>
     *     <li>{@code CharSequenceUtil.isAllNotEmpty("123", "abc")      // true}</li>
     *     <li>{@code CharSequenceUtil.isAllNotEmpty(" ", "\t", "\n")   // true}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isAllEmpty(CharSequence...)} 的区别在于：</p>
     * <ul>
     *     <li>{@link #isAllEmpty(CharSequence...)}    等价于 {@code isEmpty(...) && isEmpty(...) && ...}</li>
     *     <li>isAllNotEmpty(CharSequence...)          等价于 {@code !isEmpty(...) && !isEmpty(...) && ...}</li>
     * </ul>
     *
     * @param args 字符串数组
     * @return 所有字符串是否都不为为空白
     * 
     */
    public static boolean isAllNotEmpty(CharSequence... args) {
        return false == hasEmpty(args);
    }

    /**
     * 是否存都不为{@code null}或空对象或空白符的对象，通过{@link #hasBlank(CharSequence...)} 判断元素
     *
     * @param args 被检查的对象,一个或者多个
     * @return 是否都不为空
     * 
     */
    public static boolean isAllNotBlank(CharSequence... args) {
        return !hasBlank(args);
    }

    /**
     * 检查字符串是否为null、“null”、“undefined”
     *
     * @param str 被检查的字符串
     * @return 是否为null、“null”、“undefined”
     * 
     */
    public static boolean isNullOrUndefined(CharSequence str) {
        if (null == str) {
            return true;
        }
        return isNullOrUndefinedStr(str);
    }

    /**
     * 检查字符串是否为null、“”、“null”、“undefined”
     *
     * @param str 被检查的字符串
     * @return 是否为null、“”、“null”、“undefined”
     * 
     */
    public static boolean isEmptyOrUndefined(CharSequence str) {
        if (isEmpty(str)) {
            return true;
        }
        return isNullOrUndefinedStr(str);
    }

    /**
     * 检查字符串是否为null、空白串、“null”、“undefined”
     *
     * @param str 被检查的字符串
     * @return 是否为null、空白串、“null”、“undefined”
     * 
     */
    public static boolean isBlankOrUndefined(CharSequence str) {
        if (isBlank(str)) {
            return true;
        }
        return isNullOrUndefinedStr(str);
    }

    /**
     * 是否为“null”、“undefined”，不做空指针检查
     *
     * @param str 字符串
     * @return 是否为“null”、“undefined”
     */
    private static boolean isNullOrUndefinedStr(CharSequence str) {
        String strString = str.toString().trim();
        return NULL.equals(strString) || "undefined".equals(strString);
    }


}
