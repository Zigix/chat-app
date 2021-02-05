package com.zigix.chatapp.validation;

import com.zigix.chatapp.AppUserService;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final AppUserService appUserService;

    public void initialize(UniqueEmail constraint) {
    }

    public boolean isValid(String email, ConstraintValidatorContext context) {
        return appUserService.findByEmail(email).isEmpty();
    }
}
