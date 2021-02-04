package com.zigix.chatapp.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String first;
    private String second;

    public void initialize(FieldMatch constraint) {
        this.first = constraint.first();
        this.second = constraint.second();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {

        final Object firstObject = new BeanWrapperImpl(value).getPropertyValue(first);
        final Object secondObject = new BeanWrapperImpl(value).getPropertyValue(second);

        return (firstObject == null && secondObject == null) ||
                (firstObject != null && firstObject.equals(secondObject));
    }
}
