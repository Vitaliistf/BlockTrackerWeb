package org.vitaliistf.blocktracker.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CryptocurrencyValidator.class)
public @interface ValidCryptocurrency {
    String message() default "This cryptocurrency is not supported.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

