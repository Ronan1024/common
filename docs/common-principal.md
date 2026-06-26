# common-principal

`common-principal` 提供手机号、座机、400/800 电话和邮箱校验。

## PhoneUtil

包名：`com.ronan.common.principal.phone.PhoneUtil`

```java
PhoneUtil.isMobile("13800138000");                         // 中国大陆手机号
PhoneUtil.isMobile("51004810", MobileCountry.CHINA_HK);    // 中国香港手机号
PhoneUtil.isTel("010-12345678");                           // 座机
PhoneUtil.isTel400800("400-123-4567");                     // 400/800
PhoneUtil.isPhone("13800138000");                          // 手机或 400/800
```

支持的手机号区域：

| 枚举 | 区域 |
| --- | --- |
| `MobileCountry.CHINA_MAINLAND` | 中国大陆 |
| `MobileCountry.CHINA_HK` | 中国香港 |
| `MobileCountry.CHINA_TW` | 中国台湾 |
| `MobileCountry.CHINA_MO` | 中国澳门 |

注意：`isMobile`、`isTel` 对 `null` 和空串返回 `false`；`isTel400800` 和 `isPhone` 当前对 `null` 不安全，调用前先判空。

## EmailUtil

包名：`com.ronan.common.principal.email.EmailUtil`

```java
EmailUtil.isEmail("demo@example.com");
```

注意：`EmailUtil.isEmail(null)` 当前会触发空指针，调用前先判空。
