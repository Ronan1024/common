# common-spring

`common-spring` 提供 Spring Bean 获取、环境变量读取、SpEL 解析和事件发布。

## SpringApplicationUtil

包名：`com.ronan.common.spring.core.SpringApplicationUtil`

`common-spring` 提供自动配置文件，Spring Boot 环境中可自动注入上下文。

```java
ApplicationContext context = SpringApplicationUtil.getApplicationContext();

UserService userService = SpringApplicationUtil.getBean(UserService.class);
Map<String, Handler> handlers = SpringApplicationUtil.getBeansOfType(Handler.class);

String appName = SpringApplicationUtil.getApplicationName();
String profile = SpringApplicationUtil.getActiveProfile();
String value = SpringApplicationUtil.getProperty("app.key", "default");

SpringApplicationUtil.publishEvent(new UserCreatedEvent(userId));
```

动态注册和注销 Bean：

```java
SpringApplicationUtil.registerBean("customService", customService);
SpringApplicationUtil.unregisterBean("customService");
```

注意：这些方法依赖 Spring 容器已初始化。非 Spring 环境调用 `getBeanFactory` 会抛 `IllegalArgumentException`。

## SpringExpressionUtils

包名：`com.ronan.common.spring.core.SpringExpressionUtils`

从切面参数解析 SpEL：

```java
Object userId = SpringExpressionUtils.parseExpression(joinPoint, "#request.userId");

Map<String, Object> values = SpringExpressionUtils.parseExpressions(
        joinPoint,
        List.of("#request.userId", "#request.name")
);
```

从 BeanFactory 解析 SpEL：

```java
String name = SpringExpressionUtils.parseExpression(
        "@userService.getName(#userId)",
        Map.of("userId", 1L),
        String.class
);
```
