package com.ronan.common.string;

import org.junit.Test;

/**
 * @program: common
 * @description:
 * @author: L.J.Ran
 * @create: 2025/8/5
 */

public class StringFormatterTest {

    @Test
    public void stringFormatter() {
        String str = "msg: {}";
        String format = StringFormatter.format(str, "=======");
        System.out.println(format);
        String str1 = "msg: {{}}";
        String format1 = StringFormatter.format(str1, "=======");
        System.out.println("format1 -> " + format1);
        String advancedFormatStr = "userName: {1}   age: {2}";
        String advancedFormat = StringFormatter.advancedFormat(advancedFormatStr, "", "sixUncle", "18");
        System.out.println(advancedFormat);

    }
}

