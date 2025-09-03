package com.ronan.common.validation.annotation;


import com.ronan.common.validation.handler.EmailValidationHandler;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author L.J.Ran
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailValidationHandler.class)
public @interface Email {
    boolean required() default true;

    String message() default "邮箱格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
