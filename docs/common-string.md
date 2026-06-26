# common-string

`common-string` 提供字符串判空、字符判断和占位符格式化工具。

## CharSequenceUtil

包名：`com.ronan.common.string.chars.CharSequenceUtil`

用于处理 `CharSequence` 的空值、空白值和默认值。

| 方法 | 说明 |
| --- | --- |
| `isBlank(CharSequence)` | `null`、空串、空白字符返回 `true` |
| `isNotBlank(CharSequence)` | `isBlank` 取反 |
| `hasBlank(CharSequence...)` | 任意一个为空白返回 `true`；空数组也返回 `true` |
| `isAllBlank(CharSequence...)` | 全部为空白返回 `true`；空数组也返回 `true` |
| `isEmpty(CharSequence)` | `null` 或长度为 0 返回 `true`，不判断空格 |
| `notEmpty(CharSequence)` | `isEmpty` 取反 |
| `hasEmpty(CharSequence...)` | 任意一个为空串返回 `true`；空数组也返回 `true` |
| `isAllEmpty(CharSequence...)` | 全部为空串返回 `true` |
| `nullToEmpty(CharSequence)` | `null` 转为空串 |
| `nullToDefault(CharSequence, String)` | `null` 转为默认值 |
| `emptyToDefault(CharSequence, String)` | `null` 或空串转为默认值 |
| `blankToDefault(CharSequence, String)` | `null`、空串、空白串转为默认值 |
| `emptyToNull(CharSequence)` | `null` 或空串转为 `null` |
| `isNullOrUndefined(CharSequence)` | 判断 `null`、`"null"`、`"undefined"` |
| `isEmptyOrUndefined(CharSequence)` | 判断 `null`、空串、`"null"`、`"undefined"` |
| `isBlankOrUndefined(CharSequence)` | 判断 `null`、空白串、`"null"`、`"undefined"` |

```java
CharSequenceUtil.isBlank(" \t\n");         // true
CharSequenceUtil.isEmpty(" \t\n");         // false
CharSequenceUtil.hasBlank("id", " ");      // true
CharSequenceUtil.blankToDefault(" ", "0"); // "0"
```

建议：新代码优先使用 `CharSequenceUtil`。当前 `StringUtil.isEmpty(CharSequence)` 行为与方法名不一致，不适合作为通用判空入口。

## StringFormatter

包名：`com.ronan.common.string.text.StringFormatter`

| 方法 | 说明 |
| --- | --- |
| `format(String, Object...)` | 使用 `{}` 顺序占位；参数不足时保留原占位符 |
| `namedFormat(String, Map<?, ?>)` | 使用 `{name}` 命名占位 |
| `advancedFormat(String, Object...)` | 支持索引、命名参数和默认值 |

```java
StringFormatter.format("hello {}", "ronan");
// hello ronan

StringFormatter.format("hello {{}} {}", "x");
// hello {} x

StringFormatter.namedFormat("hello {name}", Map.of("name", "ronan"));
// hello ronan

StringFormatter.advancedFormat("{0}-{name:default}-{missing:N/A}", "A", "name", "B");
// A-B-N/A
```

注意：

- `format(null, ...)` 返回 `null`。
- `advancedFormat(null, ...)` 或模板为空串返回 `null`。
- `advancedFormat` 的命名参数按成对参数传入，例如 `"name", "ronan"`。

## CharUtil

包名：`com.ronan.common.string.chars.CharUtil`

```java
CharUtil.isAscii('A');              // true
CharUtil.isAsciiPrintable('\n');    // false
CharUtil.isLetter('a');             // true
CharUtil.isNumber('9');             // true
CharUtil.isHexChar('F');            // true
CharUtil.isBlankChar('\u3000');     // true
CharUtil.equals('A', 'a', true);    // true
CharUtil.toCloseChar('A');          // 'Ⓐ'
CharUtil.toCloseByNumber(12);       // '⑫'
```

`toCloseByNumber` 只支持 1 到 20，大于 20 会抛 `IllegalArgumentException`。
