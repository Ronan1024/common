# common-validation

`common-validation` 提供 Jakarta Validation 注解和校验分组。

## @Mobile

包名：`com.ronan.common.validation.annotation.Mobile`

```java
public class UserRequest {
    @Mobile(message = "手机号格式错误")
    private String mobile;

    @Mobile(required = false, country = MobileCountry.CHINA_HK)
    private String hkMobile;
}
```

参数：

| 参数 | 默认值 | 说明 |
| --- | --- | --- |
| `required` | `true` | 是否必填 |
| `message` | `"手机号码格式错误"` | 校验失败消息 |
| `country` | `MobileCountry.CHINA_MAINLAND` | 手机号区域 |
| `groups` | `{}` | 校验分组 |
| `payload` | `{}` | Jakarta Validation payload |

当前限制：`@Mobile(required = false)` 对空值仍会返回校验失败，不等同于“可选且为空时通过”。

## @Email

包名：`com.ronan.common.validation.annotation.Email`

```java
public class UserRequest {
    @Email(message = "邮箱格式错误")
    private String email;
}
```

当前限制：`@Email` 的 `required` 判断当前存在实现问题，非空值可能抛“邮箱不能为空”，空值可能继续进入 `EmailUtil` 并触发空指针。生产使用前建议先修复。

## 校验分组

内置分组：

- `Add`
- `Update`
- `Delete`
- `Select`

```java
public class UserRequest {
    @Mobile(groups = Add.class)
    private String mobile;
}
```
