# common 工具使用指南

本文是工具文档总入口。具体 API、示例和已知限制按 Maven 模块拆分，避免单文件过长。

## 依赖引入

按需引入模块：

```xml
<dependency>
    <groupId>com.ronan.common.string</groupId>
    <artifactId>common-string</artifactId>
    <version>1.0.1</version>
</dependency>
```

使用 `common-all` 做版本管理：

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.ronan.common</groupId>
            <artifactId>common-all</artifactId>
            <version>1.0.1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

`common-all` 不是聚合 Jar，只用于 `dependencyManagement`。当前未覆盖 `common-array`、`common-reflect`、`common-principal`。

## 模块文档

| 模块 | 文档 | 主要内容 |
| --- | --- | --- |
| `common-string` | [common-string.md](common-string.md) | `CharSequenceUtil`、`StringFormatter`、`CharUtil` |
| `common-array` | [common-array.md](common-array.md) | 对象数组和基本类型数组判空 |
| `common-utils` | [common-utils.md](common-utils.md) | `ObjectUtil`、`Assert`、`Operation`、`ColumnUtil`、`BasicType`、`Pair`、Stream |
| `common-json` | [common-json.md](common-json.md) | `JsonUtil`、`JacksonObjectMapper`、时间格式 |
| `common-principal` | [common-principal.md](common-principal.md) | `PhoneUtil`、`EmailUtil` |
| `common-validation` | [common-validation.md](common-validation.md) | `@Mobile`、`@Email`、校验分组 |
| `common-enum` | [common-enum.md](common-enum.md) | `IBaseEnum`、`EnumContainer` |
| `common-reflect` | [common-reflect.md](common-reflect.md) | `FieldUtil` |
| `common-spring` | [common-spring.md](common-spring.md) | `SpringApplicationUtil`、`SpringExpressionUtils` |

## 已知限制总览

| 位置 | 当前状态 | 建议 |
| --- | --- | --- |
| `Assert` | 多个方法命名和触发条件与常见断言库相反 | 见 [common-utils.md](common-utils.md) |
| `ColumnUtil.convert` | 受 `Assert.notNull` 当前语义影响，非空参数可能抛异常 | 暂只使用 `ColumnUtil.column`，或先修复 |
| `FieldUtil.getField` | 尚未实现，固定返回 `null` | 见 [common-reflect.md](common-reflect.md) |
| `@Email` | `required` 判断存在实现风险 | 见 [common-validation.md](common-validation.md) |
| `EmailUtil.isEmail` | 对 `null` 不安全 | 调用前判空 |
| `PhoneUtil.isTel400800/isPhone` | 对 `null` 不安全 | 调用前判空 |
| `IBaseEnum.getByCode` | 当前实现比较 `desc()` 和 `code` | 使用 `EnumContainer.getByCode` |
