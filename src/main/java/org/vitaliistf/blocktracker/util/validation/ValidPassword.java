package org.vitaliistf.blocktracker.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password should have minimum 8 characters in length and contain at least one uppercase " +
            "English letter, at least one lowercase English letter and at least one digit.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}