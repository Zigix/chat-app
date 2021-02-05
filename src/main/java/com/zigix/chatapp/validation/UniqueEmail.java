package com.zigix.chatapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueEmail {

    String message() default " this email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
