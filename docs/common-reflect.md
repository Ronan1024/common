# common-reflect

`common-reflect` 当前提供字段反射辅助。

## FieldUtil

包名：`com.ronan.common.reflect.FieldUtil`

```java
Field[] allFields = FieldUtil.getFields(User.class);
Field[] ownFields = FieldUtil.getFieldsDirectly(User.class, false);
Field[] inheritedFields = FieldUtil.getFieldsDirectly(User.class, true);
```

说明：

- `getFields` 会缓存结果，并包含父类字段。
- `getFieldsDirectly(clazz, false)` 只取当前类声明字段。
- `getFieldsDirectly(clazz, true)` 会沿父类向上收集字段。
- `getField(Class<?>, String)` 当前返回 `null`，尚未实现。
