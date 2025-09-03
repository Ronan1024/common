package com.ronan.common.principal.phone;


/**
 * 电话号码国别
 *
 * @author R.L.Ran
 */
public enum MobileCountry {
    /**
     * zh-CN:中国大陆
     */
    CHINA_MAINLAND("zh-CN", PhonePattern.MOBILE),
    /**
     * zh-HK:中国香港
     */
    CHINA_HK("zh-HK", PhonePattern.MOBILE_HK),
    /**
     * zh-TW:中国台湾
     */
    CHINA_TW("zh-TW", PhonePattern.MOBILE_TW),
    /**
     * zh-MO:中国澳门
     */
    CHINA_MO("zh-MO", PhonePattern.MOBILE_MO);;

    private final String country;
    private final String pattern;

    MobileCountry(String country, String pattern) {
        this.country = country;
        this.pattern = pattern;
    }

    public String getCountry() {
        return country;
    }

    public String getPattern() {
        return pattern;
    }
}
