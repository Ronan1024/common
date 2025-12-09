package com.ronan.common.utils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author L.J.Ran
 */
public class Operation<T> {

    /**
     * 待操作的值
     */
    private final T value;
    /**
     * 是否执行操作 默认 true
     */
    private boolean execute;

    /**
     * Operation 构造器 初始化 待操作的的参数
     *
     * @param value 待操作的参数
     */
    public Operation(T value) {
        this(value, Boolean.TRUE);
    }

    /**
     * 私有化构造器 不对外暴露
     *
     * @param value   待操作的对象
     * @param execute 是否执行操作
     */
    private Operation(T value, boolean execute) {
        this.value = value;
        this.execute = execute;
    }

    /**
     * 静态方法创建Operation
     *
     * @param value 待操作的对象
     * @param <T>   泛型
     * @return this
     */
    public static <T> Operation<T> of(T value) {
        return new Operation<>(value);
    }

    /**
     * 静态方法创建Operation 并指定为空的操作类型
     *
     * @param value 待操作的对象
     * @param <T>   范型
     * @return 为空的  this
     */
    public static <T> Operation<T> ofByIsEmpty(T value) {
        return new Operation<>(value).isNull();
    }

    /**
     * 静态方法创建Operation 并指定为非空的操作类型
     *
     * @param value 待操作的对象
     * @param <T>   范型
     * @return 为非空的 this
     */
    public static <T> Operation<T> ofByNotEmpty(T value) {
        return new Operation<>(value).notNull();
    }

    private boolean isEmpty() {
        return ObjectUtil.isEmpty(value);
    }

    private boolean notEmpty() {
        return !isEmpty();
    }

    public Operation<T> then(Consumer<? super T> consumer) {
        if (execute) {
            consumer.accept(value);
        }
        return this;
    }

    public Operation<T> then(Predicate<? super T> predicate, Consumer<? super T> consumer) {
        if (predicate.test(value)) {
            consumer.accept(value);
        }
        return this;
    }


    public void finish(Consumer<? super T> consumer) {
        if (execute) {
            consumer.accept(value);
        }
    }

    public void finish(Predicate<? super T> predicate, Consumer<? super T> consumer) {
        if (predicate.test(value)) {
            consumer.accept(value);
        }
    }

    public <R> Operation<R> map(Function<? super T, ? extends R> function) {
        if (execute) {
            return new Operation<>(function.apply(value));
        }
        return new Operation<>(null, false);
    }

    public Operation<T> execute(Predicate<? super T> predicate) {
        if (execute) {
            this.execute = predicate.test(value);
        }
        return this;
    }

    public Operation<T> execute(boolean execute) {
        if (this.execute) {
            this.execute = execute;
        }
        return this;
    }

    public Operation<T> and(Predicate<? super T> predicate) {
        this.execute = execute && predicate.test(value);
        return this;
    }

    /**
     * 对待操作的对象进行判断
     *
     * @return this
     */
    public Operation<T> notNull() {
        this.execute = notEmpty();
        return this;
    }

    /**
     * 对待操作的对象进行判断
     *
     * @return this
     */
    public Operation<T> isNull() {
        this.execute = isEmpty();
        return this;
    }

    /**
     * 获取操作的结果
     *
     * @return 操作的结果
     */
    public T get() {
        return value;
    }

    public <X extends Throwable> void thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (execute) {
            throw exceptionSupplier.get();
        }
    }

    public <X extends Throwable> void elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (!execute) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 当{@code execute} 为 {@code true} 时抛出异常
     *
     * @param message 异常消息
     */
    public void thenThrow(String message) {
        if (execute) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 如果{@code execute} 为 {@code true} 返回当前{@code Operation}
     * 否则返回 由供应商 提供的函数{@code Operation}
     *
     * @param supplier 需要返回的{@code Operation}的供应商
     * @return {@code execute}如果为{@code true}，则返回一个描述这个{@code Operation}值的值，否则由{@code Operation}供应函数生成
     */
    @SuppressWarnings("all")
    public Operation<T> or(Supplier<? extends Operation<? extends T>> supplier) {
        if (execute) {
            return this;
        } else {
            return (Operation<T>) supplier.get();
        }
    }

    public <X extends Throwable> Operation<T> orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (!execute) {
            throw exceptionSupplier.get();
        }
        return this;
    }

    /**
     * 当{@code predicate} 满足条件时 调用{@link #thenThrow(String)} 反之不做任何操作返回当前参数的{@code Operation}
     *
     * @param predicate 是否满足执行条件
     * @param message   当满足条件时执行的异常信息
     * @return 当{@code predicate} 不满足条件时 返回当前参数的{@code Operation}
     */
    public Operation<T> andThrow(Predicate<? super T> predicate, String message) {
        if (predicate.test(value)) {
            thenThrow(message);
        }
        return this;
    }

    public T orElse(T other) {
        if (!execute) {
            return other;
        } else {
            return value;
        }
    }

    public void ifPresentOrElse(Consumer<? super T> action, Consumer<? super T> emptyAction) {
        if (execute) {
            action.accept(value);
        } else {
            emptyAction.accept(value);
        }
    }
}
