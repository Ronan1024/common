package com.ronan.common.utils;

import com.ronan.common.string.StringFormatter;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @author L.J.Ran
 * @version 1.0
 */
public class Assert {

    /**
     * 断言是否为真，如果为 {@code true} 抛出给定的异常<br>
     *
     * <pre class="code">
     * Assert.isTrue(1==1, IllegalArgumentException::new);
     * </pre>
     *
     * @param expression 布尔值
     * @param msg        异常信息
     */
    public static void isTrue(boolean expression, String msg) {
        if (expression) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void isTrue(boolean expression, String msg, Object... args) {
        if (expression) {
            throw new IllegalArgumentException(StringFormatter.format(msg, args));
        }
    }


    /**
     * 断言是否为真，如果为 {@code false} 抛出给定的异常<br>
     *
     * <pre class="code">
     * Assert.isTrue(1!=1, IllegalArgumentException::new);
     * </pre>
     *
     * @param <X>        异常类型
     * @param expression 布尔值
     * @param supplier   指定断言不通过时抛出的异常
     * @throws X if expression is {@code false}
     */
    public static <X extends Throwable> void isFalse(boolean expression, Supplier<? extends X> supplier) throws X {
        if (!expression) {
            throw supplier.get();
        }
    }

    /**
     * 断言是否为真，如果为 {@code true} 抛出给定的异常<br>
     *
     * <pre class="code">
     * Assert.isTrue(1==1, IllegalArgumentException::new);
     * </pre>
     *
     * @param <X>        异常类型
     * @param expression 布尔值
     * @param supplier   指定断言不通过时抛出的异常
     * @throws X if expression is {@code true}
     */
    public static <X extends Throwable> void isTrue(boolean expression, Supplier<? extends X> supplier) throws X {
        if (expression) {
            throw supplier.get();
        }
    }

    /**
     * 断言是否为真，如果为 {@code false} 抛出给定的异常<br>
     *
     * <pre class="code">
     * Assert.isTrue(1 !=1, IllegalArgumentException::new);
     * </pre>
     *
     * @param expression 布尔值
     * @param message    异常信息
     */
    public static void isFalse(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言对象不为{@code null} 抛出指定类型异常
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     *  Assert.notNull("",IllegalArgumentException::new);
     *  Assert.notNull(null,IllegalArgumentException::new);
     *  Assert.notNull(new ArrayList(),IllegalArgumentException::new);
     * </pre>
     *
     * @param message 异常信息
     * @param object  待检测的参数
     */
    public static void isNull(Object object, String message) {
        Assert.isTrue(ObjectUtil.isEmpty(object), message);
    }

    /**
     * 断言对象不为{@code null} 抛出指定类型异常
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     *  Assert.notNull("1",IllegalArgumentException::new);
     * </pre>
     *
     * @param message 异常类型
     * @param object  待检测的参数
     */

    public static void notNull(Object object, String message) {
        Assert.isTrue(!ObjectUtil.isEmpty(object), message);
    }

    /**
     * 断言对象是否为{@code null} ，如果为{@code null} 抛出指定类型异常
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     *  Assert.isNull(Boolean.TRUE,"xxx", "",""); 会抛出异常
     *  Assert.isNull(Boolean.TRUE,"xxx", "1",""); 不会抛出异常
     *  Assert.isNull(Boolean.FALSE,"xxx", "",""); 会抛出异常
     *  Assert.isNull(Boolean.FALSE,"xxx", "1",""); 会抛出异常
     * </pre>
     *
     * @param andOrCircuit 全部为空 true 存在空 false
     * @param message      异常信息
     * @param args         待检测的参数
     */
    public static void isNull(Boolean andOrCircuit, String message, Object... args) {
        long count = Arrays.stream(args).filter(ObjectUtil::isEmpty).count();
        Assert.isTrue(count == args.length && andOrCircuit, message);
        Assert.isTrue(count != args.length && !andOrCircuit, message);
    }


    /**
     * 断言对象不为{@code null} 抛出指定类型异常
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     *  Assert.notNull(Boolean.TRUE,"xxx", "1","1"); 会抛出异常
     *  Assert.notNull(Boolean.TRUE,"xxx", "1",""); 不会抛出异常
     *  Assert.notNull(Boolean.FALSE,"xxx", "1",""); 会抛出异常
     *  Assert.notNull(Boolean.FALSE,"xxx", "1","1"); 会抛出异常
     * </pre>
     *
     * @param andOrCircuit 判断操作类型 全部非空 true 存在非空 false
     * @param args         待检测的参数
     * @param message      异常信息
     */
    public static void notNull(Boolean andOrCircuit, String message, Object... args) {
        long count = Arrays.stream(args).filter(ObjectUtil::notEmpty).count();
        Assert.isTrue(count == args.length && andOrCircuit, message);
        Assert.isTrue(count > 0 && count != args.length && !andOrCircuit, message);
    }

    /**
     * 断言对象不为{@code null} 抛出指定类型异常
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     *  Assert.notNull("",IllegalArgumentException::new);
     *  Assert.notNull(null,IllegalArgumentException::new);
     *  Assert.notNull(new ArrayList(),IllegalArgumentException::new);
     * </pre>
     *
     * @param <X>      异常类型
     * @param object   待检测的参数
     * @param supplier 指定断言不通过时抛出的异常
     * @throws X if the object is  {@code null}
     */
    public static <X extends Throwable> void isNull(Object object, Supplier<? extends X> supplier) throws X {
        Assert.isTrue(ObjectUtil.isEmpty(object), supplier);
    }

    /**
     * 断言对象不为{@code null} 抛出指定类型异常
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     *  Assert.notNull("1",IllegalArgumentException::new);
     * </pre>
     *
     * @param <X>      异常类型
     * @param object   待检测的参数
     * @param supplier 指定断言不通过时抛出的异常
     * @throws X if the object is not {@code null}
     */
    public static <X extends Throwable> void notNull(Object object, Supplier<? extends X> supplier) throws X {
        Assert.isTrue(!ObjectUtil.isEmpty(object), supplier);
    }


    /**
     * 断言对象是否为{@code null} ，如果为{@code null} 抛出指定类型异常
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     *  Assert.isNull(Boolean.TRUE,IllegalArgumentException::new, "",""); 会抛出异常
     *  Assert.isNull(Boolean.TRUE,IllegalArgumentException::new, "1",""); 不会抛出异常
     *  Assert.isNull(Boolean.FALSE,IllegalArgumentException::new, "",""); 会抛出异常
     *  Assert.isNull(Boolean.FALSE,IllegalArgumentException::new, "1",""); 会抛出异常
     * </pre>
     *
     * @param andOrCircuit 全部为空 true 存在空 false
     * @param <X>          异常类型
     * @param args         待检测的参数
     * @param supplier     指定断言不通过时抛出的异常
     * @throws X if the object is  {@code null}
     */
    public static <X extends Throwable> void isNull(Boolean andOrCircuit, Supplier<? extends X> supplier, Object... args) throws X {
        long count = Arrays.stream(args).filter(ObjectUtil::isEmpty).count();
        Assert.isTrue(count == args.length && andOrCircuit, supplier);
        Assert.isTrue(count > 0 && !andOrCircuit, supplier);
    }

    /**
     * 断言对象不为{@code null} 抛出指定类型异常
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     *  Assert.notNull(Boolean.TRUE,IllegalArgumentException::new, "1","1"); 会抛出异常
     *  Assert.notNull(Boolean.TRUE,IllegalArgumentException::new, "1",""); 不会抛出异常
     *  Assert.notNull(Boolean.FALSE,IllegalArgumentException::new, "1",""); 会抛出异常
     *  Assert.notNull(Boolean.FALSE,IllegalArgumentException::new, "1","1"); 会抛出异常
     * </pre>
     *
     * @param andOrCircuit 判断操作类型 全部非空 true 存在非空 false
     * @param <X>          异常类型
     * @param args         待检测的参数
     * @param supplier     指定断言不通过时抛出的异常
     * @throws X if the object is not {@code null}
     */
    public static <X extends Throwable> void notNull(Boolean andOrCircuit, Supplier<? extends X> supplier, Object... args) throws X {
        long count = Arrays.stream(args).filter(ObjectUtil::notEmpty).count();
        Assert.isTrue(count == args.length && andOrCircuit, supplier);
        Assert.isTrue(count > 0 && !andOrCircuit, supplier);
    }
}
