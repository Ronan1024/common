package com.ronan.common.string;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串格式化工具类
 *
 * @program: common
 * @author: L.J.Ran
 */
public class StringFormatter {

    private StringFormatter() {
        throw new IllegalArgumentException("Util Class..");
    }


    /**
     * 使用 {} 作为占位符格式化字符串
     *
     * @param template 包含 {} 占位符的模板字符串
     * @param args     替换占位符的参数
     * @return 格式化后的字符串
     */
    public static String format(String template, Object... args) {
        if (template == null) {
            return null;
        }

        // 处理转义的大括号
        template = template.replace("{{", "\uE000").replace("}}", "\uE001");

        List<String> placeholders = findPlaceholders(template);
        StringBuilder result = new StringBuilder();
        int lastIndex = 0;
        int argIndex = 0;

        for (String placeholder : placeholders) {
            // 添加占位符前的文本
            int pos = template.indexOf(placeholder, lastIndex);
            result.append(template, lastIndex, pos);

            // 处理占位符
            if (argIndex < args.length) {
                result.append(args[argIndex++]);
            } else {
                // 参数不足时保留占位符
                result.append(placeholder);
            }

            lastIndex = pos + placeholder.length();
        }

        // 添加剩余文本
        result.append(template.substring(lastIndex));

        // 恢复转义的大括号
        String formatted = result.toString();
        return formatted.replace("\uE000", "{").replace("\uE001", "}");
    }

    /**
     * 查找模板中的所有占位符
     */
    private static List<String> findPlaceholders(String template) {
        List<String> placeholders = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{\\s*}");
        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            placeholders.add(matcher.group());
        }

        return placeholders;
    }

//    /**
//     * 使用命名参数格式化字符串
//     *
//     * @param template 包含 {key} 占位符的模板
//     * @param params   命名参数键值对
//     * @return 格式化后的字符串
//     */
//    public static String namedFormat(String template, Object... params) {
//        if (template == null) {
//            return null;
//        }
//
//        if (params.length % 2 != 0) {
//            throw new IllegalArgumentException("参数必须是键值对形式");
//        }
//
//        // 处理转义的大括号
//        template = template.replace("{{", "\uE000").replace("}}", "\uE001");
//
//        // 创建参数映射
//        Map<String, Object> paramMap = new HashMap<>();
//        for (int i = 0; i < params.length; i += 2) {
//            paramMap.put(params[i].toString(), params[i + 1]);
//        }
//
//        Pattern pattern = Pattern.compile("\\{([^{}]+)}");
//        Matcher matcher = pattern.matcher(template);
//
//        StringBuffer result = new StringBuffer();
//
//        while (matcher.find()) {
//            String key = matcher.group(1).trim();
//            Object value = paramMap.get(key);
//
//            if (value != null) {
//                matcher.appendReplacement(result, Matcher.quoteReplacement(value.toString()));
//            } else {
//                // 未找到参数时保留占位符
//                matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group()));
//            }
//        }
//
//        matcher.appendTail(result);
//
//        // 恢复转义的大括号
//        String formatted = result.toString();
//        return formatted.replace("\uE000", "{").replace("\uE001", "}");
//    }


    /**
     * 使用命名参数格式化字符串
     *
     * @param template 格式化模板
     * @param params   命名参数键值对
     * @return 格式化后的字符串
     */
    public static String namedFormat(String template, Map<?, ?> params) {
        if (template == null) {
            return null;
        }

        // 处理转义的大括号
        template = escapeBraces(template);

        Pattern pattern = Pattern.compile("\\{([^{}]+)}");
        Matcher matcher = pattern.matcher(template);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1).trim();
            Object value = params.get(key);
            String replacement;
            if (value != null) {
                replacement = value.toString();
            } else {
                replacement = matcher.group();
            }

            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(result);
        return restoreBraces(result.toString());
    }


    /**
     * 索引格式化：支持索引、命名参数和默认值
     *
     * @param template 格式化模板
     * @param args     参数列表
     * @return 格式化后的字符串
     */
    public static String advancedFormat(String template, Object... args) {
        if (template == null) {
            return null;
        }

        // 处理转义的大括号
        template = escapeBraces(template);

        // 将参数数组转换为索引映射
        Map<String, Object> paramMap = new LinkedHashMap<>();
        for (int i = 0; i < args.length; i++) {
            paramMap.put(String.valueOf(i), args[i]);
        }

        Pattern pattern = Pattern.compile("\\{(\\w+)(?::([^{}]+))?}");
        Matcher matcher = pattern.matcher(template);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1);
            String defaultValue = matcher.group(2);
            String replacement = "";
            // 尝试作为索引处理
            try {
                int idx = Integer.parseInt(key);
                if (idx >= 0 && idx < args.length) {
                    replacement = args[idx].toString();
                } else if (defaultValue != null) {
                    replacement = defaultValue;
                } else {
                    replacement = matcher.group();
                }
            } catch (NumberFormatException e) {
                if (key.isEmpty() && defaultValue != null) {
                    // 处理空键情况
                    replacement = defaultValue;
                } else {
                    // 在参数数组中查找命名参数
                    boolean found = false;
                    for (int i = 0; i < args.length - 1; i += 2) {
                        if (args[i].toString().equals(key)) {
                            replacement = args[i + 1].toString();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        if (defaultValue != null) {
                            replacement = defaultValue;
                        } else {
                            replacement = matcher.group();
                        }
                    }
                }
            }

            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(result);
        return restoreBraces(result.toString());
    }

    /**
     * 处理转移的大括号: 将{{ x }} 转为临时标记
     */
    private static String escapeBraces(String template) {
        return template.replace("{{", "\uE000").replace("}}", "\uE001");
    }

    /**
     * 恢复转义的大括号：将临时标记恢复为 { x }
     */
    private static String restoreBraces(String formatted) {
        return formatted.replace("\uE000", "{").replace("\uE001", "}");
    }


}
