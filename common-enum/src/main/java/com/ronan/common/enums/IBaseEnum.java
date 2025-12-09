package com.ronan.common.enums;

import java.util.stream.Stream;

/**
 * @program: common
 * @description:
 * @author: L.J.Ran
 * @create: 2025/12/9
 */
public interface IBaseEnum<C> {

    default void initEnum(C code, String msg) {
        EnumContainer.put(this, code, msg);
    }

    default C code() {
        return EnumContainer.getEnum(this).code();
    }

    default String desc() {
        return EnumContainer.getEnum(this).desc();
    }


    @SuppressWarnings("unchecked")
    static <T, R extends IBaseEnum<T>> R getByCode(Class<? extends IBaseEnum<T>> clazz, T code) {
        return Stream.of(clazz.getEnumConstants())
                .filter(tiBaseEnum -> tiBaseEnum.desc().equals(code))
                .map(v -> (R) v)
                .findAny()
                .orElse(null);
    }


}