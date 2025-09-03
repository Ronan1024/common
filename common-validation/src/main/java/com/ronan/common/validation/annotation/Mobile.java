package com.ronan.common.validation.annotation;

import com.ronan.common.principal.phone.MobileCountry;
import com.ronan.common.validation.handler.MobileValidationHandler;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author L.J.Ran
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MobileValidationHandler.class)
public @interface Mobile {

    boolean required() default true;

    String message() default "手机号码格式错误";

    MobileCountry country() default MobileCountry.CHINA_MAINLAND;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
