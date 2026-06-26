# common

`common` 是一个 Maven 多模块 Java 工具库，按能力拆分为字符串、数组、对象、反射、JSON、Spring、校验、手机号/邮箱、枚举等模块。

本项目不是可独立启动的服务，主要交付物是各模块 Jar 包，以及用于依赖版本管理的 `common-all` POM。

## 模块

| 模块 | Maven 坐标 | 说明 |
| --- | --- | --- |
| `common-string` | `com.ronan.common.string:common-string` | 字符串判空、字符判断、占位符格式化 |
| `common-array` | `com.ronan.common:common-array` | 对象数组和基本类型数组判空 |
| `common-utils` | `com.ronan.common.utils:common-utils` | 对象判空、断言、字段名转换、基础类型、Pair、Stream 辅助 |
| `common-json` | `com.ronan.common.json:common-json` | Jackson ObjectMapper、JSON 序列化/反序列化、时间格式处理 |
| `common-principal` | `com.ronan.common.principal:common-principal` | 手机号、座机、400/800 电话、邮箱校验 |
| `common-validation` | `com.ronan.common.validation:common-validation` | Jakarta Validation 注解：`@Mobile`、`@Email` |
| `common-enum` | `com.ronan.common.enums:common-enum` | 枚举 code/desc 容器、按 code 反查、字典转换 |
| `common-reflect` | `com.ronan.common:common-reflect` | 字段反射辅助 |
| `common-spring` | `com.ronan.common.spring:common-spring` | Spring Bean 获取、环境变量、SpEL、事件发布 |
| `common-all` | `com.ronan.common:common-all` | 依赖管理 POM，不产出工具类 |

## 环境

| 项目 | 要求 |
| --- | --- |
| JDK | 父项目默认 Java 8；`common-spring`、`common-validation`、`common-all` 使用 Java 17 |
| Maven | Maven 3.x |
| 编码 | UTF-8 |

## 引入方式

按需引入单个模块：

```xml
<dependency>
    <groupId>com.ronan.common.json</groupId>
    <artifactId>common-json</artifactId>
    <version>1.0.1</version>
</dependency>
```

使用 `common-all` 统一管理部分模块版本：

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

然后按需声明依赖：

```xml
<dependency>
    <groupId>com.ronan.common.string</groupId>
    <artifactId>common-string</artifactId>
</dependency>
```

注意：`common-all` 当前只管理 `common-validation`、`common-string`、`common-spring`、`common-utils`、`common-enum`、`common-json`。`common-array`、`common-reflect`、`common-principal` 仍需显式声明版本。

## 快速示例

字符串格式化：

```java
String text = StringFormatter.format("hello {}", "ronan");
String named = StringFormatter.namedFormat("hello {name}", Map.of("name", "ronan"));
String indexed = StringFormatter.advancedFormat("{0}-{name:default}", "A", "name", "B");
```

字符串判空：

```java
boolean blank = CharSequenceUtil.isBlank(" \t\n");
String value = CharSequenceUtil.blankToDefault(input, "default");
```

数组和对象判空：

```java
boolean emptyArray = ArrayUtil.isEmpty(new String[0]);
boolean emptyObject = ObjectUtil.isEmpty(Collections.emptyMap());
```

JSON：

```java
String json = JsonUtil.toJson(user);
User user = JsonUtil.parse(json, User.class);
List<User> users = JsonUtil.parseList(jsonArray, User.class);
```

手机号和邮箱：

```java
boolean mobile = PhoneUtil.isMobile("13800138000");
boolean hongKongMobile = PhoneUtil.isMobile("51004810", MobileCountry.CHINA_HK);
boolean email = EmailUtil.isEmail("demo@example.com");
```

枚举字典：

```java
public enum Status implements IBaseEnum<Integer> {
    ENABLED(1, "启用"),
    DISABLED(0, "禁用");

    Status(Integer code, String desc) {
        initEnum(code, desc);
    }
}

Status status = EnumContainer.getByCode(Status.class, 1);
Map<Integer, String> map = EnumContainer.toMap(Status.class);
```

Spring：

```java
UserService userService = SpringApplicationUtil.getBean(UserService.class);
String appName = SpringApplicationUtil.getApplicationName();
```

## 文档

- [工具使用指南](docs/tool-usage.md)
- 模块文档：[string](docs/common-string.md)、[array](docs/common-array.md)、[utils](docs/common-utils.md)、[json](docs/common-json.md)、[principal](docs/common-principal.md)、[validation](docs/common-validation.md)、[enum](docs/common-enum.md)、[reflect](docs/common-reflect.md)、[spring](docs/common-spring.md)
- [项目操作文档](OPERATION.md)

## 构建

在项目根目录执行：

```bash
mvn test
mvn package
mvn install
```

只构建单个模块及其依赖：

```bash
mvn -pl common-json -am test
```

## 发布

父 `pom.xml` 已配置私服发布仓库：

```xml
<distributionManagement>
    <repository>
        <id>baoxin</id>
        <url>http://10.125.123.5:18080/repository/baoxin-hosted/</url>
    </repository>
</distributionManagement>
```

发布前确保本机 Maven `settings.xml` 存在同名 `server`，不要把账号、密码、Token 写入仓库。

## 已知限制

- `common-reflect` 当前只建议使用 `FieldUtil.getFields` 和 `FieldUtil.getFieldsDirectly`，`FieldUtil.getField` 尚未实现。
- `Assert` 的触发条件与常见断言库命名不同，使用前先看工具使用指南。
- `@Email` 的 `required` 分支当前存在实现风险，生产使用前建议先修复并补测试。
