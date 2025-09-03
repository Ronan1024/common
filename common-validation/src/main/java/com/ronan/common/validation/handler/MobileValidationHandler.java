package com.ronan.common.validation.handler;

import com.ronan.common.principal.phone.MobileCountry;
import com.ronan.common.principal.phone.PhoneUtil;
import com.ronan.common.validation.annotation.Mobile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import org.springframework.util.ObjectUtils;

/**
 * @author L.J.Ran
 */
public class MobileValidationHandler implements ConstraintValidator<Mobile, String> {
    private boolean required = false;
    private MobileCountry mobileCountry;

    @Override
    public void initialize(Mobile constraintAnnotation) {
        required = constraintAnnotation.required();
        mobileCountry = constraintAnnotation.country();
    }

    @Override
    public boolean isValid(String mobile, ConstraintValidatorContext constraintValidatorContext) {
        if (required && ObjectUtils.isEmpty(mobile)) {
            throw new ValidationException("手机号不能为空");
        }
        return PhoneUtil.isMobile(mobile, mobileCountry);
    }
}
