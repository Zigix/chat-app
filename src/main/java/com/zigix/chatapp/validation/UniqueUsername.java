package com.zigix.chatapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueUsername {

    String message() default "This username already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
