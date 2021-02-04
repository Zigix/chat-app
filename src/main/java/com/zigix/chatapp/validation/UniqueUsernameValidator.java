package com.zigix.chatapp.validation;

import com.zigix.chatapp.AppUserService;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

   private final AppUserService appUserService;

   @Override
   public void initialize(UniqueUsername constraint) {
   }

   @Override
   public boolean isValid(String username, ConstraintValidatorContext context) {
      return appUserService.findByUsername(username).isEmpty();
   }
}
