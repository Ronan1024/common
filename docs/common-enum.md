# common-enum

`common-enum` 提供枚举 code/desc 容器、按 code 反查和前端字典转换。

## 定义枚举

包名：

- `com.ronan.common.enums.IBaseEnum`
- `com.ronan.common.enums.EnumContainer`

```java
public enum Status implements IBaseEnum<Integer> {
    ENABLED(1, "启用"),
    DISABLED(0, "禁用");

    Status(Integer code, String desc) {
        initEnum(code, desc);
    }
}
```

## 使用

```java
Status.ENABLED.code(); // 1
Status.ENABLED.desc(); // "启用"

Status status = EnumContainer.getByCode(Status.class, 1);

List<Map<String, Object>> list = EnumContainer.toList(Status.class);
// [{"code":1,"desc":"启用"}, {"code":0,"desc":"禁用"}]

Map<Integer, String> map = EnumContainer.toMap(Status.class);
// {1="启用", 0="禁用"}
```

注意：当前建议使用 `EnumContainer.getByCode`。`IBaseEnum.getByCode` 的实现会拿 `desc()` 与 `code` 比较，结果可能不符合预期。
