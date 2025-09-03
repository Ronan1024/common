package com.ronan.common.principal.phone;


import java.util.regex.Pattern;

/**
 * 手机号码正则表达式集合
 * 常用正则表达式集合，更多正则见:<br>
 * <a href="https://any86.github.io/any-rule/">https://any86.github.io/any-rule/</a>
 * @author L.J.Ran
 */
public class PhonePattern {
    private PhonePattern(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * 移动电话
     * eg: 中国大陆： +86  180 4953 1399，2位区域码标示+11位数字
     * 中国大陆 +86 Mainland China
     */
    public static final String MOBILE = "(?:0|86|\\+86)?1[3-9]\\d{9}";

    /**
     * 中国香港移动电话
     * eg: 中国香港： +852 5100 4810， 三位区域码+10位数字, 中国香港手机号码8位数
     */
    public static final String MOBILE_HK = "(?:0|852|\\+852)?\\d{8}";
    /**
     * 中国台湾移动电话
     * eg: 中国台湾： +886 09 60 000000， 三位区域码+号码以数字09开头 + 8位数字, 中国台湾手机号码10位数
     * 中国台湾 +886 Taiwan 国际域名缩写：TW
     */
    public static final String MOBILE_TW = "(?:0|886|\\+886)?(?:|-)09\\d{8}";
    /**
     * 中国澳门移动电话
     * eg: 中国澳门： +853 68 00000， 三位区域码 +号码以数字6开头 + 7位数字, 中国澳门手机号码8位数
     * 中国澳门 +853 Macao 国际域名缩写：MO
     */
    public static final String MOBILE_MO = "(?:0|853|\\+853)?(?:|-)6\\d{7}";
    /**
     * 座机号码<br>
     * pr#387@Gitee
     */
    public static final String TEL = "(010|02\\d|0[3-9]\\d{2})-?(\\d{6,8})";
    /**
     * 座机号码+400+800电话
     *
     * @see <a href="https://baike.baidu.com/item/800">800</a>
     */
    public static final String TEL_400_800 = "0\\d{2,3}[\\- ]?[1-9]\\d{6,7}|[48]00[\\- ]?[1-9]\\d{2}[\\- ]?\\d{4}";


    /**
     * 移动电话
     */
    public static final Pattern MOBILE_PATTERN = Pattern.compile(MOBILE);
    /**
     * 中国香港移动电话
     * eg: 中国香港： +852 5100 4810， 三位区域码+10位数字, 中国香港手机号码8位数
     * eg: 中国大陆： +86  180 4953 1399，2位区域码标示+13位数字
     * 中国大陆 +86 Mainland China
     * 中国香港 +852 Hong Kong
     * 中国澳门 +853 Macao
     * 中国台湾 +886 Taiwan
     */
    public static final Pattern MOBILE_HK_PATTERN = Pattern.compile(MOBILE_HK);
    /**
     * 中国台湾移动电话
     * eg: 中国台湾： +886 09 60 000000， 三位区域码+号码以数字09开头 + 8位数字, 中国台湾手机号码10位数
     * 中国台湾 +886 Taiwan 国际域名缩写：TW
     */
    public static final Pattern MOBILE_TW_PATTERN = Pattern.compile(MOBILE_TW);
    /**
     * 中国澳门移动电话
     * eg: 中国台湾： +853 68 00000， 三位区域码 +号码以数字6开头 + 7位数字, 中国台湾手机号码8位数
     * 中国澳门 +853 Macao 国际域名缩写：MO
     */
    public static final Pattern MOBILE_MO_PATTERN = Pattern.compile(MOBILE_MO);
    /**
     * 座机号码
     */
    public static final Pattern TEL_PATTERN = Pattern.compile(TEL);
    /**
     * 座机号码+400+800电话
     */
    public static final Pattern TEL_400_800_PATTERN = Pattern.compile(TEL_400_800);


}
