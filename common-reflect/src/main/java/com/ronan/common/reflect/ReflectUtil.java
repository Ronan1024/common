package com.ronan.common.reflect;

import java.lang.reflect.Method;

/**
 * @program: common
 * @description:
 * @author: L.J.Ran
 * @create: 2025/8/29
 */
public class ReflectUtil {



//
//    /**
//     * 执行对象中指定方法
//     * 如果需要传递的参数为null,请使用NullWrapperBean来传递,不然会丢失类型信息
//     *
//     * @param <T>        返回对象类型
//     * @param obj        方法所在对象
//     * @param methodName 方法名
//     * @param args       参数列表
//     * @return 执行结果
//     * @throws UtilException IllegalAccessException等异常包装
//     * @see NullWrapperBean
//     * @since 3.1.2
//     */
//    public static <T> T invoke(Object obj, String methodName, Object... args) throws UtilException {
//        Assert.notNull(obj, "Object to get method must be not null!");
//        Assert.notBlank(methodName, "Method name must be not blank!");
//
//        final Method method = getMethodOfObj(obj, methodName, args);
//        if (null == method) {
//            throw new UtilException("No such method: [{}] from [{}]", methodName, obj.getClass());
//        }
//        return invoke(obj, method, args);
//    }
}
