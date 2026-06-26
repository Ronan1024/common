# common-json

`common-json` 基于 Jackson，提供 JSON 序列化、反序列化和时间格式处理。

## JsonUtil

包名：`com.ronan.common.json.JsonUtil`

默认 `ObjectMapper`：`JacksonObjectMapper`。

内置配置：

- 日期不写成时间戳。
- 忽略未知字段。
- 允许单引号、未转义控制字符、反斜杠转义任意字符。
- `Long`、`long`、`BigInteger`、`BigDecimal` 序列化为字符串。
- 支持 `LocalDateTime`、`LocalDate`、`LocalTime` 的固定格式序列化。

序列化：

```java
String json = JsonUtil.toJson(user);
String pretty = JsonUtil.toJsonPretty(user);
byte[] bytes = JsonUtil.toJsonAsBytes(user);
```

反序列化：

```java
User user = JsonUtil.parse(json, User.class);

List<User> users = JsonUtil.parse(json, new TypeReference<List<User>>() {});

List<User> list = JsonUtil.parseList(jsonArray, User.class);

Map<String, Object> map = JsonUtil.toMap(json);

JsonNode node = JsonUtil.readTree(json);
```

替换全局 `ObjectMapper`：

```java
ObjectMapper mapper = new JacksonObjectMapper();
JsonUtil.init(mapper);
```

## LocalDateTime 输入格式

支持：

- `yyyy-MM-dd`
- `yyyy年MM月dd日`
- `yyyy/MM/dd`
- `yyyy-MM-dd HH:mm:ss`
- `yyyy年MM月dd日HH时mm分ss秒`
- `yyyy/MM/dd HH:mm:ss`
- ISO 本地时间，例如 `2025-01-01T12:00:00`
- 毫秒时间戳，按 UTC+8 转为 `LocalDateTime`
- 数组：`[year, month, day, hour, minute, second, nano]`

## 注意事项

- `parse(String, Class<T>)` 解析失败会打印堆栈并返回 `null`。
- `parse(String, TypeReference<T>)`、`readTree(...)` 等方法解析失败会抛 `IllegalArgumentException`。
- `parseList` 解析失败返回空集合。
- `parseArray` 已标记 `@Deprecated`，新代码使用 `parseList`。
