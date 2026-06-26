# common-array

`common-array` 提供对象数组和基本类型数组判空工具。

## ArrayUtil

包名：`com.ronan.common.array.ArrayUtil`

```java
ArrayUtil.isEmpty(new String[0]);     // true
ArrayUtil.isNotEmpty(new int[]{1});   // true
ArrayUtil.isEmpty((long[]) null);     // true
```

支持对象数组：

```java
String[] values = {"a", "b"};
ArrayUtil.isNotEmpty(values); // true
```

支持的基本类型数组：

- `long[]`
- `int[]`
- `short[]`
- `char[]`
- `byte[]`
- `double[]`
- `float[]`
- `boolean[]`

说明：空数组和 `null` 都视为空；非空数组不检查元素内容。
