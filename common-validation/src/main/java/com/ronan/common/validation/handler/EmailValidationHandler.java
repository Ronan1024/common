package com.ronan.common.validation.handler;

import com.ronan.common.principal.email.EmailUtil;
import com.ronan.common.validation.annotation.Email;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import org.springframework.util.ObjectUtils;

/**
 * @author L.J.Ran
 */
public class EmailValidationHandler implements ConstraintValidator<Email, String> {
    private boolean required = false;


    @Override
    public void initialize(Email constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (required && (!ObjectUtils.isEmpty(email))) {
            throw new ValidationException("邮箱不能为空");
        }
        return EmailUtil.isEmail(email);
    }
}
