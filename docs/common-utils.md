# common-utils

`common-utils` 提供对象判空、断言、字段名转换、基础类型、Pair 和 Stream 辅助工具。

## ObjectUtil

包名：`com.ronan.common.utils.ObjectUtil`

支持判断：

- `null`
- `CharSequence`
- 数组
- `Collection`
- `Map`

```java
ObjectUtil.isEmpty(null);                    // true
ObjectUtil.isEmpty("");                      // true
ObjectUtil.isEmpty(" ");                     // false
ObjectUtil.isEmpty(Collections.emptyList()); // true
ObjectUtil.notEmpty(Map.of("k", "v"));       // true
```

## Assert

包名：`com.ronan.common.utils.Assert`

当前 `Assert` 与常见断言库的命名直觉不同。使用时按触发条件理解：

| 方法 | 抛异常条件 |
| --- | --- |
| `isTrue(boolean, String)` | 参数为 `true` |
| `isTrue(boolean, String, Object...)` | 参数为 `true`，消息支持 `{}` |
| `isTrue(boolean, Supplier)` | 参数为 `true` |
| `isFalse(boolean, String)` | 参数为 `false` |
| `isFalse(boolean, Supplier)` | 参数为 `false` |
| `isNull(Object, String)` | 对象为空 |
| `notNull(Object, String)` | 对象非空 |

```java
Assert.isFalse(userId > 0, "userId must be positive");
Assert.isTrue(ObjectUtil.isEmpty(name), "name must not be empty");
```

新代码更建议直接写清条件，或先修复 `Assert` 语义后再扩大使用。

## Operation

包名：`com.ronan.common.utils.Operation`

用于链式执行条件操作。

```java
Operation.of(user)
        .notNull()
        .then(u -> log.info("user={}", u))
        .map(User::getName)
        .execute(CharSequenceUtil::isNotBlank)
        .finish(name -> log.info("name={}", name));
```

| 方法 | 说明 |
| --- | --- |
| `of(T)` | 创建操作对象，默认可执行 |
| `ofByIsEmpty(T)` | 仅当值为空时执行 |
| `ofByNotEmpty(T)` | 仅当值非空时执行 |
| `then(Consumer)` | 当前可执行时消费值 |
| `map(Function)` | 当前可执行时映射值 |
| `execute(Predicate)` | 追加执行条件 |
| `orElse(T)` | 不可执行时返回默认值 |
| `thenThrow(...)` | 当前可执行时抛异常 |
| `elseThrow(...)` | 当前不可执行时抛异常 |

## ColumnUtil

包名：`com.ronan.common.utils.ColumnUtil`

用于从 getter/setter 方法引用中取字段名。

```java
String name = ColumnUtil.column(User::getUserName);
// userName
```

`convert(String, String)` 和 `convert(String)` 设计上用于大小写转换：

```java
ColumnUtil.convert("userName", "_"); // 设计预期：user_name
ColumnUtil.convert("UserName");      // 设计预期：userName
```

当前这两个方法内部调用了 `Assert.notNull`，会受 `Assert` 语义影响，非空参数也可能抛异常。生产使用前建议先修复并补测试。

## BasicType

包名：`com.ronan.common.lang.BasicType`

```java
BasicType.wrap(int.class);              // Integer.class
BasicType.unWrap(Integer.class);        // int.class
BasicType.isPrimitive(Integer.class);   // true
BasicType.isWrapperType(Integer.class); // true
```

## Pair

包名：`com.ronan.common.lang.Pair`

```java
Pair<String, Integer> pair = Pair.of("age", 18);
pair.getKey();   // "age"
pair.getValue(); // 18
```

## StreamBuild 与 Either

包名：

- `com.ronan.common.stream.StreamBuild`
- `com.ronan.common.stream.Either`

`StreamBuild` 是对 Java Stream 的轻量包装：

```java
List<String> names = StreamBuild.of(users)
        .filter(User::isEnabled)
        .map(User::getName)
        .toDistinctList();

String joined = StreamBuild.of(names).joining(",");
```

`Either` 用于把会抛异常的函数转成可在 Stream 中处理的结果：

```java
List<Either> results = values.stream()
        .map(Either.lift(this::parseValue))
        .collect(Collectors.toList());

List<Object> errors = results.stream()
        .filter(Either::isLeft)
        .map(e -> e.getLeft().orElse(null))
        .collect(Collectors.toList());
```
