package com.example.tp.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;

public class FieldMatchValidator implements ConstraintValidator<FieldMatchMessage, Object> {

    private String firstFName;
    private String secondFName;

    @Override
    public void initialize(final FieldMatchMessage constraintAnnotation) {
        firstFName = constraintAnnotation.first();
        secondFName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final Object firstObj1 = BeanUtils.getProperty(value, firstFName);
            final Object secondObj2 = BeanUtils.getProperty(value, secondFName);
            return firstObj1 == null && secondObj2 == null || firstObj1 != null && firstObj1.equals(secondObj2);
        } catch (final Exception e) {}
        return true;
    }
}
